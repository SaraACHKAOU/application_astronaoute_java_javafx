package com.emsi.entities;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.io.Serializable;
import java.util.Objects;

public class Astronaute implements Serializable {


    private int id;
    private String name;
    private String agence;
    private int height;
    private int weight;
    private int yearsOfExperience;
    private String grade;

    public Astronaute() {
    }

    public Astronaute(int id, String name , int yearsOfExperience, String agence , int height , int weight , String grade) {
        this.id = id;
        this.name = name;
        this.agence = agence;
        this.height = height;
        this.weight = weight;
        this.yearsOfExperience = yearsOfExperience;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getAgence() {
        return agence;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getGrade() {
        return grade;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Astronaute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", agence='" + agence + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", grade='" + grade + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Astronaute that = (Astronaute) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(agence, that.agence) && Objects.equals(height, that.height) && Objects.equals(weight, that.weight) && Objects.equals(yearsOfExperience, that.yearsOfExperience) && Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, agence, height, weight, yearsOfExperience, grade);
    }
}
