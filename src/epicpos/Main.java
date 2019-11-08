/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package epicpos;

import java.io.IOException;

//import controller.PosClientController;
import java.io.InputStream;

import java.sql.SQLException;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import beans.Employee;
import beans.EmployeeLogins;
import controller.BounceCircleController;

import controller.HomeController;
import controller.LoginController;
import controller.PosClientController;
import controller.ProgressFormController;

import tables.EmployeeLoginsManager;
import tables.EmployeeManager;

//import demo.model.User;
//import demo.security.Authenticator;
/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private static Main application;
    private Stage stage;
    private Employee loggedUser;
    public String sessionId;

    public Main() {
        application = this;
    }

    public void gotoHome() {
        try {
            HomeController posClient = (HomeController) posClient("/view/Home.fxml");
            posClient.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoLogin() {
        try {
            LoginController loginFrm = (LoginController) login("/view/login.fxml");
            loginFrm.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPosClient() {
        try {
            PosClientController posClient = (PosClientController) posClient("/view/PosClient.fxml");
            posClient.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void gotoProgressForm() {
        try {
            ProgressFormController bounce = (ProgressFormController) bounce("/view/ProgressForm.fxml");
            bounce.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable login(String fxml) throws IOException {
        stage.close();

        Stage stag = new Stage();
        this.stage = stag;
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;

        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        // Make scene occupy full screen
//      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//      Scene scene = new Scene(page, screenBounds.getWidth(), screenBounds.getHeight());
        Scene scene = new Scene(page);

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.sizeToScene();
        this.stage.show();

//      stage.setMaximized(true);
        return (Initializable) loader.getController();
    }
        private Initializable bounce(String fxml) throws IOException {
//        stage.close();

        Stage stag = new Stage();
        this.stage = stag;
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;

        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        // Make scene occupy full screen
//      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//      Scene scene = new Scene(page, screenBounds.getWidth(), screenBounds.getHeight());
        Scene scene = new Scene(page);

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.sizeToScene();
        this.stage.show();

//      stage.setMaximized(true);
        return (Initializable) loader.getController();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);

//      launch(args);
    }

    private Initializable posClient(String fxml) throws IOException {
        stage.close();
        stage.close();
        Stage stag = new Stage();
        this.stage = stag;
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;

        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        // Make scene occupy full screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(page, screenBounds.getWidth(), screenBounds.getHeight());
//      Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.sizeToScene();
        stage.show();
        stage.setMaximized(true);
        return (Initializable) loader.getController();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stage = primaryStage;
//          gotoBounce();
//            gotoPosClient();
            gotoLogin();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean userLogging(String username, String password) throws SQLException {
        loggedUser = EmployeeManager.validate(username, password);

//      gotoPosClient();
        return true;
    }

    public void userLogout() {
        String timeStamp;
        Format formatter;
        Date date = new Date();

        // 29-Jan-02
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        timeStamp = formatter.format(date);
        EmployeeLogins bean = new EmployeeLogins();
//      bean.setDate(timeStamp);
//      bean.setEmployeeId(loggedUser.getEmployeeId());
//      bean.setStatus(0);
        try {
            boolean result = EmployeeLoginsManager.updateLogout(bean, this.sessionId);
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        loggedUser = null;
        gotoLogin();
    }

    public Employee getLoggedUser() {
        return loggedUser;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
