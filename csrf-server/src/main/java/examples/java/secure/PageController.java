package examples.java.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PageController {

    Logger log = LoggerFactory.getLogger(PageController.class);

    @GetMapping("/")
    public String welcome(HttpServletRequest request, HttpServletResponse response){
        return "index";
    }

    @GetMapping("/login")
    public String login() throws IOException {
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(Model model) throws IOException {
        model.addAttribute("loginSuccess", true);
        model.addAttribute("loginError", false);
        return "login";
    }

    @GetMapping("/login-failure")
    public String loginFailure(Model model) throws IOException {
        model.addAttribute("loginSuccess", false);
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess(Model model) throws IOException {
        model.addAttribute("logoutSuccess", true);
        model.addAttribute("loginError", false);
        return "login";
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "registration";
    }
}
