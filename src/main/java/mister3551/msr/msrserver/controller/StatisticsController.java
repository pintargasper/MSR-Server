package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.entity.Statistics;
import mister3551.msr.msrserver.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/user/statistics")
    public Statistics statistics(Authentication authentication) {
        return statisticsService.statistics(authentication);
    }
}