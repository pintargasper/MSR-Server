package mister3551.msr.msrserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_weapons_statistics")
public class WeaponStatistics {

    @Id
    private Long id;
    private Long idUser;
    private String name;
    private String image;
    private Long stars;
    private Long kills;
    private Long shotsFired;
    private String usedTime;
    private Float accuracy;
    private int damage;
    private int weaponAccuracy;
    private int bulletRange;
    private String fireRate;
}