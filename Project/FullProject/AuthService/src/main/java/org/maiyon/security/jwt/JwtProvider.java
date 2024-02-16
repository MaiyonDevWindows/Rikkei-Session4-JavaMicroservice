package org.maiyon.security.jwt;

import io.jsonwebtoken.*;
import org.maiyon.security.userPrinciple.UserPrinciple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${expiredTime}")
    private Long EXPIRED;
    @Value("${secretKey}")
    private String SECRET_KEY;
    //Check Change in git
    private final Logger logger= LoggerFactory.getLogger(JwtEntryPoint.class);
    public String generateToken(UserPrinciple userPrinciple){
        String roleName=userPrinciple.getAuthorities().stream().toList().get(0).toString();
        return Jwts.builder().setSubject(roleName).claim("userId",userPrinciple.getUser().getUserId()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+EXPIRED)).signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }


    public Boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException expiredJwtException){
            logger.error("Expired Token {}",expiredJwtException.getMessage());
        }catch (SignatureException signatureException){
            logger.error("Invalid Signature Token {}",signatureException.getMessage());
        }catch (MalformedJwtException malformedJwtException){
            logger.error("Invalid format {}",malformedJwtException.getMessage());
        }
        return false;
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
