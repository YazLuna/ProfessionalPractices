package gui.coordinator.controller;

import domain.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * class FXMLChooseProjectController
 * @author MARTHA
 * @version 08/05/2020
 */
public class FXMLChooseProjectController implements Initializable {
    @FXML private Button btnLogout;
    @FXML private Button btnBehind;
    @FXML private AnchorPane apPanel;
    @FXML private ScrollPane spSpace;
    @FXML private GridPane gpPanel;
    private static String controller;
    private Project project = new Project();
    private List<Project> getAllProject = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getProjects();
        btnBehind.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stagePrincipal = (Stage) btnBehind.getScene().getWindow();
                stagePrincipal.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml"));
                Stage stage = new Stage();
                try {
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage.setScene(new Scene(root1));
                } catch(Exception e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
                stage.setResizable(false);
                stage.show();
            }
        });
    }
    public void getProjects (){
        gpPanel.setAlignment(Pos.TOP_LEFT);
        Image imageProject;
        if(controller.equals("delete")) {
            getAllProject = project.listProjectsAvailable();
        }else {
            getAllProject = project.listProjects();
        }
        for(int index=0;index<getAllProject.size();index++) {
            int par= index%2;
            if(par!=0){
                imageProject = new Image("/gui/images/project2.png");
            }else{
                imageProject = new Image("/gui/images/project.png");
            }
            ImageView imageView = new ImageView();
            imageView.setImage(imageProject);
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            String name = getAllProject.get(index).getNameProject();
            Text nameProject = new Text(name);
            nameProject.setTextAlignment(TextAlignment.LEFT);
            nameProject.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
            nameProject.autosize();
            gpPanel.add(imageView,0,index);
            gpPanel.add(nameProject,1,index);
            chooseProject(nameProject, getAllProject.get(index));
        }
    }

    public void controllerSection (String controller){
        this.controller =controller;

    }

    public void chooseProject (Text nameProject, Project project){
        nameProject.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if (controller.equals("delete")) {
                        FXMLDeleteProjectController delete = new FXMLDeleteProjectController();
                        delete.setProject(project);
                        Stage stagePrincipal = (Stage) nameProject.getScene().getWindow();
                        stagePrincipal.close();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLDeleteProject.fxml"));
                        Stage stage = new Stage();
                        try {
                            Parent root1 = (Parent) fxmlLoader.load();
                            stage.setScene(new Scene(root1));
                        } catch(Exception e) {
                            Logger logger = Logger.getLogger(getClass().getName());
                            logger.log(Level.SEVERE, "Failed to create new Window.", e);
                        }
                        stage.setResizable(false);
                        stage.show();
                    }else{
                        if(controller.equals("update")) {
                        }
                    }
                }
            }
        });
    }
    
}
