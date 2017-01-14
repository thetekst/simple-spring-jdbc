package com.dmitry.tkachenko.spring.jdbc.dao.impl;

import com.dmitry.tkachenko.spring.jdbc.dao.objects.Mp3;
import com.dmitry.tkachenko.spring.jdbc.dao.interfaces.Mp3DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dmitry tkachenko on 1/11/17.
 */
@Component("sqliteDAO")
public class SQLiteDAO implements Mp3DAO {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private SimpleJdbcInsert insertMp3;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertMp3 = new SimpleJdbcInsert(dataSource)
                .withTableName("mp3").usingColumns("name", "author")
                .usingGeneratedKeyColumns("id");
    }

    public int insert(Mp3 mp3) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", mp3.getName());
        params.addValue("author", mp3.getAuthor());

        return insertMp3.executeAndReturnKeyHolder(params).getKey().intValue();
    }

    public int insert(List<Mp3> mp3List) {
//        String sql = "insert into mp3 (name, author) VALUES (:name, :author)";
        String sql = "insert into mp3 (name, author) VALUES (:name, :author)";
        SqlParameterSource[] batch = SqlParameterSourceUtils
                .createBatch(mp3List.toArray());

        return jdbcTemplate.batchUpdate(sql, batch).length;
    }

    public void delete(Mp3 mp3) {
        delete(mp3.getId());
    }

    public void delete(int id) {
        String sql = "delete from mp3 where id=:id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbcTemplate.update(sql, params);
    }

    public int total(){
        String sql = "SELECT COUNT(*) FROM mp3";
        return jdbcTemplate.getJdbcOperations().queryForObject(sql, Integer.class);
    }

    public Mp3 getMp3ByID(int id) {
        String sql = "select * from mp3 where id = :id";

        try {
            SqlParameterSource params = new MapSqlParameterSource("id", id);
            return jdbcTemplate.queryForObject(sql, params, new Mp3RowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Mp3> getMp3ListByName(String name) {
        String sql = "select * from mp3 where upper(name) like :name";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", "%" + name.toUpperCase() + "%");

        return jdbcTemplate.query(sql, params, new Mp3RowMapper());
    }

    public List<Mp3> getMp3ListByAuthor(String author) {
        String sql = "select * from mp3 where upper(author) like :author";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author", "%" + author.toUpperCase() + "%");

        return jdbcTemplate.query(sql, params, new Mp3RowMapper());
    }

    private static final class Mp3RowMapper implements RowMapper<Mp3> {

        public Mp3 mapRow(ResultSet resultSet, int i) throws SQLException {
            Mp3 mp3 = new Mp3();
            mp3.setId(resultSet.getInt("id"));
            mp3.setName(resultSet.getString("name"));
            mp3.setAuthor(resultSet.getString("author"));
            return mp3;
        }
    }
}
