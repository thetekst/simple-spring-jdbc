package com.dmitry.tkachenko.spring.jdbc.dao.impl;

import com.dmitry.tkachenko.spring.jdbc.dao.objects.Mp3;
import com.dmitry.tkachenko.spring.jdbc.dao.interfaces.Mp3DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Mp3 mp3) {
        String sql = "insert into mp3 (name, author) VALUES (?, ?)";
        jdbcTemplate.update(sql, new Object[] { mp3.getName(), mp3.getAuthor() });
    }

    public void insert(List<Mp3> mp3List) {
        for (Mp3 mp3 : mp3List) {
            insert(mp3);
        }
    }

    public void delete(Mp3 mp3) {
        delete(mp3.getId());
    }

    public void delete(int id) {
        String sql = "delete from mp3 where id=?";
        jdbcTemplate.update(sql, id);
    }

    public int total(){
        String sql = "SELECT COUNT(*) FROM mp3";
        return jdbcTemplate.queryForObject(sql, int.class);
    }

    public Mp3 getMp3ByID(int id) {
        String sql = "select * from mp3 where id=?";
        Mp3 mp3Result = null;

        try {
            mp3Result = jdbcTemplate.queryForObject(sql, new RowMapper<Mp3>() {
                public Mp3 mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Mp3 mp3 = new Mp3();
                    mp3.setId(rs.getInt("id"));
                    mp3.setName(rs.getString("name"));
                    mp3.setAuthor(rs.getString("author"));
                    return mp3;
                }
            }, id);
        } catch (EmptyResultDataAccessException e) {

        }

        return mp3Result;
    }

    public List<Mp3> getMp3ListByName(String name) {
        String sql = "select * from mp3 where name=?";
        List<Mp3> mp3 = jdbcTemplate.query(sql, new RowMapper<Mp3>() {
            public Mp3 mapRow(ResultSet rs, int rowNum) throws SQLException {
                return getMp3Obj(rs);
            }
        }, name);
        return mp3;
    }

    public List<Mp3> getMp3ListByAuthor(String author) {
        String sql = "select * from mp3 where author=?";
        List<Mp3> mp3 = jdbcTemplate.query(sql, new RowMapper<Mp3>() {
            public Mp3 mapRow(ResultSet rs, int rowNum) throws SQLException {
                return getMp3Obj(rs);
            }
        }, author);
        return mp3;
    }

    private Mp3 getMp3Obj(ResultSet rs) throws SQLException {
        return new Mp3(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("author")
        );
    }
}
