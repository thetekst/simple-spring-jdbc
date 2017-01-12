package com.dmitry.tkachenko.spring.jdbc.dao.impl;

import com.dmitry.tkachenko.spring.jdbc.dao.objects.Mp3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitry tkachenko on 1/12/17.
 */
public class SQLiteDAOTest {

    private SQLiteDAO sqLiteDAO;

    public SQLiteDAOTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        sqLiteDAO = (SQLiteDAO) context.getBean("sqliteDAO");
    }

    @org.testng.annotations.Test
    public void insert() {
        Mp3 mp3 = new Mp3("track-04", "Muse");
        sqLiteDAO.insert(mp3);
    }

    @org.testng.annotations.Test
    public void insertList() {
        List<Mp3> mp3List = new ArrayList<Mp3>();
        Mp3 track1 = new Mp3("track-05", "Muse");
        Mp3 track2 = new Mp3("track-06", "Muse");

        mp3List.add(track1);
        mp3List.add(track2);
        sqLiteDAO.insert(mp3List);
    }

    @org.testng.annotations.Test
    public void deleteById() {
        sqLiteDAO.delete(13);
    }

    @org.testng.annotations.Test
    public void deleteObject() throws Exception {
        Mp3 mp3 = sqLiteDAO.getMp3ByID(12);
        if (null != mp3) {
            sqLiteDAO.delete(mp3);
        }
    }

    @org.testng.annotations.Test
    public void total() throws Exception {
        int total = sqLiteDAO.total();
        System.out.println(total);
    }

    @org.testng.annotations.Test
    public void getMp3ByID() {
        Mp3 mp3 = sqLiteDAO.getMp3ByID(1);
        if (null != mp3) {
            System.out.println(mp3.getId() + ". " + mp3.getAuthor() + " - " + mp3.getName());
        }
    }

    @org.testng.annotations.Test
    public void getMp3ListByName() throws Exception {
        List<Mp3> mp3List = sqLiteDAO.getMp3ListByName("track-04");
        System.out.println(mp3List.size());
    }

    @org.testng.annotations.Test
    public void getMp3ListByAuthor() throws Exception {
        List<Mp3> mp3List = sqLiteDAO.getMp3ListByAuthor("Muse");
        System.out.println(mp3List.size());
    }

}