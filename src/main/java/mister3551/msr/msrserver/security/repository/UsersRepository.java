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
    @Query(value = "INSERT INTO users (full_name, username, email_address, password, image, birthdate, country, account_confirmed) " +
            "SELECT :fullName, :username, :emailAddress, :password, IF(:image IS NULL OR :image = '', 'basic-image.jpg', :image), :birthdate, c.id_country, :token " +
            "FROM countries c " +
            "WHERE c.nice_name = :country;", nativeQuery = true)
    int insertUser(@Param("fullName") String fullName,
                   @Param("username") String username,
                   @Param("emailAddress") String emailAddress,
                   @Param("password") String password,
                   @Param("image") String image,
                   @Param("birthdate") String birthdate,
                   @Param("country") String country,
                   @Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users_authorities (id_user, id_authority) " +
            "VALUES (:idUser, 3)", nativeQuery = true)
    int insertUserRole(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO statistics (id_user) " +
            "VALUES (:idUser)", nativeQuery = true)
    int insertUserStatistics(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO missions_statistics (id_user) " +
            "VALUES (:idUser)", nativeQuery = true)
    int insertUserMissionsStatistics(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO weapons_statistics (id_user) " +
            "VALUES (:idUser)", nativeQuery = true)
    int insertUserWeaponsStatistics(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "DELETE u FROM users u " +
            "WHERE u.id_user = :idUser",
            nativeQuery = true)
    void deleteUser(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "DELETE ua FROM users_authorities ua " +
            "WHERE ua.id_user = :idUser",
            nativeQuery = true)
    void deleteUserRole(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "DELETE s FROM statistics s " +
            "WHERE s.id_user = :idUser",
            nativeQuery = true)
    void deleteUserStatistics(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "DELETE s FROM missions_statistics s " +
            "WHERE s.id_user = :idUser",
            nativeQuery = true)
    void deleteUserMissionStatistics(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(value = "DELETE s FROM weapons_statistics s " +
            "WHERE s.id_user = :idUser",
            nativeQuery = true)
    void deleteUserWeaponStatistics(@Param("idUser") Long idUser);
}