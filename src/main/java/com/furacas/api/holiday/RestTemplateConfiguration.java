package com.furacas.api.holiday;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfiguration {


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.interceptors(new UserAgentInterceptor("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"))
                .build();
    }



    public static class UserAgentInterceptor implements ClientHttpRequestInterceptor {
        private final String userAgent;

        public UserAgentInterceptor(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.add(HttpHeaders.USER_AGENT, userAgent);
            return execution.execute(request, body);
        }
    }

}
