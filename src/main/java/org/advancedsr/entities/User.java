package org.advancedsr.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.annotation.Nonnull;
import lombok.*;

import java.io.Serializable;


@Entity
@Inheritance(strategy =  InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Table(name = "app_user")
public class User implements Serializable {
    @Id
    @Nonnull
    @Size(min=8,max=16, message = "Username should be between 8 and 16 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Username must be alphanumeric and can contain ., _, or -")
    private String username;
    @Nonnull
    @Size(min=12,max=255,message = "Password should be between 8 and 255 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,255}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character")
    private String password;
    @Nonnull
    @Builder.Default
    private Boolean actif=true;


    @ManyToOne
    @JoinColumn(name="roleName")
    @Nonnull
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private UserStorage userStorage;
}