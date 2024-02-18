package com.web.dacn.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/games")
public class GameController {
     @GetMapping
     public String Games() {
          return "games/main";
     }

     @GetMapping("/flappybird")
     public String GameFlappyBird() {
          return "games/flappyBird";
     }

     @GetMapping("/fightgame")
     public String GameFight() {
          return "games/fightGame";
     }

     @GetMapping("/pacman")
     public String GamePacMan() {
          return "games/pacMan";
     }

     @GetMapping("/snackgame")
     public String GameSnack() {
          return "games/snackGame";
     }

}
