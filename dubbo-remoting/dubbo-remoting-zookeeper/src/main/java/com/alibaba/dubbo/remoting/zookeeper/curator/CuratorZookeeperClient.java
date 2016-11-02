package com.alibaba.dubbo.remoting.zookeeper.curator;

import java.util.List;

import com.alibaba.dubbo.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.zookeeper.ChildListener;
import com.alibaba.dubbo.remoting.zookeeper.StateListener;
import com.alibaba.dubbo.remoting.zookeeper.support.AbstractZookeeperClient;

public class CuratorZookeeperClient extends
		AbstractZookeeperClient<CuratorWatcher> {

	private final CuratorFramework client;

	public CuratorZookeeperClient(URL url) {
		super(url);
		Builder builder = CuratorFrameworkFactory.builder()
				.connectString(url.getBackupAddress())
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
				.connectionTimeoutMs(url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_REGISTRY_CONNECT_TIMEOUT))
                .sessionTimeoutMs(url.getParameter(Constants.SESSION_TIMEOUT_KEY, Constants.DEFAULT_SESSION_TIMEOUT));
		String authority = url.getAuthority();
		if (authority != null && authority.length() > 0) {
			builder = builder.authorization("digest", authority.getBytes());
		}
		client = builder.build();
		client.getConnectionStateListenable().addListener(
				new ConnectionStateListener() {
					public void stateChanged(CuratorFramework client,
							ConnectionState state) {
						if (state == ConnectionState.LOST) {
							CuratorZookeeperClient.this
									.stateChanged(StateListener.DISCONNECTED);
						} else if (state == ConnectionState.CONNECTED) {
							CuratorZookeeperClient.this
									.stateChanged(StateListener.CONNECTED);
						} else if (state == ConnectionState.RECONNECTED) {
							CuratorZookeeperClient.this
									.stateChanged(StateListener.RECONNECTED);
						}
					}
				});
		client.start();
	}

	public void createPersistent(String path) {
		try {
			client.create().forPath(path);
		} catch (NodeExistsException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void createEphemeral(String path) {
		try {
			client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
		} catch (NodeExistsException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void delete(String path) {
		try {
			client.delete().forPath(path);
		} catch (NoNodeException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public List<String> getChildren(String path) {
		try {
			return client.getChildren().forPath(path);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public boolean isConnected() {
		return client.getZookeeperClient().isConnected();
	}

	public void doClose() {
		client.close();
	}

	private class CuratorWatcherImpl implements CuratorWatcher {

		private volatile ChildListener listener;

		public CuratorWatcherImpl(ChildListener listener) {
			this.listener = listener;
		}

		public void unwatch() {
			this.listener = null;
		}

		public void process(WatchedEvent event) throws Exception {
			if (listener != null) {
				listener.childChanged(event.getPath(), client.getChildren()
						.usingWatcher(this).forPath(event.getPath()));
			}
		}
	}

	public CuratorWatcher createTargetChildListener(String path,
			ChildListener listener) {
		return new CuratorWatcherImpl(listener);
	}

	public List<String> addTargetChildListener(String path,
			CuratorWatcher listener) {
		try {
			return client.getChildren().usingWatcher(listener).forPath(path);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void removeTargetChildListener(String path, CuratorWatcher listener) {
		((CuratorWatcherImpl) listener).unwatch();
	}

}
