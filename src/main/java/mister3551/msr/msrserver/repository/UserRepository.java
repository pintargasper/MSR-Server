package mister3551.msr.msrserver.repository;

import jakarta.transaction.Transactional;
import mister3551.msr.msrserver.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface UserRepository extends JpaRepository<UserData, Long> {

    UserData findByUsernameAndEmailAddress(String username, String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "JOIN countries c ON c.nice_name = :country " +
            "SET u.full_name = :fullName, " +
            "    u.username = :username, " +
            "    u.email_address = :emailAddress, " +
            "    u.birthdate = :birthdate, " +
            "    u.image = :image, " +
            "    u.country = c.id_country " +
            "WHERE u.username = :oldUsername AND u.email_address = :oldEmailAddress;", nativeQuery = true)
    int updateUserData(@Param("oldUsername") String oldUsername,
                       @Param("oldEmailAddress") String oldEmailAddress,
                       @Param("fullName") String fullName,
                       @Param("username") String username,
                       @Param("emailAddress") String emailAddress,
                       @Param("birthdate") String birthdate,
                       @Param("image") String image,
                       @Param("country") String country);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password = :password " +
            "WHERE u.username = :username AND u.email_address = :emailAddress;", nativeQuery = true)
    int updatePassword(@Param("username") String username,
                       @Param("emailAddress") String emailAddress,
                       @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password_change_timer = DATE_ADD(NOW(), INTERVAL 1 DAY) " +
            "WHERE u.username = :username AND u.email_address = :emailAddress;", nativeQuery = true)
    void updatePasswordChangeTimer(@Param("username") String username,
                                   @Param("emailAddress") String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password_reset_timer = DATE_ADD(NOW(), INTERVAL 1 DAY) " +
            "WHERE u.username = :username AND u.email_address = :emailAddress;", nativeQuery = true)
    void updatePasswordResetTimer(@Param("username") String username,
                                  @Param("emailAddress") String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password = :password, " +
            "u.password_reset_token = NULL " +
            "WHERE u.username = :username " +
            "AND u.email_address = :emailAddress " +
            "AND u.password_reset_token = :token;", nativeQuery = true)
    int resetPassword(@Param("username") String username,
                                    @Param("emailAddress") String emailAddress,
                                    @Param("token") String token,
                                    @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.account_confirmed = IF(u.account_confirmed = :token, '1', u.account_confirmed) " +
            "WHERE u.username = :username AND u.email_address = :emailAddress;", nativeQuery = true)
    int confirmEmailAddress(@Param("username") String username,
                            @Param("emailAddress") String emailAddress,
                            @Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password_reset_token = :token " +
            "WHERE u.username = :username AND u.email_address = :emailAddress;", nativeQuery = true)
    int setPasswordResetToken(@Param("username") String username,
                               @Param("emailAddress") String emailAddress,
                               @Param("token") String token);
}