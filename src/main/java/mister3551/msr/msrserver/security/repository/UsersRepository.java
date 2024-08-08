package mister3551.msr.msrserver.security.repository;

import jakarta.transaction.Transactional;
import mister3551.msr.msrserver.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT " +
            "u.id_user, " +
            "u.full_name, " +
            "u.username, " +
            "u.email_address, " +
            "u.password, " +
            "GROUP_CONCAT(a.authority) AS authorities, " +
            "u.birth_date, " +
            "u.country, " +
            "CASE WHEN u.account_confirmed = '1' THEN true ELSE false END AS account_confirmed, " +
            "CASE WHEN u.account_locked = '1' THEN true ELSE false END AS account_locked, " +
            "u.unlock_date, " +
            "u.reports_number " +
            "FROM users u " +
            "INNER JOIN users_authorities ua ON ua.id_user = u.id_user " +
            "INNER JOIN authorities a ON a.id_authority = ua.id_authority " +
            "WHERE u.username = :usernameOrEmailAddress OR u.email_address = :usernameOrEmailAddress", nativeQuery = true)
    Optional<User> findByUsernameOrEmailAddress(@Param("usernameOrEmailAddress") String usernameOrEmailAddress);

    @Query(value = "SELECT COUNT(u.id_user) AS user " +
            "FROM users u " +
            "WHERE u.username = :username", nativeQuery = true)
    int findByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(u.id_user) AS user " +
            "FROM users u " +
            "WHERE u.email_address = :emailAddress", nativeQuery = true)
    int findByEmailAddress(@Param("emailAddress") String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (full_name, username, email_address, password, birth_date, country, account_confirmed) VALUES (:fullName, :username, :emailAddress, :password, :birthDate, :country, :token) ", nativeQuery = true)
    int insertUser(@Param("fullName") String fullName,
               @Param("username") String username,
               @Param("emailAddress") String emailAddress,
               @Param("password") String password,
               @Param("birthDate") LocalDate birthDate,
               @Param("country") String country,
               @Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users_authorities (id_user, id_authority) " +
            "SELECT u.id_user, 3 " +
            "FROM users u " +
            "WHERE u.username = :username", nativeQuery = true)
    int insertUserRole(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users " +
            "WHERE username = :username " +
            "AND email_address = :emailAddress", nativeQuery = true)
    void deleteUser(@Param("username") String username,
                   @Param("username") String emailAddress);
}