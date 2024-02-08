package com.example.gcalc.SettingsControllers;

import com.example.gcalc.GCMain;
import com.example.gcalc.Launchers.readOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;

import java.io.IOException;

public class settings {

    public Button graphingGeneral;
    public Button graphingStyle;
    public Button graphingPerformance;
    public Button graphingUnits;
    public Button graphingImport;
    public SubScene _generalSceneMid;
    public Group generalSceneMid;
    public Button generalGeneral;
    public Button generalStyle;
    public Button generalPerformance;
    public Button generalUnits;
    public Button generalImport;

    public void setAccuracy(DragEvent dragEvent) throws IOException {
        readOptions options = new readOptions("options.txt");
        options.setAccuracy(0.005);
    }

    private void loadFXMLAndAddToScene(String path, String fileName) {
        try {
            generalSceneMid.getChildren().add(new FXMLLoader(GCMain.class.getResource(path + fileName)).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void openSettingsMenu(ActionEvent actionEvent) {
        String graphing = "Settings/SettingsSubPanes/graphingSettings/", physics = "Settings/SettingsSubPanes/physicsSettings/", general = "Settings/SettingsSubPanes/generalSettings/";
        Button button = (Button) actionEvent.getSource();
        switch (button.getId()) {
            case "graphingGeneral" -> loadFXMLAndAddToScene(graphing, "general.fxml");
            case "graphingStyle" -> loadFXMLAndAddToScene(graphing, "style.fxml");
            case "graphingPerformance" -> loadFXMLAndAddToScene(graphing, "performance.fxml");
            case "graphingUnits" -> loadFXMLAndAddToScene(graphing, "units.fxml");
            case "graphingImport" -> loadFXMLAndAddToScene(graphing, "import.fxml");
            case "generalGeneral" -> loadFXMLAndAddToScene(general, "general.fxml");
            case "generalStyle" -> loadFXMLAndAddToScene(general, "style.fxml");
            case "generalPerformance" -> loadFXMLAndAddToScene(general, "performance.fxml");
            case "generalUnits" -> loadFXMLAndAddToScene(general, "units.fxml");
            case "generalImport" -> loadFXMLAndAddToScene(general, "import.fxml");
            case "physicsGeneral" -> loadFXMLAndAddToScene(physics, "general.fxml");
            case "physicsStyle" -> loadFXMLAndAddToScene(physics, "style.fxml");
            case "physicsPerformance" -> loadFXMLAndAddToScene(physics, "performance.fxml");
            case "physicsUnits" -> loadFXMLAndAddToScene(physics, "units.fxml");
            case "physicsImport" -> loadFXMLAndAddToScene(physics, "import.fxml");
            default -> throw new IllegalStateException("Unexpected value: " + button.getId());
        }
    }
}
