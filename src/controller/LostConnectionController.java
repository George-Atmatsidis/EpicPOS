/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Epic
 */
public class LostConnectionController implements Initializable {

    @FXML
    private MaskerPane mplostConnection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mplostConnection.setVisible(true);

    }

}
