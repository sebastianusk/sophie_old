/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sophie;

import application.apps;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author chody
 */
public class NewWindowAdmin extends javax.swing.JFrame {

    apps MainAppAdmin;
    String defaultdir;
    
    Vector listBarang   = new Vector(); // FOR COMBOBOX
    Vector listKonter   = new Vector(); // FOR COMBOBOX
    Vector listKodeBrg  = new Vector(); // FOR COMBOBOX
    
    //4. for Dead style
    private ArrayList<DeadStyleData> ListDeadStyle;
    private int deadstylecount;
    
    // 5. Setting Tab
    private int setting_databarang;
    
    /**
     * Creates new form NewWindowAdmin
     */
    public NewWindowAdmin(apps MainAppForm) {
        initComponents();
        MainAppAdmin = MainAppForm;
        MainAppAdmin.GetItemList();
        MainAppAdmin.GetKonterList();
        setVectorBarang();
        setVectorKonter();
        show_combobox_kodekonter_set();
        show_combobox_DeadStyle();
        show_combobox_konterbayar();
        CreateDir();
        settingpath();
        TextF_DeleteOrderTime.setText(Integer.toString(MainAppAdmin.GetDurOrderDel()));
        
    }
    
    public void setVectorKonter() {
        listKonter.clear();
        for(int i=0; i < MainAppAdmin.KonterList.size(); i++){
            listKonter.add(MainAppAdmin.KonterList.get(i).kode_konter + " - "  + MainAppAdmin.KonterList.get(i).nama_konter);
        }
    }
    
    public void setVectorBarang() {
        listBarang.clear();
        for(int i=0; i < MainAppAdmin.ItemList.size(); i++){
            listBarang.add(MainAppAdmin.ItemList.get(i).kode_barang.trim() + " " + MainAppAdmin.ItemList.get(i).nama_barang.trim());
        }
    }
    
    public void settingpath(){
        TextF_defaultpath.setText(MainAppAdmin.GetDefDir());
    }
    
    public void CreateDir(){
        File directory;
        defaultdir = MainAppAdmin.GetDefDir();
        directory = new File (defaultdir + "/KatalogDiff");
        directory.mkdirs();
        directory = new File (defaultdir + "/DiffDaily");
        directory.mkdirs();
        directory = new File (defaultdir + "/ComingOrder");
        directory.mkdirs();
        directory = new File (defaultdir + "/CariGudang");
        directory.mkdirs();
        directory = new File (defaultdir + "/OrderPusat");
        directory.mkdirs();
        directory = new File (defaultdir + "/Trasaction");
        directory.mkdirs();
        directory = new File (defaultdir + "/Popular");
        directory.mkdirs();
        directory = new File (defaultdir + "/DeadStyle");
        directory.mkdirs();
        directory = new File (defaultdir + "/Mutasi");
        directory.mkdirs();
        
    }
    
    public void CreateFile(File input, String Folder){
        if (input != null){
            File createdfile = new File (defaultdir+Folder+"/"+input.getName());
            if (createdfile.exists()) createdfile.delete();
            input.renameTo(createdfile);
            JOptionPane.showMessageDialog(null, "File Created");
            try {
                Desktop.getDesktop().open(createdfile);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public void show_combobox_DeadStyle(){
        Combobox_kodebarang_DS.setModel(new DefaultComboBoxModel(listBarang));
        Combobox_kodebarang_DS.setSelectedIndex(-1);
        Combobox_kodebarang_DS.setEditable(true);
        JTextField textField = (JTextField) Combobox_kodebarang_DS.getEditor().getEditorComponent();
        textField.setFocusable(true);
        textField.setText("");
        textField.addKeyListener(new List_Kode_Barang(Combobox_kodebarang_DS, listBarang));
    }
    
    public void show_combobox_kodekonter_set(){
        // for kode konter tanngal
        Combox_NamaKonter_KonterSet.setModel(new DefaultComboBoxModel(listKonter));
        Combox_NamaKonter_KonterSet.setSelectedIndex(-1);
        JTextField textField31 = (JTextField) Combox_NamaKonter_KonterSet.getEditor().getEditorComponent();
        textField31.setFocusable(true);
        textField31.setText("");
        textField31.addKeyListener(new List_Kode_Barang(Combox_NamaKonter_KonterSet, listKonter));
    }
    
    public void show_combobox_konterbayar(){
        // for konter data
        Combox_Konter_Bayar.setModel(new DefaultComboBoxModel(listKonter));
        Combox_Konter_Bayar.setSelectedIndex(-1);
        //Combox_KodeKonter_AddOrder.setEditable(true);
        JTextField textField5 = (JTextField) Combox_Konter_Bayar.getEditor().getEditorComponent();
        textField5.setFocusable(true);
        textField5.setText("");
        textField5.addKeyListener(new List_Kode_Barang(Combox_Konter_Bayar, listKonter));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        DatePick_Mulai_Popular = new org.jdesktop.swingx.JXDatePicker();
        DatePick_Akhir_Populer = new org.jdesktop.swingx.JXDatePicker();
        Btn_OK_PopItem = new javax.swing.JButton();
        SaveClosePopularItem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TField_Disc_DS = new javax.swing.JTextField();
        Combobox_kodebarang_DS = new javax.swing.JComboBox();
        TField_Jumlah_DS = new javax.swing.JTextField();
        Btn_Add_DeadStyle = new javax.swing.JButton();
        Btn_ChekOut_DS = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_DeadStyle = new javax.swing.JTable();
        SaveCloseDeadstyle = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TextF_KodeBarang_Setting = new javax.swing.JTextField();
        TextF_NamaBarang_Setting = new javax.swing.JTextField();
        TextF_Kategori_Setting = new javax.swing.JTextField();
        TextF_HargaTPG_Setting = new javax.swing.JTextField();
        TextF_DiscMember_Setting = new javax.swing.JTextField();
        TextF_Stock_Setting = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        SaveCloseDataBarangSet = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_KonterData_KonterSet = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        TextF_NamaKonter_KonterSet = new javax.swing.JTextField();
        Btn_Add_KonterSet = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        TextF_KodeKonter_KonterSet = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        TextF_HutangAwal_KonterSet = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Btn_Remove_KonterSet = new javax.swing.JButton();
        Combox_NamaKonter_KonterSet = new javax.swing.JComboBox();
        Btn_Refresh_KonterSet = new javax.swing.JButton();
        SaveCloseKonterSet = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        TextField_nominalBayar = new javax.swing.JTextField();
        Combox_Konter_Bayar = new javax.swing.JComboBox();
        Btn_Bayar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        TextF_UserSet = new javax.swing.JTextField();
        Btn_UserPassChange = new javax.swing.JButton();
        PassF_PassSet = new javax.swing.JPasswordField();
        PassF_RePassSet = new javax.swing.JPasswordField();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        TextF_defaultpath = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        TextF_DeleteOrderTime = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(761, 539));

        jLabel1.setText("Tanggal Mulai");

        jLabel2.setText("Tanggal Akhir");

        Btn_OK_PopItem.setText("OK");
        Btn_OK_PopItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_OK_PopItemActionPerformed(evt);
            }
        });

