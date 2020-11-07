package com.shyam.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
//@WebFluxTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class ConsumerControllerTest {

    @Autowired
    WebTestClient webTestClient;

     @Test
     void getNumbers() {
        Flux<Integer> exchange = webTestClient.get()
                .uri("/emp/num")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

         StepVerifier.create(exchange)
                 .expectNext(1)
                 .expectNext(2)
                 .expectNext(3)
                 .verifyComplete();
    }

    @Test
    void test(){
         List<String> list = new ArrayList<>();
         list.add("first");
         list.add("second");
         list.add("thord");
        Stream<String> stringStream = Stream.of("first", "second");
        List<Integer> collect = stringStream.map(String::length).collect(Collectors.toList());
        System.out.println(collect);

        List<List<String>> listOfList= new ArrayList<>();
        listOfList.add(Arrays.asList("first"));
        listOfList.add(Arrays.asList("second"));
        listOfList.add(Arrays.asList("third"));
        //List<Object[]> collect1 = listOfList.stream().map(List::toArray).collect(Collectors.toList());
        List<String> collect1 = listOfList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        List<String> collect2 = collect1.stream().flatMap(s -> Stream.of(s)).collect(Collectors.toList());
        System.out.println(collect2);
    }


}