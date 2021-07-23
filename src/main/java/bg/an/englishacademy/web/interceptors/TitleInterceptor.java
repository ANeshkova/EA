package bg.an.englishacademy.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TitleInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String title = "English Academy - ";

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
            modelAndView.addObject("title", "EA");
        } else {
            String addToTitle = "";
            String viewName = modelAndView.getViewName();
            int endIndex = viewName.indexOf("/");

            if (endIndex != -1) {
                addToTitle = viewName.substring(0 , endIndex);
            } else {
                addToTitle = viewName;
            }
            modelAndView.addObject("title", title + addToTitle);
        }
    }
}
