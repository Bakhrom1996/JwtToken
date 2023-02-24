package fan.company.springbootjwttoken.security;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProwider {

    long expireTime = 36_000_000; // 10 soat
    private String kalitSuz = "BuKalitSozborqalishifrlashamalgaoshiriladi";


    public String generateToken(String username) {

        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)                                 //Unikal qiymat beriladi
                .setIssuedAt(new Date())                              //Yaratilgan vaqti
                .setExpiration(expireDate)                            //Amal qilish muddati
                .signWith(SignatureAlgorithm.HS512, kalitSuz)         // shifrlash
                .compact();

        return token;

    }

    public boolean validationToken(String authToken) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(kalitSuz)
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            System.err.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.err.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.err.println("Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
            System.err.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.err.println("JWT claims string is empty");
        }
        return false;


    }


    public String getUsernameFromToken(String token) {

        try {
            String username = Jwts
                    .parser()
                    .setSigningKey(kalitSuz)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return username;
        } catch (Exception e){
            e.printStackTrace();

        }
        return null;


    }
}
