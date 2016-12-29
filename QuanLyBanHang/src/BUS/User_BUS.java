
package BUS;

import DAO.ConnectionData;
import java.sql.*;
import javax.swing.JOptionPane;
public class User_BUS{
        public ConnectionData con= new ConnectionData(); //khoi tao bien connecttion
        //ham khoi tao lop
        public User_BUS() throws SQLException{
             con.getSQLServerConection();//mo ket noi den database
        }
        //ham tra ve danh sach tat ca cac user
        public ResultSet ShowUSer() throws SQLException{
            return con.LoadData("select * from [dbo].[User]");//ham thuc thi cau lenh sql roi tra ve resulset
        }
        //ham hien thi tat ca cac user co cung ho ten
        public ResultSet ShowUSer(String hoten) throws SQLException{
            return con.LoadData("select * from [dbo].[User] where [dbo].[User].HoTen=N"+hoten+"");//ham thuc thi cau lenh sql tra ve danh sach cac user co cung ho va ten
        }
        public ResultSet ShowUser(String id) throws SQLException {
            return con.LoadData("select * from [dbo].[User] a, Quyen b where a.IdQuyen=b.IdQuyen and a.UserId = '"+id+"'");
        }
        //ham hien thi username theo Userid
        public String ShowName(int id) throws SQLException{
            String ten=null;//khoi tao gia tri ten;
            try {
                ResultSet rs=null;//khi tao gia tri resultset
                rs=con.LoadData("select [dbo].[User].HoTen from [dbo].[User] where [dbo].[User].UserId="+id+"");//ham thuc thi cau lenh sql theo yeu cau
                while(rs!=null && rs.next()){ //duyet het cac danh sach cua rs
                    ten=rs.getString(1);//lay gia tri cot thu 1 trong rs gan cho bien ten
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ten; //ham tra ve gia tri ten theo yeu cau
        }
        //ham lay gia userid lon nhat
        public String getNextUSer(){
            String id=null; //khi tao gia tri id 
            int idd=0;//khi tao gia tri tra ve lon nhat
            try {
                ResultSet rs=null;//khoi tao gia tri resultset
                rs=con.LoadData("select * from [dbo].[User] where [dbo].[User].UserId = (SELECT top 1 with ties [dbo].[User].UserId from [dbo].[User] order by [dbo].[User].UserId desc)");//ham thuc thi cau lenh sql
                while( rs!=null && rs.next()){//duyet cac dong du lieu cua rs
                    id=rs.getString(1);//gan gia tri cot 1 cho id
                }
                idd=Integer.parseInt(id)+1;//tang id len 1 gia tri
            } catch (Exception e) {
                e.printStackTrace();
            }
            return String.valueOf(idd);//tra ve gia tri tiep theo
        }
        //ham ma hoa mat khau
        public String mahoa(String id,String ma){
            EnDeCryption end= new EnDeCryption();//khoi tao gia tri ma hoa
            return end.MD5(id+ma+"truong");//tien hanh ma hoa 
        }
        //ham lay gia tri id khi dang nhap thanh cong
        public int getId(String idu, String ma) throws SQLException{
            int id=0; //khoi tao id
            ResultSet rs=null; //khoi tao resultset
            try {
                rs=con.LoadData("select [dbo].[User].UserId from [dbo].[User] where [dbo].[User].UserId = '"+idu+"' and [dbo].[User].MatKhau = '"+mahoa(idu,ma)+"'");//thuc thi cau lenh sql
                while(rs!=null && rs.next()){ //duyet danh sach du lieu trong rs
                    id=Integer.parseInt(rs.getString(1)); //gan du lieu cho id
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return id;//tra ve gia tri can tim la id khi dang nhap thanh cong
        }
        //ham lay gia tri phan quyen
        public int getPhanQuyen(String  user){
            int quyen=0;//khoi tao gia tri quyen =0
            ResultSet rs=null;//tao gia tri resultset
            try {
                rs=con.LoadData("select b.IdQuyen from [dbo].[User] a, Quyen b where a.IdQuyen=b.IdQuyen and a.UserId = '"+user+"'");//ham thuc thi cau lenh sql
                while(rs.next()){//duyet cac rs
                    quyen=Integer.parseInt(rs.getString(1));//gan gia tri cot thu 1 cho quyen
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  quyen;//tra ve gia tri la quyen khi dang nhap vao
        }
        //ham them moi mot user
        public void AddUser(String id, String matkhau, String hoten,String dienthoai,boolean tt,String idquyen,String email){
            con.UpdateData("insert into [dbo].[User] values ('"+id+"','"+mahoa(id, matkhau)+"',N'"+hoten+"','"+dienthoai+"','"+tt+"','','','"+idquyen+"','"+email+"')");//thuc thi cau lenh sql them moi mot user
        };
        //ham xoa user
        public void DeleteUSer(String id){
        con.UpdateData("delete from [dbo].[User] where UserId='"+id+"'");
        };
        //ham sua user
        public void EditUSer(String id,String matkhau, String hoten,String dienthoai,boolean tt,String idquyen,String email){
            con.UpdateData("update [dbo].[User] set MatKhau='"+matkhau+"',HoTen=N'"+hoten+"',DienThoai='"+dienthoai+"',TrangThai='"+tt+"',IdQuyen='"+idquyen+"',Email='"+email+"'where UserId='"+id+"'");
        };
}
