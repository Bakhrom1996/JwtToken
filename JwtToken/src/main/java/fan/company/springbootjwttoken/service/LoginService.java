package fan.company.springbootjwttoken.service;

import fan.company.springbootjwttoken.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    MyAuthService myAuthService;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public boolean singUser(Users users) {

        /**
         * Parolni ruchnoy tekshirish
         */
//        UserDetails userDetails = myAuthService.loadUserByUsername(users.getUsername());
//        if (passwordEncoder.matches(users.getPassword(), userDetails.getPassword())) {    //paswordlarni tekshirish
//            return true;
//        }
//        return false;

        /**
         * authenticationManager orqali parolni tekshirish
         */

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
            return true;
        } catch (BadCredentialsException exception) {
            return false;
        }
    }
}
