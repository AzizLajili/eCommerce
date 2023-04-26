package com.example.ecommerce.DAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Transactional
public class Role {

    @Id

    private TypeRole role;

    public Role( TypeRole role) {
        this.role = role;
    }
}
