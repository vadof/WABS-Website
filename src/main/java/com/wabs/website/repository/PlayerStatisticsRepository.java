package com.wabs.website.repository;

import com.wabs.website.models.Player;
import com.wabs.website.models.PlayerStatistics;
import org.springframework.data.repository.CrudRepository;

public interface PlayerStatisticsRepository extends CrudRepository<PlayerStatistics, Long> {

}
