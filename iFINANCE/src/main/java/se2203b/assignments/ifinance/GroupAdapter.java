//package se2203b.assignments.ifinance;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class GroupAdapter {
//    Connection connection;
//
//    public GroupAdapter(Connection conn, Boolean reset) throws SQLException {
//        connection = conn;
//        // issue SQL statements to the DBMS
//        Statement stmt = connection.createStatement();
//        if (reset) {
//            try {
//                // Remove tables if database tables have been created.
//                // This will throw an exception if the tables do not exist
//                stmt.execute("DROP TABLE Group");
//            } catch (SQLException ex) {
//                ex.getErrorCode();
//                // No need to report an error.
//                // The table simply did not exist.
//            }
//        }
//        try {
//            // Create the table
//            /***Group and AccountCategory**/
//            stmt.execute("CREATE TABLE Group ("
//                    + "id INT NOT NULL PRIMARY KEY,"
//                    + "name VARCHAR(20) NOT NULL,"
//                    + "parent INT,"
//                    + "element VARCHAR(20) NOT NULL"
//                    + ")");
//            addGroup();
//
//        } catch (SQLException ex) {
//            ex.getErrorCode();
//            // No need to report an error.
//            // The table exists and may have some data.
//        }
////        try{
////            addGroup();
////        } catch (SQLException ex) {
////            // No need to report an error.
////            // The table exists and may have some data.
////        }
//    }
//
//    // adds new group, reads from Administrator object
//    public void insertGroup(int id, String name, int parent, String element) throws SQLException {
//        Statement stmt = connection.createStatement();
//        stmt.executeUpdate("INSERT INTO Group (id, name, parent, element) "
//                + "VALUES ("
//                + id + ", '"
//                + name + "', "
//                + parent + ", '"
//                + element + "')");
//    }
//
//    // Modify one group based on the given object
//    public void updateGroup(Group data) throws SQLException {
//        Statement stmt = connection.createStatement();
//        stmt.executeUpdate("UPDATE UserAccount SET "
//                + "id = " + data.getID() + ", "
//                + "name = '" + data.getName() + "', "
//                + "parent = " + data.getParent() + ", "
//                + "element = '" + data.getElement() + "' "
//                // since name is primary key
//                + "WHERE id = " + data.getID() + " ");
//    }
//
//    // Delete one group based on the given object
//    public void deleteGroup(Group data) throws SQLException {
//        Statement stmt = connection.createStatement();
//        // group
//        System.out.println("DELETE FROM Group WHERE id = " + data.getID());
//        stmt.executeUpdate("DELETE FROM Group WHERE id = " + data.getID() );
//    }
//
//    // finding the parent of a group
//    public Group findGroup(Group data) throws SQLException {
//        Group group = new Group();
//        ResultSet rs;
//
//        // Create a Statement object
//        Statement stmt = connection.createStatement();
//        // Create a string with a SELECT statement
//        String sqlStatement = "SELECT * FROM Group WHERE parent= " + data + " ";
//        // Execute the statement and return the result
//        rs = stmt.executeQuery(sqlStatement);
//        while (rs.next()) {
//            // note that, this loop will run only once
//            group.setID(rs.getInt(1));
//            group.setName(rs.getString(2));
//            group.setParent((Group) rs.getObject(3));
//            group.setElement((AccountCategory) rs.getObject(4));
//        }
//        return group;
//    }
//
//    // Add the default admin account
//    private void addGroup() throws SQLException {
//
//        insertGroup(1, "Fixed Assets", 0, "Assets");
//        insertGroup(2, "Investments", 0, "Assets");
//        insertGroup(3, "Branch/divisions", 0, "Assets");
//        insertGroup(4, "Cash in hand", 0, "Assets");
//        insertGroup(5, "Bank accounts", 0, "Assets");
//        insertGroup(6, "Deposits (assets)", 0, "Assets");
//        insertGroup(7, "Advances (assets)", 0, "Assets");
//
//        insertGroup(8, "Capital account", 0, "Liabilities");
//        insertGroup(9, "Long term loans", 0, "Liabilities");
//        insertGroup(10, "Current liabilities", 0, "Liabilities");
//        insertGroup(11, "Reserves and surplus", 0, "Liabilities");
//
//        insertGroup(12, "Sales account", 0, "Income");
//
//        insertGroup(13, "Purchase account", 0, "Expenses");
//        insertGroup(14, "Expenses (direct)", 0, "Expenses");
//        insertGroup(15, "Expenses (indirect)", 0, "Expenses");
//
//        insertGroup(16, "Secured loans", 9, "Liabilities");
//        insertGroup(17, "Unsecured loans", 9, "Liabilities");
//        insertGroup(18, "Duties taxes payable", 10, "Liabilities");
//        insertGroup(19, "Provisions", 10, "Liabilities");
//        insertGroup(20, "Sundry creditors", 10, "Liabilities");
//        insertGroup(21, "Bank od & limits", 10, "Liabilities");
//    }
//
//    public int getMax() throws SQLException {
//        int num = 0;
//
//        Statement stmt = connection.createStatement();
//        String sqlStatement = "SELECT MAX(id) FROM Group";
//        ResultSet rs = stmt.executeQuery(sqlStatement);
//
//        while (rs.next()){
//            num = (rs.getInt(1));
//        }
//        return num;
//    }
//
//    // return name of the id
//    public String getName(int id) throws SQLException{
//        Statement stmt = connection.createStatement();
//
//        String sqlStatement = "SELECT name FROM Group WHERE id = " + id;
//        ResultSet group = stmt.executeQuery(sqlStatement);
//        group.next();
//        String name = group.getString(1);
//        return name;
//    }
//
//    // return parent of the id
//   public int getParent(int id) throws SQLException{
//       Statement stmt = connection.createStatement();
//
//       String sqlStatement = "SELECT parent FROM Group WHERE id = " + id;
//       ResultSet group = stmt.executeQuery(sqlStatement);
//       group.next();
//       int parent = group.getInt(1);
//       return parent;
//   }
//
//   // return element of id
//    public String getElement (int id) throws SQLException{
//        Statement stmt = connection.createStatement();
//
//        String sqlStatement = "SELECT element FROM Group WHERE id = " + id;
//        ResultSet group = stmt.executeQuery(sqlStatement);
//        group.next();
//        String element = group.getString(1);
//        return element;
//    }
//
//}

