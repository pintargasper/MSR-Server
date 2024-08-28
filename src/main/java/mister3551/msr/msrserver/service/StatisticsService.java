package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.entity.Statistics;
import mister3551.msr.msrserver.entity.UserData;
import mister3551.msr.msrserver.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final UserService userService;

    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository, UserService userService) {
        this.statisticsRepository = statisticsRepository;
        this.userService = userService;
    }

    public Statistics statistics(Authentication authentication) {
        UserData userData = userService.getUserData(authentication);
        return statisticsRepository.findStatisticsByIdUser(userData.getId());
    }
}