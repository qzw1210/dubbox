#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright 1999-2014 dangdang.com.
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
package ${package}.consumer;

import ${package}.user.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author lishen
 */
public class RestClient {

    public static void main(String[] args) {
        final String port = "8888";
//        final String port = "8080";

//        for (int i = 0; i < 500; i++) {
//            final int index = i + 1;
//            new Thread(new Runnable() {
//                public void run() {
//                    System.out.println("Starting thread " + index + "...");
//                    for (int j = 0; j < 500; j++) {
        registerUser("http://localhost:" + port + "/services/users/register.json", MediaType.APPLICATION_JSON_TYPE);

        registerUser("http://localhost:" + port + "/services/users/register.xml", MediaType.TEXT_XML_TYPE);

        getUser("http://localhost:" + port + "/services/users/1.json");

        getUser("http://localhost:" + port + "/services/users/2.xml");

//                    }
//                }
//            }).start();
//        }
    }

    private static void registerUser(String url, MediaType mediaType) {
        System.out.println("Registering user via " + url);
        User user = new User(1L, "larrypage");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target.request().post(Entity.entity(user, mediaType));

        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            System.out.println("Successfully got result: " + response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }

    private static void getUser(String url) {
        System.out.println("Getting user via " + url);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target.request().get();
        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            System.out.println("Successfully got result: " + response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }
}
