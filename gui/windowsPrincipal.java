package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException ;

public class windowsPrincipal extends Application {
    @Override
    public void start(Stage stageLogin) throws  IOException {


        Parent rootLogin = FXMLLoader.load(getClass().getResource("/gui/login/FXMLLogin.fxml"));
        Scene sceneLogin = new Scene(rootLogin);
        stageLogin.setScene(sceneLogin);
        stageLogin.setResizable(false);
        stageLogin.show();
        //System.out.println("Hola");
    }
    public static void main(String[] args) {
        launch(args);

    }

}



