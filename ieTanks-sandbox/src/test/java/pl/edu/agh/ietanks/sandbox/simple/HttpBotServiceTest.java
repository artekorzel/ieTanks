package pl.edu.agh.ietanks.sandbox.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.Json;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.junit.Test;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.sandbox.util.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class HttpBotServiceTest {

    private String apiAddress = "http://localhost:8888";
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockHttpTransportBuilder transportBuilder = new MockHttpTransportBuilder();

    private HttpBotService createBotService(MockLowLevelHttpResponseCreator responseCreator) {
        HttpTransport httpTransport = transportBuilder.create(responseCreator);
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

        return new HttpBotService(apiAddress, requestFactory, objectMapper);
    }

    private String loadResource(String name) throws IOException {
        URL url = Resources.getResource(name);

        return Resources.toString(url, Charsets.UTF_8);
    }

    @Test
    public void shouldListAvailableBots() throws IOException {
        // given
        String botsJson = loadResource("bots.json");
        MockLowLevelHttpResponseCreator responseCreator = new StringBodyResponseCreator(botsJson, Json.MEDIA_TYPE);
        HttpBotService botService = createBotService(responseCreator);

        // when
        List<BotId> bots = botService.listAvailableBots();

        // then
        assertThat(bots).containsExactly(new BotId("first-bot"), new BotId("second-bot"));
    }

    @Test
    public void shouldFetchBotById() throws IOException {
        // given
        String botCode = "some-code";
        BotId botId = new BotId("some-bot");
        MockLowLevelHttpResponseCreator responseCreator = new StringBodyResponseCreator(botCode, "text/plain");
        HttpBotService botService = createBotService(responseCreator);

        // when
        BotAlgorithm botRepresentation = botService.fetch(botId);

        // then
        assertThat(botRepresentation).isEqualTo(new BotAlgorithm(botId, botCode));
    }

    @Test
    public void shouldFetchBotsById() throws IOException {
        // given
        String firstBotCode = "first-code";
        String secondBotCode = "second-code";
        BotId firstBotId = new BotId("first-bot");
        BotId secondBotId = new BotId("second-bot");

        MockLowLevelHttpResponseCreator firstBotResponseCreator = new StringBodyResponseCreator(firstBotCode, "text/plain");
        MockLowLevelHttpResponseCreator secondBotResponseCreator = new StringBodyResponseCreator(secondBotCode, "text/plain");
        CompositeResponseCreator compositeResponseCreator = new CompositeResponseCreator(firstBotResponseCreator, secondBotResponseCreator);
        HttpBotService botService = createBotService(compositeResponseCreator);

        // when
        List<BotAlgorithm> bots = botService.fetch(Lists.newArrayList(firstBotId, secondBotId));

        // then
        assertThat(bots).containsExactly(new BotAlgorithm(firstBotId, firstBotCode), new BotAlgorithm(secondBotId, secondBotCode));
    }

    @Test(expected = BotServiceUnavailableException.class)
    public void shouldThrowExceptionWhenErrorIsEncounteredDuringBotListing() {
        // given
        MockLowLevelHttpResponseCreator responseCreator = new IoExceptionHttpResponseCreator();
        HttpBotService botService = createBotService(responseCreator);

        // when-then
        botService.listAvailableBots();
    }

    @Test(expected = BotServiceUnavailableException.class)
    public void shouldThrowExceptionWhenErrorIsEncounteredDuringBotFetching() {
        // given
        MockLowLevelHttpResponseCreator responseCreator = new IoExceptionHttpResponseCreator();
        HttpBotService botService = createBotService(responseCreator);

        // when-then
        botService.fetch(new BotId("some-bot"));
    }
}