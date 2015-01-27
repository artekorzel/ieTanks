package pl.edu.agh.ietanks.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.bot.api.BotService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BotRestController {

    @Autowired
    BotService botService;

    @RequestMapping(value = "/api/bot", method = RequestMethod.GET)
    List<BotPojo> getBots() {
        return botService.listAvailableBots().stream().map(BotPojo::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "bot",method = RequestMethod.POST)
    void saveBot(@RequestBody BotForm botREST) {
        System.out.println(botREST.getBotID());
        System.out.println(botREST.getUserID());
        System.out.println(botREST.getCode());
    }

}