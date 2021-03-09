package com.umidbek.data.access.repository;

import com.umidbek.data.access.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUsername(String username);

    List<Profile> findAllByUsername(String username);

    Optional<Profile> findByEmail(String email);

    List<Profile> findAllByEmail(String email);
}
