package com.bunyaminemre.paylasim.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    //TOKEN OLUŞTURURKEN KULLANACAĞIMIZ ANAHTAR
    @Value("${jwtDeneme.app.secret}")
    private String APP_SECRET;
    //SANİYE CİNSİNDEN KAÇ SANİYEDE TOKEN GEÇERLİLİĞİNİ KAYBEDİYOR.
    @Value("${jwtDeneme.expires.in}")
    private Long EXPIRES_IN;

    public String generateJwtToken(Authentication auth){
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();
    }

    Long getUserIdFromJwt(String token){
        Claims claims  = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }


    //Gönderilen jwt tokeni kontrol editoruz dokğru mu diye. eğer gizli anahtarımıza göre
    //çö<ülebiliyorsa bu geçerli bir jwt tokendır diyoruz.
    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    // gönderilen tokenin tarihi hala geçerli mi ?
    private boolean isTokenExpired(String token) {
        Date expiration =  Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();

        return expiration.before(new Date());
    }
}
