package fan.company.springbootjwttoken.security;


import fan.company.springbootjwttoken.service.MyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Kirishdan oldin filtrdan o'tadi
     */

    @Autowired
    JwtProwider jwtProwider;
    @Autowired
    MyAuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Requestdan Authorization kaliti orqali tokenni olish
        String token = request.getHeader("Authorization");

        //tokenni boshi bearer ekanini va token borligini tekshirish
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);

            // tokenni validatsiyadan o'tkazamiz (Token to'g'riligini, muddati o'tmaganligini tekshirish)
            boolean validationToken = jwtProwider.validationToken(token);
            if (validationToken) {

                //token orqali userni olish
                String usernameFromToken = jwtProwider.getUsernameFromToken(token);
                if (usernameFromToken != null) {

                    //Username orqali userDetails ni olish
                    UserDetails userDetails = myAuthService.loadUserByUsername(usernameFromToken);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //Sistemaga kim kirganligini o'rnatdik
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


                }
            }
        }

        //boshqa filtirlarni ishlatishni buyrug'ini beradi
        filterChain.doFilter(request, response);


    }
}
