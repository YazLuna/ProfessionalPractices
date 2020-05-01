package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException ;
import java.text.SimpleDateFormat;
import java.util.Date;

public class windowsPrincipal extends Application {
    @Override
    public void start(Stage stageLogin) throws  IOException {


        Parent rootLogin = FXMLLoader.load(getClass().getResource("/gui/login/FXMLLogin.fxml"));
        Scene sceneLogin = new Scene(rootLogin);
        stageLogin.setScene(sceneLogin);
        stageLogin.setResizable(false);
        stageLogin.show();
    }
    public static void main(String[] args) {
            Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate

            System.out.println(objDate);
            String strDateFormat = "hh: mm: ss a dd-MMM-aaaa"; // El formato de fecha está especificado
            SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objetode formato de fecha
            System.out.println(objSDF.format(objDate)); // El formato de fecha se aplica a la fecha actual

        Date myDate = new Date();
        //Aquí obtienes el formato que deseas
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate));


        launch(args);
    }

}
