package owl_home.rsoket.senders;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;


@Component
public class RequestFlowRequester {
    private RSocketRequester sender;
    
    @Autowired
    public RequestFlowRequester(RSocketRequester.Builder requestBuilder) {
        this.sender = requestBuilder.tcp("localhost", 7000);
    }

    public void request(String message){
        sender
            .route("request-responce/{name}", "Ghost")
            .data(message)
            .retrieveFlux(String.class)
            .doOnNext(System.out::println)
            .subscribe();
    }
}
