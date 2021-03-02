package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import web.model.User;


import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class Rest {

    private static final String URL_USERS_API = "http://91.241.64.178:7081/api/users/";


    public static void main(String[] args) {

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        //headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON); //и без этого работает
        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(URL_USERS_API, HttpMethod.GET, entity, String.class);

        HttpHeaders headers1 = response.getHeaders();
        String cookie = headers1.getFirst(headers.SET_COOKIE);
        System.out.println(cookie);


        //add user
        Long id = 3L;
        User userJames = new User(id, "James", "Brown", (byte) 15);
        HttpHeaders requestHeaders  = new HttpHeaders();

        requestHeaders.add("Cookie", cookie);
        HttpEntity entity1 = new HttpEntity(userJames, requestHeaders);
        ResponseEntity response2 = restTemplate.exchange(URL_USERS_API,HttpMethod.POST, entity1, String.class);
        String word1 = (String) response2.getBody();


        //update user
        User userThomas = new User(id, "Thomas", "Shelby", (byte) 15);

        HttpHeaders requestHeaders2  = new HttpHeaders();
        requestHeaders2.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        requestHeaders2.add("Cookie", cookie);
        HttpEntity entity2 = new HttpEntity(userThomas, requestHeaders2);
        ResponseEntity response3 = restTemplate.exchange(URL_USERS_API,HttpMethod.PUT, entity2, String.class);
        String word2 = (String) response3.getBody();

        System.out.println(response3);


        //delete user
        ResponseEntity response4 = restTemplate.exchange(URL_USERS_API + id, HttpMethod.DELETE, entity1,String.class);
        String word3 = (String) response4.getBody();
        System.out.println(response4);
        System.out.println(word1+word2+word3);

    }

}




