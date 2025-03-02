package com.betacom.dischi.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtToken {
	
	private static final String JWT_TOKEN ="aW9vM2U1ZWNkNmU0Njg0YzlkY2Q2YmZkM2ViMGNjZGUzN2ZmYjY";
	private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_TOKEN));
	
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 *60*60))
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractUsername (String token) {
		return getClaims(token).getSubject();
	}
	
	private boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}
	public boolean validateToken(String token,String username) {
		return username.equals(extractUsername(token)) && !isTokenExpired(token);
	}

}
