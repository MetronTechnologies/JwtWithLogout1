package com.security.Models;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "xuser")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userid;

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "firstname cannot be empty")
    private String firstName;

    @NotBlank(message = "lastname cannot be empty")
    private String lastName;

    @NotBlank(message = "password cannot be empty")
    private String password;

    @NotBlank(message = "email cannot be empty")
    private String email;
    private Instant created;

    private boolean enabled;

//    @ManyToMany(
//            fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL
//    )
//    @JoinTable(
//            name = "userxroles",
//            joinColumns = @JoinColumn(
//                    name = "userid",
//                    referencedColumnName = "userid"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "roleid",
//                    referencedColumnName = "roleid"
//            )
//    )
//    private List<UserRoles> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @OneToMany(mappedBy = "userModel")
    private List<UserToken> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.name()));
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
    public boolean isEnabled(){
        return true;
    }

}
