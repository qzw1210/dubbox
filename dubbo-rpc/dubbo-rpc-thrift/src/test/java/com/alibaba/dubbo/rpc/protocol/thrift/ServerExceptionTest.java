/**
 * File Created at 2011-12-09
 * $Id$
 *
 * Copyright 2008 Alibaba.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.dubbo.rpc.protocol.thrift;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.gen.dubbo.$__DemoStub;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:gang.lvg@alibaba-inc.com">kimi</a>
 */
public class ServerExceptionTest extends AbstractTest {

    @Override
    protected $__DemoStub.Iface getServiceImpl() {

        return new $__DemoStub.Iface () {

            public boolean echoBool( boolean arg )  {

                return false;
            }

            public byte echoByte( byte arg )  {

                return 0;
            }

            public short echoI16( short arg )  {

                return 0;
            }

            public int echoI32( int arg )  {

                return 0;
            }

            public long echoI64( long arg )  {

                return 0;
            }

            public double echoDouble( double arg )  {
                return 0;
            }

            public String echoString( String arg )  {
                // 在 server 端，thrift 无法处理 idl 中没有声明的异常
                throw new RuntimeException( "just for test" );
            }
        };

    }

    @Test( expected = RpcException.class )
    public void testServerException() throws Exception {

        Assert.assertNotNull( invoker );

        RpcInvocation invocation = new RpcInvocation();

        invocation.setMethodName( "echoString" );

        invocation.setParameterTypes( new Class<?>[]{ String.class } );

        String arg = "Hello, World!";

        invocation.setArguments( new Object[] { arg } );

        Result result = invoker.invoke( invocation );

        System.out.println( result );

    }

    @Override
    protected URL getUrl() {
        URL url = super.getUrl();
//        url = url.addParameter( Constants.TIMEOUT_KEY, Integer.MAX_VALUE );
        return url;
    }

}
