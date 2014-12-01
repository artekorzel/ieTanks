package pl.edu.agh.ietanks.sandbox.util;

import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class CompositeResponseCreator implements MockLowLevelHttpResponseCreator {

    private Queue<MockLowLevelHttpResponseCreator> creators;

    public CompositeResponseCreator(MockLowLevelHttpResponseCreator... creators) {
        this.creators = new ArrayDeque<>(Arrays.asList(creators));
    }

    @Override
    public MockLowLevelHttpResponse create() throws IOException {
        MockLowLevelHttpResponseCreator creator = creators.poll();

        return creator.create();
    }
}
