
package BUS;

import DAO.ConnectionData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Loai_BUS {
    //khoi taoo gia tri connectiondata
    private static ConnectionData con= new ConnectionData();;
    //khoi tao coontructor cho class
    public Loai_BUS() throws SQLException{
        con.getSQLServerConection();//ket noi den database
    }
    //ham tra ve gia tri tiep theo cua idloai
    public String getNextIdLoai(){
        String chuoi=null; //tao gia tri chua id voi kieu dinh danh chuoi
        int id=0;//tao gia tri id +1
        try {
            ResultSet rs=null;//tao bien resultset chua du lieu
            rs=con.LoadData("select * from Loai where Loai.IdLoai = (SELECT top 1 with ties Loai.IdLoai from Loai order by Loai.IdLoai desc)");//thuc thi cau lenh sql tra ve cho resultset
            while(rs!=null && rs.next()){
                chuoi=rs.getString(1);//lay du lieu dong 1 gan cho gia tri chuoi;
            }
            id=Integer.parseInt(chuoi)+1;//gan gia tri kieu so ve cho id;
        } catch (Exception e) {
        }
        return String.valueOf(id); //tra ket qua kieu string
    }
    //ham them moi mot loai san pham
    public void AddLoai(String id, String ten, boolean tt) throws SQLException {
        con.UpdateData("insert into [dbo].[Loai] values ('"+id+"',N'"+ten+"','"+tt+"')");//ham thuc thi cau lenh sql
    }
    //ham chinh sua thong tin cua loai san pham
    public void EditLoai(String id, String ten, boolean tt) throws SQLException {
        con.UpdateData("update [dbo].[Loai] set TenLoai = N'"+ten+"', TrangThai = '"+tt+"' where IdLoai = '"+id+"'");//cau lenh thuc thi troong sql
    }
    //ham xoa loai
    public void DeleteLoai(String id) throws SQLException {
        con.UpdateData("delete from  [dbo].[Loai] where IdLoai = '"+id+"'");//cau lenh thuc thi cau lenh truy van sql server
    }
    //ham hien thi tat cac cac loai san pham
    public ResultSet  ShowLoai() throws SQLException {
        return con.LoadData("select * from [dbo].[Loai]  where TrangThai='true'");//tra ve danh sach lai san pham
    }
    public ResultSet ShowTrangThaiLoai(boolean tt) throws SQLException {
        return con.LoadData("select * from [dbo].[Loai] where TrangThai = '"+tt+"'");
    }
    public String ShowLoai(String chuoi) throws SQLException {
        ResultSet rs=null;
        String out=null;
        rs=con.LoadData("select * from [dbo].[Loai] where TenLoai = N'"+chuoi+"'and TrangThai='true'");
        while( rs!=null && rs.next()){
            out=rs.getString(1);
        }
       return out;
    }
    public ResultSet ShowLoai(int id) throws SQLException {
        return con.LoadData("select * from Loai where Loai.IdLoai>='"+id+"'and TrangThai='true'");
    }
      public ResultSet ShowLoaiSua(int id) throws SQLException {
        return con.LoadData("select * from Loai where Loai.IdLoai>='"+id+"'");
    }
    public ResultSet ShowLoaiId(int id) throws SQLException {
        return con.LoadData("select * from Loai where Loai.IdLoai='"+id+"'");
    }
    public ResultSet ShowLoaiTim(String ten) throws SQLException {
         return con.LoadData("select * from Loai where Loai.TenLoai='"+ten+"'");
    }
     public ResultSet  ShowLoaiAll() throws SQLException {
        return con.LoadData("select * from [dbo].[Loai]");//tra ve danh sach lai san pham
    }
}
