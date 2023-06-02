package com.emsi.dao.impl;

import com.emsi.dao.AstronauteDao;
import com.emsi.entities.Astronaute;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AstronauteDaoImp implements AstronauteDao {

    private Connection conn = DB.getConnection();

    @Override
    public void insert(Astronaute astronaute) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO astronaute (Name, Agence, Height, Weight, YearsOfExperience, Grade) " +
                    "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, astronaute.getName());
            ps.setString(2, astronaute.getAgence());
            ps.setInt(3, astronaute.getHeight());
            ps.setInt(4, astronaute.getWeight());
            ps.setInt(5, astronaute.getYearsOfExperience());
            ps.setString(6, astronaute.getGrade());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    astronaute.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("No rows affected");
            }
        } catch (SQLException e) {
            System.err.println("Failed to insert an astronaut");
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Astronaute astronaute) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE astronaute SET Name = ?, Agence = ?, Height = ?, Weight = ?, " +
                    "YearsOfExperience = ?, Grade = ? WHERE Id = ?");

            ps.setString(1, astronaute.getName());
            ps.setString(2, astronaute.getAgence());
            ps.setInt(3, astronaute.getHeight());
            ps.setInt(4, astronaute.getWeight());
            ps.setInt(5, astronaute.getYearsOfExperience());
            ps.setString(6, astronaute.getGrade());
            ps.setInt(7, astronaute.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update an astronaut");
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM astronaute WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete an astronaut");
        } finally {
            DB.closeStatement(ps);
        }
    }


    @Override
    public Astronaute findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM astronaute WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Astronaute astronaute = new Astronaute();

                astronaute.setId(rs.getInt("Id"));
                astronaute.setName(rs.getString("Name"));
                astronaute.setAgence(rs.getString("Agence"));
                astronaute.setHeight(rs.getInt("Height"));
                astronaute.setWeight(rs.getInt("Weight"));
                astronaute.setYearsOfExperience(rs.getInt("YearsOfExperience"));
                astronaute.setGrade(rs.getString("Grade"));

                return astronaute;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un département");
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Astronaute> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM astronaute");
            rs = ps.executeQuery();

            List<Astronaute> listAstronaute = new ArrayList<>();

            while (rs.next()) {
                Astronaute astronaute = new Astronaute();

                astronaute.setId(rs.getInt("Id"));
                astronaute.setName(rs.getString("Name"));
                astronaute.setAgence(rs.getString("Agence"));
                astronaute.setHeight(rs.getInt("Height"));
                astronaute.setWeight(rs.getInt("Weight"));
                astronaute.setYearsOfExperience(rs.getInt("YearsOfExperience"));
                astronaute.setGrade(rs.getString("Grade"));

                listAstronaute.add(astronaute);
            }

            return listAstronaute;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner un astronaute");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

}

