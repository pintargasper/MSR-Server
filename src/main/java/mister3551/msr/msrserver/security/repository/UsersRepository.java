package mister3551.msr.msrserver.security.repository;

import jakarta.transaction.Transactional;
import mister3551.msr.msrserver.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface UsersRepository extends JpaRepository<User, Integer> {

    User findByUsernameOrEmailAddress(String username, String emailAddress);
    User findByUsername(String username);
    User findByEmailAddress(String emailAddress);

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
    int deleteUser(@Param("username") String username,
                   @Param("username") String emailAddress);
}