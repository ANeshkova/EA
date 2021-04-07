package bg.an.englishacademy.config;

import bg.an.englishacademy.service.impl.MyUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsServiceImpl myUserDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(MyUserDetailsServiceImpl myUserDetailsServiceImpl, PasswordEncoder passwordEncoder) {
        this.myUserDetailsServiceImpl = myUserDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().disable().
                authorizeRequests().
                // allow access to static resources to anyone
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll(). //img must be images!!!
                //antMatchers("/js/**", "/css/**", "/img/**").permitAll().
                // allow access to index, user login and registration to anyone
                        antMatchers("/", "/users/login", "/users/register", "/error").permitAll().
                // protect all other pages
                        antMatchers("/**").authenticated().
        and().
                // configure login with HTML form
                        formLogin().
                // our login page will be served by the controller with mapping /users/login
                        loginPage("/users/login").
                // the name of the user name input field in OUR login form is username (optional)
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                //usernameParameter("username").
                // the name of the user password input field in OUR login form is password (optional)
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                //passwordParameter("password").
                // on login success redirect here
                        defaultSuccessUrl("/").
                // on login failure redirect here
                        failureForwardUrl("/users/login-error");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(myUserDetailsServiceImpl).
                passwordEncoder(passwordEncoder);
    }
}
