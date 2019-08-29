package app;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
public class PageController {

    Logger log = LoggerFactory.getLogger(PageController.class);

    @GetMapping("/")
    public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("index");
        HttpSession session = request.getSession(false);
        Object username = Optional.ofNullable(session).map(s -> s.getAttribute("username")).orElse(null);
        if(username != null) {
            modelAndView.getModel().put("user", username);
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isNoneBlank(username, password)) {
            // invalidate old session
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            // create fresh session
            HttpSession newSession = request.getSession(true);
            log.info("MYSESSIONID={}",newSession.getId());
            // expires in 2 minutes
            newSession.setMaxInactiveInterval(2 * 60);
            newSession.setAttribute("username",username);

            Cookie message = new Cookie("id1", "fef238f89h2f3");
            // TODO: true in production https
            message.setSecure(false);
            // inaccessible to client side scripts
            // done automatically by Tomcat
            message.setHttpOnly(true);
            response.addCookie(message);

            response.sendRedirect("/");
        } else {
            response.sendRedirect("/error");
        }
    }
}