package se2203b.assignments.ifinance;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupAdapter {
    Connection connection;

    public GroupAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Group");
            } catch (SQLException ex) {
            }
        }

        try {
            // Create the table
            stmt.execute("CREATE TABLE Group ("
                    + "groupID INT NOT NULL PRIMARY KEY,"
                    + "groupName VARCHAR(20) NOT NULL,"
                    + "groupParent INT,"
                    + "groupElement VARCHAR(20) NOT NULL"
                    + ")");
            addGroup();
        } catch (SQLException ex) {
        }
    }

    // Add the groups to database
    private void addGroup() throws SQLException{
        this.insertGroup(1, "Fixed Assets", 0, "Assets");
        this.insertGroup(2, "Investments", 0, "Assets");
        this.insertGroup(3, "Branch/divisions", 0, "Assets");
        this.insertGroup(4, "Cash in hand", 0, "Assets");
        this.insertGroup(5, "Bank accounts", 0, "Assets");
        this.insertGroup(6, "Deposits (assets)", 0, "Assets");
        this.insertGroup(7, "Advances (assets)", 0, "Assets");
        this.insertGroup(8, "Capital account", 0, "Liabilities");
        this.insertGroup(9, "Long term loans", 0, "Liabilities");
        this.insertGroup(10, "Current liabilities", 0, "Liabilities");
        this.insertGroup(11, "Reserves and surplus", 0, "Liabilities");
        this.insertGroup(12, "Sales account", 0, "Income");
        this.insertGroup(13, "Purchase account", 0, "Expenses");
        this.insertGroup(14, "Expenses (direct)", 0, "Expenses");
        this.insertGroup(15, "Expenses (indirect)", 0, "Expenses");
        this.insertGroup(16, "Secured loans", 9, "Liabilities");
        this.insertGroup(17, "Unsecured loans", 9, "Liabilities");
        this.insertGroup(18, "Duties taxes payable", 10, "Liabilities");
        this.insertGroup(19, "Provisions", 10, "Liabilities");
        this.insertGroup(20, "Sundry creditors", 10, "Liabilities");
        this.insertGroup(21, "Bank od & limits", 10, "Liabilities");
    }

    public void insertGroup(int id, String name, int parent, String element) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Group (groupID, groupName, groupParent, groupElement) "
                + "VALUES ("
                + id + ", '"
                + name + "', "
                + parent + ", '"
                + element + "' )"
        );
    }

    // Getting max for new group creation
    public int getMax() throws SQLException {
        int num = 0;

        Statement stmt = connection.createStatement();
        String sqlStatement = "SELECT MAX(groupID) FROM Group";
        ResultSet rs = stmt.executeQuery(sqlStatement);

        while (rs.next()){
            num = (rs.getInt(1));
        }
        return num;
    }

    public String getGroupName(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        String sqlStatement = "SELECT * FROM Group WHERE GROUPID = "+ id;
        ResultSet rs = stmt.executeQuery(sqlStatement);

        rs.next();
        String name =rs.getString("groupName");
        return name;
    }

    public int getGroupParent(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        String sqlStatement = "SELECT * FROM Group WHERE GROUPID = "+ id;
        ResultSet rs = stmt.executeQuery(sqlStatement);

        rs.next();
        int parent =rs.getInt("groupParent");
        return parent;
    }

    public String getGroupElement(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        String sqlStatement = "SELECT * FROM Group WHERE GROUPID = "+ id;
        ResultSet rs = stmt.executeQuery(sqlStatement);

        rs.next();
        String element =rs.getString("groupElement");
        return element;
    }
}