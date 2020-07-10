package gui.practitioner.controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;

/**
 * Menu Practitioner Controller
 * @author Yazmin
 * @version 29/06/2020
 */
public class FXMLMenuPractitionerController extends FXMLGeneralController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    /**
     * Method to exit the system
     */
    public void logOutPractitioner() {
        logOutGeneral();
    }

}
