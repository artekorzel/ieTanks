package pl.edu.agh.ietanks.bot;

import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.io.IOException;

public interface MockLowLevelHttpResponseCreator {

    public MockLowLevelHttpResponse create() throws IOException;
}
