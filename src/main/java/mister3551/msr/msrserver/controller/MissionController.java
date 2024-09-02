package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.entity.MissionStatistics;
import mister3551.msr.msrserver.entity.MissionsStatistics;
import mister3551.msr.msrserver.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class MissionController {

    private final MissionService missionService;

    @Autowired
    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @PostMapping("/user/mission/statistics")
    public ArrayList<MissionsStatistics> missionsStatistics(Authentication authentication) {
        return missionService.missionsStatistics(authentication);
    }

    @PostMapping("/user/mission/single/statistics")
    public ArrayList<MissionStatistics> missionStatistics(Authentication authentication, @Param("name") String name) {
        return missionService.missionStatistics(authentication, name);
    }
}