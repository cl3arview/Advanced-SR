package org.advancedsr.repositories;

import org.advancedsr.entities.UserStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStorageRepository extends JpaRepository<UserStorage,Long> {
}
