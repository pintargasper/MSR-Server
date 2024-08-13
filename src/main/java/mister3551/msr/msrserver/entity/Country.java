package mister3551.msr.msrserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_countries")
public class Country {

    @Id
    private Long id;
    private String name;
    private String code;
}