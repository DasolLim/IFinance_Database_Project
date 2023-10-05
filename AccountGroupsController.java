package se2203b.assignments.ifinance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountGroupsController implements Initializable {

    private GroupAdapter groupAdapter;

    public void setAdapters(GroupAdapter groupAdapter) {
        this.groupAdapter = groupAdapter;
        setUpTreeView();
    }

    @FXML
    private TreeView treeView;
    @FXML
    private TextField groupName;
    @FXML
    private Button saveBtn;
    @FXML
    private Button exitBtn;

    private boolean save;
    private boolean change;

    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem addGroup;
    @FXML
    private MenuItem deleteGroup;
    @FXML
    private MenuItem changeGroup;

    // Root
    TreeItem<String> root;

    // AccountCategory
    TreeItem<String> assets = new TreeItem<>("Assets");
    TreeItem<String> liabilities = new TreeItem<>("Liabilities");
    TreeItem<String>income = new TreeItem<>("Income");
    TreeItem<String>expenses = new TreeItem<>("Expenses");

    // ContextMenu Item
    TreeItem<String> selectedItem;
    String selectedName;
    String emptyName = "";

    public void setUpTreeView(){
        try {
            getChildren();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root = new TreeItem<>();
        root.getChildren().addAll(assets,liabilities,income,expenses);
        treeView.setRoot(root);

        treeView.setShowRoot(false);
        saveBtn.setDisable(true);
        groupName.setDisable(true);
        save = false;
        change = false;
    }

    public void getChildren() throws SQLException {
        // looping through every row of groupTable
        for (int id = 1; id <= groupAdapter.getMaxID(); id++) {
            boolean found = groupAdapter.checkID(id);
            if(found){
                // getName of the id
                String name = groupAdapter.getName(id);
                // if getParent of given id is 0, no parent exists
                if (groupAdapter.getParent(id) == 0){
                    // checking which account category using getElement
                    // group holds account category
                    // inserted into groupItem which gets added into one of 4 elements
                    String element = groupAdapter.getElement(id);
                    if (element.equals("Assets")){
                        TreeItem<String> groupItem = new TreeItem<>(name);
                        assets.getChildren().add(groupItem);
                    }if (element.equals("Liabilities")){
                        TreeItem<String> groupItem = new TreeItem<>(name);
                        liabilities.getChildren().add(groupItem);
                    }if (element.equals("Income")){
                        TreeItem<String> groupItem = new TreeItem<>(name);
                        income.getChildren().add(groupItem);
                    }if (element.equals("Expenses")){
                        TreeItem<String> groupItem = new TreeItem<>(name);
                        expenses.getChildren().add(groupItem);
                    }
                // if group is not groupParent but a subGroup
                }else{
                    // get parent id
                    int parent = groupAdapter.getParent(id);
                    // get parent name
                    String parentName = groupAdapter.getName(parent);

                    // add subGroup to one of 4 elements
                    addSubGroup(liabilities, parentName,name);
                    addSubGroup(assets,parentName,name);
                    addSubGroup(income,parentName,name);
                    addSubGroup(expenses,parentName,name);
                }
            }
        }
    }

    // recursive method to add subGroup
    private void addSubGroup(TreeItem<String> root, String name, String parent){
        for(TreeItem<String> child: root.getChildren()){
            if(child.getValue().equals(parent)){
                TreeItem<String> groupItem = new TreeItem<>(name);
                child.getChildren().add(groupItem);
            }else{
                addSubGroup(child,name,parent);
            }
        }
    }

    // contextMenu select treeView item
    public void selectItem(){
        if (treeView.getSelectionModel().getSelectedItem()!= null){
            selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
            String item = treeView.getSelectionModel().getSelectedItem().toString();
            selectedName = item.substring(item.indexOf(":") + 2, item.length()-2);
            if(selectedName.trim().equals("Assets")||selectedName.trim().equals("Liabilities")||selectedName.trim().equals("Income")||selectedName.trim().equals("Expenses")){
                deleteGroup.setDisable(true);
                changeGroup.setDisable(true);
            }
            else if(!selectedItem.getChildren().isEmpty()){
                deleteGroup.setDisable(true);
                changeGroup.setDisable(false);
            }
            else{
                deleteGroup.setDisable(false);
                changeGroup.setDisable(false);
            }
        }

    }

    // ADD NEW GROUP
    public void addNewGroup() {
        groupName.setText(emptyName);
        saveBtn.setDisable(false);
        groupName.setDisable(false);
        groupName.requestFocus();
        change = false;
        save = true;
    }

    // UPDATE GROUP
    public void changeGroupName() {
        groupName.setText(selectedName);
        saveBtn.setDisable(false);
        groupName.setDisable(false);
        groupName.requestFocus();
        change = true;
        save = false;
    }

    // DELETE GROUP
    public void deleteGroup() throws SQLException{
        groupAdapter.deleteGroup(selectedName);
        selectedItem.getParent().getChildren().remove(selectedItem);
    }

    public void setDefault (Button btn, TextField field) {
        btn.setDisable(true);
        field.setDisable(true);
        field.setText(emptyName);
    }

    public void save() throws SQLException{
        // find root parent
        if(selectedName.trim().equals("Assets")||selectedName.trim().equals("Liabilities")||selectedName.trim().equals("Income")||selectedName.trim().equals("Expenses")) {
            int id = 0;
            String element = selectedName;
            if(save){
                groupAdapter.insertGroup(groupName.getText().trim(),id,element);
                TreeItem<String> newItem = new TreeItem<>(groupName.getText());
                selectedItem.getChildren().add(newItem);
                setDefault(saveBtn, groupName);
            }else{
                groupAdapter.updateGroup(selectedName,groupName.getText());
                selectedItem.setValue(groupName.getText().trim());
                setDefault(saveBtn, groupName);
            }
        }else{
            int id = groupAdapter.getID(selectedName);
            String element = groupAdapter.getElement(id);
            if(save){
                groupAdapter.insertGroup(groupName.getText().trim(),id,element);
                TreeItem<String> newItem = new TreeItem<>(groupName.getText());
                selectedItem.getChildren().add(newItem);
                setDefault(saveBtn, groupName);
            }else{
                groupAdapter.updateGroup(selectedName,groupName.getText());
                selectedItem.setValue(groupName.getText().trim());
                setDefault(saveBtn, groupName);
            }
        }

    }

    public void exit(){
        // Get current stage reference
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

}
