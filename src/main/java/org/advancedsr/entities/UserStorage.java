package org.advancedsr.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserStorage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserStorageId;

    @OneToOne
    @JoinColumn(name = "username",referencedColumnName = "username")
    @Nonnull
    private User user;

    @Lob
    @JoinColumn(name="large_object",columnDefinition = "oid")
    //@Nonnull
    private Long largeObject;





}
