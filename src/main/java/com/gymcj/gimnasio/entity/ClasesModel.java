package com.gymcj.gimnasio.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="clases")
public class ClasesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable=false, name = "clase")
    private String nombreClase;

    
    @Column(nullable=false, name = "sub_clases")
    private String subClases;

    //Cardinalidad para la tabla usuarios
    @ManyToMany(mappedBy = "clases")
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();

}
