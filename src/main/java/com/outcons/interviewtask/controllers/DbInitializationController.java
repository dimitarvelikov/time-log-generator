package com.outcons.interviewtask.controllers;

import java.util.List;

import com.outcons.interviewtask.models.User;
import com.outcons.interviewtask.services.DbInitializationService;
import com.outcons.interviewtask.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/init")
public class DbInitializationController
{
    private final DbInitializationService dbInitializationService;

    private final UserService userService;


    public DbInitializationController(final DbInitializationService dbInitializationService,
                                      final UserService userService)
    {
        this.dbInitializationService = dbInitializationService;
        this.userService = userService;
    }


    @GetMapping
    public List<User> initializeDb()
    {
        dbInitializationService.runDbInitProcedure();
        return userService.getUsersPaginated(0, 10, 0);
    }
}
