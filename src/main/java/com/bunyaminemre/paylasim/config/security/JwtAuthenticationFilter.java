package com.bunyaminemre.paylasim.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Autowired
    UserDetailsServiceImpl userDetailsService;
    //Reques geldi jwt kontrol ediyoruz.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //headerde geliyor. header içinde tokeni alıp dönen bir fonskiyon yaratıyoruz.
            String jwtToken = extracJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)){
                Long id = jwtTokenProvider.getUserIdFromJwt(jwtToken);
                UserDetails user = userDetailsService.loadUserById(id);

                if(user != null){

                    UsernamePasswordAuthenticationToken  auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (Exception e){
            return;
        }
        filterChain.doFilter(request,response);
    }

    //header geldi bize içinde jwt var şimdi onu ayırıp geri döndüreceğiz.
    private String extracJwtFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        //gelen requestin headerina baktık. ve içindeki authorization a bakıyoruz boş mu diye.
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
            //beareri atlayıp token'e bakıyoruz direkt.
            return bearer.substring("Bearer".length()+1);
        }
        return null;
    }
}
