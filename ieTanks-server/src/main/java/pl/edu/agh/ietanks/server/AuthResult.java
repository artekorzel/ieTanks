package pl.edu.agh.ietanks.server;

public class AuthResult {
    private String code;
    private String clientId;
    private String redirectUri;

    public String getCode() {
        return code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}