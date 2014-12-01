package pl.edu.agh.ietanks.sandbox.util;

import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.io.IOException;

public interface MockLowLevelHttpResponseCreator {

    public MockLowLevelHttpResponse create() throws IOException;
}
