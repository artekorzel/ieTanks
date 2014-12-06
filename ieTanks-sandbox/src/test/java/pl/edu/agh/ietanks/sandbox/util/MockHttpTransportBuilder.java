package pl.edu.agh.ietanks.sandbox.util;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;

import java.io.IOException;

// Based on example from documentation:
// https://code.google.com/p/google-http-java-client/source/browse/google-http-client/src/test/java/com/google/api/client/http/HttpResponseTest.java
public class MockHttpTransportBuilder {

    public MockHttpTransport create(final MockLowLevelHttpResponseCreator responseCreator) {
        final MockLowLevelHttpRequest requestToReturn = new MockLowLevelHttpRequest() {
            @Override
            public LowLevelHttpResponse execute() throws IOException {
                return responseCreator.create();
            }
        };

        return new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return requestToReturn;
            }
        };
    }
}
