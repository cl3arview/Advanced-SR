package org.advancedsr.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Role {
    @Id
    private String roleName;
    @Nonnull
    @Builder.Default
    private String permissions= "default";


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<User> user = new HashSet<>();
}