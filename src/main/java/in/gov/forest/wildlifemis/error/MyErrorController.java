package in.gov.forest.wildlifemis.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MyErrorController implements ErrorController{
    @RequestMapping("/error")
    public String handleError(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("X-Frame-Options","Deny");response.setHeader("X-XSS-Protection","1;mode-block");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Content-Security-Policy","base-uri 'none'; default-src 'self' ; child-src 'none'; connect-src 'self'; font-src 'self' ; form-action 'self' ; frame-ancestors 'none'; img-src 'self' ; media-src 'self'; object-src 'none'; script-src 'self' ; style-src 'self'");
        //do something like logging
        return "error";
    }



}
