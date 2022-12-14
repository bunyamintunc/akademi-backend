package com.bunyaminemre.paylasim.entitiy;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="_user")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public String email;
    public String surname;
    public String username;
    public String password;
    public boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;


}
