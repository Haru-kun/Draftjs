
package BUS;

import DAO.ConnectionData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class USer_ChucVu_BUS {
    private ConnectionData con= new ConnectionData();
    //ham khoi lap lop
    public USer_ChucVu_BUS() throws SQLException{
        con.getSQLServerConection();//ham mo ket noi
    }
    //ham tra ve id tiep theo
    public String getNextId(){
        String id=null; //khi tao gia tri id 
        int idd=0;//khi tao gia tri tra ve lon nhat
        try {
            ResultSet rs=null;//khoi tao gia tri resultset
            rs=con.LoadData("select * from User_ChucVu where User_ChucVu.Id = (SELECT top 1 with ties User_ChucVu.Id from User_ChucVu order by User_ChucVu.Id desc)");//ham thuc thi cau lenh sql
            while( rs!=null && rs.next()){//duyet cac dong du lieu cua rs
                id=rs.getString(1);//gan gia tri cot 1 cho id
            }
            idd=Integer.parseInt(id)+1;//tang id len 1 gia tri
        } catch (Exception e) {
            e.printStackTrace();
        }
         return String.valueOf(idd);//tra ve gia tri tiep theo
    }
    //ham tra ve resultset theo tat ca cac chuc vu
    public ResultSet ShowUserChucVu() throws SQLException{
        return con.LoadData("select * from User_ChucVu");//ham thuc thi cau lenh sql
    }
    //ham tra ve cac danh sach theo user da thay doi
    public ResultSet ShowUserChucVu(String userthaydoi) throws SQLException{
        return con.LoadData("select * from User_ChucVu where UserThayDoi ='"+userthaydoi+"'");//ham thuc thi cau lenh sql
    }
    //ham add mooi mot user chuc vu
    public void AddUserChucVu(String id, String UserId, Date ngaytao,String Userchange, String lydo) throws SQLException {
        con.UpdateData("insert into User_ChucVu values('"+id+"','"+UserId+"','"+ngaytao+"','"+Userchange+"',N'"+lydo+"')");
    }
    //ham edit userchgucvu
    public void EditUserChuvVu(String id, String UserId,String Userchange, String lydo) throws SQLException {
        con.UpdateData("update User_ChucVu set UserId='"+UserId+"' ,UserThayDoi='"+Userchange+"', LyDo=N'"+lydo+"' where Id='"+id+"'");
    }
    //ham xoa mot userchucvu theo id
    public void DeleteUserChucVu(String id) throws SQLException {
        con.UpdateData("delete from User_ChucVu where Id='"+id+"'");//ham thuc thi cau lenh sql xoa
    }
}
