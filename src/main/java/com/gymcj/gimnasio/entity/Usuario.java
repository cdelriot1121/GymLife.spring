package com.gymcj.gimnasio.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Column(name = "rol")
    private Set<String> roles;

    //Cardinalidad para la tabla clases
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    
    @JoinTable(name = "Usuarios_clases",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_usuario-clase")),
            inverseJoinColumns = @JoinColumn(name = "clase_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_clase-usuario")))
    private Set<ClasesModel> clases = new HashSet<>();
}
