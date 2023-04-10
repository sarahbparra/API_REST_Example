package com.example.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "_users")
//La tabla user ya existe y es manejada por spring. 
//NO se puede llamar user. 

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    
    private long id; 
    private String firstName; 
    private String lastName; 
    
    @Column(unique = true) //No se repite pero no forma parte de la PK 
    // @NaturalId(mutable = true)
    private String email; 
    private String password; 

    @Enumerated(EnumType.STRING)
    private Role role; 
}
