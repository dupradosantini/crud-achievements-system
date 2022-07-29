package dupradosantini.achievementsystem.security;

import dupradosantini.achievementsystem.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static dupradosantini.achievementsystem.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .authorizeRequests() //to authorize a request
                .antMatchers("/","/index","/css/*","/js/*").permitAll()//Permits all patterns stated.
                .antMatchers("/players/**").hasRole(ADMIN.name()) //Disabling this for now
                .anyRequest()   //being it any request
                .authenticated(); // has to be authenticated
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // Hard defining a user
        //Must define the password encoder as well
        UserDetails luisUser = User.builder()
                .username("luis")
                .password(passwordEncoder.encode("password"))
                .authorities(PLAYER.getGrantedAuthorities())
                .build();
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("adminpw"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails adminUser2 = User.builder()
                .username("admin2")
                .password(passwordEncoder.encode("adminpw2"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(luisUser,adminUser,adminUser2);
    }
}
