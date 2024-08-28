package mister3551.msr.msrserver.security.repository;

import jakarta.transaction.Transactional;
import mister3551.msr.msrserver.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<User, Integer> {

    User findByUsernameOrEmailAddress(String username, String emailAddress);
    User findByUsername(String username);
    User findByEmailAddress(String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (full_name, username, email_address, password, birthdate, country, account_confirmed) " +
            "SELECT :fullName, :username, :emailAddress, :password, :birthdate, c.id_country, :token " +
            "FROM countries c " +
            "WHERE c.nice_name = :country;", nativeQuery = true)
    int insertUser(@Param("fullName") String fullName,
                   @Param("username") String username,
                   @Param("emailAddress") String emailAddress,
                   @Param("password") String password,
                   @Param("birthdate") String birthdate,
                   @Param("country") String country,
                   @Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users_authorities (id_user, id_authority) " +
            "SELECT u.id_user, 3 " +
            "FROM users u " +
            "WHERE u.username = :username;", nativeQuery = true)
    int insertUserRole(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users " +
            "WHERE username = :username " +
            "AND email_address = :emailAddress;", nativeQuery = true)
    void deleteUser(@Param("username") String username,
                   @Param("username") String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO statistics (id_user) " +
            "SELECT u.id_user " +
            "FROM users u " +
            "WHERE u.username = :username;", nativeQuery = true)
    int insertUserStatistics(@Param("username") String username);


    @Modifying
    @Transactional
    @Query(value = "DELETE ua FROM users_authorities ua " +
            "JOIN users u ON ua.id_user = u.id_user " +
            "WHERE u.username = :username",
            nativeQuery = true)
    void deleteUserRole(@Param("username") String username);
}