package bg.an.englishacademy.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        String link = "https://freepngimg.com/download/united_kingdom/3-2-united-kingdom-flag-free-png-image.png";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
