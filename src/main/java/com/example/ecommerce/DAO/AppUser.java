package com.example.ecommerce.DAO;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long cin;
    private String nom;
    private String prenom;
    private String password;
    private String adresse;
    private String image;
    private String specialité;
    private String numLicence;
    private String nomEtablissement;
    private String zoneLivraison;
    private String allegies;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String genre;
    @Column(unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Role> roles = new ArrayList<>();
    public AppUser(Long cin, String nom, String prenom, String password, String email, ArrayList<Role> roles) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.roles = roles;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    @Override
    public boolean isEnabled() {
        return false;
    }
}
