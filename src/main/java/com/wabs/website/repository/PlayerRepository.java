package com.wabs.website.repository;

import com.wabs.website.models.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PlayerRepository extends CrudRepository<Player, Long> {

    Player findByUsername(String username);

    @Query("SELECT p FROM Player p WHERE DATE(p.registered) = CURRENT_DATE")
    List<Player> findAllPlayersRegisteredToday();

}
