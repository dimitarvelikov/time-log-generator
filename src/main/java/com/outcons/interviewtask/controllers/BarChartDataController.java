package com.outcons.interviewtask.controllers;

import java.util.List;
import java.util.Optional;

import com.outcons.interviewtask.models.bar_chart.BarChartProjectDTO;
import com.outcons.interviewtask.models.bar_chart.BarChartUserDTO;
import com.outcons.interviewtask.services.BarChartDataService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/chart/data")
/**
 * The endpoints in this class can be reduced by 4 to 2
 * by adding optional userId param to getTopTenUsers and getTopTenUsersForProject
 */
public class BarChartDataController
{
    private final BarChartDataService barChartDataService;


    public BarChartDataController(final BarChartDataService barChartDataService)
    {
        this.barChartDataService = barChartDataService;
    }


    @GetMapping("/users")
    public List<BarChartUserDTO> getTopTenUsers(@RequestParam final Optional<String> from,
                                                @RequestParam final Optional<String> to)
    {
        return barChartDataService.getTopTenUsers(from, to);
    }


    @GetMapping("/users/{id}")
    public List<BarChartUserDTO> getTopTenAndUserToCompare(@PathVariable(value = "id") final int userId,
                                                           @RequestParam final Optional<String> from,
                                                           @RequestParam final Optional<String> to)
    {
        return barChartDataService.getTopTenUsersAndUserToCompare(userId, from, to);
    }


    @GetMapping("/projects")
    public List<BarChartProjectDTO> getTopTenUsersForProject(@RequestParam final Optional<String> from,
                                                             @RequestParam final Optional<String> to)
    {
        return barChartDataService.getTopTenUsersForProject(from, to);
    }


    @GetMapping("/projects/{id}")
    public List<BarChartProjectDTO> getTopTenUsersForProjectAndUserToCompare(@PathVariable(value = "id") final int userId,
                                                                             @RequestParam final Optional<String> from,
                                                                             @RequestParam final Optional<String> to)
    {
        return barChartDataService.getTopTenUsersForProjectAndUserToCompare(userId, from, to);
    }
}