        SaveClosePopularItem.setText("Save & Close");
        SaveClosePopularItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveClosePopularItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(37, 37, 37)
                                .addComponent(DatePick_Mulai_Popular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37)
                                .addComponent(DatePick_Akhir_Populer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(Btn_OK_PopItem)))
                .addGap(429, 429, 429))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(SaveClosePopularItem)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1))
                    .addComponent(DatePick_Mulai_Popular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2))
                    .addComponent(DatePick_Akhir_Populer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Btn_OK_PopItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addComponent(SaveClosePopularItem)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Popular Items", jPanel1);

        jLabel3.setText("Discount");

        jLabel4.setText("Kode Barang");

        jLabel5.setText("Jumlah");

        Combobox_kodebarang_DS.setEditable(true);

        TField_Jumlah_DS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TField_Jumlah_DSKeyPressed(evt);
            }
        });

        Btn_Add_DeadStyle.setText("Add");
        Btn_Add_DeadStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Add_DeadStyleActionPerformed(evt);
            }
        });

        Btn_ChekOut_DS.setText("Check Out");
        Btn_ChekOut_DS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ChekOut_DSActionPerformed(evt);
            }
        });

        Table_DeadStyle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Jumlah", "Kategori", "Harga TPG", "Disc", "Net", "Total TPG", "Total Net"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Table_DeadStyle);

        SaveCloseDeadstyle.setText("Save & Close");
        SaveCloseDeadstyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveCloseDeadstyleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Btn_Add_DeadStyle)
                                .addGap(49, 49, 49)
                                .addComponent(Btn_ChekOut_DS))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TField_Disc_DS)
                                .addComponent(Combobox_kodebarang_DS, 0, 140, Short.MAX_VALUE)
                                .addComponent(TField_Jumlah_DS)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SaveCloseDeadstyle)
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TField_Disc_DS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Combobox_kodebarang_DS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(TField_Jumlah_DS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_ChekOut_DS)
                    .addComponent(Btn_Add_DeadStyle))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SaveCloseDeadstyle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dead Style", jPanel2);

        jLabel8.setText("Kode Barang");

        jLabel9.setText("Nama Barang");

        jLabel10.setText("Kategori");

        jLabel11.setText("Harga TPG");

        jLabel12.setText("Disc Member");

        jLabel13.setText("Stock");

        TextF_KodeBarang_Setting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_KodeBarang_SettingKeyPressed(evt);
            }
        });

        TextF_Stock_Setting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_Stock_SettingKeyPressed(evt);
            }
        });

        jButton5.setText("Check");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Confirm");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        SaveCloseDataBarangSet.setText("Save & Close");
        SaveCloseDataBarangSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveCloseDataBarangSetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextF_DiscMember_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextF_HargaTPG_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextF_Kategori_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextF_NamaBarang_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextF_KodeBarang_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextF_Stock_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6)
                            .addComponent(jButton5))))
                .addGap(0, 165, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SaveCloseDataBarangSet)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TextF_KodeBarang_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TextF_NamaBarang_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TextF_Kategori_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(TextF_HargaTPG_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(TextF_DiscMember_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(TextF_Stock_Setting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(SaveCloseDataBarangSet)
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("DataBarangSet", jPanel3);

        Table_KonterData_KonterSet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Konter", "Nama Konter", "Hutang Konter"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Table_KonterData_KonterSet);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel6.setText("Kode Konter");

        TextF_NamaKonter_KonterSet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_NamaKonter_KonterSetKeyPressed(evt);
            }
        });

        Btn_Add_KonterSet.setText("Add");
        Btn_Add_KonterSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Add_KonterSetActionPerformed(evt);
            }
        });

        jLabel14.setText("Nama Konter");

        TextF_KodeKonter_KonterSet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_KodeKonter_KonterSetKeyPressed(evt);
            }
        });

        jLabel18.setText("Hutang Awal");

        TextF_HutangAwal_KonterSet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_HutangAwal_KonterSetKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Btn_Add_KonterSet))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(TextF_NamaKonter_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TextF_KodeKonter_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TextF_HutangAwal_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(TextF_KodeKonter_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(TextF_NamaKonter_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(TextF_HutangAwal_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(Btn_Add_KonterSet)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel5.setPreferredSize(new java.awt.Dimension(236, 118));

        jLabel7.setText("Nama Konter");

        Btn_Remove_KonterSet.setText("Remove");
        Btn_Remove_KonterSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Remove_KonterSetActionPerformed(evt);
            }
        });

        Combox_NamaKonter_KonterSet.setEditable(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Combox_NamaKonter_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(Btn_Remove_KonterSet)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Combox_NamaKonter_KonterSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(Btn_Remove_KonterSet)
                .addContainerGap())
        );

        Btn_Refresh_KonterSet.setText("Refresh");
        Btn_Refresh_KonterSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Refresh_KonterSetActionPerformed(evt);
            }
        });

        SaveCloseKonterSet.setText("Save &  Close");
        SaveCloseKonterSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveCloseKonterSetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Btn_Refresh_KonterSet)
                .addGap(185, 185, 185)
                .addComponent(SaveCloseKonterSet)
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Refresh_KonterSet)
                    .addComponent(SaveCloseKonterSet))
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("KonterSet", jPanel4);

        jLabel15.setText("Kode Konter   :");

        jLabel16.setText("Nominal          :");

        TextField_nominalBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextField_nominalBayarKeyPressed(evt);
            }
        });

        Btn_Bayar.setText("Bayar");
        Btn_Bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_BayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Btn_Bayar)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addGap(18, 18, 18)
                            .addComponent(Combox_Konter_Bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addGap(18, 18, 18)
                            .addComponent(TextField_nominalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(Combox_Konter_Bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(TextField_nominalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Btn_Bayar)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Konter Bayar", jPanel6);

        jLabel19.setText("User Name        :");

        jLabel20.setText("Password          :");

        jLabel21.setText("Re-Password    :  ");

        TextF_UserSet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_UserSetKeyPressed(evt);
            }
        });

        Btn_UserPassChange.setText("Change");
        Btn_UserPassChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UserPassChangeActionPerformed(evt);
            }
        });

        PassF_PassSet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PassF_PassSetKeyPressed(evt);
            }
        });

        PassF_RePassSet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PassF_RePassSetKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Btn_UserPassChange)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TextF_UserSet)
                            .addComponent(PassF_PassSet)
                            .addComponent(PassF_RePassSet, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(TextF_UserSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(PassF_PassSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(PassF_RePassSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Btn_UserPassChange)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setText("Default Path :");

        TextF_defaultpath.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_defaultpathKeyPressed(evt);
            }
        });

        jButton1.setText("Change");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(TextF_defaultpath, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(TextF_defaultpath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel22.setText("Order Hapus        :");

        jButton2.setText("Set");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        TextF_DeleteOrderTime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextF_DeleteOrderTimeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextF_DeleteOrderTime, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(83, 83, 83))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jButton2)
                    .addComponent(TextF_DeleteOrderTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(235, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel9.getAccessibleContext().setAccessibleName("Admin");

        jTabbedPane1.addTab("Setting", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveCloseKonterSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveCloseKonterSetActionPerformed
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec("java -jar sophie.jar");
            System.exit(0);

        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }//GEN-LAST:event_SaveCloseKonterSetActionPerformed

    private void Btn_Refresh_KonterSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Refresh_KonterSetActionPerformed
        // TODO add your handling code here:
        MainAppAdmin.GetKonterList();
        String [] columnNames = {"Kode Konter", "Nama Konter", "Hutang Konter"};
        TableModel model_viewdata = new DefaultTableModel (columnNames, MainAppAdmin.KonterList.size());
        for (int i = 0; i < MainAppAdmin.KonterList.size(); i ++){
            model_viewdata.setValueAt(MainAppAdmin.KonterList.get(i).kode_konter, i, 0);
            model_viewdata.setValueAt(MainAppAdmin.KonterList.get(i).nama_konter, i, 1);
            model_viewdata.setValueAt(MainAppAdmin.KonterList.get(i).hutang_konter, i, 2);
        }
        Table_KonterData_KonterSet.setModel(model_viewdata);
        setVectorKonter();
    }//GEN-LAST:event_Btn_Refresh_KonterSetActionPerformed

    private void Btn_Remove_KonterSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Remove_KonterSetActionPerformed
        // TODO add your handling code here:
        if (Combox_NamaKonter_KonterSet.getSelectedIndex() != -1){
            String kode_konter = MainAppAdmin.KonterList.get(Combox_NamaKonter_KonterSet.getSelectedIndex()).kode_konter;
            MainAppAdmin.RemoveKonterManual(kode_konter);
            MainAppAdmin.GetKonterList();
            setVectorKonter();
            JOptionPane.showMessageDialog(null, "Konter Behasil Dihapus");
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
    }//GEN-LAST:event_Btn_Remove_KonterSetActionPerformed

    private void Btn_Add_KonterSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Add_KonterSetActionPerformed
        // TODO add your handling code here:
        if (!TextF_NamaKonter_KonterSet.getText().isEmpty() && !TextF_KodeKonter_KonterSet.getText().isEmpty() ){
            int konterfound = 0;
            MainAppAdmin.GetKonterList();
            String Kode_Konter = TextF_KodeKonter_KonterSet.getText().trim();
            String Nama_Konter = TextF_NamaKonter_KonterSet.getText().trim();
            int Hutang_Konter = Integer.parseInt(TextF_HutangAwal_KonterSet.getText().trim());
            for (int i = 0; i < MainAppAdmin.KonterList.size(); i ++){
                if (MainAppAdmin.KonterList.get(i).kode_konter.trim().equals(Kode_Konter)){
                    konterfound = 1;
                    break;
                }
            }
            if (konterfound == 1){
                JOptionPane.showMessageDialog(null, "Konter Already Exist");
            } else {
                MainAppAdmin.AddKonterManual(Kode_Konter, Nama_Konter, Hutang_Konter);
                MainAppAdmin.GetKonterList();
                setVectorKonter();
                TextF_KodeKonter_KonterSet.setText("");
                TextF_NamaKonter_KonterSet.setText("");
                TextF_HutangAwal_KonterSet.setText("");
                JOptionPane.showMessageDialog(null, "Konter Berhasil Ditambahkan");
            }            
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }

    }//GEN-LAST:event_Btn_Add_KonterSetActionPerformed

    private void SaveCloseDataBarangSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveCloseDataBarangSetActionPerformed
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec("java -jar sophie.jar");
            System.exit(0);

        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }//GEN-LAST:event_SaveCloseDataBarangSetActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (setting_databarang == 1 || setting_databarang == 2){
            String kode_barang = TextF_KodeBarang_Setting.getText();
            String nama_barang = TextF_NamaBarang_Setting.getText();
            String kategori    = TextF_Kategori_Setting.getText();
            String hargatpg    = TextF_HargaTPG_Setting.getText();
            int HargaTPG = 0;
            if (!hargatpg.isEmpty()) HargaTPG = Integer.valueOf(hargatpg);
            String discmember  = TextF_DiscMember_Setting.getText();
            int DiscMember     = 0;
            if (!hargatpg.isEmpty()) DiscMember = Integer.valueOf(discmember);
            String stock       = TextF_Stock_Setting.getText();
            int Stock     = 0;
            if (!hargatpg.isEmpty()) Stock = Integer.valueOf(stock);

            if (setting_databarang == 1){
                if (Stock != MainAppAdmin.ViewDataBarang.stock){
                    MainAppAdmin.ChangeStockData(kode_barang,Stock);
                    setting_databarang = 0;
                    JOptionPane.showMessageDialog(null, "Data Berhasil Update/Create");
                }
            } else if (setting_databarang == 2){
                if (kode_barang.equals("") ||
                    nama_barang.equals("") ||
                    kategori.equals("") ||
                    HargaTPG == 0 ||
                    DiscMember == 0 ||
                    Stock == 0){
                    JOptionPane.showMessageDialog(null, "Some field are empty");
                }
                MainAppAdmin.AddStockManual(kode_barang, nama_barang, kategori, HargaTPG, DiscMember, Stock);
                MainAppAdmin.GetItemList();
                setVectorBarang();
                JOptionPane.showMessageDialog(null, "Data Berhasil Update/Create");
            }
        } else
        JOptionPane.showMessageDialog(null, "Check kode barang first");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String kode_barang = TextF_KodeBarang_Setting.getText();
        int found_kode = 0;
        if (!kode_barang.isEmpty()){
            for (int i = 0; i < MainAppAdmin.ItemList.size(); i ++){
                if(MainAppAdmin.ItemList.get(i).kode_barang.trim().equals(kode_barang.trim())){
                    found_kode = 1;
                    break;
                }
            }
            if (found_kode == 1){
                MainAppAdmin.ViewAllDataBarang(kode_barang);
                TextF_NamaBarang_Setting.setText(MainAppAdmin.ViewDataBarang.nama_barang);
                TextF_Kategori_Setting.setText(MainAppAdmin.ViewDataBarang.kategori);
                TextF_HargaTPG_Setting.setText(String.valueOf(MainAppAdmin.ViewDataBarang.harga_tpg));
                TextF_DiscMember_Setting.setText(String.valueOf(MainAppAdmin.ViewDataBarang.disc_member));
                TextF_Stock_Setting.setText(String.valueOf(MainAppAdmin.ViewDataBarang.stock));
                setting_databarang = 1;
            } else {
                JOptionPane.showMessageDialog(null, "Data Unavailable, Add Data");
                TextF_NamaBarang_Setting.setText("");
                TextF_Kategori_Setting.setText("");
                TextF_HargaTPG_Setting.setText("");
                TextF_DiscMember_Setting.setText("");
                TextF_Stock_Setting.setText("");
                setting_databarang = 2;
            }
        } else
        JOptionPane.showMessageDialog(null, "Kode Barang is empty");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void SaveCloseDeadstyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveCloseDeadstyleActionPerformed
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec("java -jar sophie.jar");
            System.exit(0);

        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }//GEN-LAST:event_SaveCloseDeadstyleActionPerformed

    private void Btn_ChekOut_DSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ChekOut_DSActionPerformed
        // TODO add your handling code here:
        ListDeadStyle = new ArrayList<>();
        for (int i = 0; i < Table_DeadStyle.getModel().getRowCount(); i++){
            //"Kode Barang","Nama Barang","Quantity", "Kategori", "Harga TPG", "Diskon Member", "Net","Total TPG","Total Net"
            DeadStyleData constructor = new DeadStyleData();
            constructor.kode_barang = Table_DeadStyle.getModel().getValueAt(i,0).toString();
            constructor.nama_barang = Table_DeadStyle.getModel().getValueAt(i,1).toString();
            constructor.Jumlah      = Integer.parseInt(Table_DeadStyle.getModel().getValueAt(i,2).toString());
            constructor.kategori    = Table_DeadStyle.getModel().getValueAt(i,3).toString();
            constructor.harga_tpg   = Double.parseDouble(Table_DeadStyle.getModel().getValueAt(i,4).toString());
            constructor.disc_member = Double.parseDouble(Table_DeadStyle.getModel().getValueAt(i,5).toString());
            constructor.Net         = Double.parseDouble(Table_DeadStyle.getModel().getValueAt(i,6).toString());
            constructor.Total_tpg   = Double.parseDouble(Table_DeadStyle.getModel().getValueAt(i,7).toString());
            constructor.Total_Net   = Double.parseDouble(Table_DeadStyle.getModel().getValueAt(i,8).toString());
            ListDeadStyle.add(constructor);
        }
        File FileToSave = MainAppAdmin.ViewDeadStylePrint(ListDeadStyle, Integer.parseInt(TField_Disc_DS.getText()));
        CreateFile(FileToSave,"DeadStyle");

        MainAppAdmin.DecrementDeadStyle(ListDeadStyle);
    }//GEN-LAST:event_Btn_ChekOut_DSActionPerformed

    private void Btn_Add_DeadStyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Add_DeadStyleActionPerformed
        // TODO add your handling code here:
        if (!TField_Disc_DS.getText().isEmpty() && Combobox_kodebarang_DS.getSelectedIndex() != -1
            && !TField_Jumlah_DS.getText().isEmpty()){
            String [] columnNames = {"Kode Barang","Nama Barang","Quantity", "Kategori", "Harga TPG", "Diskon Member", "Net","Total TPG","Total Net"};
            TableModel model_viewdata = new DefaultTableModel (columnNames, deadstylecount + 1);
            for (int i = 0; i < deadstylecount; i ++){
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 0),i,0);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 1),i,1);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 2),i,2);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 3),i,3);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 4),i,4);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 5),i,5);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 6),i,6);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 7),i,7);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 8),i,8);
            }
            MainAppAdmin.ViewAllDataBarang(MainAppAdmin.ItemList.get(Combobox_kodebarang_DS.getSelectedIndex()).kode_barang);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.kode_barang, deadstylecount,0);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.nama_barang, deadstylecount,1);
            model_viewdata.setValueAt(TField_Jumlah_DS.getText(), deadstylecount,2);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.kategori, deadstylecount,3);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.harga_tpg, deadstylecount,4);
            int DSDisc = (MainAppAdmin.ViewDataBarang.disc_member != 10) ? Integer.parseInt(TField_Disc_DS.getText()) : 0;
            model_viewdata.setValueAt(DSDisc, deadstylecount,5);
            double Net = ((100.0 - (double)DSDisc)/100.0) * (double)MainAppAdmin.ViewDataBarang.harga_tpg;
            model_viewdata.setValueAt(Net, deadstylecount,6);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.harga_tpg * Integer.parseInt(TField_Jumlah_DS.getText()), deadstylecount,7);
            model_viewdata.setValueAt(Net * Integer.parseInt(TField_Jumlah_DS.getText()),deadstylecount,8);
            deadstylecount  += 1;
            Table_DeadStyle.setModel(model_viewdata);
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }

    }//GEN-LAST:event_Btn_Add_DeadStyleActionPerformed

    private void SaveClosePopularItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveClosePopularItemActionPerformed
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec("java -jar sophie.jar");
            System.exit(0);

        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }//GEN-LAST:event_SaveClosePopularItemActionPerformed

    private void Btn_OK_PopItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_OK_PopItemActionPerformed
        // TODO add your handling code here:
        if (DatePick_Mulai_Popular.getDate() != null && DatePick_Akhir_Populer.getDate() != null){
            Date start = DatePick_Mulai_Popular.getDate();
            Date finish = DatePick_Akhir_Populer.getDate();

            File FileToSave = MainAppAdmin.ViewPopularData(start,finish);
            CreateFile(FileToSave,"Popular");
            //            if (FileToSave != null){
                //                JFileChooser chooser = new JFileChooser();
                //                chooser.setSelectedFile(new File(FileToSave.getName()));
                //                int retrival = chooser.showSaveDialog(null);
                //                if (retrival == JFileChooser.APPROVE_OPTION) {
                    //                    File filechoosen = chooser.getSelectedFile();
                    //                    if(filechoosen.exists()) filechoosen.delete();
                    //                    FileToSave.renameTo(filechoosen);
                    //                }
                //            }
        } else {
            JOptionPane.showMessageDialog(null, "Some field are empty");
        }
    }//GEN-LAST:event_Btn_OK_PopItemActionPerformed

    private void Btn_BayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_BayarActionPerformed
        // TODO add your handling code here:
        if (Combox_Konter_Bayar.getSelectedIndex()!= -1 && !TextField_nominalBayar.getText().isEmpty()){
            String kodeKonterbayar = MainAppAdmin.KonterList.get(Combox_Konter_Bayar.getSelectedIndex()).kode_konter;
            int nominal         = Integer.parseInt(TextField_nominalBayar.getText());
            MainAppAdmin.TerimaBayar(kodeKonterbayar,nominal);
            JOptionPane.showMessageDialog(null, "Pembayaran Konter Berhasil");
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
    }//GEN-LAST:event_Btn_BayarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MainAppAdmin.StoreDefDir(TextF_defaultpath.getText());
        CreateDir();
        JOptionPane.showMessageDialog(null, "Default Direktori Berhasil Diubah");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TField_Jumlah_DSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TField_Jumlah_DSKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        if (!TField_Disc_DS.getText().isEmpty() && Combobox_kodebarang_DS.getSelectedIndex() != -1
            && !TField_Jumlah_DS.getText().isEmpty()){
            String [] columnNames = {"Kode Barang","Nama Barang","Quantity", "Kategori", "Harga TPG", "Diskon Member", "Net","Total TPG","Total Net"};
            TableModel model_viewdata = new DefaultTableModel (columnNames, deadstylecount + 1);
            for (int i = 0; i < deadstylecount; i ++){
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 0),i,0);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 1),i,1);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 2),i,2);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 3),i,3);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 4),i,4);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 5),i,5);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 6),i,6);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 7),i,7);
                model_viewdata.setValueAt(Table_DeadStyle.getModel().getValueAt(i, 8),i,8);
            }
            MainAppAdmin.ViewAllDataBarang(MainAppAdmin.ItemList.get(Combobox_kodebarang_DS.getSelectedIndex()).kode_barang);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.kode_barang, deadstylecount,0);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.nama_barang, deadstylecount,1);
            model_viewdata.setValueAt(TField_Jumlah_DS.getText(), deadstylecount,2);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.kategori, deadstylecount,3);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.harga_tpg, deadstylecount,4);
            int DSDisc = (MainAppAdmin.ViewDataBarang.disc_member != 10) ? Integer.parseInt(TField_Disc_DS.getText()) : 0;
            model_viewdata.setValueAt(DSDisc, deadstylecount,5);
            double Net = ((100.0 - (double)DSDisc)/100.0) * (double)MainAppAdmin.ViewDataBarang.harga_tpg;
            model_viewdata.setValueAt(Net, deadstylecount,6);
            model_viewdata.setValueAt(MainAppAdmin.ViewDataBarang.harga_tpg * Integer.parseInt(TField_Jumlah_DS.getText()), deadstylecount,7);
            model_viewdata.setValueAt(Net * Integer.parseInt(TField_Jumlah_DS.getText()),deadstylecount,8);
            deadstylecount  += 1;
            Table_DeadStyle.setModel(model_viewdata);
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
        }
    }//GEN-LAST:event_TField_Jumlah_DSKeyPressed

    private void TextF_KodeBarang_SettingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_KodeBarang_SettingKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            String kode_barang = TextF_KodeBarang_Setting.getText();
            int found_kode = 0;
            if (!kode_barang.isEmpty()){
                for (int i = 0; i < MainAppAdmin.ItemList.size(); i ++){
                    if(MainAppAdmin.ItemList.get(i).kode_barang.trim().equals(kode_barang.trim())){
                        found_kode = 1;
                        break;
                    }
                }
                if (found_kode == 1){
                    MainAppAdmin.ViewAllDataBarang(kode_barang);
                    TextF_NamaBarang_Setting.setText(MainAppAdmin.ViewDataBarang.nama_barang);
                    TextF_Kategori_Setting.setText(MainAppAdmin.ViewDataBarang.kategori);
                    TextF_HargaTPG_Setting.setText(String.valueOf(MainAppAdmin.ViewDataBarang.harga_tpg));
                    TextF_DiscMember_Setting.setText(String.valueOf(MainAppAdmin.ViewDataBarang.disc_member));
                    TextF_Stock_Setting.setText(String.valueOf(MainAppAdmin.ViewDataBarang.stock));
                    setting_databarang = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Data Unavailable, Add Data");
                    TextF_NamaBarang_Setting.setText("");
                    TextF_Kategori_Setting.setText("");
                    TextF_HargaTPG_Setting.setText("");
                    TextF_DiscMember_Setting.setText("");
                    TextF_Stock_Setting.setText("");
                    setting_databarang = 2;
                }
            } else
            JOptionPane.showMessageDialog(null, "Kode Barang is empty");
        }
    }//GEN-LAST:event_TextF_KodeBarang_SettingKeyPressed

    private void TextF_Stock_SettingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_Stock_SettingKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (setting_databarang == 1 || setting_databarang == 2){
            String kode_barang = TextF_KodeBarang_Setting.getText();
            String nama_barang = TextF_NamaBarang_Setting.getText();
            String kategori    = TextF_Kategori_Setting.getText();
            String hargatpg    = TextF_HargaTPG_Setting.getText();
            int HargaTPG = 0;
            if (!hargatpg.isEmpty()) HargaTPG = Integer.valueOf(hargatpg);
            String discmember  = TextF_DiscMember_Setting.getText();
            int DiscMember     = 0;
            if (!hargatpg.isEmpty()) DiscMember = Integer.valueOf(discmember);
            String stock       = TextF_Stock_Setting.getText();
            int Stock     = 0;
            if (!hargatpg.isEmpty()) Stock = Integer.valueOf(stock);

            if (setting_databarang == 1){
                if (Stock != MainAppAdmin.ViewDataBarang.stock){
                    MainAppAdmin.ChangeStockData(kode_barang,Stock);
                    setting_databarang = 0;
                    JOptionPane.showMessageDialog(null, "Data Berhasil Update/Create");
                }
            } else if (setting_databarang == 2){
                if (kode_barang.equals("") ||
                    nama_barang.equals("") ||
                    kategori.equals("") ||
                    HargaTPG == 0 ||
                    DiscMember == 0 ||
                    Stock == 0){
                    JOptionPane.showMessageDialog(null, "Some field are empty");
                }
                MainAppAdmin.AddStockManual(kode_barang, nama_barang, kategori, HargaTPG, DiscMember, Stock);
                MainAppAdmin.GetItemList();
                setVectorBarang();
                JOptionPane.showMessageDialog(null, "Data Berhasil Update/Create");
            }
        } else
        JOptionPane.showMessageDialog(null, "Check kode barang first");
        }
    }//GEN-LAST:event_TextF_Stock_SettingKeyPressed

    private void TextF_KodeKonter_KonterSetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_KodeKonter_KonterSetKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        if (!TextF_NamaKonter_KonterSet.getText().isEmpty() && !TextF_KodeKonter_KonterSet.getText().isEmpty() ){
            int konterfound = 0;
            MainAppAdmin.GetKonterList();
            String Kode_Konter = TextF_KodeKonter_KonterSet.getText().trim();
            String Nama_Konter = TextF_NamaKonter_KonterSet.getText().trim();
            int Hutang_Konter = Integer.parseInt(TextF_HutangAwal_KonterSet.getText().trim());
            for (int i = 0; i < MainAppAdmin.KonterList.size(); i ++){
                if (MainAppAdmin.KonterList.get(i).kode_konter.trim().equals(Kode_Konter)){
                    konterfound = 1;
                    break;
                }
            }
            if (konterfound == 1){
                JOptionPane.showMessageDialog(null, "Konter Already Exist");
            } else {
                MainAppAdmin.AddKonterManual(Kode_Konter, Nama_Konter, Hutang_Konter);
                MainAppAdmin.GetKonterList();
                setVectorKonter();
                TextF_KodeKonter_KonterSet.setText("");
                TextF_NamaKonter_KonterSet.setText("");
                TextF_HutangAwal_KonterSet.setText("");
                JOptionPane.showMessageDialog(null, "Konter Berhasil Ditambahkan");
            }            
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
        }
    }//GEN-LAST:event_TextF_KodeKonter_KonterSetKeyPressed

    private void TextF_NamaKonter_KonterSetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_NamaKonter_KonterSetKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        if (!TextF_NamaKonter_KonterSet.getText().isEmpty() && !TextF_KodeKonter_KonterSet.getText().isEmpty() ){
            int konterfound = 0;
            MainAppAdmin.GetKonterList();
            String Kode_Konter = TextF_KodeKonter_KonterSet.getText().trim();
            String Nama_Konter = TextF_NamaKonter_KonterSet.getText().trim();
            int Hutang_Konter = Integer.parseInt(TextF_HutangAwal_KonterSet.getText().trim());
            for (int i = 0; i < MainAppAdmin.KonterList.size(); i ++){
                if (MainAppAdmin.KonterList.get(i).kode_konter.trim().equals(Kode_Konter)){
                    konterfound = 1;
                    break;
                }
            }
            if (konterfound == 1){
                JOptionPane.showMessageDialog(null, "Konter Already Exist");
            } else {
                MainAppAdmin.AddKonterManual(Kode_Konter, Nama_Konter, Hutang_Konter);
                MainAppAdmin.GetKonterList();
                setVectorKonter();
                TextF_KodeKonter_KonterSet.setText("");
                TextF_NamaKonter_KonterSet.setText("");
                TextF_HutangAwal_KonterSet.setText("");
                JOptionPane.showMessageDialog(null, "Konter Berhasil Ditambahkan");
            }            
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
        }
    }//GEN-LAST:event_TextF_NamaKonter_KonterSetKeyPressed

    private void TextF_HutangAwal_KonterSetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_HutangAwal_KonterSetKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        if (!TextF_NamaKonter_KonterSet.getText().isEmpty() && !TextF_KodeKonter_KonterSet.getText().isEmpty() ){
            int konterfound = 0;
            MainAppAdmin.GetKonterList();
            String Kode_Konter = TextF_KodeKonter_KonterSet.getText().trim();
            String Nama_Konter = TextF_NamaKonter_KonterSet.getText().trim();
            int Hutang_Konter = Integer.parseInt(TextF_HutangAwal_KonterSet.getText().trim());
            for (int i = 0; i < MainAppAdmin.KonterList.size(); i ++){
                if (MainAppAdmin.KonterList.get(i).kode_konter.trim().equals(Kode_Konter)){
                    konterfound = 1;
                    break;
                }
            }
            if (konterfound == 1){
                JOptionPane.showMessageDialog(null, "Konter Already Exist");
            } else {
                MainAppAdmin.AddKonterManual(Kode_Konter, Nama_Konter, Hutang_Konter);
                MainAppAdmin.GetKonterList();
                setVectorKonter();
                TextF_KodeKonter_KonterSet.setText("");
                TextF_NamaKonter_KonterSet.setText("");
                TextF_HutangAwal_KonterSet.setText("");
                JOptionPane.showMessageDialog(null, "Konter Berhasil Ditambahkan");
            }            
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
        }
    }//GEN-LAST:event_TextF_HutangAwal_KonterSetKeyPressed

    private void TextField_nominalBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextField_nominalBayarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (Combox_Konter_Bayar.getSelectedIndex()!= -1 && !TextField_nominalBayar.getText().isEmpty()){
                String kodeKonterbayar = MainAppAdmin.KonterList.get(Combox_Konter_Bayar.getSelectedIndex()).kode_konter;
                int nominal         = Integer.parseInt(TextField_nominalBayar.getText());
                MainAppAdmin.TerimaBayar(kodeKonterbayar,nominal);
                JOptionPane.showMessageDialog(null, "Pembayaran Konter Berhasil");
            } else {
                JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
            }
        }
    }//GEN-LAST:event_TextField_nominalBayarKeyPressed

    private void TextF_defaultpathKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_defaultpathKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            MainAppAdmin.StoreDefDir(TextF_defaultpath.getText());
            CreateDir();
            JOptionPane.showMessageDialog(null, "Default Direktori Berhasil Diubah");
        }
    }//GEN-LAST:event_TextF_defaultpathKeyPressed

    private void Btn_UserPassChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UserPassChangeActionPerformed
        // TODO add your handling code here:
        if (TextF_UserSet.getText().isEmpty() || PassF_PassSet.getPassword().length == 0
                || PassF_RePassSet.getPassword().length == 0){
            JOptionPane.showMessageDialog(null, "Some Field are Empty");
        } else {
            String user_name = TextF_UserSet.getText();
            char [] rawpass = PassF_PassSet.getPassword();
            String pass = String.copyValueOf(rawpass);
            char [] rerawpass = PassF_RePassSet.getPassword();
            String repass = String.copyValueOf(rerawpass);
            if (!pass.equals(repass)){
                JOptionPane.showMessageDialog(null, "Password missmatched");
            } else {
                MainAppAdmin.ChangeAdmin(user_name, pass);
                JOptionPane.showMessageDialog(null, "Admin Changed");
            }
        }
    }//GEN-LAST:event_Btn_UserPassChangeActionPerformed

    private void TextF_UserSetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_UserSetKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (TextF_UserSet.getText().isEmpty() || PassF_PassSet.getPassword().length == 0
                    || PassF_RePassSet.getPassword().length == 0){
                JOptionPane.showMessageDialog(null, "Some Field are Empty");
            } else {
                String user_name = TextF_UserSet.getText();
                char [] rawpass = PassF_PassSet.getPassword();
                String pass = String.copyValueOf(rawpass);
                char [] rerawpass = PassF_RePassSet.getPassword();
                String repass = String.copyValueOf(rerawpass);
                if (!pass.equals(repass)){
                    JOptionPane.showMessageDialog(null, "Password missmatched");
                } else {
                    MainAppAdmin.ChangeAdmin(user_name, pass);
                    JOptionPane.showMessageDialog(null, "Admin Changed");
                }
            }
        }
    }//GEN-LAST:event_TextF_UserSetKeyPressed

    private void PassF_PassSetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PassF_PassSetKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (TextF_UserSet.getText().isEmpty() || PassF_PassSet.getPassword().length == 0
                    || PassF_RePassSet.getPassword().length == 0){
                JOptionPane.showMessageDialog(null, "Some Field are Empty");
            } else {
                String user_name = TextF_UserSet.getText();
                char [] rawpass = PassF_PassSet.getPassword();
                String pass = String.copyValueOf(rawpass);
                char [] rerawpass = PassF_RePassSet.getPassword();
                String repass = String.copyValueOf(rerawpass);
                if (!pass.equals(repass)){
                    JOptionPane.showMessageDialog(null, "Password missmatched");
                } else {
                    MainAppAdmin.ChangeAdmin(user_name, pass);
                    JOptionPane.showMessageDialog(null, "Admin Changed");
                }
            }
        }
    }//GEN-LAST:event_PassF_PassSetKeyPressed

    private void PassF_RePassSetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PassF_RePassSetKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (TextF_UserSet.getText().isEmpty() || PassF_PassSet.getPassword().length == 0
                    || PassF_RePassSet.getPassword().length == 0){
                JOptionPane.showMessageDialog(null, "Some Field are Empty");
            } else {
                String user_name = TextF_UserSet.getText();
                char [] rawpass = PassF_PassSet.getPassword();
                String pass = String.copyValueOf(rawpass);
                char [] rerawpass = PassF_RePassSet.getPassword();
                String repass = String.copyValueOf(rerawpass);
                if (!pass.equals(repass)){
                    JOptionPane.showMessageDialog(null, "Password missmatched");
                } else {
                    MainAppAdmin.ChangeAdmin(user_name, pass);
                    JOptionPane.showMessageDialog(null, "Admin Changed");
                }
            }
        }
    }//GEN-LAST:event_PassF_RePassSetKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int duration = Integer.parseInt(TextF_DeleteOrderTime.getText());
        MainAppAdmin.StoreDurOrderDel(duration);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void TextF_DeleteOrderTimeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextF_DeleteOrderTimeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextF_DeleteOrderTimeKeyPressed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(NewWindowAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(NewWindowAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(NewWindowAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(NewWindowAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                NewWindowAdmin nwa = new NewWindowAdmin();
//                nwa.setDefaultCloseOperation(nwa.EXIT_ON_CLOSE);
//                nwa.setVisible(true);
//                
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Add_DeadStyle;
    private javax.swing.JButton Btn_Add_KonterSet;
    private javax.swing.JButton Btn_Bayar;
    private javax.swing.JButton Btn_ChekOut_DS;
    private javax.swing.JButton Btn_OK_PopItem;
    private javax.swing.JButton Btn_Refresh_KonterSet;
    private javax.swing.JButton Btn_Remove_KonterSet;
    private javax.swing.JButton Btn_UserPassChange;
    private javax.swing.JComboBox Combobox_kodebarang_DS;
    private javax.swing.JComboBox Combox_Konter_Bayar;
    private javax.swing.JComboBox Combox_NamaKonter_KonterSet;
    private org.jdesktop.swingx.JXDatePicker DatePick_Akhir_Populer;
    private org.jdesktop.swingx.JXDatePicker DatePick_Mulai_Popular;
    private javax.swing.JPasswordField PassF_PassSet;
    private javax.swing.JPasswordField PassF_RePassSet;
    private javax.swing.JButton SaveCloseDataBarangSet;
    private javax.swing.JButton SaveCloseDeadstyle;
    private javax.swing.JButton SaveCloseKonterSet;
    private javax.swing.JButton SaveClosePopularItem;
    private javax.swing.JTextField TField_Disc_DS;
    private javax.swing.JTextField TField_Jumlah_DS;
    private javax.swing.JTable Table_DeadStyle;
    private javax.swing.JTable Table_KonterData_KonterSet;
    private javax.swing.JTextField TextF_DeleteOrderTime;
    private javax.swing.JTextField TextF_DiscMember_Setting;
    private javax.swing.JTextField TextF_HargaTPG_Setting;
    private javax.swing.JTextField TextF_HutangAwal_KonterSet;
    private javax.swing.JTextField TextF_Kategori_Setting;
    private javax.swing.JTextField TextF_KodeBarang_Setting;
    private javax.swing.JTextField TextF_KodeKonter_KonterSet;
    private javax.swing.JTextField TextF_NamaBarang_Setting;
    private javax.swing.JTextField TextF_NamaKonter_KonterSet;
    private javax.swing.JTextField TextF_Stock_Setting;
    private javax.swing.JTextField TextF_UserSet;
    private javax.swing.JTextField TextF_defaultpath;
    private javax.swing.JTextField TextField_nominalBayar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
