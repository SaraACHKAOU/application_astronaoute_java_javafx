package com.emsi.services;

import com.emsi.dao.AstronauteDao;
import com.emsi.dao.impl.AstronauteDaoImp;
import com.emsi.entities.Astronaute;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class AstronauteService  {

        private final AstronauteDao astronauteDao = new AstronauteDaoImp();

        public List<Astronaute> findAll() {
            return astronauteDao.findAll();
        }
    public Astronaute getAstronauteById(int id) {
        return astronauteDao.findById(id);
    }

        public void save(Astronaute astronaute) {
            astronauteDao.insert(astronaute);
        }

        public void update(Astronaute astronaute) {
            try {
                if (astronaute != null) {
                    astronauteDao.update(astronaute);
                     System.out.println(astronaute);
                    System.out.println("Astronaut updated successfully.");
                } else {
                    throw new IllegalArgumentException("Error updating astronaut: Astronaut cannot be null.");
                }
            } catch (Exception e) {
                System.err.println("Error updating astronaut: " + e.getMessage());
            }
        }

        public void remove(Astronaute astronaute) {
            try {
                if (astronaute != null) {
                    astronauteDao.deleteById(astronaute.getId());
                    System.out.println("Astronaut removed successfully.");
                } else {
                    throw new IllegalArgumentException("Error removing astronaut: Astronaut cannot be null.");
                }
            } catch (Exception e) {
                System.err.println("Error removing astronaut: " + e.getMessage());
            }
        }

    public void importFromExcel(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                String agence = row.getCell(2).getStringCellValue();
                int height = (int) row.getCell(3).getNumericCellValue();
                int weight = (int) row.getCell(4).getNumericCellValue();
                int yearsOfExperience = (int) row.getCell(5).getNumericCellValue();
                String grade = row.getCell(6).getStringCellValue();

                Astronaute astronaute = new Astronaute(id, name, yearsOfExperience,agence, height, weight, grade);
                astronauteDao.insert(astronaute);
            }

            fis.close();

            System.out.println("Data imported from Excel successfully.");
        } catch (IOException e) {
            System.err.println("Error importing data from Excel: " + e.getMessage());
        }
    }

    public void importFromText(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                int id = Integer.parseInt(values[0]);
                String name = values[1];
                String agence = values[2];
                int height = Integer.parseInt(values[3]);
                int weight = Integer.parseInt(values[4]);
                int yearsOfExperience = Integer.parseInt(values[5]);
                String grade = values[6];

                Astronaute astronaute = new Astronaute(id, name, yearsOfExperience,agence, height, weight, grade);
                astronauteDao.insert(astronaute);
            }

            reader.close();

            System.out.println("Data imported from text file successfully.");
        } catch (IOException e) {
            System.err.println("Error importing data from text file: " + e.getMessage());
        }
    }

    public void importFromJson(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Astronaute> astronautes = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<Astronaute>>() {});

            for (Astronaute astronaute : astronautes) {
                astronauteDao.insert(astronaute);
            }

            System.out.println("Data imported from JSON: " + jsonFilePath);
        } catch (IOException e) {
            System.err.println("Error importing data from JSON: " + e.getMessage());
        }
    }

    public void exportToExcel(String filePath) {
        try {
            List<Astronaute> astronautes = astronauteDao.findAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Astronautes");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Agence");
            headerRow.createCell(3).setCellValue("Height");
            headerRow.createCell(4).setCellValue("Weight");
            headerRow.createCell(5).setCellValue("Years of Experience");
            headerRow.createCell(6).setCellValue("Grade");

            int rowNum = 1;
            for (Astronaute astronaute : astronautes) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(astronaute.getId());
                dataRow.createCell(1).setCellValue(astronaute.getName());
                dataRow.createCell(2).setCellValue(astronaute.getAgence());
                dataRow.createCell(3).setCellValue(astronaute.getHeight());
                dataRow.createCell(4).setCellValue(astronaute.getWeight());
                dataRow.createCell(5).setCellValue(astronaute.getYearsOfExperience());
                dataRow.createCell(6).setCellValue(astronaute.getGrade());
            }

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);

            fos.close();

            System.out.println("Data exported to Excel successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting data to Excel: " + e.getMessage());
        }
    }

    public void exportToText(String filePath) {
        try {
            List<Astronaute> astronautes = astronauteDao.findAll();

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            for (Astronaute astronaute : astronautes) {
                String sb = astronaute.getId() + "," +
                        astronaute.getName() + "," +
                        astronaute.getAgence() + "," +
                        astronaute.getHeight() + "," +
                        astronaute.getWeight() + "," +
                        astronaute.getYearsOfExperience() + "," +
                        astronaute.getGrade() + "\n";
                writer.write(sb);
            }

            writer.close();

            System.out.println("Data exported to text file successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting data to text file: " + e.getMessage());
        }
    }
    public void exportToJson(String filePath) {
        try {
            List<Astronaute> astronautes = astronauteDao.findAll();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(astronautes);

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
            writer.close();

            System.out.println("Data exported to JSON file successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting data to JSON file: " + e.getMessage());
        }
    }


}


