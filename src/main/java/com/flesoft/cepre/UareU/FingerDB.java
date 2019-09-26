package com.flesoft.cepre.UareU;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FingerDB {

    private static final String tableName = "huella";
    private static final String userColumn = "id";
    private static final String print1Column = "huella";
    private static final String print2Column = "print2";

    private String URL = "jdbc:mysql://localhost:3306/huellas?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String host;
    private String database;
    private String userName;
    private String pwd;
    private java.sql.Connection connection = null;
    private String preppedStmtInsert = null;
    private String preppedStmtUpdate = null;

    public FingerDB(String _host, String db, String user, String password) {
        database = "huellas";
        userName = "root";
        pwd = "";
        host = _host;

        URL = "jdbc:mysql://localhost:3306/";
        preppedStmtInsert = "INSERT INTO " + tableName + "(" + print1Column + ") VALUES(?)";
    }

    public void finalize() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Open() throws SQLException {
        connection = DriverManager.getConnection(URL + database, userName, pwd);
    }

    public void Close() throws SQLException {
        connection.close();
    }

    public boolean UserExists(String userID) throws SQLException {
        String sqlStmt = "Select " + userColumn + " from " + tableName + " WHERE " + userColumn + "='" + userID + "'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sqlStmt);
        return rs.next();
    }

    public void Insert(String userID, byte[] print1) throws SQLException {
        java.sql.PreparedStatement pst = connection.prepareStatement(preppedStmtInsert);
        //pst.setString(1, userID);
        pst.setBytes(1, print1);
        pst.execute();
    }

    public List<Record> GetAllFPData() throws SQLException {
        List<Record> listUsers = new ArrayList<Record>();
        String sqlStmt = "Select * from " + tableName;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sqlStmt);
        while (rs.next()) {
            // if (rs.getBytes(print1Column) != null) {
            //System.out.println("added: " + rs.getInt(userColumn));
            listUsers.add(new Record(rs.getInt(userColumn), rs.getBytes(print1Column)));
            // }
        }
        return listUsers;
    }

    public String GetConnectionString() {
        return URL + " User: " + this.userName;
    }

    public String GetExpectedTableSchema() {
        return "Table: " + tableName + " PK(VARCHAR(32)): " + userColumn + "VARBINARY(4000): " + print1Column;
    }
}
