package com.outcons.interviewtask.persistence.dao;

import java.util.List;

import com.outcons.interviewtask.models.User;
import com.outcons.interviewtask.persistence.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDAO
{
    private static final RowMapper<User> rowMapper = (resultSet, i) ->
    {
        final User u = new User();

        u.setId(resultSet.getInt("id"));
        u.setName(resultSet.getString("name"));
        u.setSurname(resultSet.getString("surname"));
        u.setEmail(resultSet.getString("email"));

        return u;
    };

    private final UserRepository userRepository;

    private final JdbcTemplate jdbcTemplate;


    public UserDAO(final UserRepository userRepository, final JdbcTemplate jdbcTemplate)
    {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }


    public int getUserRecordsCount()
    {
        return (int) userRepository.count();
    }


    public List<User> getUsersPaginated(final int start, final int end, final int sortColumn)
    {
        String orderCriteria = "";
        switch (sortColumn)
        {
            case 0:
                orderCriteria = "id";
                break;
            case 1:
                orderCriteria = "name";
                break;
            case 2:
                orderCriteria = "surname";
                break;
            case 3:
                orderCriteria = "email";
        }
        final String query =
                "SELECT id,name,surname,email FROM outcons.user ORDER BY " + orderCriteria + " LIMIT " + start + ',' + end;

        return jdbcTemplate.query(query, rowMapper);
    }
}
