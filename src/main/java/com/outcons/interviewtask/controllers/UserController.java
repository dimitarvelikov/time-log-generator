package com.outcons.interviewtask.controllers;

import java.util.List;

import com.outcons.interviewtask.models.User;
import com.outcons.interviewtask.models.data_table.DataTableResp;
import com.outcons.interviewtask.models.data_table.DataTblRq;
import com.outcons.interviewtask.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;


    public UserController(final UserService userService)
    {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getUsers()
    {
        return userService.getAllUsers();
    }


    @PostMapping("/paginated")
    public DataTableResp getUsersPaginated(@RequestBody final DataTblRq requestBody)
    {
        return userService.getUsersPaginated(requestBody.getsEcho(), requestBody.getFirstRowId(),
                                             requestBody.getRowsPerPage(), requestBody.getSortCol());
    }

}
