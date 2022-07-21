package dupradosantini.achievementsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static dupradosantini.achievementsystem.security.ApplicationUserPermission.*;
import static dupradosantini.achievementsystem.security.ApplicationUserRole.*;

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
        http
                .csrf().disable() //TODO se what this does.
                .authorizeRequests() //to authorize a request
                .antMatchers("/","/index","/css/*","/js/*").permitAll()//Permits all patterns stated.
                //.antMatchers("/players/**").hasRole(ADMIN.name()) //Disabling this for now
                .antMatchers(HttpMethod.POST, "/players/**").hasAuthority(PLAYER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/players/**").hasAuthority(PLAYER_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/players/**").hasAuthority(PLAYER_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/players/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
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
//                .roles(PLAYER.name()) //ROLE_PLAYER (how springSec understands this role)
                .authorities(PLAYER.getGrantedAuthorities())
                .build();
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("adminpw"))
//                .roles(ADMIN.name()) //ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails adminUser2 = User.builder()
                .username("admin2")
                .password(passwordEncoder.encode("adminpw2"))
//                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(luisUser,adminUser,adminUser2);
    }
}
