package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.entity.MissionStatistics;
import mister3551.msr.msrserver.entity.MissionsStatistics;
import mister3551.msr.msrserver.entity.UserData;
import mister3551.msr.msrserver.repository.MissionRepository;
import mister3551.msr.msrserver.repository.MissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MissionService {

    private final MissionsRepository missionsRepository;
    private final MissionRepository missionRepository;
    private final UserService userService;

    @Autowired
    public MissionService(MissionsRepository missionsRepository, MissionRepository missionRepository, UserService userService) {
        this.missionsRepository = missionsRepository;
        this.missionRepository = missionRepository;
        this.userService = userService;
    }

    public ArrayList<MissionsStatistics> missionsStatistics(Authentication authentication) {
        UserData userData = userService.getUserData(authentication);

        ArrayList<MissionsStatistics> userMissions = missionsRepository.findByIdUser(userData.getId());
        ArrayList<MissionsStatistics> nonUserMissions = missionsRepository.findByIdUserIsNull();

        return (ArrayList<MissionsStatistics>) Stream.concat(userMissions.stream(), nonUserMissions.stream())
                .distinct()
                .sorted(Comparator.comparing(MissionsStatistics::getId))
                .collect(Collectors.toList());
    }

    public ArrayList<MissionStatistics> missionStatistics(Authentication authentication, String name) {
        UserData userData = userService.getUserData(authentication);
        return missionRepository.findByIdUserAndName(userData.getId(), name);
    }
}