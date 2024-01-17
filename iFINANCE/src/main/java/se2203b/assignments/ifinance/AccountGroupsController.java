package se2203b.assignments.ifinance;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountGroupsController implements Initializable {

    @FXML
    private Button exitBtn;

    @FXML
    private TextField groupField;

    @FXML
    private Button saveBtn;

    @FXML
    /*** TreeView holds Group objects, which help identify which group is selected ***/
    // treeView holds groups and groups hold account category (constructor)
    // displays the groups
    private TreeView<String> treeView;

    @FXML
//    TreeItem root = new TreeItem(null);
    TreeItem<String> root = new TreeItem<>();

    // initializing account categories
    AccountCategory asset = new AccountCategory("Assets", "Debit");
    AccountCategory liabilities = new AccountCategory("Liabilities", "Debit");
    AccountCategory income = new AccountCategory("Income", "Debit");
    AccountCategory expenses = new AccountCategory("Expenses", "Debit");

    // setting account category names
    // automatically calls toString method and get name of each AccountCategory
    TreeItem<String> assetItem = new TreeItem<>(asset.getName());
    TreeItem<String> liabilityItem = new TreeItem<>(liabilities.getName());
    TreeItem<String> incomeItem = new TreeItem<>(income.getName());
    TreeItem<String> expenseItem = new TreeItem<>(expenses.getName());

    /***
     * UserAccountAdapter creates or resets userAccount Table
     * username, password, passExpiryTime, passExpiryDate, and accountType
     * insert and update method
     * get usernameList
     ***/

    private UserAccountAdapter userAccountAdapter;

    /***
     * NonAdminUserAdapter
     * id, fullName, address, email, and accountType
     * insert, update, and delete method
     * findRecord, match with the given name value (id)
     ***/

    private NonAdminUserAdapter nonAdminUserAdapter;

    private AccountCategoryAdapter accountCategoryAdapter;
    private GroupAdapter groupAdapter;

    IFinanceController iFinanceController;

    private final ObservableList<Group> groupList = FXCollections.observableArrayList();

    // Create context menu
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem addGroup = new MenuItem("Add New Group");
    private MenuItem updateGroup = new MenuItem("Change Group Name");
    private MenuItem deleteGroup = new MenuItem("Delete Group");

    // adapter for table creation and finding, adding, modifying, deleting, specific record
    // collaborating with userAccountAdapter and nonAdminAdapter for this controller class
    public void setAdapters(UserAccountAdapter account, NonAdminUserAdapter profile) {
        userAccountAdapter = account;
        nonAdminUserAdapter = profile;

    }

    // adapter for table creation and finding, adding, modifying, deleting, specific record
    // collaborating with accountCategoryAdapter and groupAdapter for this controller class
    public void setAdapters(AccountCategoryAdapter account, GroupAdapter group) throws SQLException{
        accountCategoryAdapter = account;
        groupAdapter = group;
        // populated groupList
//        buildData();

        for (int i = 1; i<=group.getMax(); i++){
            if(group.getGroupElement(i) == asset.getName()){
                TreeItem<String> groupItem = new TreeItem<>(group.getGroupName(i));
                root.getChildren().add(groupItem);
            }
            else if(group.getGroupElement(i) == liabilities.getName()){
                TreeItem<String> groupItem = new TreeItem<>(group.getGroupName(i));
                root.getChildren().add(groupItem);
            }
            else if(group.getGroupElement(i) == income.getName()){
                TreeItem<String> groupItem = new TreeItem<>(group.getGroupName(i));
                root.getChildren().add(groupItem);
            }
            else if(group.getGroupElement(i) == expenses.getName()){
                TreeItem<String> groupItem = new TreeItem<>(group.getGroupName(i));
                root.getChildren().add(groupItem);
            }
        }
    }

    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;
    }

    // populating groupList, holds all the groups
//    public void buildData() {
//    try{
//        groupList.addAll(groupAdapter.getGroupList());
//
//    } catch (SQLException e) {
//        iFinanceController.displayAlert("Group List: " + e.getMessage());
//    }
//    }


    @FXML
    void close(ActionEvent event) {
        // Get current stage reference
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    @FXML
    void showContextMenu() {
        // Add context menu to treeView
        contextMenu.getItems().addAll(addGroup, updateGroup, deleteGroup);
        treeView.setOnContextMenuRequested(e -> {
            // if SelectedItem is a TreeItem
            if (treeView.getSelectionModel().getSelectedItem() instanceof TreeItem) {
                // show contextMenu at the location of the mouse event
                contextMenu.show(treeView, e.getSceneX(), e.getSceneY());
            }
        });
    }

//
//    void create() throws SQLException {
//        // Add TreeItems for all groups and their subgroups
//        for (int i = 1; i<=groupAdapter.getMax(); i++){
//            if(groupAdapter.getElement(i) == asset.getName()){
//                TreeItem<Group> groupItem = new TreeItem<>(new Group(0, groupAdapter.getName(i), null, null));
//                root.getChildren().add(groupItem);
//            }
//            else if(groupAdapter.getElement(i) == liabilities.getName()){
//                TreeItem<Group> groupItem = new TreeItem<>(new Group(0, groupAdapter.getName(i), null, null));
//                root.getChildren().add(groupItem);
//            }
//            else if(groupAdapter.getElement(i) == income.getName()){
//                TreeItem<Group> groupItem = new TreeItem<>(new Group(0, groupAdapter.getName(i), null, null));
//                root.getChildren().add(groupItem);
//            }
//            else if(groupAdapter.getElement(i) == expenses.getName()){
//                TreeItem<Group> groupItem = new TreeItem<>(new Group(0, groupAdapter.getName(i), null, null));
//                root.getChildren().add(groupItem);
//            }
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create treeView with groups
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        root.getChildren().addAll(assetItem, liabilityItem, incomeItem, expenseItem);

        assetItem.getChildren().addAll();

        /***
         * groupList holds all the group
         * treeView displays the groups
         * root for root groups = AccountCategories (Assets, Liabilities, Income, and Expenses)
         *
         * add treeItems for all groups and their subgroups
         * use AccountCategoryAdapter
         * use GroupAdapter
         * findGroup and compare groups' parent objects from groupList
         * match groupElement and parent
         * add to subGroup using parent object
         * Loop (recursive) and Condition
         *
         * ID reference of parent
         ***/

    }
}
