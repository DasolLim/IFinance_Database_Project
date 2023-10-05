package se2203b.assignments.ifinance;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountCategoryAdapter implements Initializable {
    private GroupAdapter groupAdapter;
    public void AccountCategoryAdapter(GroupAdapter gA) {
        this.groupAdapter = gA;
        setUpTreeView();
    }
    @FXML
    private TreeView treeView;
    @FXML
    private TextField groupNameField;
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

    private TreeItem<String> assets;
    private TreeItem<String> liabilities;
    private TreeItem<String> income;
    private TreeItem<String> expenses;
    private TreeItem<String> dummyNode;
    private String selectedName;
    private TreeItem<String> selectedItem;

    public void setUpTreeView(){
        try {
            getChildren();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assets = new TreeItem<>("Assets");
        liabilities = new TreeItem<>("Liabilities");
        income = new TreeItem<>("Income");
        expenses = new TreeItem<>("Expenses");
        dummyNode = new TreeItem<>();
        dummyNode.getChildren().addAll(assets,liabilities,income,expenses);
        treeView.setRoot(dummyNode);
        treeView.setShowRoot(false);
        saveBtn.setDisable(true);
        groupNameField.setDisable(true);
        save = false;
        change = false;
    }
    public void getChildren() throws SQLException {
        //for every row in the table
        for (int id =1; id<=groupAdapter.getMaxID();id++){
            boolean found = groupAdapter.checkID(id);
            if(found){
                //get the name of the group
                String name = groupAdapter.getName(id);
                //depending on parent, place in treeView
                //parent equals 0
                if (groupAdapter.getParent(id) == 0){
                    String element = groupAdapter.getElement(id);
                    if (element.equals("Assets")){
                        TreeItem<String> item = new TreeItem<>(name);
                        assets.getChildren().add(item);
                    }if (element.equals("Liabilities")){
                        TreeItem<String> item = new TreeItem<>(name);
                        liabilities.getChildren().add(item);
                    }if (element.equals("Income")){
                        TreeItem<String> item = new TreeItem<>(name);
                        income.getChildren().add(item);
                    }if (element.equals("Expenses")){
                        TreeItem<String> item = new TreeItem<>(name);
                        expenses.getChildren().add(item);
                    }
                    //parent does not equal 0
                }else{
                    //get parent number
                    int parent = groupAdapter.getParent(id);
                    //get parent name
                    String parentName = groupAdapter.getName(parent);

                    addChild(liabilities, parentName,name);
                    addChild(assets,parentName,name);
                    addChild(income,parentName,name);
                    addChild(expenses,parentName,name);
                }
            }

        }
    }

    private void addChild(TreeItem<String> root, String parent, String name){
        for(TreeItem<String> child: root.getChildren()){
            if(child.getValue().equals(parent)){
                TreeItem<String> item = new TreeItem<>(name);
                child.getChildren().add(item);
            }else{
                addChild(child,parent,name);
            }
        }
    }

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
    public void addNewGroup(){
        groupNameField.setText("");
        saveBtn.setDisable(false);
        groupNameField.setDisable(false);
        groupNameField.requestFocus();
        save = true;
        change = false;
    }
    public void deleteGroup() throws SQLException{
        //delete group from table
        groupAdapter.deleteGroup(selectedName);
        //delete group from treeView
        selectedItem.getParent().getChildren().remove(selectedItem);
    }
    public void changeGroupName() throws SQLException{
        groupNameField.setText(selectedName);
        saveBtn.setDisable(false);
        groupNameField.setDisable(false);
        groupNameField.requestFocus();
        change = true;
        save = false;
    }
    public void save() throws SQLException{
        //need to find root parent
        if(selectedName.trim().equals("Assets")||selectedName.trim().equals("Liabilities")||selectedName.trim().equals("Income")||selectedName.trim().equals("Expenses")){
            int id = 0;
            String element = selectedName;
            if(save){
                groupAdapter.insertGroup(groupNameField.getText().trim(),id,element);
                TreeItem<String> newItem = new TreeItem<>(groupNameField.getText());
                selectedItem.getChildren().add(newItem);
                saveBtn.setDisable(true);
                groupNameField.setDisable(true);
                groupNameField.setText("");
            }else{
                groupAdapter.updateGroup(selectedName,groupNameField.getText());
                selectedItem.setValue(groupNameField.getText().trim());
                groupNameField.setText("");
                saveBtn.setDisable(true);
                groupNameField.setDisable(true);
            }
        }else{
            int id = groupAdapter.getID(selectedName);
            String element = groupAdapter.getElement(id);
            if(save){
                groupAdapter.insertGroup(groupNameField.getText().trim(),id,element);
                TreeItem<String> newItem = new TreeItem<>(groupNameField.getText());
                selectedItem.getChildren().add(newItem);
                saveBtn.setDisable(true);
                groupNameField.setDisable(true);
                groupNameField.setText("");
            }else{
                groupAdapter.updateGroup(selectedName,groupNameField.getText());
                selectedItem.setValue(groupNameField.getText().trim());
                groupNameField.setText("");
                saveBtn.setDisable(true);
                groupNameField.setDisable(true);
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
