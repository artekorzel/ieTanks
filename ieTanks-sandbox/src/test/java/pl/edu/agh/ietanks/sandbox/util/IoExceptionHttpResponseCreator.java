package pl.edu.agh.ietanks.sandbox.util;

import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.io.IOException;

public class IoExceptionHttpResponseCreator implements MockLowLevelHttpResponseCreator {
    @Override
    public MockLowLevelHttpResponse create() throws IOException {
        throw new IOException();
    }
}
