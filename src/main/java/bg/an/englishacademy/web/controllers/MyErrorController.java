package bg.an.englishacademy.web.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest httpServletRequest) {
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            model.addAttribute("statusCode", statusCode);

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("message", "You don't have permission to access this page.");
                return "errors/error-with-message";

            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("message", "The resource you want to access was not found.");
                return "errors/error-with-message";

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("message", "The server encountered an unexpected condition that prevented it from fulfilling the request.");
                return "errors/error-with-message";

            } else {
                return "errors/error-with-status";
            }
        }

        return "errors/error";
    }
}
