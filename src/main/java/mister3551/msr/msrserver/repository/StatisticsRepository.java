package mister3551.msr.msrserver.repository;

import mister3551.msr.msrserver.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Statistics findStatisticsByIdUser(Long idUser);
}