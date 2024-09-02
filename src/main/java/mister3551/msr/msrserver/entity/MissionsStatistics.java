package mister3551.msr.msrserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_missions_statistics")
public class MissionsStatistics {

    @Id
    private Long id;
    private Long idUser;
    private String name;
    private String image;
    private Long score;
    private String usedTime;
    private Long playedCount;
    private Long bestScore;
    private String bestUsedTime;
}