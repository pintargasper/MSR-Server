package mister3551.msr.msrserver.repository;

import mister3551.msr.msrserver.entity.WeaponStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface WeaponRepository extends JpaRepository<WeaponStatistics, Long> {

    ArrayList<WeaponStatistics> findByIdUser(Long idUser);
    ArrayList<WeaponStatistics> findByIdUserIsNull();
}