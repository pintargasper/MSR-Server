package mister3551.msr.msrserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_user_data")
public class UserData {

    @Id
    private Long id;
    private String fullName;
    private String username;
    private String emailAddress;
    private String birthdate;
    private String country;
    private String image;
}