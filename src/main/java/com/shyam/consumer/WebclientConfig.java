package com.shyam.consumer;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebclientConfig {

    private final String baseURL="http://localhost:8081";
    private final int TIMEOUT=8000;

    @Bean
    public WebClient getWebClient(){

        TcpClient client = TcpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS,TIMEOUT)
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT)))
                .doOnConnected(connection -> connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT)));

        return WebClient.builder()
                .baseUrl(baseURL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(client)))
                .build();
    }
}
