
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.ConnectionManager;

/**
 *
 * @author KwabenaEpic
 */
public class EmployeeLoginsManager {

    private static Connection conn = ConnectionManager.getInstance().getConnection();

    public static boolean insertLogin(beans.EmployeeLogins bean) throws Exception {
        String sql = "INSERT into employeelogins (date, username, sessionId, status, salesOutletId, loginTime)" + "VALUES (?, ?, ?, ?, ?, CURRENT_TIME())"
                + "ON DUPLICATE KEY UPDATE " + "status = VALUES(status)";
        ResultSet keys = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, bean.getDate());
            stmt.setString(2, bean.getUsername());
            stmt.setString(3, bean.getSessionId());
            stmt.setInt(4, bean.getStatus());
            stmt.setString(5, bean.getSalesOutletId());

            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();

                int newKey = keys.getInt(1);

                bean.setLoginId(newKey);
            } else {
                System.err.println("No rows affected");

                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);

            return false;
        } finally {
            if (keys != null) {
                keys.close();
            }
        }

        return true;
    }

    public static boolean updateLogout(beans.EmployeeLogins bean, String sessionId) throws Exception {
        String sql = "UPDATE employeelogins SET status = 0, logoutTime = CURRENT_TIME() WHERE sessionId = ? ";
        ResultSet keys = null;

        // Execute UPDATE operation
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

//          stmt.setString(1, bean.getLogoutTime());
            stmt.setString(1, sessionId);

            int affected = stmt.executeUpdate();

            return affected == 1;
        } catch (SQLException e) {
            System.err.println(e);

            return false;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
