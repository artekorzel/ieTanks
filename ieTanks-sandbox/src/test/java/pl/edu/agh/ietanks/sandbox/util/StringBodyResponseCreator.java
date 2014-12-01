package pl.edu.agh.ietanks.sandbox.util;

import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.io.IOException;

public class StringBodyResponseCreator implements MockLowLevelHttpResponseCreator {

    private final String body;
    private final String contentType;

    public StringBodyResponseCreator(String body, String contentType) {
        this.body = body;
        this.contentType = contentType;
    }

    @Override
    public MockLowLevelHttpResponse create() throws IOException {
        MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();

        result.setContentType(contentType);
        result.setContent(body);

        return result;
    }
}
