package dupradosantini.achievementsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //to authorize a request
                .antMatchers("/","/index","/css/*","/js/*")
                .permitAll() //Permits all patterns above.
                .anyRequest()   //being it any request
                .authenticated() // has to be authenticated
                .and()
                .httpBasic(); //and using basic auth.
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // Hard defining a user
        //Must define the password encoder as well
        UserDetails luisUser = User.builder()
                .username("luis")
                .password(passwordEncoder.encode("password"))
                .roles("DEV") //ROLE_DEV (how springSec understands this role)
                .build();
        return new InMemoryUserDetailsManager(luisUser);
    }
}
