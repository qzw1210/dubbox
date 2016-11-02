/*
 * Copyright 1999-2011 Alibaba Group.
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
package com.alibaba.dubbo.remoting.http.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.remoting.http.HttpHandler;

/**
 * Service dispatcher Servlet.
 * 
 * @author qian.lei
 */
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 5766349180380479888L;
	
	private static DispatcherServlet INSTANCE;

    private static final Map<Integer, HttpHandler> handlers = new ConcurrentHashMap<Integer, HttpHandler>();

    public static void addHttpHandler(int port, HttpHandler processor) {
        handlers.put(port, processor);
    }

    public static void removeHttpHandler(int port) {
        handlers.remove(port);
    }
    
    public static DispatcherServlet getInstance() {
    	return INSTANCE;
    }
    
    public DispatcherServlet() {
    	DispatcherServlet.INSTANCE = this;
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
        HttpHandler handler = handlers.get(request.getLocalPort());
        if( handler == null ) {// service not found.
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Service not found.");
        } else {
            handler.handle(request, response);
        }
    }

}