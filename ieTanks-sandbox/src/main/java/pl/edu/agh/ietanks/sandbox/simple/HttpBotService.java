package pl.edu.agh.ietanks.sandbox.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpBotService implements BotService {

    private final String apiAddress;
    private final GenericUrl getBotsUrl;
    private final HttpRequestFactory httpRequestFactory;
    private final ObjectMapper objectMapper;

    public HttpBotService(String apiAddress,
                          HttpRequestFactory httpRequestFactory,
                          ObjectMapper objectMapper) {
        this.apiAddress = apiAddress;
        this.httpRequestFactory = httpRequestFactory;
        this.objectMapper = objectMapper;
        this.getBotsUrl = new GenericUrl(apiAddress + "/rest/bots");
    }

    // TODO - improve URL handling - do not concatenate manually
    private GenericUrl getBotUrl(String id) {
        return new GenericUrl(apiAddress + "/rest/bots/" + id);
    }

    private <T> T handleResponse(HttpResponse response, Class<T> clazz) throws IOException {
        String body = response.parseAsString();
        T result = objectMapper.readValue(body, clazz);

        return result;
    }

    @Override
    public List<BotId> listAvailableBots() {
        try {
            HttpRequest request = httpRequestFactory.buildGetRequest(getBotsUrl);
            BotIdJson[] botIds = handleResponse(request.execute(), BotIdJson[].class);
            List<BotId> result = Lists.transform(Arrays.asList(botIds), botJson -> new BotId(botJson.id()));


            return result;
        } catch (IOException e) {
            throw new BotServiceUnavailableException(e);
        }
    }

    @Override
    public BotAlgorithm fetch(BotId botId) {
        try {
            HttpRequest request = httpRequestFactory.buildGetRequest(getBotUrl(botId.id()));
            String botCode = request.execute().parseAsString();
            BotAlgorithm result = new BotAlgorithm(botId, botCode);

            return result;
        } catch (IOException e) {
            throw new BotServiceUnavailableException(e);
        }
    }

    @Override
    public List<BotAlgorithm> fetch(List<BotId> botIds) {
        return botIds.stream().map(id -> fetch(id)).collect(Collectors.toList());
    }
}
