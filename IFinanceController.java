package se2203b.assignments.ifinance;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/***
 * @author Abdelkader Ouda
 *
 * ABOUT IFinanceController
 * IFinanceApplication Main Page Control
 * On Action controls
 * Functionalities of each menu item (new window)
 ***/
public class IFinanceController implements Initializable {

    @FXML
    private Menu userMenuItem;

    @FXML
    private Menu chartOfAccountMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Menu doubleEntryMenuItem;

    @FXML
    private Menu fileMenu;

    @FXML
    private Menu financialReportMenuItem;

    @FXML
    private MenuItem loginMenuItem;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private MenuBar mainMenu;

    @FXML
    private Menu manageAccountGroupsMenuItem;

    @FXML
    private Menu manageUserAccountsMenu;

    @FXML
    private MenuItem changePasswordMenuItem;

    private Connection conn;

    private UserAccountAdapter account;

    private GroupAdapter group;

    // On Action method for About Us menu item
    public void showAbout() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IFinanceController.class.getResource("About-view.fxml"));
        // create the root node
        Parent About = fxmlLoader.load();
        // create new stage
        Stage stage = new Stage();
        // add the about's UI elements to the stage
        stage.setScene(new Scene(About));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        stage.setTitle("About Us");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    //On Action method for Login menu item
    public void login() throws Exception {
        account = new UserAccountAdapter(conn, false);
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IFinanceController.class.getResource("Login-view.fxml"));

        // create the root node
        // new window displayed when login() is called
        Parent Login = fxmlLoader.load();
        // calls LoginController
        LoginController loginController = (LoginController) fxmlLoader.getController();
        loginController.setIFinanceController(this);
        // identifying which account to use (model)
        loginController.setUserAccountModel(account);

        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(Login));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        stage.setTitle("Login");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // On Action method for Change Password menu item
    public void changePassword() throws Exception {
        account = new UserAccountAdapter(conn, false);
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IFinanceController.class.getResource("ChangePassword-view.fxml"));

        // create the root node
        Parent changePassword = fxmlLoader.load();
        ChangePasswordController changePasswordController = (ChangePasswordController) fxmlLoader.getController();
        changePasswordController.setIFinanceController(this);
        changePasswordController.setUserAccountModel(account);

        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(changePassword));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        stage.setTitle("Change Password");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // On Action method for Create User Account menu item
    public void createUserAccount() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IFinanceController.class.getResource("CreateUserAccount-view.fxml"));
        // create the root node
        Parent newUser = fxmlLoader.load();
        CreateUserAccountController createUserAccountController = (CreateUserAccountController) fxmlLoader.getController();
        createUserAccountController.setIFinanceController(this);
        // adapting between UserAccountAdapter and NonAdminAdapter (nonAdmin)
        createUserAccountController.setAdapters(new UserAccountAdapter(conn, false), new NonAdminUserAdapter(conn, false) );
        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(newUser));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        stage.setTitle("Create User Account");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // On Action method for Modify User Info menu item
    public void modifyUserProfile() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IFinanceController.class.getResource("ModifyUserProfile-view.fxml"));
        // create the root node
        Parent aUser = fxmlLoader.load();
        ModifyUserProfileController modifyUserProfileController = (ModifyUserProfileController) fxmlLoader.getController();
        modifyUserProfileController.setIFinanceController(this);
        // adapting between UserAccountAdapter and NonAdminAdapter (nonAdmin)
        modifyUserProfileController.setAdapters(new UserAccountAdapter(conn, false), new NonAdminUserAdapter(conn, false) );
        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(aUser));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        stage.setTitle("Modify User Profile");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // On Action method for Delete User Account menu item
    public void deleteUserProfile() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IFinanceController.class.getResource("DeleteUserProfile-view.fxml"));
        // create the root node
        Parent toDelete = fxmlLoader.load();
        DeleteUserProfileController deleteUserProfileController = (DeleteUserProfileController) fxmlLoader.getController();
        deleteUserProfileController.setIFinanceController(this);
        // adapting between UserAccountAdapter and NonAdminAdapter (nonAdmin)
        deleteUserProfileController.setAdapters(new UserAccountAdapter(conn, false), new NonAdminUserAdapter(conn, false) );
        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(toDelete));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        stage.setTitle("Modify User Profile");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void managerGroups() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(IFinanceController.class.getResource("AccountGroups-view.fxml"));
        // create the root node
        Parent toDelete = fxmlLoader.load();
        AccountGroupsController accountGroupsController = (AccountGroupsController) fxmlLoader.getController();
        group = new GroupAdapter(conn, false);
        accountGroupsController.setAdapters(group);

        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(toDelete));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        stage.setTitle("Manage Account Groups");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void logout() {
        disableMenuItems();
    }

    public void exit() {
        // Get current stage reference
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        // Close stage
        stage.close();
    }

    // enable method for admin control (different access control)
    public void enableAdminControls() {
        chartOfAccountMenuItem.setDisable(true);
        doubleEntryMenuItem.setDisable(true);
        financialReportMenuItem.setDisable(true);
        loginMenuItem.setDisable(true);
        manageAccountGroupsMenuItem.setDisable(true);

        fileMenu.setDisable(false);
        logoutMenuItem.setDisable(false);
        mainMenu.setDisable(false);
        closeMenuItem.setDisable(false);
        userMenuItem.setVisible(true);
        manageUserAccountsMenu.setDisable(false);
    }

    // enable method for nonAdmin control (different access control)
    public void enableNonAdminControls() {
        chartOfAccountMenuItem.setDisable(false);
        doubleEntryMenuItem.setDisable(false);
        financialReportMenuItem.setDisable(false);
        manageAccountGroupsMenuItem.setDisable(false);
        fileMenu.setDisable(false);
        logoutMenuItem.setDisable(false);
        mainMenu.setDisable(false);
        closeMenuItem.setDisable(false);
        userMenuItem.setVisible(true);

        loginMenuItem.setDisable(true);
        manageUserAccountsMenu.setDisable(true);
    }

    public void disableMenuItems() {
        chartOfAccountMenuItem.setDisable(true);
        doubleEntryMenuItem.setDisable(true);
        financialReportMenuItem.setDisable(true);
        manageAccountGroupsMenuItem.setDisable(true);
        logoutMenuItem.setDisable(true);
        userMenuItem.setVisible(false);
        manageUserAccountsMenu.setDisable(true);

        fileMenu.setDisable(false);
        mainMenu.setDisable(false);
        closeMenuItem.setDisable(false);
        loginMenuItem.setDisable(false);
    }

    // set the logged-in username into the menu item
    public void setUserName(String userName) {
        userMenuItem.setText(userName);
    }

    public String getUserName() {
        return userMenuItem.getText();
    }

    public void displayAlert(String msg) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(IFinanceApplication.class.getResource("Alert.fxml"));
            // create the root node
            Parent alertWindow = fxmlLoader.load();
            AlertController alertController = (AlertController) fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(alertWindow));
            stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
            alertController.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex1) {
            System.out.println("Error in Display Alert " + ex1);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView face = new ImageView(new Image("file:src/main/resources/se2203b/assignments/ifinance/UserIcon.png"));
        face.setFitWidth(20);
        face.setFitHeight(20);
        userMenuItem.setGraphic(face);
        disableMenuItems();

        try {
            // Create a named constant for the URL
            // NOTE: This value is specific for Java DB
            String DB_URL = "jdbc:derby:iFinanceDB;create=true";
            // Create a connection to the database
            conn = DriverManager.getConnection(DB_URL);
            // Create the admin account if it is not already in the database
            new AdministratorAdapter(conn, false);
            new GroupAdapter(conn, true);

        } catch (SQLException ex) {
            displayAlert(ex.getMessage());
        }
    }

}
