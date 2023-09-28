package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account createUser(Account account){

        Connection c = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?,?);";
            PreparedStatement ps = c.prepareStatement(sql);


            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                String sql2 = "SELECT * FROM Account WHERE username = ?";
                PreparedStatement ps2 = c.prepareStatement(sql2);
                ps2.setString(1, account.getUsername());
                ResultSet rs = ps2.executeQuery();
                while(rs.next()){
                    Account ac = new Account(rs.getInt("account_id"),
                            rs.getString("username"),
                            rs.getString("password"));
                    return ac;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }

     
      
     public Account loginUser(Account account){
        Connection c = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE username=? AND password=?;";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"),
                            rs.getString("username"),
                            rs.getString("password"));
                return ac;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }


}

   