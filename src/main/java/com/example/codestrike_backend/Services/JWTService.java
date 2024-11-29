package com.example.codestrike_backend.Services;




import com.example.codestrike_backend.Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {


    //    private String key;
    @Value("${jwt.secret}")
    private  String key;

//    protected JWTService() {
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            key = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }





    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();


        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 30L *24 * 60 * 60 * 1000))
                .and()
                .signWith(getKey())
                .compact();
    }


    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token , Function<Claims,T> claimResolver){
        final Claims claims= extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractId(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    public String extractId(HttpServletRequest request){
        return extractId(request.getHeader("Authorization").substring(7));
    }

    public boolean validateToken(String token, User user) {
        final String userId = extractId(token);
        return (userId.equals(user.getUserId()) && !isTokenExpired(token));
    }

    private boolean  isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
}
