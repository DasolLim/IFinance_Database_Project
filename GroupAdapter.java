package se2203b.assignments.ifinance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupAdapter {
    Connection connection;

    public GroupAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        // issue SQL statements to the DBMS
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE GroupTable");
            } catch (SQLException ex) {
                ex.getErrorCode();
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        try {
            // Create the table
            stmt.execute("CREATE TABLE GroupTable ("
                    + "id INT NOT NULL,"
                    + "name VARCHAR(60) NOT NULL,"
                    + "parent INT,"
                    + "element VARCHAR(50) NOT NULL"
                    + ")");
            addGroup();

        } catch (SQLException ex) {
            ex.getErrorCode();
            // No need to report an error.
            // The table exists and may have some data.
        }
    }

    // adds new group
    public void insertGroup(String name, int parent, String element) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO GroupTable ( id, name, parent, element) "
                + "VALUES ("
                + getNewID() + ", '"
                + name + "', "
                + parent + ", '"
                + element + "'"
                + ")"
        );
    }

    // Add the default admin account
    private void addGroup() throws SQLException {

        this.insertGroup("Fixed Assets", 0, "Assets");
        this.insertGroup("Investments", 0, "Assets");
        this.insertGroup("Branch/divisions", 0, "Assets");
        this.insertGroup("Cash in hand", 0, "Assets");
        this.insertGroup("Bank accounts", 0, "Assets");
        this.insertGroup("Deposits (assets)", 0, "Assets");
        this.insertGroup("Advances (assets)", 0, "Assets");

        this.insertGroup("Capital account", 0, "Liabilities");
        this.insertGroup("Long term loans", 0, "Liabilities");
        this.insertGroup("Current liabilities", 0, "Liabilities");
        this.insertGroup("Reserves and surplus", 0, "Liabilities");

        this.insertGroup("Sales account", 0, "Income");

        this.insertGroup("Purchase account", 0, "Expenses");
        this.insertGroup("Expenses (direct)", 0, "Expenses");
        this.insertGroup("Expenses (indirect)", 0, "Expenses");

        this.insertGroup("Secured loans", 9, "Liabilities");
        this.insertGroup("Unsecured loans", 9, "Liabilities");
        this.insertGroup("Duties taxes payable", 10, "Liabilities");
        this.insertGroup("Provisions", 10, "Liabilities");
        this.insertGroup("Sundry creditors", 10, "Liabilities");
        this.insertGroup("Bank od & limits", 10, "Liabilities");

    }

    // check given from GroupTable is found or not found
    public boolean checkID(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        boolean found = false;
        rs = stmt.executeQuery("SELECT id FROM GroupTable");
        while(rs.next()){
            int i = rs.getInt(1);
            if(i == id){
                found = true;
            }
        }
        return found;
    }

    // return new ID which is expanded GroupTable size
    public int getNewID() throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT MAX(id) FROM GroupTable");
        rs.next();
        int newID = rs.getInt(1)+1;
        return newID;
    }

    // return maxID, which is size of the GroupTable
    public int getMaxID() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT MAX(id) FROM GroupTable");
        rs.next();
        int maxID = rs.getInt(1);
        return maxID;
    }

    public int getID(String name) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT id FROM GroupTable WHERE name = '" + name + "'");
        rs.next();
        int id = rs.getInt(1);
        return id;
    }

    // return name of id
    public String getName(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT name FROM GroupTable WHERE id = " + id);
        rs.next();
        String name = rs.getString(1).trim();
        return name;
    }

    // return parent of id
    public int getParent(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT parent FROM GroupTable WHERE id = " + id);
        rs.next();
        int parent = rs.getInt(1);
        return parent;
    }

    // return element of id
    public String getElement (int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT element FROM GroupTable WHERE id = " + id);
        rs.next();
        String element = rs.getString(1).trim();
        return element;
    }

    // Update one name
    public void updateGroup(String oldName, String newName) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE GroupTable "
                + "SET name = '" + newName + "' "
                + "WHERE name = '" + oldName + "'");
    }

    // Delete one group
    public void deleteGroup(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        // group
        stmt.executeUpdate("DELETE FROM GroupTable WHERE name = '" + name + "'" );
    }

}