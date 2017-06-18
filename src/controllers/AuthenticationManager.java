package controllers;

import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.NotAuthorizedException;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class AuthenticationManager {
	
	private static Key key = MacProvider.generateKey();
	
	public static String createToken(String login) {
		//The JWT signature algorithm we will be using to sign the token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    
	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setIssuedAt(now)
	                                .setSubject(login)
	                                .signWith(signatureAlgorithm, key);
	 
	    //expiration (5min)
	    long expMillis = nowMillis + 1000*60*5;
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
	    
	    System.out.println("TOKEN CREE ! ");
	 
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}
	
	public static boolean checkToken(String token){
		try{
			// Check if the HTTP Authorization header is present and formatted correctly 
	        if (token == null) {
	            throw new NotAuthorizedException("Authorization header must be provided");
	        }
	        
			//This line will throw an exception if it is not a signed JWS (as expected)
		    Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		    
		    System.out.println("Subject: " + claims.getSubject());
		    System.out.println("Expiration: " + claims.getExpiration());
		    
		    long nowMillis = System.currentTimeMillis();
		    Date now = new Date(nowMillis);
		    
		    // If the token is not expired
		    return claims.getExpiration().after(now);
		    
		}catch(Exception e){
			System.out.println("Signature invalide !");
			return false;
		}
	}
}
