package com.wabs.website.repository;

import com.wabs.website.models.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Player> findByUsername(String username);

    @Query("SELECT p FROM Player p WHERE DATE(p.registered) = CURRENT_DATE")
    List<Player> findAllPlayersRegisteredToday();

}
