
package BUS;

import DAO.ConnectionData;
import java.sql.*;
import java.util.*;
import java.sql.Date;

public class HoaDon_BUS {
    //goi lop dconnectiondata
    ConnectionData con= new ConnectionData();;
    //tao bien luu thang va nam
    private String thang,nam;
    //khoi tao construtor
    public HoaDon_BUS() throws SQLException {
        con.getSQLServerConection();//ket noi den database
    }
    //ham gan gia tri ch thang va nam
    public void khoang(String a,String b){
        thang=a;nam=b;
    }
    //ham lay gia tri tiep theo cho hoadonid
    public String getNextId() throws SQLException {
        String chuoi=null; //tao gia tri chua id voi kieu dinh danh chuoi
        int id=0;//tao gia tri id +1
        try {
            ResultSet rs=null;//tao bien resultset chua du lieu
            rs=con.LoadData("select * from [dbo].[HoaDon] where [dbo].[HoaDon].IdHoaDon = (SELECT top 1 with ties [dbo].[HoaDon].IdHoaDon from Loai order by [dbo].[HoaDon].IdHoaDon desc)");//thuc thi cau lenh sql tra ve cho resultset
            while(rs!=null && rs.next()){
                chuoi=rs.getString(1);//lay du lieu dong 1 gan cho gia tri chuoi;
            }
            id=Integer.parseInt(chuoi)+1;//gan gia tri kieu so ve cho id;
        } catch (Exception e) {
        }
        return String.valueOf(id); //tra ket qua kieu string
    }
    //hien thiu tat cac cac hoa don
    public ResultSet ShowHoaDon() throws SQLException {
        return con.LoadData("select a.IdHoaDon,b.IdSanPham,b.SoLuong,b.GiaGiam,a.TongTien,c.UserId,c.HoTen from HoaDon a, CT_HoaDon b, [dbo].[User] c, KhuyenMai d where a.IdHoaDon=b.IdHoaDon and a.UserId=c.UserId and a.IdGiamGia = d.Id and a.IdHoaDon in(select [dbo].[HoaDon].IdHoaDon from [dbo].[HoaDon] )");//tra ve danh sach cac hoa don
    }
    //ham hien thi danh sach cachoooa don duoc tao boi user
    public ResultSet ShowHoaDonbyUser(String user) throws SQLException {
        return con.LoadData("select * from [dbo].[HoaDon] where [dbo].[HoaDon].UserId = '"+user+"'");//cau lenh truy van gia tri can tim
    }
    //ham hien thi danh sach cac hoa don theo ngay
    public ResultSet ShowHoaDonNgay(Date ngay) throws SQLException {
        return con.LoadData("select a.IdHoaDon,b.IdSanPham,b.SoLuong,b.GiaGiam,a.TongTien,c.UserId,c.HoTen from HoaDon a, CT_HoaDon b, [dbo].[User] c, KhuyenMai d where a.IdHoaDon=b.IdHoaDon and a.UserId=c.UserId and a.IdGiamGia = d.Id and a.IdHoaDon in(select [dbo].[HoaDon].IdHoaDon from [dbo].[HoaDon] where [dbo].[HoaDon].NgayTao = CONVERT(date,'"+ngay+"',103))");//cau lenh sql truy van
    }
    //ham hien thi danh sach cac goa don theo khoang thoi gian
    public ResultSet ShowHoaDonThang() throws SQLException {
        return con.LoadData("select a.IdHoaDon,b.IdSanPham,b.SoLuong,b.GiaGiam,a.TongTien,c.UserId,c.HoTen from HoaDon a, CT_HoaDon b, [dbo].[User] c, KhuyenMai d where a.IdHoaDon=b.IdHoaDon and a.UserId=c.UserId and a.IdGiamGia = d.Id and a.IdHoaDon in(select [dbo].[HoaDon].IdHoaDon from [dbo].[HoaDon] where MONTH([dbo].[HoaDon].NgayTao)='"+thang+"' and year([dbo].[HoaDon].NgayTao) = '"+nam+"')");//cau lenh sql truy van
    }
    //ham hien thi tat cac cac hoa don
    public void AddHoaDon(String id,float tien,String user,Date ngay,String idgiam) throws SQLException {
        con.LoadData("insert into [dbo].[HoaDon] values ('"+id+"','"+tien+"','"+user+"','"+ngay+"','"+idgiam+"')");//thuc thi cau lenh them
    }
    public void EditHoaDon(String id,float tien,String user,Date ngay,String idgiam) throws SQLException {
        con.UpdateData("update [dbo].[HoaDon] set  TongTien = '"+tien+"',UserId = '"+user+"', NgayTao='"+ngay+"',IdGiamGia='"+idgiam+"' where IdHoaDon = '"+id+"'");//thuc thi cau lenh edit
    }
    public void DeleteHoaDon(String id) throws SQLException {
        con.UpdateData("delete from  [dbo].[HoaDon] where IdHoaDon = '"+id+"'");//thuc thi cau lenh sql xoa
    }
}
