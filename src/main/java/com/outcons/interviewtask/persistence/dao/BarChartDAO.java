package com.outcons.interviewtask.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.outcons.interviewtask.models.bar_chart.BarChartProjectDTO;
import com.outcons.interviewtask.models.bar_chart.BarChartUserDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class BarChartDAO
{
    private static final RowMapper<BarChartUserDTO> topUsersMapper = (resultSet, i) ->
    {
        final BarChartUserDTO obj = new BarChartUserDTO();

        obj.setUser_id(resultSet.getInt("user_id"));
        obj.setFull_name(resultSet.getString("full_name"));
        obj.setTime(resultSet.getFloat("time"));
        obj.setCompared(false);

        return obj;
    };

    private static final RowMapper<BarChartProjectDTO> topProjectsMapper = (resultSet, i) ->
    {
        final BarChartProjectDTO obj = new BarChartProjectDTO();

        obj.setUser_id(resultSet.getInt("user_id"));
        obj.setFull_name(resultSet.getString("full_name"));
        obj.setProject_id(resultSet.getInt("project_id"));
        obj.setProject_name(resultSet.getString("project_name"));
        obj.setTime(resultSet.getFloat("time"));
        obj.setCompared(false);

        return obj;
    };

    private final JdbcTemplate jdbcTemplate;


    public BarChartDAO(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }


    public BarChartUserDTO getSingleUserTime(final int userId, final Optional<String> from,
                                             final Optional<String> to)
    {
        final String sql = "SELECT " +
                "    u.id AS user_id," +
                "    CONCAT(u.name, ' ', u.surname) AS full_name," +
                "    SUM(tl.time) AS time " +
                "FROM " +
                "    outcons.time_log AS tl " +
                "        LEFT JOIN " +
                "    outcons.user AS u ON tl.user_id = u.id " +
                "WHERE " +
                "    user_id = " + userId +
                whereClauseParserFromToDate(from, to, true);
        final BarChartUserDTO obj = jdbcTemplate.queryForObject(sql, topUsersMapper);
        obj.setCompared(true);
        return obj;
    }


    public List<BarChartUserDTO> getTopTenUsers(final Optional<String> from,
                                                final Optional<String> to)
    {
        final String sql = "SELECT " +
                "    u.id as user_id," +
                "    CONCAT(u.name,' ', u.surname) AS full_name," +
                "    SUM(tl.time) AS time " +
                "FROM" +
                "    outcons.time_log AS tl " +
                "        LEFT JOIN" +
                "    outcons.user AS u ON tl.user_id = u.id " +
                whereClauseParserFromToDate(from, to, false) +
                "GROUP BY user_id " +
                "ORDER BY time DESC " +
                "LIMIT 1 , 10;";

        return jdbcTemplate.query(sql, topUsersMapper);
    }


    public List<BarChartProjectDTO> getTopTenUsersForProject(final Optional<String> from,
                                                             final Optional<String> to)
    {
        final String sql = "SELECT \n" +
                "    u.id AS user_id,\n" +
                "    tl.project_id,\n" +
                "    p.name as project_name,\n" +
                "    CONCAT(u.name, ' ', u.surname) AS full_name,\n" +
                "    SUM(tl.time) AS time\n" +
                "FROM\n" +
                "    outcons.time_log AS tl\n" +
                "        LEFT JOIN\n" +
                "    outcons.user AS u ON tl.user_id = u.id\n" +
                "        LEFT JOIN\n" +
                "    outcons.project AS p ON tl.project_id = p.id\n" +
                whereClauseParserFromToDate(from, to, false) +
                "GROUP BY user_id , project_id\n" +
                "ORDER BY time DESC\n" +
                "LIMIT 1 , 10;";
        return jdbcTemplate.query(sql, topProjectsMapper);
    }


    public BarChartProjectDTO getSingleUserForProject(final int userId, final Optional<String> from,
                                                      final Optional<String> to)
    {
        final String sql = "SELECT \n" +
                "    u.id AS user_id,\n" +
                "    tl.project_id,\n" +
                "    p.name AS project_name,\n" +
                "    CONCAT(u.name, ' ', u.surname) AS full_name,\n" +
                "    SUM(tl.time) AS time\n" +
                "FROM\n" +
                "    outcons.time_log AS tl\n" +
                "        LEFT JOIN\n" +
                "    outcons.user AS u ON tl.user_id = u.id\n" +
                "        LEFT JOIN\n" +
                "    outcons.project AS p ON tl.project_id = p.id\n" +
                "WHERE\n" +
                "    user_id = " + userId +
                whereClauseParserFromToDate(from, to, true) +
                " GROUP BY user_id , project_id\n" +
                " ORDER BY time DESC\n" +
                " LIMIT 1;";
        final BarChartProjectDTO obj = jdbcTemplate.queryForObject(sql, topProjectsMapper);
        obj.setCompared(true);
        return obj;
    }


    private String whereClauseParserFromToDate(final Optional<String> from, final Optional<String> to,
                                               boolean addAndBeforeWhereClause)
    {
        if (ObjectUtils.isEmpty(from.get()) || ObjectUtils.isEmpty(to.get()))
        {
            return "";
        }

        final String whereClause =
                " tl.date >= '" + from.get() + "' AND tl.date <= '" + to.get() + "' ".replaceAll(
                        "/",
                        "-");
        return addAndBeforeWhereClause ? " AND " + whereClause : " WHERE " + whereClause;
    }
}
