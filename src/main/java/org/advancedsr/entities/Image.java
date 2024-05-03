package org.advancedsr.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Image {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String imageId;

    @Nonnull
    private String imagePath;

    @Nonnull
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedAt;

    @ManyToOne
    @JoinColumn(name="username")
    @Nonnull
    private User user;


    /*@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<User> user = new HashSet<>();*/
}