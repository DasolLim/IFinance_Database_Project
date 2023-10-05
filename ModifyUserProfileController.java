package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/***
 * ABOUT Modify User Control
 * list of Username (ComboBox)
 * Update ID, fullName, Address, Email
 ***/
public class ModifyUserProfileController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField address;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField email;

    @FXML
    private Label errorMsg;

    @FXML
    private TextField fullName;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox userName;

    private String loggedInUser;
    IFinanceController iFinanceController;

    private UserAccountAdapter userAccountAdapter;
    private NonAdminUserAdapter nonAdminUserAdapter;
    // array list of userName (accounts)
    private final ObservableList<String> usernamesList = FXCollections.observableArrayList();
    private final ObservableList<UserAccount> userAccountList = FXCollections.observableArrayList();

    public void setAdapters(UserAccountAdapter account, NonAdminUserAdapter profile) {
        userAccountAdapter = account;
        nonAdminUserAdapter = profile;
        buildData();
    }

    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;
    }

    // building array list of iFinance usernames by using addAll
    public void buildData() {
        try {
            // getting usernameList from userAccountAdapter
            usernamesList.addAll(userAccountAdapter.getUsernamesList());
        } catch (SQLException ex) {
            iFinanceController.displayAlert("User Accounts List: " + ex.getMessage());
        }
    }

    // creating a new userProfile and updating to nonAdminUserAdapter using .updateRecord() method
    public void saveAccount() {
        try {
            UserAccount account = userAccountAdapter.findRecord(userName.getValue().toString());
            NonAdminUser userProfile = new NonAdminUser(Integer.valueOf(id.getText()),fullName.getText(), address.getText(), email.getText(), account);
            nonAdminUserAdapter.updateRecord(userProfile);

            // Get current stage reference
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            // Close stage
            stage.close();
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Find User Account: " + ex.getMessage());
        }


    }

    // retrieve the selected profile and update the screen
    public void getAccount() {
        try {
            NonAdminUser profile = nonAdminUserAdapter.findRecord(userName.getValue().toString());
            fullName.setDisable(false);
            address.setDisable(false);
            email.setDisable(false);
            saveBtn.setDisable(false);
            id.setText(String.valueOf(profile.getID()));
            fullName.setText(profile.getFullName());
            address.setText(profile.getAddress());
            email.setText(profile.getEmail());
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Find Profile: " + ex.getMessage());
        }
    }

    public void cancel() {
        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    public void clearErrorMsg() {
        errorMsg.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setItems(usernamesList);

    }
}
