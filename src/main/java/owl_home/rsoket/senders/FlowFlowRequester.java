package owl_home.rsoket.senders;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;


@Component
public class FlowFlowRequester {
    private RSocketRequester sender;
    
    @Autowired
    public FlowFlowRequester(RSocketRequester.Builder requestBuilder) {
        this.sender = requestBuilder.tcp("localhost", 7000);
    }

    public void request(List<String> message){
        Flux<String> messageFlow = Flux.fromIterable(message);
        
        sender
            .route("request-responce/{name}", "Ghost")
            .data(messageFlow)
            .retrieveFlux(String.class)
            .subscribe(System.out::println);
    }    
}