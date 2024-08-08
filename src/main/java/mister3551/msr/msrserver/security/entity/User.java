package mister3551.msr.msrserver.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "get_user")
public class User {

    @Id
    private Integer idUser;
    private String fullName;
    private String username;
    private String emailAddress;
    private String password;
    private String authorities;
    private LocalDate birthDate;
    private String country;
    private boolean accountConfirmed;
    private boolean accountLocked;
    private LocalDateTime unlockDate;
    private int reportsNumber;
}