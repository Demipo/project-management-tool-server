package com.bernard.ppmtool.security;

import com.bernard.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import static  com.bernard.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static  com.bernard.ppmtool.security.SecurityConstants.SECRET;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullname", user.getFullname());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

     }

     //Validate the token

     public boolean validationToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch(SignatureException e){
            System.out.println("Invalid JWT signature");
        }catch(MalformedJwtException e) {
            System.out.println("Invalid JWT token");
        }catch(ExpiredJwtException e){
            System.out.println("Expired JWT token");
        }catch(UnsupportedJwtException e){
            System.out.println("Unsupport JWT token");
        }catch(IllegalArgumentException e){
            System.out.println("JWT claims string is empty");
        }
        return false;
     }

     //Get user id from token
    public Long getUserFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");
//        Long id = (Long) claims.get("id");
//        return  id;

        return Long.parseLong(id);
    }

}
