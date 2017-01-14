package com.dmitry.tkachenko.spring.jdbc.dao.impl;

import com.dmitry.tkachenko.spring.jdbc.dao.interfaces.Mp3DAO;
import com.dmitry.tkachenko.spring.jdbc.dao.objects.Mp3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by dmitry tkachenko on 1/12/17.
 */
public class SQLiteDAOTest {

    private Mp3DAO mp3DAO;

    public SQLiteDAOTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        mp3DAO = context.getBean(SQLiteDAO.class);
    }

    @Test
    public void insertValid() {
        Mp3 mp3 = new Mp3("track-04", "Muse");
        int key = mp3DAO.insert(mp3);
        System.out.println(key);
    }

    @Test
    public void insertListValid() {
        List<Mp3> mp3List = new ArrayList<Mp3>();
        Mp3 track1 = new Mp3("track-05", "Muse");
        Mp3 track2 = new Mp3("track-06", "Muse");

        mp3List.add(track1);
        mp3List.add(track2);
        int keys = mp3DAO.insert(mp3List);
        assertEquals(keys, 2);
    }

    @Test
    public void deleteByIdValid() {
        mp3DAO.delete(14);
    }

    @Test
    public void deleteByIdWrong() {
        mp3DAO.delete(0);
    }

    @Test
    public void deleteObjectValid() {
        Mp3 mp3 = mp3DAO.getMp3ByID(15);
        if (null != mp3) {
            mp3DAO.delete(mp3);
        }
    }

    @Test
    public void deleteObjectWrong() {
        Mp3 mp3 = mp3DAO.getMp3ByID(0);
        if (null != mp3) {
            mp3DAO.delete(mp3);
        }
    }

    @Test
    public void total() {
        int total = mp3DAO.total();
        System.out.println(total);
    }

    @Test
    public void getMp3ByIDValid() {
        Mp3 mp3 = mp3DAO.getMp3ByID(2);
        if (null != mp3) {
            System.out.println(mp3.getId() + ". " + mp3.getAuthor() + " - " + mp3.getName());
        }
    }

    @Test
    public void getMp3ByIDWrong() {
        Mp3 mp3 = mp3DAO.getMp3ByID(0);
        if (null != mp3) {
            System.out.println(mp3.getId() + ". " + mp3.getAuthor() + " - " + mp3.getName());
        }
    }

    @Test
    public void getMp3ListByNameValid() {
        List<Mp3> mp3List = mp3DAO.getMp3ListByName("track-0");
        System.out.println(mp3List.size());
    }

    @Test
    public void getMp3ListByNameEmpty() {
        List<Mp3> mp3List = mp3DAO.getMp3ListByName("");
        System.out.println(mp3List.size());
    }

    @Test
    public void getMp3ListByNameNull() {
        try {
            List<Mp3> mp3List = mp3DAO.getMp3ListByName(null);
            System.out.println(mp3List.size());
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void getMp3ListByAuthorValid() {
        List<Mp3> mp3List = mp3DAO.getMp3ListByAuthor("Muse");
        System.out.println(mp3List.size());
    }

    @Test
    public void getMp3ListByAuthorEmpty() {
        List<Mp3> mp3List = mp3DAO.getMp3ListByAuthor("");
        System.out.println(mp3List.size());
    }

    @Test
    public void getMp3ListByAuthorNull() {
        try {
            List<Mp3> mp3List = mp3DAO.getMp3ListByAuthor(null);
            System.out.println(mp3List.size());
        } catch (NullPointerException e) {

        }
    }

}