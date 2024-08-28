package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.entity.WeaponStatistics;
import mister3551.msr.msrserver.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WeaponController {

    private final WeaponService weaponService;

    @Autowired
    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }

    @PostMapping("/user/weapon/statistics")
    public ArrayList<WeaponStatistics> weaponStatistics(Authentication authentication) {
        return weaponService.statistics(authentication);
    }
}