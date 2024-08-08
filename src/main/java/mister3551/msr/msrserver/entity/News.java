package mister3551.msr.msrserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_news")
public class News {

    @Id
    private Long id;
    private String title;
    private String text;
    private String image;
    private String images;
    private String entryTime;
}