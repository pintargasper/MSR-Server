package mister3551.msr.msrserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_mission_statistics")
public class MissionStatistics {

    @Id
    private Long id;
    private Long idUser;
    private String name;
    private String image;
    private Long score;
    private Float money;
    private String usedTime;
    private String playedOn;
}