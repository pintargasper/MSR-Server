package mister3551.msr.msrserver.repository;

import mister3551.msr.msrserver.entity.MissionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MissionRepository extends JpaRepository<MissionStatistics, Long> {

    ArrayList<MissionStatistics> findByIdUserAndName(Long idUser, String name);
}