package se2203b.assignments.ifinance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountCategoryAdapter {
    // connecting to the database
    Connection connection;

    // constructor for creating AccountCategoryTable
    public AccountCategoryAdapter(Connection conn, Boolean reset) throws SQLException{
        connection = conn;
        // issue SQL statements to the DBMS
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE AccountCategory");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        try {
            // Create the table
            stmt.execute("CREATE TABLE AccountCategory ("
                    + "name VARCHAR(20) NOT NULL PRIMARY KEY,"
                    + "type VARCHAR(20) NOT NULL,"
                    + ")");


        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }

    }

    // adds new record, reads from Administrator object
    public int insertRecord(AccountCategory data) throws SQLException {
        Statement stmt = connection.createStatement();
        int rows = stmt.executeUpdate("INSERT INTO AccountCategory ( name,  type) "
                + "VALUES ('"
                + data.getName() + "', '"
                + data.getType() + "' )"
        );
        return rows;
    }

    // Modify one record based on the given object
    public void updateRecord(AccountCategory data) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE UserAccount SET "
                + "name = '" + data.getName() + "', "
                + "type = '" + data.getType() + "' "
                // since name is primary key
                + "WHERE name = '" + data.getName() + "' ");
    }

    // Delete one record based on the given object
    public void deleteRecord(AccountCategory data) throws SQLException {
        Statement stmt = connection.createStatement();
        // account category
        System.out.println("DELETE FROM AccountCategory WHERE name = " + data.getName());
        stmt.executeUpdate("DELETE FROM AccountCategory WHERE name = " + data.getName() );
    }

    // get one record, that matches the given name value
    public AccountCategory findRecord(String name) throws SQLException {
        AccountCategory account = new AccountCategory();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM AccountCategory WHERE name = '" + name + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            // note that, this loop will run only once
            account.setName(rs.getString(1));
            account.setType(rs.getString(2));
        }
        return account;
    }


}
