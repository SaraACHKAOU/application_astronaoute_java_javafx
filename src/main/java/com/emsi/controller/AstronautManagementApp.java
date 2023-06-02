package com.emsi.controller;
import com.emsi.entities.Astronaute;
import com.emsi.services.AstronauteService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public abstract class AstronautManagementApp extends Application {

    private AstronauteService astronauteService = new AstronauteService();
    private ObservableList<Astronaute> astronautList = FXCollections.observableArrayList();

    private TableView<Astronaute> tableView;
    private TextField idField;
    private TextField nameField;
    private TextField agencyField;
    private TextField heightField;
    private TextField weightField;
    private TextField experienceField;
    private TextField gradeField;

    public static void main(String[] args) {
        launch(args);
    }

    public void launchApp() throws Exception {
        Stage primaryStage = new Stage();
        start(primaryStage);

        // Create form fields
        idField = new TextField();
        nameField = new TextField();
        agencyField = new TextField();
        heightField = new TextField();
        weightField = new TextField();
        experienceField = new TextField();
        gradeField = new TextField();

        // Create buttons
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button importButton = new Button("Import");
        Button exportButton = new Button("Export");

        // Set button actions
        addButton.setOnAction(event -> addAstronaut());
        updateButton.setOnAction(event -> updateAstronaut());
        deleteButton.setOnAction(event -> deleteAstronaut());
        importButton.setOnAction(event -> importData());
        exportButton.setOnAction(event -> exportData());

        // Create table columns
        TableColumn<Astronaute, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Astronaute, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Astronaute, String> agencyColumn = new TableColumn<>("Agence");
        TableColumn<Astronaute, Integer> heightColumn = new TableColumn<>("Height");
        TableColumn<Astronaute, Integer> weightColumn = new TableColumn<>("Weight");
        TableColumn<Astronaute, Integer> experienceColumn = new TableColumn<>("Years of Experience");
        TableColumn<Astronaute, String> gradeColumn = new TableColumn<>("Grade");

        // Configure column mapping
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        agencyColumn.setCellValueFactory(new PropertyValueFactory<>("agence"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // Create table view
        tableView = new TableView<>();
        tableView.setItems(astronautList);
        tableView.getColumns().addAll(idColumn, nameColumn, agencyColumn, heightColumn, weightColumn, experienceColumn, gradeColumn);

        // Create form layout
        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(10));
        formLayout.setHgap(10);
        formLayout.setVgap(5);
        formLayout.addRow(0, new Label("ID:"), idField);
        formLayout.addRow(1, new Label("Name:"), nameField);
        formLayout.addRow(2, new Label("Agence:"), agencyField);
        formLayout.addRow(3, new Label("Height:"), heightField);
        formLayout.addRow(4, new Label("Weight:"), weightField);
        formLayout.addRow(5, new Label("Years of Experience:"), experienceField);
        formLayout.addRow(6, new Label("Grade:"), gradeField);
        formLayout.addRow(7, addButton, updateButton, deleteButton);
        formLayout.addRow(8, importButton, exportButton);
        // Create root layout
        VBox rootLayout = new VBox(tableView, formLayout);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        // Create scene
        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial data
        loadAstronauts();
    }

    private void loadAstronauts() {
        List<Astronaute> astronauts = astronauteService.findAll();
        astronautList.clear();
        astronautList.addAll(astronauts);
    }

    private void addAstronaut() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String agence = agencyField.getText();
        int height = Integer.parseInt(heightField.getText());
        int weight = Integer.parseInt(weightField.getText());
        int yearsOfExperience = Integer.parseInt(experienceField.getText());
        String grade = gradeField.getText();

        Astronaute astronaut = new Astronaute(id, name, yearsOfExperience, agence, height, weight, grade);
        astronauteService.save(astronaut);
        astronautList.add(astronaut);

        clearFormFields();
    }

    private void updateAstronaut() {
        Astronaute selectedAstronaut = tableView.getSelectionModel().getSelectedItem();
        if (selectedAstronaut != null) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String agence = agencyField.getText();
            int height = Integer.parseInt(heightField.getText());
            int weight = Integer.parseInt(weightField.getText());
            int experience = Integer.parseInt(experienceField.getText());
            String grade = gradeField.getText();

            selectedAstronaut.setId(id);
            selectedAstronaut.setName(name);
            selectedAstronaut.setAgence(agence);
            selectedAstronaut.setHeight(height);
            selectedAstronaut.setWeight(weight);
            selectedAstronaut.setYearsOfExperience(experience);
            selectedAstronaut.setGrade(grade);

            astronauteService.update(selectedAstronaut);
            loadAstronauts();

            clearFormFields();
        }
    }

    private void deleteAstronaut() {
        Astronaute selectedAstronaut = tableView.getSelectionModel().getSelectedItem();
        if (selectedAstronaut != null) {
            astronauteService.remove(selectedAstronaut);
            loadAstronauts();
            clearFormFields();
        }
    }

    private void importData() {
        supprimerTous();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String filePath = file.getAbsolutePath();
            String extension = getFileExtension(file.getName());

            switch (extension) {
                case "json":
                    astronauteService.importFromJson(filePath);
                    break;
                case "xls":
                    astronauteService.importFromExcel(filePath);
                    break;
                case "txt":
                    astronauteService.importFromText(filePath);
                    break;
                default:
                    System.err.println("Unsupported file format: " + extension);
            }

            loadAstronauts();
        }
    }

    private void exportData() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            String filePath = file.getAbsolutePath();
            String extension = getFileExtension(file.getName());

            switch (extension) {
                case "json":
                    astronauteService.exportToJson(filePath);
                    break;
                case "xls":
                    astronauteService.exportToExcel(filePath);
                    break;
                case "txt":
                    astronauteService.exportToText(filePath);
                    break;
                default:
                    System.err.println("Unsupported file format: " + extension);
            }
        }
    }


    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private void clearFormFields() {
        idField.clear();
        nameField.clear();
        agencyField.clear();
        heightField.clear();
        weightField.clear();
        experienceField.clear();
        gradeField.clear();
    }
    public List<Astronaute> GetAll(){
            return astronauteService.findAll();
    }
    public void supprimerTous(){
        for (Astronaute astronaute:GetAll())
            astronauteService.remove(astronaute);

    }
}
