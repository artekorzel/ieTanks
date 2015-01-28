package pl.edu.agh.ietanks.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private static final String googleClientSecret = "MR3csGuHzjJEk9ZpZ3cbEcBD";
    private static final String grantType = "authorization_code";
    private static final String googleAccessTokenUrl = "https://accounts.google.com/o/oauth2/token";
    private static final String googlePeopleApiUrl = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
    private static final String authHeaderKey = "Authorization";
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Client client = new Client();

    @Autowired
    public AuthTokensService authTokensService;

    @RequestMapping(value = "/auth/google", method = RequestMethod.POST)
    public Map<String, String> loginWithGoogle(@RequestBody AuthResult authResult, HttpServletRequest request) throws Exception {
        MultivaluedMap<String, String> accessData = new MultivaluedMapImpl();
        accessData.add("client_id", authResult.getClientId());
        accessData.add("redirect_uri", authResult.getRedirectUri());
        accessData.add("client_secret", googleClientSecret);
        accessData.add("code", authResult.getCode());
        accessData.add("grant_type", grantType);

        ClientResponse clientResponse;

        clientResponse = client.resource(googleAccessTokenUrl).entity(accessData).post(ClientResponse.class);
        accessData.clear();

        String accessToken = (String) getResponseEntity(clientResponse).get("access_token");
        clientResponse = client.resource(googlePeopleApiUrl).header(authHeaderKey, String.format("Bearer %s", accessToken)).get(ClientResponse.class);
        Map<String, Object> userInfo = getResponseEntity(clientResponse);

        String token = authTokensService.generateToken(request.getRemoteHost(), (String) userInfo.get("sub"), (String) userInfo.get("email"));

        Map<String, String> result = new HashMap<String, String>();
        result.put("token", token);

        return result;
    }

    @RequestMapping(value = "/auth/userId", method = RequestMethod.GET)
    public String getUserId(@RequestHeader("authenticationToken") String authenticationToken) {
        if (authenticationToken == null) {
            return null;
        }

        return authTokensService.getUserId(authenticationToken);
    }

    private Map<String, Object> getResponseEntity(ClientResponse response) throws ClientHandlerException, UniformInterfaceException, IOException {
        return mapper.readValue(response.getEntity(String.class),
                new TypeReference<Map<String, Object>>() {
                });
    }
}
