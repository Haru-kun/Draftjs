
package GUI;
import BUS.User_BUS;
import DAO.ConnectionData;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.*;

public class DangNhap extends javax.swing.JFrame {
    private static ConnectionData con;
    public boolean ngonngu;
    private int _dem=0;
    public DangNhap() {
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// <- prevent closing
        final DangNhap gui=this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(gui,
                        "Bạn có chắc chắn muốn thoát không?", "Closing dialog",
                        JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        ngonngu=false;
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/image/shopping-girl-icon.png")));
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        lbTaiKhoan = new javax.swing.JLabel();
        lbMatKhau = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        lbTieuDe = new javax.swing.JLabel();
        lbQuen = new javax.swing.JLabel();
        btnDangNhap = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        cboNgonNgu = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ĐĂNG NHẬP");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(380, 250));
        setResizable(false);
        setSize(new java.awt.Dimension(380, 250));
        getContentPane().setLayout(null);

        lbTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lbTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Admin-icon .png"))); // NOI18N
        lbTaiKhoan.setText("Tài Khoản:");
        getContentPane().add(lbTaiKhoan);
        lbTaiKhoan.setBounds(30, 65, 120, 30);

        lbMatKhau.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        lbMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Register-icon.png"))); // NOI18N
        lbMatKhau.setText("Mật Khẩu:");
        getContentPane().add(lbMatKhau);
        lbMatKhau.setBounds(30, 100, 120, 24);

        txtUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUserKeyReleased(evt);
            }
        });
        getContentPane().add(txtUser);
        txtUser.setBounds(150, 70, 176, 23);

        txtPass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPassKeyReleased(evt);
            }
        });
        getContentPane().add(txtPass);
        txtPass.setBounds(150, 100, 176, 23);

        lbTieuDe.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbTieuDe.setForeground(new java.awt.Color(255, 0, 102));
        lbTieuDe.setText("ĐĂNG NHẬP VÀO HỆ THỐNG TECHLAZA");
        getContentPane().add(lbTieuDe);
        lbTieuDe.setBounds(10, 20, 360, 30);

        lbQuen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbQuen.setForeground(new java.awt.Color(255, 51, 51));
        lbQuen.setText("Quên mật khẩu?");
        lbQuen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbQuenMouseClicked(evt);
            }
        });
        getContentPane().add(lbQuen);
        lbQuen.setBounds(50, 140, 100, 20);

        btnDangNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDangNhap.setText("Đăng Nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });
        getContentPane().add(btnDangNhap);
        btnDangNhap.setBounds(70, 180, 110, 30);

        btnThoat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        getContentPane().add(btnThoat);
        btnThoat.setBounds(200, 180, 110, 30);

        cboNgonNgu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiếng Việt", "English" }));
        cboNgonNgu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNgonNguItemStateChanged(evt);
            }
        });
        getContentPane().add(cboNgonNgu);
        cboNgonNgu.setBounds(230, 140, 100, 20);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/background.jpg"))); // NOI18N
        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(0, 0, 380, 230);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
        private boolean kiemtraloi(String ma){
        String[] b = ma.split(",");
        for(int i=0; i<b.length; i++){
             System.out.println(b[i]);
            if(b[i]=="q" || b[i]=="=")
            {
                return false;
            }
        }
        return true;
    }
    private void lbQuenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQuenMouseClicked
        // TODO add your handling code here:
        if(txtUser.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Vui lòng nhập đúng tên tài khoản!");
        }else{
            int kiemtra=JOptionPane.showConfirmDialog(this,"Bạn sẽ gởi yêu cầu đến quản lý để xác nhận lại mật khẩu?","Thông Báo",JOptionPane.YES_NO_OPTION);
            if(kiemtra==JOptionPane.YES_OPTION){
                //doo something here
                JOptionPane.showMessageDialog(this,"Đã gởi yêu cầu đến quản lý vui lòng chờ xác nhận User: "+txtUser.getText());
            }else{
                txtUser.setText("");
                txtPass.setText("");
            }
        }
    }//GEN-LAST:event_lbQuenMouseClicked

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        int click= JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát khỏi chương trình không?","Thông Báo",JOptionPane.YES_NO_OPTION);
        if(click==JOptionPane.YES_OPTION){
            //System.exit(0);
            int one= JOptionPane.showConfirmDialog(null, "Thiệt à! Bạn có chắc chắn chứ?","Thông Báo",JOptionPane.YES_NO_OPTION);
            if(one==JOptionPane.YES_OPTION){
                int two= JOptionPane.showConfirmDialog(null, "Hỏi thế rồi mà vẫn muốn Thoát? Liều vậy","Thông Báo",JOptionPane.YES_NO_OPTION);
                if(two==JOptionPane.YES_OPTION){
                    int thre= JOptionPane.showConfirmDialog(null, "ĐM sao mày lỳ vkl thế. Hỏi nhiều vậy là không muốn thoát rồi! Còn muốn thoát nữa không?","Thông Báo",JOptionPane.YES_NO_OPTION);
                    if(thre==JOptionPane.YES_OPTION)
                    {
                        int forr= JOptionPane.showConfirmDialog(null, "có não không vậy?. Có thấy cái nút X ở trên bên phải không? mà nhấn THOÁT mãi! Đập máy đi","Thông Báo",JOptionPane.CANCEL_OPTION);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed

        try {
            if(txtUser.getText().equals("")==false && txtPass.getText().equals("")==false){
                if(Integer.parseInt(txtUser.getText())>0){
                    if(txtPass.getText().length()<=4){
                        JOptionPane.showMessageDialog(this,"Mật khẩu có độ dài không đúng");
                    }else{
                        //do code something here
                        User_BUS bus= new User_BUS();
                        if(bus.getId(txtUser.getText(),txtPass.getText())==0){
                            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoăc mật khẩu không chính xác");
                            txtPass.setText(null);
                            _dem =_dem+1;
                            if(_dem >3){
                                JOptionPane.showMessageDialog(this,"Bạn đã đăng nhập sai quá số lần quy định. Chương trình sẽ đóng");
                                con.getSQLServerClose();
                                System.exit(0);
                            }  
                            con.getSQLServerClose();
                        }else{
                            this.setVisible(false);
                            QuanLyBanHang ql= new QuanLyBanHang();
                            ql.getId(bus.getId(txtUser.getText(),txtPass.getText()));
                            ql.setVisible(true);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(this,"Tài khoản không đúng định dạng");
                    txtPass.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(this,"Chưa nhập hết thông tin cần tìm");
                _dem =_dem+1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            txtUser.setText(null);
            txtPass.setText(null);
        }
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void cboNgonNguItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNgonNguItemStateChanged
        
        try{
           if(cboNgonNgu.getSelectedIndex()==1){
           ngonngu=true;
           lbTieuDe.setText("LogIn To TechLaza");
           lbTieuDe.setLocation(100, 20);
           lbTaiKhoan.setText("UserName:");
           lbMatKhau.setText("Password:");
           lbQuen.setText("Forgot password?");
           btnDangNhap.setText("LogIn");
           btnThoat.setText("Exit");
           QuanLyBanHang ql= new QuanLyBanHang();
           ql.getNgonNgu(ngonngu);
           this.setTitle("LogIn");
        }else{
           ngonngu=false;
           lbTieuDe.setText("Đăng nhập vào hệ thống TechLaza");
           lbTieuDe.setLocation(20, 20);
           lbTaiKhoan.setText("Tài Khoản:");
           lbMatKhau.setText("Mật Khẩu:");
           lbQuen.setText("Quên mật khẩu?");
           btnDangNhap.setText("Đăng Nhập");
           btnThoat.setText("Thoát");
           this.setTitle("Đăng Nhập");
        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_cboNgonNguItemStateChanged

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnDangNhap.doClick();
        }
    }//GEN-LAST:event_txtUserKeyPressed

    private void txtPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            btnDangNhap.doClick();
        }
    }//GEN-LAST:event_txtPassKeyReleased

    private void txtUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            btnDangNhap.doClick();
        }
    }//GEN-LAST:event_txtUserKeyReleased

    public static void main(String args[]) {
        DangNhap dn= new DangNhap();
        dn.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnThoat;
    private javax.swing.JComboBox<String> cboNgonNgu;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lbMatKhau;
    private javax.swing.JLabel lbQuen;
    private javax.swing.JLabel lbTaiKhoan;
    private javax.swing.JLabel lbTieuDe;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
