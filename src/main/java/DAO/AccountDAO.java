package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account addAccount(Account account){
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());

            statement.executeUpdate();
            return account;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
