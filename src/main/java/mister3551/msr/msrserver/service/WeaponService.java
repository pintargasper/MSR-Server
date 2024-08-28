package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.entity.UserData;
import mister3551.msr.msrserver.entity.WeaponStatistics;
import mister3551.msr.msrserver.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final UserService userService;

    @Autowired
    public WeaponService(WeaponRepository weaponRepository, UserService userService) {
        this.weaponRepository = weaponRepository;
        this.userService = userService;
    }

    public ArrayList<WeaponStatistics> statistics(Authentication authentication) {
        UserData userData = userService.getUserData(authentication);

        ArrayList<WeaponStatistics> userWeapons = weaponRepository.findByIdUser(userData.getId());
        ArrayList<WeaponStatistics> nonUserWeapons = weaponRepository.findByIdUserIsNull();

        return (ArrayList<WeaponStatistics>) Stream.concat(userWeapons.stream(), nonUserWeapons.stream())
                .distinct()
                .sorted(Comparator.comparing(WeaponStatistics::getId))
                .collect(Collectors.toList());
    }
}