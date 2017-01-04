
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionData {
    //khooi tao gia tri connection
    private static Connection con;
    public void getSQLServerConection() throws SQLException{
        try {
            String userName ="sa";//dinh nghia user
            String password = "1";//dinh nghia password
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLySieuThiMini;";//dinh nghia chuoi ket noi den database
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//khi tao sql driver connection
            con = java.sql.DriverManager.getConnection(url,userName,password);//thuc thi cau lenh ket noi den database
            Statement st=con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Không kết nối được do: " + e.getMessage() );
        }
    }
    //ham dong ket noi
    public void getSQLServerClose() throws SQLException{
        con.close();//doong ket nooi den database
    }
    //ham thuc hien cau lenh select sql
    public ResultSet LoadData(String sql){
        ResultSet result = null;//khoi tao gia tri resultset
        try {
            Statement stae=con.createStatement();//khoi taoo gia tri stament
            return stae.executeQuery(sql);//thuc thi cau lenh sql va luu vaoo resultset
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//tra ve gia tri resultset
    }
    //ham thuc hien cau lenh them,xoa,sua
    public void UpdateData(String sql){
        try {
            Statement stae=con.createStatement();
            stae.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
