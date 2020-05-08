package gui.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public class FXMLLoginController implements Initializable {
    @FXML private Button btnLogin;
    @FXML private TextField tfUsser;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void login(ActionEvent actionEvent) throws IOException {
        String usserLogin = tfUsser.getText();
        if(usserLogin.equalsIgnoreCase("Administrator")){
            btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    try {
                        Stage stagePrincipal = (Stage) btnLogin.getScene().getWindow();
                        stagePrincipal.close();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/administrator/fxml/FXMLMenuAdministrator.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch(Exception e) {
                        Logger logger = Logger.getLogger(getClass().getName());
                        logger.log(Level.SEVERE, "Failed to create new Window.", e);
                    }
                }
            });
        }else{
            if(usserLogin.equalsIgnoreCase("Coordinator")){
                btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        try {
                            Stage stagePrincipal = (Stage) btnLogin.getScene().getWindow();
                            stagePrincipal.close();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/coordinator/fxml/FXMLMenuCoordinator.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setResizable(false);
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch(Exception e) {
                            Logger logger = Logger.getLogger(getClass().getName());
                            logger.log(Level.SEVERE, "Failed to create new Window.", e);
                        }
                    }
                });
            }else{
                if(usserLogin.equalsIgnoreCase("Practitioner")){
                    btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            try {
                                Stage stagePrincipal = (Stage) btnLogin.getScene().getWindow();
                                stagePrincipal.close();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/practitioner/fxml/FXMLMenuPractitioner.fxml"));
                                Parent root1 = (Parent) fxmlLoader.load();
                                Stage stage = new Stage();
                                stage.setResizable(false);
                                stage.setScene(new Scene(root1));
                                stage.show();
                            } catch(Exception e) {
                                Logger logger = Logger.getLogger(getClass().getName());
                                logger.log(Level.SEVERE, "Failed to create new Window.", e);
                            }
                        }
                    });
                }else{
                    if(usserLogin.equalsIgnoreCase("Teacher")){
                        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                try {
                                    Stage stagePrincipal = (Stage) btnLogin.getScene().getWindow();
                                    stagePrincipal.close();
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/teacher/FXMLMenuTeacher.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.setResizable(false);
                                    stage.setScene(new Scene(root1));
                                    stage.show();
                                } catch(Exception e) {
                                    Logger logger = Logger.getLogger(getClass().getName());
                                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                                }
                            }
                        });
                    }else{
                        tfUsser.setText("This usser is wrong");
                    }
                }
            }
        }


    }
}
