package com.emsi.dao;

import com.emsi.entities.Astronaute;

import java.util.List;

public interface AstronauteDao {

        void insert(Astronaute astronaute);
        void update(Astronaute astronaute);

        void deleteById(int id);

        Astronaute findById(int id);

        List<Astronaute> findAll();
}
