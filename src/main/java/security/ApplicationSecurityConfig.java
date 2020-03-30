package security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(("/**")).permitAll()
            .and().formLogin()
            .loginPage(("/login"))
            .loginProcessingUrl("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/login/logout");

    }

}
