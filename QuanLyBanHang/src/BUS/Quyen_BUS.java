
package BUS;

import DAO.ConnectionData;
import java.sql.*;

public class Quyen_BUS {
    private ConnectionData con= new ConnectionData();
    //ham khoi tao ch lop
    public Quyen_BUS() throws SQLException{
        con.getSQLServerConection();//ham ket noi den databse
    }
    //ham ra ve danh sach du  lieu theo id
    public ResultSet FindDataId(String id) throws SQLException {
            return con.LoadData("select * from Quyen where IdQuyen = '"+id+"'"); //cau lenh truy van du lieu
    }
    //ham tra ve id lon nhat
    public String getNextIdQuyen() throws SQLException {
        String chuoi=null; //tao gia tri chua id voi kieu dinh danh chuoi
        int id=0;//tao gia tri id +1
        try {
            ResultSet rs=null;//tao bien resultset chua du lieu
            rs=con.LoadData("select * from Quyen where Quyen.IdQuyen = (SELECT top 1 with ties Quyen.IdQuyen from Quyen order by Quyen.IdQuyen desc)");//thuc thi cau lenh sql tra ve cho resultset
            while(rs!=null && rs.next()){
                chuoi=rs.getString(1);//lay du lieu dong 1 gan cho gia tri chuoi;
            }
            id=Integer.parseInt(chuoi)+1;//gan gia tri kieu so ve cho id;
        } catch (Exception e) {
        }
        return String.valueOf(id); //tra ket qua kieu string
    }
    //ham tra ve danh sach cac quyen
    public ResultSet ShowData() throws SQLException {
        return con.LoadData("select * from Quyen"); //ham tra ve gia tri resulset
    }
    //ham them quyen vao database
    public void addQuyen(String chucvu, boolean  trangthai, String cap,String mota) throws SQLException {
        con.UpdateData("insert into Quyen values ('"+getNextIdQuyen()+"',N'"+chucvu+"','"+trangthai+"','"+cap+"',N'"+mota+"')");// ham thuc thi cau lenh
    }
    //ham xoa quyen
    public void deleteQuyen(String id) throws SQLException {
        con.UpdateData("delete from Quyen where Quyen.IdQuyen = '"+id+"'");//ham thuc thi cau lenh sql
    }
    //ham edit quyen
    public void editQuyen(String id, String chucvu, boolean  trangthai, String cap,String mota) throws SQLException {
        con.UpdateData("update Quyen set ChucVu =N'"+chucvu+"', TrangThai = '"+trangthai+"', CapBac = '"+cap+"', MoTa =N'"+mota+"' where IdQuyen = '"+id+"'"); //ham thuc thi cau lenh sql
    }
}
