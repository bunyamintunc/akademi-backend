package com.bunyaminemre.paylasim.config.security;


import com.bunyaminemre.paylasim.entitiy.Role;
import com.bunyaminemre.paylasim.entitiy.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class JwtUserDetails implements UserDetails {

    private Long id;
    private   String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    //degişkenleri alıyoruz kullanıcı ile ilil olanları
    private JwtUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities){
        this.id=id;
        this.username= username;
        this.password =password;
        this.authorities =authorities;
    }

    // JwtUserDetail objesine çeviriyoruz

    public  static JwtUserDetails create(User user){

        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        for (Role role: user.getRoles()){
            authoritiesList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new JwtUserDetails(user.getId(),user.getUsername(),user.getPassword(),authoritiesList);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


