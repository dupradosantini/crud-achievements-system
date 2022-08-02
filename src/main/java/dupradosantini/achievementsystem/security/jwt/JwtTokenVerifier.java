package dupradosantini.achievementsystem.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//extending this class because we want the filter to be applied once per request
public class JwtTokenVerifier extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //getting the token from the header
        String authorizationHeader = request.getHeader("Authorization");
        //Covering problems with the request.
        if(Strings.isNullOrEmpty(authorizationHeader)|| !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //Replacing the "Bearer " prefix. from the received token
        String token = authorizationHeader.replace("Bearer ", "");

        try{
            //Defining the key to be used for verification.
            String secretKey = "supersecurekeythatnoonecouldeverguess90218300928asj1283j9s83hu";
            //Creating the token parser.
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build();
            //parsing the token
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();
            //Correctly mapping the authorities to be used in line 60.
            var authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e){
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(request,response);
    }
}
