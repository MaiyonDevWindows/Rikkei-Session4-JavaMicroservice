package org.maiyon.service;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUtils {
    @Value("${secret.key}")
    private final String SECRET_KEY="Maiyonaisu1102";
    public void validateToken(String token){
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }
    public String getUsernameFromToken(String token){
        return (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("username");
    }
    public List<String> getRolesFromToken(String token){
        return (List <String>) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("roles");
    }
}