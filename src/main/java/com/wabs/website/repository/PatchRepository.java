package com.wabs.website.repository;

import com.wabs.website.models.Patch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PatchRepository extends JpaRepository<Patch, Long> {

    @Query("SELECT MAX(p.releaseDate) FROM Patch p")
    LocalDateTime getLastPatchDate();

    @Query("SELECT p FROM Patch p WHERE p.releaseDate = (SELECT MAX(p1.releaseDate) FROM Patch p1)")
    Patch getLastPatch();

    @Query("SELECT p FROM Patch p WHERE p.published = FALSE")
    List<Patch> getUnPublishedPatches();

    @Query("SELECT p FROM Patch p WHERE p.published = TRUE ORDER BY p.releaseDate DESC")
    List<Patch> getLastPublishedPatches();

}
