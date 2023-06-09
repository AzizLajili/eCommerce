package com.example.ecommerce.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne
    private AppUser user;
    @JsonIgnore
    @OneToMany(mappedBy = "commande")
    private List<CommandeDetails> commandeDetailsList;




}
