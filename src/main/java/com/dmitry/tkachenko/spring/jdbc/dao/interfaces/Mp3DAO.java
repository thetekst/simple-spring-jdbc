package com.dmitry.tkachenko.spring.jdbc.dao.interfaces;
import com.dmitry.tkachenko.spring.jdbc.dao.objects.Mp3;

import java.util.List;

/**
 * Created by dmitry tkachenko on 1/11/17.
 */
public interface Mp3DAO {

    void insert(Mp3 mp3);

    void insert(List<Mp3> mp3List);

    void  delete(int id);

    void  delete(Mp3 mp3);

    Mp3 getMp3ByID(int id);

    List<Mp3> getMp3ListByName(String name);

    List<Mp3> getMp3ListByAuthor(String author);

    int total();
}
