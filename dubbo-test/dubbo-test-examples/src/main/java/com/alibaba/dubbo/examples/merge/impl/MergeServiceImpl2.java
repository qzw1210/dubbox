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
package com.alibaba.dubbo.examples.merge.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.examples.merge.api.MergeService;

/**
 * MenuServiceImpl
 * 
 * @author william.liangf
 */
public class MergeServiceImpl2 implements MergeService {
    
    public List<String> mergeResult() {
        List<String> menus = new ArrayList<String>();
        menus.add("group-2.1");
        menus.add("group-2.2");
        return menus;
    }

}