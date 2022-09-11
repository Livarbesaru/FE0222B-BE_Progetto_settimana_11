package com.dblibrary.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.dblibrary.model.service.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component 
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	//@Value("${jwt.secret}")  //Proprietà
	private String jwtSecret="chiavesegreta123";

	//@Value("${jwt.expirationms}") //Proprietà
	private Long jwtExpirationMs=864000000L;

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal(); //Facciamo il cast per recuperare le informazioni relativi all'utente
		Date now = new Date();
		Date exp = new Date((now).getTime() + jwtExpirationMs); //Recuperiamo la data e ad essa aggiungiamo la scadenza
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(now).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact(); 	//SetSubject serve a prendere l'username del richiedente e successivamente aggiungiamo alla data la scadenza
	} // SignatureAlgorithm.HS512, jwtSecret - Firma e produce un token criptato

	//Passi un Token lui lo decodifica e ti restituisce l'username a partire dal token 
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	//Controlla se la firma è corretta e se quello che mi stai mandando è un token valido e non manomesso
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		}
		return false;
	}

}
