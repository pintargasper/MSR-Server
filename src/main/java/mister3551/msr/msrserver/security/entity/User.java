package mister3551.msr.msrserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
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
    private String unlockDate;
    private int reportsNumber;
}