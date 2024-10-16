package DAO;

import java.sql.Statement;
// overwrote this to make RETURN_GENERATED_KEYS work
// import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account getAccountByUsername(String username){
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Account account = new Account(rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account addAccount(Account account){
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());

            statement.executeUpdate();
            ResultSet pkeyResultSet = statement.getGeneratedKeys();
            int new_id = -1;
            if (pkeyResultSet.next()){
                new_id = pkeyResultSet.getInt(1);
                return new Account(new_id, account.getUsername(), account.getPassword());
            }
            // return new Account(-2, account.getUsername(), account.getPassword());
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
