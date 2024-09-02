package mister3551.msr.msrserver.repository;

import mister3551.msr.msrserver.entity.MissionsStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MissionsRepository extends JpaRepository<MissionsStatistics, Long> {

    ArrayList<MissionsStatistics> findByIdUser(Long idUser);
    ArrayList<MissionsStatistics> findByIdUserIsNull();
}