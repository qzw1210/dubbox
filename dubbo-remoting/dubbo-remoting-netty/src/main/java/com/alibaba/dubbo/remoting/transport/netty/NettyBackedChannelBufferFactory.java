package com.alibaba.dubbo.remoting.transport.netty;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffers;

import com.alibaba.dubbo.remoting.buffer.ChannelBuffer;
import com.alibaba.dubbo.remoting.buffer.ChannelBufferFactory;

/**
 * Wrap netty dynamic channel buffer.
 *
 * @author <a href="mailto:gang.lvg@taobao.com">kimi</a>
 */
public class NettyBackedChannelBufferFactory implements ChannelBufferFactory {

    private static final NettyBackedChannelBufferFactory INSTANCE = new NettyBackedChannelBufferFactory();

    public static ChannelBufferFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public ChannelBuffer getBuffer(int capacity) {
        return new NettyBackedChannelBuffer(ChannelBuffers.dynamicBuffer(capacity));
    }

    @Override
    public ChannelBuffer getBuffer(byte[] array, int offset, int length) {
        org.jboss.netty.buffer.ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(length);
        buffer.writeBytes(array, offset, length);
        return new NettyBackedChannelBuffer(buffer);
    }

    @Override
    public ChannelBuffer getBuffer(ByteBuffer nioBuffer) {
        return new NettyBackedChannelBuffer(ChannelBuffers.wrappedBuffer(nioBuffer));
    }
}
