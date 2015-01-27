package pl.edu.agh.ietanks.bot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.ietanks.bot.HttpBotService;
import pl.edu.agh.ietanks.bot.api.BotService;

@Configuration
public class SandboxSpringConfiguration {

    private static final String API_ADDRESS = "http://localhost:8889";

    @Bean
    public HttpTransport getHttpTransport() {
        return new NetHttpTransport();
    }

    @Bean(name = "httpBotService")
    public BotService getHttpBotService() {
        HttpRequestFactory requestFactory = getHttpTransport().createRequestFactory();
        ObjectMapper objectMapper = new ObjectMapper();

        return new HttpBotService(API_ADDRESS, requestFactory, objectMapper);
    }
}
