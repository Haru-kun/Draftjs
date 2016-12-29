
package BUS;

import DAO.ConnectionData;
import java.sql.*;

public class KhuyenMai_BUS {
    //khi tao gia tri connectionData
    ConnectionData con= new ConnectionData();;
    //ham khoi tao constructor
    public KhuyenMai_BUS() throws SQLException {
        con.getSQLServerConection();//ham taoo ket nooi den database
    }
    //ham lay id tiep theo
    public String getNextId() throws SQLException {
        String chuoi=null; //tao gia tri chua id voi kieu dinh danh chuoi
        int id=0;//tao gia tri id +1
        try {
            ResultSet rs=null;//tao bien resultset chua du lieu
            rs=con.LoadData("select * from KhuyenMai where KhuyenMai.Id = (SELECT top 1 with ties KhuyenMai.Id from KhuyenMai order by KhuyenMai.Id desc)");//thuc thi cau lenh sql tra ve cho resultset
            while(rs!=null && rs.next()){
                chuoi=rs.getString(1);//lay du lieu dong 1 gan cho gia tri chuoi;
            }
            id=Integer.parseInt(chuoi)+1;//gan gia tri kieu so ve cho id;
        } catch (Exception e) {
        }
        return String.valueOf(id); //tra ket qua kieu string
    }
    //ham hien thi tat cac cac khuyen mai
    public ResultSet ShowKhuyenMai() throws SQLException {
        return con.LoadData("select * from [dbo].[KhuyenMai]");//ham thuc thi cau lenh truy van sql
    }
    //ham hien thi tat cac khuyen mai theo gia giam
    public ResultSet ShowTimGiamGia(float tu, float den) throws SQLException {
        return con.LoadData("select * from [dbo].[KhuyenMai] where GiaGiam >= '"+tu+"' and GiaGiam <= '"+den+"'");//thuc thi cau lenh sql
    }
    //ham them moi khuyen mai
    public void AddKhuyenMai(String id, String ma, String mota, float Gia) throws SQLException {
        con.UpdateData("insert into [dbo].[KhuyenMai] values ('"+id+"','"+ma+"',N'"+mota+"','"+Gia+"')");//ham thuc thi cau lenh sql them moi
    }
    //ham chinh sua mot khuyen mai
    public void EditKhuyenMai(String id, String ma, String mota, float Gia) throws SQLException {
        con.UpdateData("update [dbo].[KhuyenMai] set Ma = '"+ma+"', MoTa = N'"+mota+"',GiaGiam = '"+Gia+"' where Id = '"+id+"'");//ham thuc thi cau lenh sql edit
    }
    //ham xoa mot khuyen mai
    public void DeleteKhuyenMai(String id) throws SQLException {
        con.UpdateData("delete from  [dbo].[KhuyenMai] where Id = '"+id+"'");//ham thuc thi au lenh xoa mot khuyen mai
    }
    public String ShowKhuyenMai(String chuoi) throws  SQLException {
        ResultSet re=null;
        String out=null;
        re=con.LoadData("select * from KhuyenMai where MoTa=N'"+chuoi+"'");
        while(re!=null && re.next()){
            out=re.getString(1);
        }
        return out;
    }
}
