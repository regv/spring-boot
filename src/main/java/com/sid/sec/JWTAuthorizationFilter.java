package com.sid.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
//u
public class JWTAuthorizationFilter extends OncePerRequestFilter{
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		String jwt =request.getHeader(SecurityConstants.HEADER_STRING);
		
		System.out.println("*****authori*****");
		System.out.println(jwt);
              //si le token et null Ou ne commance pas par le TOKEN_PRFIX  alors ne rien faire
		if(jwt==null ){
			filterChain.doFilter(request, response);return;
		}
		
		
		
		
		
		//jwt.substring(7);
		System.out.println("******************11"+jwt.replaceAll("Bearer ",""));
		/*Claims claims=Jwts.parser()
					.setSigningKey("iheb@chaaraoui.net")//signer avec le secret
					                     //parser et remplace le TOKEN_PREFIX par chaine vide
					.parseClaimsJws(jwt)
					                     //recuper le corps du token
					.getBody();*/
		try {
            // Validate the token
            JwtParser parser = Jwts.parser().setSigningKey("iheb@chaaraoui.net");
            Claims claims = parser.parseClaimsJws(jwt).getBody();
           
            String username =claims.getSubject();
   		 //returne tableau d'objet qui contient les roles du user
   		 ArrayList<Map<String,String>> roles=(ArrayList<Map<String,String>>) claims.get("role");
   		 Collection<GrantedAuthority> authorities=new ArrayList<>();
   		 UsernamePasswordAuthenticationToken  authenticatedUserToken=
				 new UsernamePasswordAuthenticationToken(username,null,authorities);
		 //charger l'identiter dans le context de l'application
		 SecurityContextHolder.getContext().setAuthentication(authenticatedUserToken);

        } catch (JwtException | IllegalArgumentException e) {

            System.out.println("Invalid JWT, processing as guest!  "+e);
        }
		
	 	System.out.println("******************22");
	/*	 String username =claims.getSubject();
		 //returne tableau d'objet qui contient les roles du user
		 ArrayList<Map<String,String>> roles=(ArrayList<Map<String,String>>) claims.get("role");
		 Collection<GrantedAuthority> authorities=new ArrayList<>();
		 //parcourire et recuperer les role de mon token
		 roles.forEach(r->{
			authorities.add(new SimpleGrantedAuthority(r.get("authority"))); 
		 });
		 //mettre dans authenticatedUserToken le nom et authoriter d'un utilisateur authentifier dans le token 
		 UsernamePasswordAuthenticationToken  authenticatedUserToken=
				 new UsernamePasswordAuthenticationToken(username,null,authorities);
		 //charger l'identiter dans le context de l'application
		 SecurityContextHolder.getContext().setAuthentication(authenticatedUserToken);*/
		 filterChain.doFilter(request, response);
	} 


}
