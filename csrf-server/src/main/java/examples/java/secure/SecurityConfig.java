package examples.java.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .failureForwardUrl("/login-failure")
                .successForwardUrl("/login-success")
                .usernameParameter("username").passwordParameter("password")
                .and().logout().logoutSuccessUrl("/logout")
                /*.and().authorizeRequests().anyRequest().hasAnyRole("ADMIN","USER")
                .and().authorizeRequests().antMatchers(HttpMethod.GET,"/login","/registration").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST,"/login").permitAll()
                .and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")*/
                .and().csrf().disable();
    }

    @Profile("default")
    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user1").password("pass1").roles("USER").build());
        manager.createUser(users.username("user2").password("pass2").roles("USER").build());
        manager.createUser(users.username("admin").password("topsecret").roles("USER", "ADMIN").build());
        return manager;
    }
}
