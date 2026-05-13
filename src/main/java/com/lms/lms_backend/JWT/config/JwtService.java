package com.lms.lms_backend.JWT.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.JWT_SECRET}")
    private String secretKey;

    @Value("${jwt.token.expiration-ms}")
    private long tokenExpiration;

    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken , Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
    //used for refreshing the token as it wouldn't need extra claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String , Object> extraClaims , UserDetails userDetails){
        return Jwts.builder().claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getSignInKey() , Jwts.SIG.HS256).compact();
    }

    public boolean isTokenValid(String jwtToken , UserDetails userDetails){
        final String username = extractUserEmail(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken) ;
    }

    private boolean isTokenExpired(String jwtToken) {
    return extractExpiration(jwtToken).before(new Date()) ;
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken , Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
