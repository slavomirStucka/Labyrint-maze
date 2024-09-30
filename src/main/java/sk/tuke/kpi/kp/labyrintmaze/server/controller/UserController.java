package sk.tuke.kpi.kp.labyrintmaze.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.labyrintmaze.entity.Hrac;
import sk.tuke.kpi.kp.labyrintmaze.service.HracService;
import sk.tuke.kpi.kp.labyrintmaze.service.ScoreService;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    public Hrac loggedUser;
    @Autowired
    private HracService hracService;
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(String login,String password){
        String hesloZistene= hracService.getHeslo(login);

        if(hesloZistene!=null&&hesloZistene.equals(password)) {
            loggedUser = hracService.getHrac(login);
            return "redirect:/labyrint-maze/new";
        }

        return  "redirect:/";
    }
    @RequestMapping("/register")
    public String register(String login,String password){
        hracService.addHrac(new Hrac(login,password,new Date()));
        loggedUser=hracService.getHrac(login);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(){
        loggedUser=null;
        return  "redirect:/";
    }

    public Hrac getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged(){
        return loggedUser!=null;
    }
}
