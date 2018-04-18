package com.sid.sec;

import java.io.IOException;
import java.util.Date;

import javax.management.RuntimeErrorException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.Jwt1ApplicationTests;
import com.sid.entity.AppUser;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
//constructeur avec champs de la class JWTAuthenticationFilter pour qu'il soit utiliser
	//la class spring security configuration('secu')
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	//recevoire les donnees en json et les convertire en objet ensuite les stocker dans appUser class
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AppUser appUser=null;
		//String username=request.getParameter("username");
		try {
			appUser=new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		System.out.println("******************");
		System.out.println("username: "+appUser.getUsername());
		System.out.println("username: "+appUser.getPassword());
		//returne le user et password en format object 
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
	}
	
	//là dans la methode en gener le token a envoyer appres le success de l'authontifiction
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		//user contient et recuper les information sur le user authentifier
		User springUser=(User) authResult.getPrincipal();
		String jwt=Jwts.builder()
				//mettre le username de la class user dans  subject du token
				.setSubject(springUser.getUsername())
				//temps de expiration token
				.setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
				//le secret et l'algorithem de cryptage du token 
				.signWith(SignatureAlgorithm.HS256,SecurityConstants.SECRET.getBytes("UTF-8"))
				
				.claim("roles",springUser.getAuthorities())
				.compact(); //compacter le token
		//en ajoute header avec le prifix+jwt le corps du crypter  dans le httpRepose
		 response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+" "+jwt);
	
	}
	
	

}
