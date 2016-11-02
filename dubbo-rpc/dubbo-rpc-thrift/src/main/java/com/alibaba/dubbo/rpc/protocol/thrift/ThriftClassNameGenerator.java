/**
 * File Created at 2012-02-02
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

/**
 * @author <a href="mailto:gang.lvg@alibaba-inc.com">kimi</a>
 */
public class ThriftClassNameGenerator implements ClassNameGenerator {

    public static final String NAME = "thrift";

    public String generateArgsClassName( String serviceName, String methodName ) {
        return ThriftUtils.generateMethodArgsClassNameThrift( serviceName, methodName );
    }

    public String generateResultClassName( String serviceName, String methodName ) {
        return ThriftUtils.generateMethodResultClassNameThrift( serviceName, methodName );
    }

}
