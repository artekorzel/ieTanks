package pl.edu.agh.ietanks.sandbox.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.sandbox.rest.pojo.StartGameForm;
import pl.edu.agh.ietanks.sandbox.simple.api.Sandbox;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SandboxRestController {

    @Autowired
    private Sandbox sandboxService;

    @RequestMapping(value = "/api/game", method = RequestMethod.POST)
    GameId startGame(@RequestBody StartGameForm startGameForm) {
        List<BotId> botIds = startGameForm.getBotIds().stream().map(BotId::new).collect(Collectors.toList());
        return sandboxService.startNewGameplay(startGameForm.getBoardId(), botIds);
    }
}
