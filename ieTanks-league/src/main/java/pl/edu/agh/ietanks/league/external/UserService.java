package pl.edu.agh.ietanks.league.external;

import org.springframework.stereotype.Component;

@Component
public class UserService {
    public String currentUser() {
        return "mequrel";
    }
}
