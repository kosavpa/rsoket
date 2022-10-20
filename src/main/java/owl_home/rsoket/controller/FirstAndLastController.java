package owl_home.rsoket.controller;


import java.util.Arrays;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Controller
public class FirstAndLastController {
    
    @MessageMapping("request-responce/{name}")
    public Mono<String> rsoketRequestResponceHandler(@DestinationVariable("name") String name, Mono<String> request){
        return request
                    .doOnNext(message -> 
                        System.out.println(
                            "------------------------------------------------------------\n"
                            + "|Request-Flow handler: \"" + message + "\" on " + name +"|\n"
                            + "----------------------------------------------------------"))
                    .map(message -> 
                            "Message for Request-Responce handler sended!");
    }

    @MessageMapping("request-flow/{name}")
    public Flux<String> rsoketHandler(@DestinationVariable("name") String name, Mono<String> request){
        request
            .doOnNext(message -> 
                System.out.println(
                    "------------------------------------------------------------\n"
                    + "|Request-Flow handler: \"" + message + "\" on " + name +"|\n"
                    + "----------------------------------------------------------"));

        return Flux
                    .fromIterable(
                        Arrays.asList("Message", "for", "Request-Flow", "handler", "sended!"));
    }

    @MessageMapping("request-and-forgot/{name}")
    public Mono<Void> runAndForgotHandler(@DestinationVariable("name") String name, Mono<String> request){

        return request
                    .doOnNext(message -> 
                        System.out.println(
                            "------------------------------------------------------------\n"
                            + "|Request-Flow handler: \"" + message + "\" on " + name +"|\n"
                            + "----------------------------------------------------------"))
                    .thenEmpty(Mono.empty());
    }

    @MessageMapping("flow-flow/{name}")
    public Flux<String> flowFlowHandler(@DestinationVariable("name") String name, Flux<String> requestFlux){

        return requestFlux
                    .doOnNext(message -> 
                    System.out.println(
                        "------------------------------------------------------------\n"
                        + "|Request-Flow handler: \"" + message + "\" on " + name +"|\n"
                        + "----------------------------------------------------------"))
                    .thenMany(Flux.fromIterable(
                        Arrays.asList("Message", "for", "Request-Flow", "handler", "sended!")));
    }
}
