package org.advancedsr.repositories;

import org.advancedsr.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findAllByUserUsername(String username);
    Optional<Image> findImageByImagePath(String imagePath);
}
