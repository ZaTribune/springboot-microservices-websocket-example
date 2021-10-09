package zatribune.spring.microservices.websocket.websocket_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * {@link EnableWebSocketMessageBroker} annotation.
 * This annotation configures Spring to "enable broker-backed messaging over WebSocket using
 * a higher-level messaging sub-protocol," as noted in the annotation's javadoc.
 * In more simple terms, this allows Spring to talk to a message broker via a protocol like STOMP
 * (Simple Text Oriented Message Protocol) or AMQP (Advanced Message Queuing Protocol), etc.
 * , rather than raw TCP WebSocket protocol which will enable more features of the message broker
 * than the low-level protocol would.
 *
 * The configuration class also extends a {@link WebSocketMessageBrokerConfigurer},
 * an interface provided by Spring for us to modify the configuration provided by
 * the @EnableWebSocketMessageBroker annotation.
 **/

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${broker.relay.host}")
    private String brokerRelayHost;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/queue", "/topic")
                .setRelayHost(brokerRelayHost);
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*");
        registry.addEndpoint("/sockjs")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}

