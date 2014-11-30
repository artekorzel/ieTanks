package pl.edu.agh.ietanks.gameplay.testutils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ResourceUtils {
    public static String loadResourceFromFile(String filename) {
        try {
            final URL url = Resources.getResource(filename);
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception!";
        }

    }
}
