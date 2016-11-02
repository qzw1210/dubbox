/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.examples.aop;

import com.alibaba.dubbo.examples.aop.action.AopAnnotationAction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dylan
 */
public class AopAnnotationConsumer {

    public static void main(String[] args) throws Exception {
        String config = AopAnnotationConsumer.class.getPackage().getName().replace('.', '/') + "/annotation-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();
        final AopAnnotationAction annotationAction = (AopAnnotationAction)context.getBean("aopAnnotationAction");
        String hello = annotationAction.doSayHello("world");
        System.out.println("result :" + hello);
        System.in.read();
    }

}
