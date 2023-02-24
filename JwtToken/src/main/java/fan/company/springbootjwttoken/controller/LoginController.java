package fan.company.springbootjwttoken.controller;

import fan.company.springbootjwttoken.entity.Users;
import fan.company.springbootjwttoken.security.JwtProwider;
import fan.company.springbootjwttoken.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    JwtProwider jwtProwider;

    @PostMapping("/login")
    public HttpEntity<?> signToSystem(@RequestBody Users users) {
        boolean singUser = loginService.singUser(users);
        String token = "";
        if(singUser){
             token = jwtProwider.generateToken(users.getUsername());
        }
        return ResponseEntity.status(singUser? HttpStatus.OK: HttpStatus.UNAUTHORIZED).body(singUser?"Bearer "+ token:"Login yoki parol xato");
    }


}
