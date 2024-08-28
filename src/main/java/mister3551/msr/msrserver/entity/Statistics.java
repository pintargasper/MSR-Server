package mister3551.msr.msrserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_statistics")
public class Statistics {

    @Id
    private Long id;
    private Long idUser;
    private Long level;
    private Long wins;
    private Long losses;
    private Long winPercentage;
    private Long score;
    private Float money;
    private String timePlayed;
    private Long kills;
    private Long deaths;
    private Long killDeathRatio;
    private String weaponName;
    private Long weaponKills;
    private String weaponImage;
}