
package BUS;

import DAO.ConnectionData;
import java.sql.*;

public class SanPham_BUS {
    private ConnectionData con= new ConnectionData();;//khoi tao ghia tri connecttion ket noi den database
    //ham khoi tao gia tri cho lop
    public SanPham_BUS() throws SQLException{
        con.getSQLServerConection();//ham ket noi den database
    }
    //ham tra ve tat cac cac san pham
    public ResultSet ShowSanPham() throws SQLException {
        return con.LoadData("select * from SanPham a, Loai b,. KhuyenMai c where a.IdLoaiSP=b.IdLoai and a.IdKhuyenMai=c.Id");//ham thuc thi cau lenh sql tra ve tat cac cac san pham
    }
    public ResultSet ShowSanPham(boolean tt) throws SQLException {
        return con.LoadData("select * from SanPham a, Loai b,. KhuyenMai c where a.IdLoaiSP=b.IdLoai and a.IdKhuyenMai=c.Id and a.TrangThai='"+tt+"'");//ham thuc thi cau lenh sql tra ve tat cac cac san pham
    }
    public ResultSet ShowSanPhamKM(String km) throws SQLException {
        return con.LoadData("select * from SanPham a, Loai b,. KhuyenMai c where a.IdLoaiSP=b.IdLoai and a.TrangThai='true' and a.IdKhuyenMai=c.Id and c.GiaGiam!='"+km+"'");//ham thuc thi cau lenh sql tra ve tat cac cac san pham
    }
    //ham tra ve id tiep theo khi them moi mot san pham
    public String getNextId(){
        String id=null; //khoi tao gia tri id 
        int idd=0;//khi tao gia tri tra ve lon nhat
        try {
            ResultSet rs=null;//khoi tao gia tri resultset
            rs=con.LoadData("select * from SanPham where SanPham.Id = (SELECT top 1 with ties SanPham.Id from SanPham order by SanPham.Id desc)");//ham thuc thi cau lenh sql
            while(rs!=null && rs.next()){//duyet cac dong du lieu cua rs
                id=rs.getString(1);//gan gia tri cot 1 cho id
            }
            if(id==null)
                 return String.valueOf(idd+1);
            idd=Integer.parseInt(id)+1;//tang id len 1 gia tri
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         return String.valueOf(idd);//tra ve gia tri tiep theo   
    }
    //ham tra ve danh sach cac san pham co ten theo yeu cau
    public ResultSet ShowTenSanPham(String tensp) throws SQLException {
        return con.LoadData("select * from SanPham where TenSanPham= N'"+tensp+"'");//ham thuc thi cau lenh sql theo yeu cau
    }
    //ham tra ve resultset theo id
    public ResultSet ShowSanPham(String id) throws SQLException {
        return con.LoadData("select * from SanPham a, Loai b,. KhuyenMai c where a.IdLoaiSP=b.IdLoai and a.IdKhuyenMai=c.Id and a.Id='"+id+"'");//ham thuc thi cau lenh sql theo yeu cau
    }
    public ResultSet ShowSanPhamMa(String ma) throws SQLException {
        return con.LoadData("select * from SanPham a, Loai b,. KhuyenMai c where a.IdLoaiSP=b.IdLoai and a.TrangThai='true' and a.IdKhuyenMai=c.Id and a.Ma='"+ma+"'");
    }
    //ham tra ve gia tri  don gia san pham co dieu kien
    public ResultSet ShowDonGia(String tu, String den) throws SQLException {
        return con.LoadData("select * from SanPham where TrangThai='true' and DonGia > '"+tu+"' and DonGia < '"+den+"'");//ham thuc thi cau lenh sql theo yeu cau
    }
    public ResultSet ShowSanPham(int tu) throws SQLException {
        return con.LoadData("select * from SanPham a, Loai b,. KhuyenMai c where a.IdLoaiSP=b.IdLoai and a.IdKhuyenMai=c.Id and a.Id >= '"+tu+"'");//ham thuc thi cau lenh sql theo yeu cau
    }
    //ham tra ve cac san pham co trang thai theo yeu cau
    public ResultSet ShowTrangThai(boolean tt) throws SQLException {
        return con.LoadData("select * from SanPham where TrangThai ='"+tt+"'");//ham thuc thi cau lenh sql theo yeu cau voi gia tri nhap vao la trang thai cua san pham
    }
    //ham thuc thi cau lenh sql them san pham vao voi cac thuoc tinh nhap tu form
    public void AddSanPham(String id, String ten, String ma, int sl,float dongia,boolean tt,String idloai,String idkm) throws SQLException {
        con.UpdateData("insert into SanPham values ('"+id+"',N'"+ten+"','"+ma+"','"+sl+"','"+dongia+"','"+tt+"','"+idloai+"','"+idkm+"')");//ham thuc thi cau lenh sql theo yeu cau la add san pham moi vao database
    }
    //ham thuc thi chinh sua thong tin cua san pham
    public void UpdateSanPham(String id, String ten, String ma, int sl,float dongia,boolean tt,String idloai,String idkm) throws SQLException {
        con.UpdateData("update SanPham set TenSanPham = N'"+ten+"',ma = '"+ma+"',SoLuong = '"+sl+"',DonGia = '"+dongia+"',TrangThai = '"+tt+"',IdLoaiSP = '"+idloai+"', IdKhuyenMai='"+idkm+"' where Id = '"+id+"'");
    }
    public ResultSet ShowTonTaiLoai() throws SQLException {
        return con.LoadData("select * from Loai a where exists ( select Id from SanPham b where a.IdLoai=b.IdLoaiSP)");
    }
}
