package se2203b.assignments.ifinance;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Group {
    private StringProperty id;
    private StringProperty name;
    private ObjectProperty<Group> parent;
    private ObjectProperty<AccountCategory> element;
    // Constructors
    public Group() {
        this("", "");
    }

    public Group(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.parent = new SimpleObjectProperty(new Group());
        this.element = new SimpleObjectProperty(new AccountCategory());
    }

    //set and get methods
    // id property
    public void setID(String id) {
        this.name.set(id);
    }
    public StringProperty idProperty() {
        return this.id;
    }
    public String getID() {
        return this.id.get();
    }

    // name property
    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return this.name;
    }
    public String getName() {
        return this.name.get();
    }

    // parent Property
    public void setParent(Group parent) {
        this.parent.set(parent);
    }
    public ObjectProperty <Group> parentProperty() {
        return this.parent;
    }
    public Group getParent() {
        return this.parent.get();
    }

    // element Property
    public void setElement(AccountCategory element) {
        this.element.set(element);
    }
    public ObjectProperty <AccountCategory> elementProperty() {
        return this.element;
    }
    public AccountCategory getElement() {
        return this.element.get();
    }


}
