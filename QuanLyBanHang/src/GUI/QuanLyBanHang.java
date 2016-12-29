/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.KhuyenMai_BUS;
import BUS.Loai_BUS;
import BUS.SanPham_BUS;
import BUS.User_BUS;
import DAO.ConnectionData;
import com.sun.org.apache.xpath.internal.operations.Or;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NNT-IT
 */
public class QuanLyBanHang extends javax.swing.JFrame {

    private ConnectionData con;
    private User_BUS us;
    private SanPham_BUS sp;
    private Loai_BUS loai;
    private KhuyenMai_BUS khuyemai;
    private String ngonngu;
    private ResultSet rs=null;
    private int id=0,quyen=0,stt=0,dem=0,sst=0,ddem=0;
    private boolean trangthai,timkiem,addloai;
    private final DefaultTableModel tableTT = new DefaultTableModel();
    private DefaultTableModel TableQLSP = new DefaultTableModel();
    private final DefaultTableModel tableKM= new DefaultTableModel();
    public QuanLyBanHang() throws SQLException{
        initComponents();
        ShowLoai();
        ShowKhuyenMai();
        btnQLSP_SuaSP.setEnabled(false);
        btnQLSP_Loai_Sua.setEnabled(false);
        cboQLSP_XemTheo.addItem("Danh Sách Sản Phẩm");
        cboQLSP_XemTheo.addItem("Danh Sách Loại Sản Phẩm");
        this.txtQLSP_TimKiem.setEnabled(false);
        TTQLSP_Add.setDefaultEditor(Object.class, null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/image/shopping-girl-icon.png")));
    }
    
    public void getId(int x){
        id=x;
    }
   
    private String getTenDangNhap(int id) throws SQLException{
        String chuoi=null,a=null,b=null;
        us= new User_BUS();
        rs= us.ShowUser(String.valueOf(id));
        while(rs!=null && rs.next()){
            a=rs.getString(1)+"-"+rs.getString(3)+"-"+rs.getString(11);
        }
        return a;
    }
   
    public void getNgonNgu(boolean xx){
        if(xx==true){
            //tieng anh
        }
        if(xx==false){
            //tiengviet
        }
    }
    
    private void ShowLoai() throws SQLException{
        ResultSet res= null;
        loai= new Loai_BUS();
        try {
            res=loai.ShowLoai();
            while(res!=null && res.next() ){
                cboQLSP_LoaiSP.addItem(res.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void ShowKhuyenMai() throws SQLException {
        ResultSet res= null;
        khuyemai= new KhuyenMai_BUS();
        try {
            res=khuyemai.ShowKhuyenMai();
            while(res!=null && res.next() ){
                cboQLSP_KhuyenMai.addItem(res.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void LoadSanPham() throws SQLException{
        ResultSet rss=null;
        sp= new SanPham_BUS();
        try {
            rss=sp.ShowSanPham(true);
            while(rss.next())
            {
                String row[] = new String[6];
                row[0]=rss.getString(2);
                row[1]=rss.getString(3);
                row[2]=rss.getString(5);
                row[3]=rss.getString(10);
                row[4]=rss.getString(14);
                row[5]=rss.getString(15);
                tableTT.addRow(row); // đưa dòng dữ liệu vào tableModel
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void LoadKhuyenMai() throws SQLException {
        ResultSet rss=null;
        sp= new SanPham_BUS();
        try {
            rss=sp.ShowSanPhamKM("0");
            while(rss.next())
            {
                String row[] = new String[6];
                row[0]=rss.getString(2);
                row[1]=rss.getString(3);
                row[2]=rss.getString(5);
                row[3]=rss.getString(10);
                row[4]=rss.getString(14);
                row[5]=rss.getString(15);
                tableKM.addRow(row); // đưa dòng dữ liệu vào tableModel
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String getTrinhTrang(String x){
        if(Integer.parseInt(x)==1)
        {
            return "Còn Hàng";
        }else
        { 
            return "Tạm Hết";
        }
    }
    
    private String getTrinhTrangLoai(String x){
         if(Integer.parseInt(x)==1)
        {
            return "Sử Dụng";
        }else
        { 
            return "Không Sử Dụng";
        }
    }
   
    private void LoadSPAdd() throws SQLException {
        ResultSet rss=null;
        sp= new SanPham_BUS();
        try {
            rss=sp.ShowSanPham(stt);
            while(rss.next())
            {
                String row[] = new String[9];
                row[0]=rss.getString(1);
                row[1]=rss.getString(2);
                row[2]=rss.getString(3);
                row[3]=rss.getString(4);
                row[4]=rss.getString(5);
                row[5]=rss.getString(10);
                row[6]=rss.getString(14);
                row[7]=rss.getString(15);
                row[8]=getTrinhTrang(rss.getString(6));
                TableQLSP.addRow(row); // đưa dòng dữ liệu vào tableModel
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void ClearData() throws SQLException{
        int n=tableTT.getRowCount()-1;
        for(int i=n;i>=0;i--)
        {
            tableTT.removeRow(i);//Remove tung dong
        }
        int m= TableQLSP.getRowCount()-1;
        for(int i=m;i>=0;i--)
        {
            TableQLSP.removeRow(i);//Remove tung dong
        }
        int g=tableKM.getRowCount()-1;
        for(int i=g;i>=0;i--)
        {
            tableKM.removeRow(i);//Remove tung dong
        }
    }
    
    private void capnhatTrangChu() throws SQLException{
        String []colsName = {"Tên Sản Phẩm", "Mã","Giá","Loại","Mô Tả","Giá Giảm"};
        tableTT.setColumnIdentifiers(colsName);
        tableKM.setColumnIdentifiers(colsName);
        LoadSanPham();
        LoadKhuyenMai();
        TTrangChu_SP.setDefaultEditor(Object.class, null);
        TTrangChu_KhuyenMai.setDefaultEditor(Object.class, null);
        TTrangChu_SP.setModel(tableTT);
        TTrangChu_KhuyenMai.setModel(tableKM);
    }
    
    private void CapNhatSPAdd() throws  SQLException {
        String []colsName = {"Mã Sản Phẩm","Tên Sản Phẩm","Mã","Số Lượng","Giá","Loại","Mô Tả","Giá Giảm","Trình Trạng"};
        TableQLSP.setColumnIdentifiers(colsName);   
        LoadSPAdd();
        TTQLSP_Add.setDefaultEditor(Object.class, null);
        TTQLSP_Add.setModel(TableQLSP);
    }
    
    private void setButtomQLSP(Boolean t){
        this.btnQLSP_ThemSP.setEnabled(t);
        this.btnQLSP_Luu.setEnabled(!t);
        this.btnQLSP_HuySP.setEnabled(!t);
        this.btnQLSP_TimKiemMa.setEnabled(t);
        this.btnQLSP_TimKiemTM.setEnabled(t);
        this.txtQLSP_TenSanPham.setEnabled(!t);
        this.txtQLSP_code.setEnabled(!t);
        this.spQLSP_SoLuong.setEnabled(!t);
        this.SpQLSP_Gia.setEnabled(!t);
        this.cboQLSP_KhuyenMai.setEnabled(!t);
        this.cboQLSP_LoaiSP.setEnabled(!t);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        JtForm = new javax.swing.JTabbedPane();
        JTTrangChu = new javax.swing.JTabbedPane();
        pnTrangChu = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TTrangChu_KhuyenMai = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TTrangChu_SP = new javax.swing.JTable();
        JtCMS = new javax.swing.JTabbedPane();
        paCMS_BanHang = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        paCMS_DoiTra = new javax.swing.JPanel();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        JtHeThong = new javax.swing.JTabbedPane();
        JTHeThong_QLSP = new javax.swing.JTabbedPane();
        paHeThong_QLSP = new javax.swing.JPanel();
        JFQLSP = new javax.swing.JInternalFrame();
        jPanel5 = new javax.swing.JPanel();
        txtQLSP_TimKiem = new javax.swing.JTextField();
        btnQLSP_TimKiemTM = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbIdSanPham = new javax.swing.JLabel();
        txtQLSP_TenSanPham = new javax.swing.JTextField();
        cboQLSP_TrangThai = new javax.swing.JComboBox<>();
        cboQLSP_LoaiSP = new javax.swing.JComboBox<>();
        spQLSP_SoLuong = new javax.swing.JSpinner();
        cboQLSP_KhuyenMai = new javax.swing.JComboBox<>();
        SpQLSP_Gia = new javax.swing.JSpinner();
        txtQLSP_code = new javax.swing.JTextField();
        btnQLSP_ThemSP = new javax.swing.JButton();
        btnQLSP_SuaSP = new javax.swing.JButton();
        btnQLSP_Luu = new javax.swing.JButton();
        btnQLSP_HuySP = new javax.swing.JButton();
        btnQLSP_TimKiemMa = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TTQLSP_Add = new javax.swing.JTable();
        cboQLSP_XemTheo = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbIdQLSP_IDLoai = new javax.swing.JLabel();
        cboQLSP_addTTLoai = new javax.swing.JComboBox<>();
        txtQLSP_Loai_MoTa = new javax.swing.JTextField();
        btnQLSP_Loai_Them = new javax.swing.JButton();
        btnQLSP_Loai_Sua = new javax.swing.JButton();
        btnQLSP_Loai_Huy = new javax.swing.JButton();
        txtQLSP_Loai_Tim = new javax.swing.JTextField();
        btnQLSP_Loai_Tim = new javax.swing.JButton();
        btnQLSP_Loai_Show = new javax.swing.JButton();
        btnQLSP_Loai_Luu = new javax.swing.JButton();
        paHeThong_ThongKe = new javax.swing.JPanel();
        JTTKSP = new javax.swing.JInternalFrame();
        JTHeThong_QLTK = new javax.swing.JTabbedPane();
        paHeThong_QLTK_TaiKhoan = new javax.swing.JPanel();
        JFQLTK = new javax.swing.JInternalFrame();
        paHeThong_QLTK_Chucvu = new javax.swing.JPanel();
        JFQLCV = new javax.swing.JInternalFrame();
        paHeThong_QLTK_ThongKe = new javax.swing.JPanel();
        JFTKHT = new javax.swing.JInternalFrame();
        JttroGiup = new javax.swing.JTabbedPane();
        paTroGiup_PhanMem = new javax.swing.JPanel();
        jInternalFrame8 = new javax.swing.JInternalFrame();
        paTrooGiup_QuanLy = new javax.swing.JPanel();
        jInternalFrame9 = new javax.swing.JInternalFrame();
        JTCaiDat = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        lbIdLogIn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hệ Thống Quản Trị Bán Hàng TECHLAZA");
        setMaximumSize(new java.awt.Dimension(1200, 735));
        setMinimumSize(new java.awt.Dimension(1200, 735));
        setPreferredSize(new java.awt.Dimension(1217, 735));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setLayout(null);

        pnTrangChu.setLayout(null);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Đang Khuyến Mãi"));
        jPanel3.setForeground(new java.awt.Color(255, 51, 0));
        jPanel3.setLayout(null);

        TTrangChu_KhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(TTrangChu_KhuyenMai);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(10, 20, 1170, 240);

        pnTrangChu.add(jPanel3);
        jPanel3.setBounds(0, 0, 1190, 280);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẩm mới"));
        jPanel4.setLayout(null);

        TTrangChu_SP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TTrangChu_SP.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(TTrangChu_SP);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(10, 20, 1170, 270);

        pnTrangChu.add(jPanel4);
        jPanel4.setBounds(0, 290, 1190, 310);

        JTTrangChu.addTab("Danh Sách Sản Phẩm", pnTrangChu);

        JtForm.addTab("Trang Chủ", JTTrangChu);

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1174, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paCMS_BanHangLayout = new javax.swing.GroupLayout(paCMS_BanHang);
        paCMS_BanHang.setLayout(paCMS_BanHangLayout);
        paCMS_BanHangLayout.setHorizontalGroup(
            paCMS_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );
        paCMS_BanHangLayout.setVerticalGroup(
            paCMS_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );

        JtCMS.addTab("Bán Hàng", paCMS_BanHang);

        jInternalFrame2.setVisible(true);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1174, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paCMS_DoiTraLayout = new javax.swing.GroupLayout(paCMS_DoiTra);
        paCMS_DoiTra.setLayout(paCMS_DoiTraLayout);
        paCMS_DoiTraLayout.setHorizontalGroup(
            paCMS_DoiTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame2)
        );
        paCMS_DoiTraLayout.setVerticalGroup(
            paCMS_DoiTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame2)
        );

        JtCMS.addTab("Đổi Trả", paCMS_DoiTra);

        JtForm.addTab("CMS", JtCMS);

        JFQLSP.setTitle("Quản Lý Sản Phẩm");
        JFQLSP.setVisible(true);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Sản Phẩm"));
        jPanel5.setLayout(null);

        txtQLSP_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQLSP_TimKiemKeyReleased(evt);
            }
        });
        jPanel5.add(txtQLSP_TimKiem);
        txtQLSP_TimKiem.setBounds(410, 20, 260, 20);

        btnQLSP_TimKiemTM.setText("Tìm Kiếm Thông Minh");
        btnQLSP_TimKiemTM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_TimKiemTMActionPerformed(evt);
            }
        });
        jPanel5.add(btnQLSP_TimKiemTM);
        btnQLSP_TimKiemTM.setBounds(240, 20, 160, 23);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Mã Sản Phẩm:");
        jPanel5.add(jLabel1);
        jLabel1.setBounds(10, 60, 80, 15);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Tên Sản Phẩm:");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(10, 100, 90, 15);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Code:");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(680, 100, 32, 15);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Số Lượng:");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(280, 100, 60, 15);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Giá:");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(690, 60, 20, 15);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Trình Trạng");
        jPanel5.add(jLabel6);
        jLabel6.setBounds(280, 60, 70, 15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Loại Sản Phẩm:");
        jPanel5.add(jLabel7);
        jLabel7.setBounds(450, 60, 83, 15);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Khuyến Mãi:");
        jPanel5.add(jLabel8);
        jLabel8.setBounds(450, 100, 66, 15);

        lbIdSanPham.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbIdSanPham.setText("   ");
        jPanel5.add(lbIdSanPham);
        lbIdSanPham.setBounds(120, 60, 38, 15);
        jPanel5.add(txtQLSP_TenSanPham);
        txtQLSP_TenSanPham.setBounds(100, 100, 170, 20);

        cboQLSP_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn Hàng", "Tạm Hết" }));
        jPanel5.add(cboQLSP_TrangThai);
        cboQLSP_TrangThai.setBounds(350, 60, 90, 20);

        jPanel5.add(cboQLSP_LoaiSP);
        cboQLSP_LoaiSP.setBounds(570, 60, 100, 20);

        spQLSP_SoLuong.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        jPanel5.add(spQLSP_SoLuong);
        spQLSP_SoLuong.setBounds(350, 100, 90, 20);

        jPanel5.add(cboQLSP_KhuyenMai);
        cboQLSP_KhuyenMai.setBounds(520, 100, 150, 20);

        SpQLSP_Gia.setModel(new javax.swing.SpinnerNumberModel(1000, 1000, null, 500));
        jPanel5.add(SpQLSP_Gia);
        SpQLSP_Gia.setBounds(720, 60, 90, 20);
        jPanel5.add(txtQLSP_code);
        txtQLSP_code.setBounds(720, 100, 90, 20);

        btnQLSP_ThemSP.setText("Thêm");
        btnQLSP_ThemSP.setPreferredSize(new java.awt.Dimension(110, 25));
        btnQLSP_ThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_ThemSPActionPerformed(evt);
            }
        });
        jPanel5.add(btnQLSP_ThemSP);
        btnQLSP_ThemSP.setBounds(90, 150, 110, 25);

        btnQLSP_SuaSP.setText("Sửa");
        btnQLSP_SuaSP.setPreferredSize(new java.awt.Dimension(110, 25));
        btnQLSP_SuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_SuaSPActionPerformed(evt);
            }
        });
        jPanel5.add(btnQLSP_SuaSP);
        btnQLSP_SuaSP.setBounds(250, 150, 110, 25);

        btnQLSP_Luu.setText("Cập Nhật");
        btnQLSP_Luu.setPreferredSize(new java.awt.Dimension(110, 25));
        btnQLSP_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_LuuActionPerformed(evt);
            }
        });
        jPanel5.add(btnQLSP_Luu);
        btnQLSP_Luu.setBounds(430, 150, 110, 25);

        btnQLSP_HuySP.setText("Hủy");
        btnQLSP_HuySP.setPreferredSize(new java.awt.Dimension(110, 25));
        btnQLSP_HuySP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_HuySPActionPerformed(evt);
            }
        });
        jPanel5.add(btnQLSP_HuySP);
        btnQLSP_HuySP.setBounds(620, 150, 110, 25);

        btnQLSP_TimKiemMa.setText("Tìm Theo Mã");
        btnQLSP_TimKiemMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_TimKiemMaActionPerformed(evt);
            }
        });
        jPanel5.add(btnQLSP_TimKiemMa);
        btnQLSP_TimKiemMa.setBounds(110, 20, 110, 23);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));
        jPanel6.setLayout(null);

        TTQLSP_Add.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TTQLSP_Add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TTQLSP_AddMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TTQLSP_Add);

        jPanel6.add(jScrollPane3);
        jScrollPane3.setBounds(10, 50, 1150, 270);

        cboQLSP_XemTheo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboQLSP_XemTheoItemStateChanged(evt);
            }
        });
        jPanel6.add(cboQLSP_XemTheo);
        cboQLSP_XemTheo.setBounds(10, 20, 180, 20);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Loại Sản Phẩm"));
        jPanel7.setLayout(null);

        jLabel9.setText("Mã Loại:");
        jPanel7.add(jLabel9);
        jLabel9.setBounds(10, 60, 50, 20);

        jLabel10.setText("Trạng Thái:");
        jPanel7.add(jLabel10);
        jLabel10.setBounds(10, 90, 70, 14);

        jLabel11.setText("Tên Loại:");
        jPanel7.add(jLabel11);
        jLabel11.setBounds(10, 120, 60, 20);

        lbIdQLSP_IDLoai.setText("   ");
        jPanel7.add(lbIdQLSP_IDLoai);
        lbIdQLSP_IDLoai.setBounds(80, 60, 40, 20);

        cboQLSP_addTTLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sử Dụng", "Không Sử Dụng" }));
        jPanel7.add(cboQLSP_addTTLoai);
        cboQLSP_addTTLoai.setBounds(90, 90, 110, 20);

        txtQLSP_Loai_MoTa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQLSP_Loai_MoTaKeyReleased(evt);
            }
        });
        jPanel7.add(txtQLSP_Loai_MoTa);
        txtQLSP_Loai_MoTa.setBounds(90, 120, 190, 20);

        btnQLSP_Loai_Them.setText("Thêm");
        btnQLSP_Loai_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_Loai_ThemActionPerformed(evt);
            }
        });
        jPanel7.add(btnQLSP_Loai_Them);
        btnQLSP_Loai_Them.setBounds(4, 150, 70, 23);

        btnQLSP_Loai_Sua.setText("Sửa");
        btnQLSP_Loai_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_Loai_SuaActionPerformed(evt);
            }
        });
        jPanel7.add(btnQLSP_Loai_Sua);
        btnQLSP_Loai_Sua.setBounds(84, 150, 70, 23);

        btnQLSP_Loai_Huy.setText("Hủy");
        btnQLSP_Loai_Huy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_Loai_HuyActionPerformed(evt);
            }
        });
        jPanel7.add(btnQLSP_Loai_Huy);
        btnQLSP_Loai_Huy.setBounds(264, 150, 70, 23);

        txtQLSP_Loai_Tim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQLSP_Loai_TimKeyReleased(evt);
            }
        });
        jPanel7.add(txtQLSP_Loai_Tim);
        txtQLSP_Loai_Tim.setBounds(50, 30, 130, 20);

        btnQLSP_Loai_Tim.setText("Tìm the tên");
        btnQLSP_Loai_Tim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_Loai_TimActionPerformed(evt);
            }
        });
        jPanel7.add(btnQLSP_Loai_Tim);
        btnQLSP_Loai_Tim.setBounds(200, 30, 120, 23);

        btnQLSP_Loai_Show.setText("Hiển thị hết");
        btnQLSP_Loai_Show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_Loai_ShowActionPerformed(evt);
            }
        });
        jPanel7.add(btnQLSP_Loai_Show);
        btnQLSP_Loai_Show.setBounds(200, 60, 120, 23);

        btnQLSP_Loai_Luu.setText("Cập Nhật");
        btnQLSP_Loai_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP_Loai_LuuActionPerformed(evt);
            }
        });
        jPanel7.add(btnQLSP_Loai_Luu);
        btnQLSP_Loai_Luu.setBounds(170, 150, 90, 23);

        javax.swing.GroupLayout JFQLSPLayout = new javax.swing.GroupLayout(JFQLSP.getContentPane());
        JFQLSP.getContentPane().setLayout(JFQLSPLayout);
        JFQLSPLayout.setHorizontalGroup(
            JFQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JFQLSPLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        JFQLSPLayout.setVerticalGroup(
            JFQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JFQLSPLayout.createSequentialGroup()
                .addGroup(JFQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout paHeThong_QLSPLayout = new javax.swing.GroupLayout(paHeThong_QLSP);
        paHeThong_QLSP.setLayout(paHeThong_QLSPLayout);
        paHeThong_QLSPLayout.setHorizontalGroup(
            paHeThong_QLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JFQLSP)
        );
        paHeThong_QLSPLayout.setVerticalGroup(
            paHeThong_QLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JFQLSP)
        );

        JTHeThong_QLSP.addTab("Sản Phẩm", paHeThong_QLSP);

        JTTKSP.setVisible(true);

        javax.swing.GroupLayout JTTKSPLayout = new javax.swing.GroupLayout(JTTKSP.getContentPane());
        JTTKSP.getContentPane().setLayout(JTTKSPLayout);
        JTTKSPLayout.setHorizontalGroup(
            JTTKSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1169, Short.MAX_VALUE)
        );
        JTTKSPLayout.setVerticalGroup(
            JTTKSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paHeThong_ThongKeLayout = new javax.swing.GroupLayout(paHeThong_ThongKe);
        paHeThong_ThongKe.setLayout(paHeThong_ThongKeLayout);
        paHeThong_ThongKeLayout.setHorizontalGroup(
            paHeThong_ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JTTKSP)
        );
        paHeThong_ThongKeLayout.setVerticalGroup(
            paHeThong_ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JTTKSP)
        );

        JTHeThong_QLSP.addTab("Thống Kê", paHeThong_ThongKe);

        JtHeThong.addTab("Quản Lý Sản Phẩm", JTHeThong_QLSP);

        JTHeThong_QLTK.setToolTipText("Thống Kê Tài Khoản");

        JFQLTK.setTitle("Quản Lý Tài Khoản");
        JFQLTK.setVisible(true);

        javax.swing.GroupLayout JFQLTKLayout = new javax.swing.GroupLayout(JFQLTK.getContentPane());
        JFQLTK.getContentPane().setLayout(JFQLTKLayout);
        JFQLTKLayout.setHorizontalGroup(
            JFQLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1169, Short.MAX_VALUE)
        );
        JFQLTKLayout.setVerticalGroup(
            JFQLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paHeThong_QLTK_TaiKhoanLayout = new javax.swing.GroupLayout(paHeThong_QLTK_TaiKhoan);
        paHeThong_QLTK_TaiKhoan.setLayout(paHeThong_QLTK_TaiKhoanLayout);
        paHeThong_QLTK_TaiKhoanLayout.setHorizontalGroup(
            paHeThong_QLTK_TaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JFQLTK)
        );
        paHeThong_QLTK_TaiKhoanLayout.setVerticalGroup(
            paHeThong_QLTK_TaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JFQLTK)
        );

        JTHeThong_QLTK.addTab("Tài Khoản", paHeThong_QLTK_TaiKhoan);

        JFQLCV.setTitle("Quản Lý Chức Vụ");
        JFQLCV.setVisible(true);

        javax.swing.GroupLayout JFQLCVLayout = new javax.swing.GroupLayout(JFQLCV.getContentPane());
        JFQLCV.getContentPane().setLayout(JFQLCVLayout);
        JFQLCVLayout.setHorizontalGroup(
            JFQLCVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1159, Short.MAX_VALUE)
        );
        JFQLCVLayout.setVerticalGroup(
            JFQLCVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paHeThong_QLTK_ChucvuLayout = new javax.swing.GroupLayout(paHeThong_QLTK_Chucvu);
        paHeThong_QLTK_Chucvu.setLayout(paHeThong_QLTK_ChucvuLayout);
        paHeThong_QLTK_ChucvuLayout.setHorizontalGroup(
            paHeThong_QLTK_ChucvuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paHeThong_QLTK_ChucvuLayout.createSequentialGroup()
                .addComponent(JFQLCV)
                .addContainerGap())
        );
        paHeThong_QLTK_ChucvuLayout.setVerticalGroup(
            paHeThong_QLTK_ChucvuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JFQLCV)
        );

        JTHeThong_QLTK.addTab("Chức Vụ", paHeThong_QLTK_Chucvu);

        JFTKHT.setVisible(true);

        javax.swing.GroupLayout JFTKHTLayout = new javax.swing.GroupLayout(JFTKHT.getContentPane());
        JFTKHT.getContentPane().setLayout(JFTKHTLayout);
        JFTKHTLayout.setHorizontalGroup(
            JFTKHTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1169, Short.MAX_VALUE)
        );
        JFTKHTLayout.setVerticalGroup(
            JFTKHTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paHeThong_QLTK_ThongKeLayout = new javax.swing.GroupLayout(paHeThong_QLTK_ThongKe);
        paHeThong_QLTK_ThongKe.setLayout(paHeThong_QLTK_ThongKeLayout);
        paHeThong_QLTK_ThongKeLayout.setHorizontalGroup(
            paHeThong_QLTK_ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JFTKHT)
        );
        paHeThong_QLTK_ThongKeLayout.setVerticalGroup(
            paHeThong_QLTK_ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JFTKHT)
        );

        JTHeThong_QLTK.addTab("Thống Kê", paHeThong_QLTK_ThongKe);

        JtHeThong.addTab("Quản Lý Tài Khoản", JTHeThong_QLTK);

        JtForm.addTab("Hệ Thống", JtHeThong);

        jInternalFrame8.setVisible(true);

        javax.swing.GroupLayout jInternalFrame8Layout = new javax.swing.GroupLayout(jInternalFrame8.getContentPane());
        jInternalFrame8.getContentPane().setLayout(jInternalFrame8Layout);
        jInternalFrame8Layout.setHorizontalGroup(
            jInternalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1174, Short.MAX_VALUE)
        );
        jInternalFrame8Layout.setVerticalGroup(
            jInternalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paTroGiup_PhanMemLayout = new javax.swing.GroupLayout(paTroGiup_PhanMem);
        paTroGiup_PhanMem.setLayout(paTroGiup_PhanMemLayout);
        paTroGiup_PhanMemLayout.setHorizontalGroup(
            paTroGiup_PhanMemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame8)
        );
        paTroGiup_PhanMemLayout.setVerticalGroup(
            paTroGiup_PhanMemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame8)
        );

        JttroGiup.addTab("Hệ Thống", paTroGiup_PhanMem);

        jInternalFrame9.setVisible(true);

        javax.swing.GroupLayout jInternalFrame9Layout = new javax.swing.GroupLayout(jInternalFrame9.getContentPane());
        jInternalFrame9.getContentPane().setLayout(jInternalFrame9Layout);
        jInternalFrame9Layout.setHorizontalGroup(
            jInternalFrame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1174, Short.MAX_VALUE)
        );
        jInternalFrame9Layout.setVerticalGroup(
            jInternalFrame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paTrooGiup_QuanLyLayout = new javax.swing.GroupLayout(paTrooGiup_QuanLy);
        paTrooGiup_QuanLy.setLayout(paTrooGiup_QuanLyLayout);
        paTrooGiup_QuanLyLayout.setHorizontalGroup(
            paTrooGiup_QuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame9)
        );
        paTrooGiup_QuanLyLayout.setVerticalGroup(
            paTrooGiup_QuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame9)
        );

        JttroGiup.addTab("Quản Lý", paTrooGiup_QuanLy);

        JtForm.addTab("Trợ Giúp", JttroGiup);
        JtForm.addTab("Cài Đặt", JTCaiDat);

        jPanel1.add(JtForm);
        JtForm.setBounds(0, 0, 1200, 650);
        JtForm.getAccessibleContext().setAccessibleName("");

        jPanel2.setBackground(new java.awt.Color(0, 255, 0));
        jPanel2.setLayout(null);

        lbIdLogIn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbIdLogIn.setForeground(new java.awt.Color(255, 51, 51));
        lbIdLogIn.setText("jLabel1");
        jPanel2.add(lbIdLogIn);
        lbIdLogIn.setBounds(960, 10, 240, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 650, 1200, 80);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1200, 740);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            // TODO add your handling code here:
            lbIdLogIn.setText(getTenDangNhap(id));
            cboQLSP_TrangThai.setEnabled(false);
            cboQLSP_addTTLoai.setEnabled(false);
            capnhatTrangChu();
            setButtomQLSP(true);
            SetButtomAllLoai(true);
            btnQLSP_Loai_Them.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened
    
    private void SetNullQLSP(){
        this.txtQLSP_TenSanPham.setText(null);
        this.txtQLSP_code.setText(null);
    }
    
    private void btnQLSP_TimKiemTMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_TimKiemTMActionPerformed
        // TODO add your handling code here:
        this.txtQLSP_TimKiem.setEnabled(true);
        this.btnQLSP_TimKiemMa.setEnabled(true);
        this.btnQLSP_TimKiemTM.setEnabled(false);
        timkiem=true;
        txtQLSP_TimKiem.setText(null);
        TTQLSP_Add.setModel(new DefaultTableModel());
    }//GEN-LAST:event_btnQLSP_TimKiemTMActionPerformed

    private void btnQLSP_ThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_ThemSPActionPerformed
         try {
            // TODO add your handling code here:
            sp=new SanPham_BUS();
            btnQLSP_SuaSP.setEnabled(false);
            cboQLSP_TrangThai.setEnabled(false);
            txtQLSP_TimKiem.setEnabled(false);
            cboQLSP_XemTheo.setEnabled(false);
            lbIdSanPham.setText(sp.getNextId());
            TTQLSP_Add.setModel(new DefaultTableModel());
            setButtomQLSP(false);
            SetNullQLSP();
            cboQLSP_TrangThai.setSelectedIndex(0);
            cboQLSP_LoaiSP.setSelectedIndex(0);
            cboQLSP_KhuyenMai.setSelectedIndex(0);
            txtQLSP_TimKiem.setText(null);
            trangthai=true;
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_btnQLSP_ThemSPActionPerformed

    private void btnQLSP_SuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_SuaSPActionPerformed
        // TODO add your handling code here:
        setButtomQLSP(false);
        this.btnQLSP_SuaSP.setEnabled(false);
        trangthai=false;
        txtQLSP_TimKiem.setEnabled(false);
        cboQLSP_TrangThai.setEnabled(true);
        cboQLSP_XemTheo.setEnabled(false);
    }//GEN-LAST:event_btnQLSP_SuaSPActionPerformed

    private boolean KiemtraThemSp(){
        if(txtQLSP_TenSanPham.getText().equals("") || txtQLSP_code.getText().equals(""))
            return false;
        return true;
    }
    
    private void btnQLSP_LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_LuuActionPerformed
        // TODO add your handling code here:
        try{
            sp=new SanPham_BUS();
            loai= new Loai_BUS();
            if(trangthai==true) //them
            {
                if(KiemtraThemSp()==true)
                {
                    
                    boolean tt;
                    if(cboQLSP_TrangThai.getSelectedIndex()==0)
                    {
                        tt=true;
                    }
                    else
                    {
                        tt=false;
                    }
                    String idl=cboQLSP_LoaiSP.getSelectedItem().toString();
                    String km= cboQLSP_KhuyenMai.getSelectedItem().toString();
                    khuyemai = new KhuyenMai_BUS();
                    float dongia= Float.parseFloat(SpQLSP_Gia.getValue().toString());
                    sp.AddSanPham(lbIdSanPham.getText(), txtQLSP_TenSanPham.getText(), txtQLSP_code.getText(),(Integer)spQLSP_SoLuong.getValue(),dongia,tt,loai.ShowLoai(idl),khuyemai.ShowKhuyenMai(km));
                    JOptionPane.showMessageDialog(this, "Thêm Sản Phẩm thành công");
                    dem++;
                    if(dem==1)
                    {
                        stt=Integer.parseInt(lbIdSanPham.getText());
                    }
                    ClearData();
                    CapNhatSPAdd();
                    capnhatTrangChu();
                    setButtomQLSP(true);
                    SetNullQLSP();
                    lbIdSanPham.setText(null);
                    cboQLSP_TrangThai.setEnabled(false);
                    cboQLSP_XemTheo.setEnabled(true);
                }else
                {
                    JOptionPane.showMessageDialog(this, "Chưa nhập hết thông tin cần thiết");
                }
            }else //sua
            {
                boolean tt;
                if(cboQLSP_TrangThai.getSelectedIndex()==0)
                {
                    tt=true;
                }
                else
                {
                    tt=false;
                }
                String idl=cboQLSP_LoaiSP.getSelectedItem().toString();
                String km= cboQLSP_KhuyenMai.getSelectedItem().toString();
                khuyemai = new KhuyenMai_BUS();
                float dongia= Float.parseFloat(SpQLSP_Gia.getValue().toString());
                sp.UpdateSanPham(lbIdSanPham.getText(), txtQLSP_TenSanPham.getText(), txtQLSP_code.getText(),(Integer)spQLSP_SoLuong.getValue(),dongia,tt,loai.ShowLoai(idl),khuyemai.ShowKhuyenMai(km));
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                SetNullQLSP();
                ClearData();
                CapNhatSPAdd();
                capnhatTrangChu();
                setButtomQLSP(true);
                lbIdSanPham.setText(null);
                cboQLSP_TrangThai.setEnabled(false);
                btnQLSP_SuaSP.setEnabled(false);
                cboQLSP_XemTheo.setEnabled(true);
            }
        } catch (SQLException ex) {
                Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnQLSP_LuuActionPerformed

    private void btnQLSP_HuySPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_HuySPActionPerformed
        // TODO add your handling code here:
        SetNullQLSP();
        setButtomQLSP(true);
        lbIdSanPham.setText(null);
        TTQLSP_Add.clearSelection();
        cboQLSP_TrangThai.setEnabled(false);
         cboQLSP_XemTheo.setEnabled(true);
    }//GEN-LAST:event_btnQLSP_HuySPActionPerformed

    
    private boolean setTrangThai(String in){
        if(in.equals("Sử Dụng"))
            return true;
        return false;
    }
    private void TTQLSP_AddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TTQLSP_AddMouseClicked
        // TODO add your handling code here:
        if(cboQLSP_XemTheo.getSelectedIndex()==0)
        {
            try {  
                int row=-1;
                row=TTQLSP_Add.getSelectedRow();
                if(row!=-1)
                {
                    if(cboQLSP_XemTheo.getSelectedIndex()==0)
                    {
                         btnQLSP_SuaSP.setEnabled(true);
                         btnQLSP_Loai_Sua.setEnabled(false);
                    }else
                    {
                        btnQLSP_Loai_Sua.setEnabled(true);
                        btnQLSP_SuaSP.setEnabled(false);
                    }
                }else
                {
                    btnQLSP_SuaSP.setEnabled(false);
                    btnQLSP_Loai_Sua.setEnabled(false);
                }
                sp= new SanPham_BUS();
                ResultSet res=null;
                res=sp.ShowSanPham(String.valueOf(TTQLSP_Add.getValueAt(row, 0)));
                while(res!=null && res.next())
                {
                    lbIdSanPham.setText(res.getString(1));
                    txtQLSP_TenSanPham.setText(res.getString(2));
                    txtQLSP_code.setText(res.getString(3));
                    spQLSP_SoLuong.setValue(Integer.parseInt(res.getString(4)));
                    SpQLSP_Gia.setValue(Integer.parseInt(res.getString(5)));
                    cboQLSP_TrangThai.setSelectedItem(getTrinhTrang(res.getString(6)));
                    cboQLSP_LoaiSP.setSelectedItem(res.getString(10));
                    cboQLSP_KhuyenMai.setSelectedItem(res.getString(14));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }    
        }else
        {
            //show du lien loai
            try {
                
                
                int row=-1;
                row=TTQLSP_Add.getSelectedRow();
                if(row!=-1){
                    btnQLSP_Loai_Sua.setEnabled(true);
                }else{
                    btnQLSP_Loai_Sua.setEnabled(false);
                }
                loai=new Loai_BUS();
                ResultSet res=null;
                res=loai.ShowLoaiId(Integer.parseInt(TTQLSP_Add.getValueAt(row,0).toString()));
                while(res!=null && res.next())
                {
                    lbIdQLSP_IDLoai.setText(res.getString(1));
                    txtQLSP_Loai_MoTa.setText(res.getString(2));
                    cboQLSP_addTTLoai.setSelectedItem(getTT(Integer.parseInt(res.getString(3))));
                    System.out.println(getTT(Integer.parseInt(res.getString(3))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_TTQLSP_AddMouseClicked

    private String getTT(int t){
        if(t==1)
            return "Sử Dụng";
        return "Không Sử Dụng";
    }
    
    private void txtQLSP_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQLSP_TimKiemKeyReleased
        // TODO add your handling code here:
        if(timkiem==true){
            //thong minh
            if(txtQLSP_TimKiem.getText().length()==0){
                TTQLSP_Add.setModel(new DefaultTableModel());
            }else
            {
                 String chuoi= txtQLSP_TimKiem.getText().trim();//xóa khoảng trắng 2 đầu ký tự
                //tạo tên cột
                 ResultSet rss=null;
                 Vector cols= new Vector();
                 cols.addElement("Mã Sản Phẩm");
                 cols.addElement("Tên Sản Phẩm");
                 cols.addElement("Mã");
                 cols.addElement("Số Lượng");
                 cols.addElement("Giá");
                 cols.addElement("Loại");
                 cols.addElement("Mô Tả");
                 cols.addElement("Giá Giảm");
                 cols.addElement("Trình Trạng");
                 Vector search= new Vector();
                try {
                    sp= new SanPham_BUS();
                    rss=sp.ShowSanPham();
                    while(rss.next())
                    {
                        Vector sanpham= new Vector();
                        sanpham.add(rss.getString(1));
                        sanpham.add(rss.getString(2));
                        sanpham.add(rss.getString(3));
                        sanpham.add(rss.getString(4));
                        sanpham.add(rss.getString(5));
                        sanpham.add(rss.getString(10));
                        sanpham.add(rss.getString(14));
                        sanpham.add(rss.getString(15));
                        sanpham.add(getTrinhTrang(rss.getString(6)));
                        if(sanpham.toString().contains(chuoi)) /*sanpham.toString().contains("Còn Hàng") || sanpham.toString().contains("còn hàng") || sanpham.toString().contains("Tạm Hết")||sanpham.toString().contains("tạm hết")*/
                        {
                            search.addElement(sanpham); // đưa dòng dữ liệu vào tableModel
                        }
                    }
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
                TTQLSP_Add.setModel(new DefaultTableModel(search, cols));
            }
        }else
        {
            if(txtQLSP_TimKiem.getText().length()==0){
                TTQLSP_Add.setModel(new DefaultTableModel());
            }else
            {
                String chuoi= txtQLSP_TimKiem.getText().trim();//xóa khoảng trắng 2 đầu ký tự
                //tạo tên cột
                 ResultSet rss=null;
                 Vector cols= new Vector();
                 cols.addElement("Mã Sản Phẩm");
                 cols.addElement("Tên Sản Phẩm");
                 cols.addElement("Mã");
                 cols.addElement("Số Lượng");
                 cols.addElement("Giá");
                 cols.addElement("Loại");
                 cols.addElement("Mô Tả");
                 cols.addElement("Giá Giảm");
                 cols.addElement("Trình Trạng");
                 Vector search= new Vector();
                 try {
                    sp= new SanPham_BUS();
                    rss=sp.ShowSanPhamMa(chuoi);
                    while(rss.next())
                    {
                        Vector sanpham= new Vector();
                        sanpham.add(rss.getString(1));
                        sanpham.add(rss.getString(2));
                        sanpham.add(rss.getString(3));
                        sanpham.add(rss.getString(4));
                        sanpham.add(rss.getString(5));
                        sanpham.add(rss.getString(10));
                        sanpham.add(rss.getString(14));
                        sanpham.add(rss.getString(15));
                        sanpham.add(getTrinhTrang(rss.getString(6)));
                        if(rss.getString(3).contains(chuoi))
                        {
                            search.addElement(sanpham); // đưa dòng dữ liệu vào tableModel
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
                 TTQLSP_Add.setModel(new DefaultTableModel(search, cols));
            }
        }
    }//GEN-LAST:event_txtQLSP_TimKiemKeyReleased

    private void btnQLSP_TimKiemMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_TimKiemMaActionPerformed
        // TODO add your handling code here:
        this.txtQLSP_TimKiem.setEnabled(true);
        this.btnQLSP_TimKiemMa.setEnabled(false);
        this.btnQLSP_TimKiemTM.setEnabled(true);
        timkiem=false;
        txtQLSP_TimKiem.setText(null);
        TTQLSP_Add.setModel(new DefaultTableModel());
    }//GEN-LAST:event_btnQLSP_TimKiemMaActionPerformed

    private void btnQLSP_Loai_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_Loai_ThemActionPerformed
        // TODO add your handling code here:
        btnQLSP_Loai_Them.setEnabled(false);
        btnQLSP_Loai_Show.setEnabled(false);
        txtQLSP_Loai_Tim.setEditable(false);
        btnQLSP_Loai_Tim.setEnabled(false);
            if(cboQLSP_XemTheo.getSelectedIndex()!=1){
                TTQLSP_Add.setModel(new DefaultTableModel());
            }
            cboQLSP_addTTLoai.setEnabled(false);
            cboQLSP_addTTLoai.setSelectedIndex(0);
            btnQLSP_Loai_Sua.setEnabled(false);
            addloai=true;
            txtQLSP_Loai_MoTa.setText(null);
            SetButtomAllLoai(false);
            try {
                loai= new Loai_BUS();
                lbIdQLSP_IDLoai.setText(loai.getNextIdLoai());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }//GEN-LAST:event_btnQLSP_Loai_ThemActionPerformed

    private void btnQLSP_Loai_HuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_Loai_HuyActionPerformed
        // TODO add your handling code here:
        lbIdQLSP_IDLoai.setText(null);
        TTQLSP_Add.clearSelection();
        txtQLSP_Loai_MoTa.setText(null);
        cboQLSP_addTTLoai.setEnabled(false);
        SetButtomAllLoai(true);
        btnQLSP_Loai_Show.setEnabled(true);
        txtQLSP_Loai_Tim.setEditable(true);
        btnQLSP_Loai_Tim.setEnabled(true);
    }//GEN-LAST:event_btnQLSP_Loai_HuyActionPerformed

    private void btnQLSP_Loai_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_Loai_SuaActionPerformed
        // TODO add your handling code here:
        cboQLSP_addTTLoai.setEnabled(true);
        addloai=false;
        btnQLSP_Loai_Sua.setEnabled(false);
        SetButtomAllLoai(false);
        btnQLSP_Loai_Show.setEnabled(false);
        txtQLSP_Loai_Tim.setEditable(false);
        btnQLSP_Loai_Tim.setEnabled(false);
    }//GEN-LAST:event_btnQLSP_Loai_SuaActionPerformed

    private void LoadLoaiSanPham(int id) throws SQLException {
        try {
            loai= new Loai_BUS();
            ResultSet res= null;
            res=loai.ShowLoaiSua(id);
            while(res!=null && res.next()){
                String rows[]= new String[3];
                rows[0]=res.getString(1);
                rows[1]=res.getString(2);
                rows[2]=getTT(Integer.parseInt(res.getString(3)));
                TableQLSP.addRow(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void CapNhatLoaiSanPham(int x) throws SQLException{
        String[] colsName={"Mã Loại","Tên Loại","Trạng Thái"};
        TableQLSP.setColumnIdentifiers(colsName);
        LoadLoaiSanPham(x);
        TTQLSP_Add.setModel(TableQLSP);
        TTQLSP_Add.setDefaultEditor(Object.class, null);

    }
    
    private void btnQLSP_Loai_LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_Loai_LuuActionPerformed
        // TODO add your handling code here:
        if(addloai==true){
            try {
                //them
                if(KiemTraLoai(txtQLSP_Loai_MoTa.getText()))
                {
                    try {
                        if(txtQLSP_Loai_MoTa.getText().length()>3)
                        {
                            loai= new Loai_BUS();;
                            loai.AddLoai(lbIdQLSP_IDLoai.getText(), txtQLSP_Loai_MoTa.getText(), getTrangThaiLoai(cboQLSP_addTTLoai.getSelectedIndex()));
                            JOptionPane.showMessageDialog(this, "Thêm Loại Sản Phẩm Thành Công");
                            ddem++;
                            if(ddem==1){
                                sst=Integer.parseInt(lbIdQLSP_IDLoai.getText());
                            }
                            txtQLSP_Loai_MoTa.setText(null);
                            SetButtomAllLoai(true); 
                            capnhatLoai();
                            ClearData();
                            CapNhatLoaiSanPham(sst);
                            capnhatTrangChu();
                            cboQLSP_addTTLoai.setEnabled(false);
                            
                        }else
                        {
                            JOptionPane.showMessageDialog(this,"Mô Tả khuyến mãi tối thiểu 3 lí tự");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this,"Tên loại đã tồn tại rồi");
                }
            } catch (SQLException ex) {   
                Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else//sua
        {
            try {
                int dong=TTQLSP_Add.getSelectedRow();
                int cot =2;
                boolean tt = setTrangThai((String) TableQLSP.getValueAt(dong, cot));
            if((KiemTraLoaiSP(String.valueOf(lbIdQLSP_IDLoai.getText()))==true)||((KiemTraLoaiSP(String.valueOf(lbIdQLSP_IDLoai.getText()))==false)&&(tt==false)))
            {
                    //thoa dieu kien
                    loai= new Loai_BUS();
                    boolean xx;
                    if(cboQLSP_addTTLoai.getSelectedIndex()==0)
                    {
                        xx=true;
                    }else
                    {
                        xx=false;
                    }
                    loai.EditLoai(lbIdQLSP_IDLoai.getText(),txtQLSP_Loai_MoTa.getText(),xx);
                    JOptionPane.showMessageDialog(this, "Cập nhật Loại sản phẩm thành công");
                    txtQLSP_Loai_MoTa.setText(null);
                    SetButtomAllLoai(true); 
                    ClearData();
                    capnhatLoai();
                    CapNhatLoaiSanPham(sst);
                    capnhatTrangChu();
                    btnQLSP_Loai_Show.doClick();
                    cboQLSP_addTTLoai.setEnabled(false);
                    SetButtomAllLoai(true);
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Loại Sản Phẩm này đang được sử dụng.");
                cboQLSP_addTTLoai.setSelectedIndex(0);
            }
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            btnQLSP_Loai_Show.setEnabled(true);
            txtQLSP_Loai_Tim.setEditable(true);
            btnQLSP_Loai_Tim.setEnabled(true);
        }
    }//GEN-LAST:event_btnQLSP_Loai_LuuActionPerformed

    private boolean KiemTraLoaiSP(String in) throws SQLException{
       int x=0;
        try {
            sp= new SanPham_BUS();
            ResultSet res=null;
            res=sp.ShowTonTaiLoai();
            while(res.next())
            {
                x=0;
                String[] chuoi= new String[1];
                chuoi[0]=res.getString(1);
                
                if(Integer.parseInt(chuoi[0])==Integer.parseInt(in))
                {
                    x++;
                    return false;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return true;
    }
    
    private boolean KiemTraLoai(String in) throws SQLException {
    
            loai= new Loai_BUS();
            String ten=loai.ShowLoai(in);
           if(ten==null)
               return true;
           return false;
    }
    
    private void cboQLSP_XemTheoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboQLSP_XemTheoItemStateChanged
        // TODO add your handling code here:
        SetNullQLSP();
        lbIdSanPham.setText(null);
        try {  
             if(cboQLSP_XemTheo.getSelectedIndex()==0)
            {
                //xem theo sp
                TTQLSP_Add.setModel(new DefaultTableModel());
                txtQLSP_TimKiem.setText(null);
                btnQLSP_ThemSP.setEnabled(true);
                btnQLSP_TimKiemMa.setEnabled(true);
                btnQLSP_TimKiemTM.setEnabled(true);
                btnQLSP_Loai_Them.setEnabled(false);
                btnQLSP_Loai_Show.setEnabled(false);
                btnQLSP_Loai_Tim.setEnabled(false);
                btnQLSP_Loai_Sua.setEnabled(false);
                txtQLSP_Loai_Tim.setEditable(false);
            }else{
                txtQLSP_TimKiem.setText(null);
                TTQLSP_Add.setModel(new DefaultTableModel());
                btnQLSP_ThemSP.setEnabled(false);
                btnQLSP_TimKiemMa.setEnabled(false);
                btnQLSP_TimKiemTM.setEnabled(false);
                btnQLSP_Loai_Them.setEnabled(true);
                 btnQLSP_Loai_Show.setEnabled(true);
                btnQLSP_Loai_Tim.setEnabled(true);
                txtQLSP_Loai_Tim.setEditable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cboQLSP_XemTheoItemStateChanged

    private void txtQLSP_Loai_MoTaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQLSP_Loai_MoTaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            btnQLSP_Loai_Luu.doClick();
        }
    }//GEN-LAST:event_txtQLSP_Loai_MoTaKeyReleased

    private void tim(boolean t,String x){
         try {
                //tim theo trang thai
                loai= new Loai_BUS();
                ResultSet res=null;
                if(t==true)
                {
                    res=loai.ShowLoaiTim(x);
                }else
                {
                    res=loai.ShowLoaiAll();
                }
                while(res.next())
                {
                    String row[] = new String[3];
                    row[0]=res.getString(1);
                    row[1]=res.getString(2);
                    row[2]=getTrinhTrangLoai(res.getString(3));
                    TableQLSP.addRow(row);
                }
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void btnQLSP_Loai_TimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_Loai_TimActionPerformed
        try {
            // TODO add your handling code here:
            ClearData();
            String []colsName = {"Mã","Tên","Trình Trạng"};
            TableQLSP.setColumnIdentifiers(colsName);
            tim(true,txtQLSP_Loai_Tim.getText());
            TTQLSP_Add.setDefaultEditor(Object.class, null);
            TTQLSP_Add.setModel(TableQLSP);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }//GEN-LAST:event_btnQLSP_Loai_TimActionPerformed

    private void txtQLSP_Loai_TimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQLSP_Loai_TimKeyReleased
        // TODO add your handling code here:
         if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
            btnQLSP_Loai_Tim.doClick();
        }
         if(txtQLSP_Loai_Tim.getText().equals("")){
             btnQLSP_Loai_Show.doClick();
         }
    }//GEN-LAST:event_txtQLSP_Loai_TimKeyReleased

    private void btnQLSP_Loai_ShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP_Loai_ShowActionPerformed
        try {
            // TODO add your handling code here:
            ClearData();
            String []colsName = {"Mã","Tên","Trình Trạng"};
            TableQLSP.setColumnIdentifiers(colsName);
            tim(false,null);
            TTQLSP_Add.setDefaultEditor(Object.class, null);
             TTQLSP_Add.setModel(new DefaultTableModel());
            TTQLSP_Add.setModel(TableQLSP);
            txtQLSP_TimKiem.setText(null);
               
                btnQLSP_ThemSP.setEnabled(false);
                btnQLSP_TimKiemMa.setEnabled(false);
                btnQLSP_TimKiemTM.setEnabled(false);
                btnQLSP_Loai_Them.setEnabled(true);
                 btnQLSP_Loai_Show.setEnabled(true);
                btnQLSP_Loai_Tim.setEnabled(true);
                txtQLSP_Loai_Tim.setEditable(true);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnQLSP_Loai_ShowActionPerformed

    private boolean getTT(String x){
        if(x.equals("sử dụng") || x.equals("sử dụng"))
            return true;
        if(x.equals("không sử dụng") || x.equals("Không Sử Dụng"))
            return true;
        return false;
    }
    
    private void SetButtomAllLoai(boolean  t){
        this.btnQLSP_Loai_Them.setEnabled(t);
        txtQLSP_Loai_MoTa.setEnabled(!t);
        this.btnQLSP_Loai_Luu.setEnabled(!t);
        this.btnQLSP_Loai_Huy.setEnabled(!t);
    }
   
    private boolean KiemTraSo(String x){
        try {
            Integer.parseInt(x);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void capnhatLoai() throws SQLException {
        try {
            cboQLSP_LoaiSP.removeAllItems();
            loai= new Loai_BUS();
            ResultSet res= null;
            res=loai.ShowLoai();
            while(res!=null && res.next())
            {
                cboQLSP_LoaiSP.addItem(res.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean getTrangThaiLoai(int chuoi){
        if(chuoi==0)
            return true;
        return false;
    }
    
    public static void main(String args[]) throws SQLException {
        QuanLyBanHang ql= new QuanLyBanHang();
        ql.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame JFQLCV;
    private javax.swing.JInternalFrame JFQLSP;
    private javax.swing.JInternalFrame JFQLTK;
    private javax.swing.JInternalFrame JFTKHT;
    private javax.swing.JTabbedPane JTCaiDat;
    private javax.swing.JTabbedPane JTHeThong_QLSP;
    private javax.swing.JTabbedPane JTHeThong_QLTK;
    private javax.swing.JInternalFrame JTTKSP;
    private javax.swing.JTabbedPane JTTrangChu;
    private javax.swing.JTabbedPane JtCMS;
    private javax.swing.JTabbedPane JtForm;
    private javax.swing.JTabbedPane JtHeThong;
    private javax.swing.JTabbedPane JttroGiup;
    private javax.swing.JSpinner SpQLSP_Gia;
    private javax.swing.JTable TTQLSP_Add;
    private javax.swing.JTable TTrangChu_KhuyenMai;
    private javax.swing.JTable TTrangChu_SP;
    private javax.swing.JButton btnQLSP_HuySP;
    private javax.swing.JButton btnQLSP_Loai_Huy;
    private javax.swing.JButton btnQLSP_Loai_Luu;
    private javax.swing.JButton btnQLSP_Loai_Show;
    private javax.swing.JButton btnQLSP_Loai_Sua;
    private javax.swing.JButton btnQLSP_Loai_Them;
    private javax.swing.JButton btnQLSP_Loai_Tim;
    private javax.swing.JButton btnQLSP_Luu;
    private javax.swing.JButton btnQLSP_SuaSP;
    private javax.swing.JButton btnQLSP_ThemSP;
    private javax.swing.JButton btnQLSP_TimKiemMa;
    private javax.swing.JButton btnQLSP_TimKiemTM;
    private javax.swing.JComboBox<String> cboQLSP_KhuyenMai;
    private javax.swing.JComboBox<String> cboQLSP_LoaiSP;
    private javax.swing.JComboBox<String> cboQLSP_TrangThai;
    private javax.swing.JComboBox<String> cboQLSP_XemTheo;
    private javax.swing.JComboBox<String> cboQLSP_addTTLoai;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame8;
    private javax.swing.JInternalFrame jInternalFrame9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbIdLogIn;
    private javax.swing.JLabel lbIdQLSP_IDLoai;
    private javax.swing.JLabel lbIdSanPham;
    private javax.swing.JPanel paCMS_BanHang;
    private javax.swing.JPanel paCMS_DoiTra;
    private javax.swing.JPanel paHeThong_QLSP;
    private javax.swing.JPanel paHeThong_QLTK_Chucvu;
    private javax.swing.JPanel paHeThong_QLTK_TaiKhoan;
    private javax.swing.JPanel paHeThong_QLTK_ThongKe;
    private javax.swing.JPanel paHeThong_ThongKe;
    private javax.swing.JPanel paTroGiup_PhanMem;
    private javax.swing.JPanel paTrooGiup_QuanLy;
    private javax.swing.JPanel pnTrangChu;
    private javax.swing.JSpinner spQLSP_SoLuong;
    private javax.swing.JTextField txtQLSP_Loai_MoTa;
    private javax.swing.JTextField txtQLSP_Loai_Tim;
    private javax.swing.JTextField txtQLSP_TenSanPham;
    private javax.swing.JTextField txtQLSP_TimKiem;
    private javax.swing.JTextField txtQLSP_code;
    // End of variables declaration//GEN-END:variables
}
