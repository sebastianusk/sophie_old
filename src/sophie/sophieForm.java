/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sophie;
import application.apps;
//import com.mysql.jdbc.Connection;
import excel.StockData;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import stringedit.StringTools;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.FileNameExtensionFilter;
import stringedit.ViewListData;






/**
 *
 * @author chody
 */
public class sophieForm extends javax.swing.JFrame {
    // MAIN OBJECT THAT IS ESENTIAL FOR THE PROGRAM
    apps MainApp;           // main function
    StringTools Strings;    // tools for editing text
    
    String defaultdir;

    //  SOME GENERIC PARAMETER
    Vector listBarang   = new Vector(); // FOR COMBOBOX
    Vector listKonter   = new Vector(); // FOR COMBOBOX
    Vector listKodeBrg  = new Vector(); // FOR COMBOBOX
    Vector listView     = new Vector(); // FOR COMBOBOX


    // 1. for update katalog
    // for update katalog
    private String  dbfFile;
    private String  updKatalogDate;
    File DBFFile;
    
    // 2. for add order
    private ArrayList<PreOrderData> WLOrder;
    int numberWaitingList;
    
    // 3. for update daily
    private String XLSFile;
    File excelFile;
    public ArrayList<StockData> editUpdStockData;
    int addedUpdStockEdit;
    
    //4. for Dead style
    private ArrayList<DeadStyleData> ListDeadStyle;
    private int deadstylecount;
    
    // 5. Setting Tab
    private int setting_databarang;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;





    /**
     * Creates new form sophieForm
     */
    public sophieForm() {
        initComponents();
        try {
            MainApp = new apps();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e);
            Runtime.getRuntime().exit(0);
        }
        
        Strings = new StringTools();
        MainApp.GetItemList();
        MainApp.GetKonterList();
        MainApp.getListView();
        setVectorBarang();
        setVectorKonter();
        setVectorViewList();
        numberWaitingList = 0;
        WLOrder = new ArrayList<>();
        show_combobox_kodebarang_order();
        show_combobox_kodekonter_order();
        show_combobox_kodebarang_viewdata();
        show_combobox_kodekonter_transaction();
        show_combobox_kodebarang_listview();
        deadstylecount = 0;
        numberWaitingList = 0;
        setting_databarang = 0;
        CreateDir();
        JTextField_TotalNominal.setText(Integer.toString(MainApp.GetNominalNetBarang()));
        JTextField_TotalNominal.setEditable(false);
        
    }
    

    
    public void CreateDir(){
        File directory;
        defaultdir = MainApp.GetDefDir();
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
//            JOptionPane.showMessageDialog(null, "File Created");
            try {
                Desktop.getDesktop().open(createdfile);
                JOptionPane.showMessageDialog(null, "File Created");
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public void setVectorBarang() {
        listBarang.clear();
        for(int i=0; i < MainApp.ItemList.size(); i++){
            listBarang.add(MainApp.ItemList.get(i).kode_barang.trim() + " " + MainApp.ItemList.get(i).nama_barang.trim());
        }
    }
    
    public void setVectorViewList() {
        listView.clear();
        if(!MainApp.ListViewData.isEmpty()){
            for(int i=0; i < MainApp.ListViewData.size(); i++){
                listView.add(MainApp.ListViewData.get(i).namaList.trim());
            }
        }
    }
    
    public void setVectorKonter() {
        listKonter.clear();
        for(int i=0; i < MainApp.KonterList.size(); i++){
            listKonter.add(MainApp.KonterList.get(i).kode_konter + " - "  + MainApp.KonterList.get(i).nama_konter);
        }
    }
    
    public void show_combobox_kodebarang_viewdata(){
        // for view data
        Combox_ViewData.setModel(new DefaultComboBoxModel(listBarang));
        Combox_ViewData.setSelectedIndex(-1);
        Combox_ViewData.setEditable(true);
        JTextField textField2 = (JTextField) Combox_ViewData.getEditor().getEditorComponent();
        textField2.setFocusable(true);
        textField2.setText("");
        textField2.addKeyListener(new List_Kode_Barang(Combox_ViewData, listBarang));
    }
    
    public void show_combobox_kodebarang_listview(){
        // for view data
        Combox_ListViewData.setModel(new DefaultComboBoxModel(listView));
        Combox_ListViewData.setSelectedIndex(-1);
        Combox_ListViewData.setEditable(true);
        JTextField textField2 = (JTextField) Combox_ListViewData.getEditor().getEditorComponent();
        textField2.setFocusable(true);
        textField2.setText("");
        textField2.addKeyListener(new List_Kode_Barang(Combox_ListViewData, listView));
    }
    
    public void show_combobox_kodekonter_order(){
        // for konter data
        Combox_KodeKonter_AddOrder.setModel(new DefaultComboBoxModel(listKonter));
        Combox_KodeKonter_AddOrder.setSelectedIndex(-1);
        JTextField textField3 = (JTextField) Combox_KodeKonter_AddOrder.getEditor().getEditorComponent();
        textField3.setFocusable(true);
        textField3.setText("");
        textField3.addKeyListener(new List_Kode_Barang(Combox_KodeKonter_AddOrder, listKonter));
    }
    
    public void show_combobox_kodekonter_transaction(){
        // for kode konter tanngal
        Combox_KodeKonter_Mutasi.setModel(new DefaultComboBoxModel(listKonter));
        Combox_KodeKonter_Mutasi.setSelectedIndex(-1);
        JTextField textField31 = (JTextField) Combox_KodeKonter_Mutasi.getEditor().getEditorComponent();
        textField31.setFocusable(true);
        textField31.setText("");
        textField31.addKeyListener(new List_Kode_Barang(Combox_KodeKonter_Mutasi, listKonter));
        
        // for konter data
        Combox_Konter_Kirim.setModel(new DefaultComboBoxModel(listKonter));
        Combox_Konter_Kirim.setSelectedIndex(-1);
        //Combox_KodeKonter_AddOrder.setEditable(true);
        JTextField textField4 = (JTextField) Combox_Konter_Kirim.getEditor().getEditorComponent();
        textField4.setFocusable(true);
        textField4.setText("");
        textField4.addKeyListener(new List_Kode_Barang(Combox_Konter_Kirim, listKonter));
        
    }
    
    public void show_combobox_kodebarang_order(){
        // for order barang
        Combox_KodeBarang_AddOrder.setModel(new DefaultComboBoxModel(listBarang));
        Combox_KodeBarang_AddOrder.setSelectedIndex(-1);
        Combox_KodeBarang_AddOrder.setEditable(true);
        JTextField textField = (JTextField) Combox_KodeBarang_AddOrder.getEditor().getEditorComponent();
        textField.setFocusable(true);
        textField.setText("");
        textField.addKeyListener(new List_Kode_Barang(Combox_KodeBarang_AddOrder, listBarang));    
    }
    
    public String getKodeBarangCombox(JComboBox combox){
        String full[] = combox.getSelectedItem().toString().split(" ",2);
        return full[0];
    }
    
    public String getKodeKonterCombox(JComboBox combox){
        String full[] = combox.getSelectedItem().toString().split(" ",2);
        return full[0];
    }
    
    
    /* some main function */
    // ADD ORDER
    
    private void add_order_action (){
                if (Combox_KodeKonter_AddOrder.getSelectedIndex() != -1 && Combox_KodeBarang_AddOrder.getSelectedIndex() != -1
                && !TextField_JmlOrder.getText().isEmpty()){
            String [] columnNames = {"Kode Konter","Kode Barang","Nama Barang", "Jumlah", "Sisa Stok"};
            TableModel model_viewdata = new DefaultTableModel (columnNames, Table_Order.getModel().getRowCount() + 1);
            for (int i = 0; i < Table_Order.getModel().getRowCount(); i ++){
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 0),i,0);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 1),i,1);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 2),i,2);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 3),i,3);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 4),i,4);
            }
            String kode_konter = MainApp.KonterList.get(Combox_KodeKonter_AddOrder.getSelectedIndex()).kode_konter;
            //String full[] = Combox_KodeBarang_AddOrder.getSelectedItem().toString().split(" ",2);
            String kode_barang = getKodeBarangCombox(Combox_KodeBarang_AddOrder);
            MainApp.ViewAllDataBarang(kode_barang);
            model_viewdata.setValueAt(kode_konter, Table_Order.getModel().getRowCount(),0);
            model_viewdata.setValueAt(kode_barang, Table_Order.getModel().getRowCount(),1);
            model_viewdata.setValueAt(MainApp.getNamaBarang(kode_barang), Table_Order.getModel().getRowCount(),2);
            model_viewdata.setValueAt(Integer.parseInt(TextField_JmlOrder.getText()), Table_Order.getModel().getRowCount(),3);
            model_viewdata.setValueAt(MainApp.ViewDataBarang.stock, Table_Order.getModel().getRowCount(),4);
            // numberWaitingList  += 1;
            Table_Order.setModel(model_viewdata);    
            Table_Order.getTableHeader().setReorderingAllowed(false);
            Combox_KodeBarang_AddOrder.setSelectedItem("");
            TextField_JmlOrder.setText("");
            
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
    }
    
    private void view_databarang_action(){
        if ( Combox_ViewData.getSelectedIndex() != -1){
//            MainApp.ViewAllDataBarang(MainApp.ItemList.get(Combox_ViewData.getSelectedIndex()).kode_barang);
            String kode_barang = getKodeBarangCombox(Combox_ViewData);
            MainApp.ViewAllDataBarang(kode_barang);
            String [] columnNames = {"Kode Barang","Nama Barang", "Kategori", "Harga TPG", "Diskon Member", "Stok"};
            TableModel model_viewdata = new DefaultTableModel (columnNames, Table_ViewData.getModel().getRowCount() + 1);
            for(int i = 0; i < Table_ViewData.getModel().getRowCount(); i++){
                model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 0),i,0);
                model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 1),i,1);
                model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 2),i,2);
                model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 3),i,3);
                model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 4),i,4);
                model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 5),i,5);
            }
            model_viewdata.setValueAt(kode_barang,Table_ViewData.getModel().getRowCount(), 0);
            model_viewdata.setValueAt(MainApp.ViewDataBarang.nama_barang, Table_ViewData.getModel().getRowCount(), 1);
            model_viewdata.setValueAt(MainApp.ViewDataBarang.kategori, Table_ViewData.getModel().getRowCount(), 2);
            model_viewdata.setValueAt(MainApp.ViewDataBarang.harga_tpg, Table_ViewData.getModel().getRowCount(), 3);
            model_viewdata.setValueAt(MainApp.ViewDataBarang.disc_member, Table_ViewData.getModel().getRowCount(), 4);
            model_viewdata.setValueAt(MainApp.ViewDataBarang.stock, Table_ViewData.getModel().getRowCount(), 5);
            Table_ViewData.setModel(model_viewdata);
            Table_ViewData.setAutoCreateRowSorter(true);
            Table_ViewData.setFocusable(false);
            Table_ViewData.setRowSelectionAllowed(true);
            Table_ViewData.getTableHeader().setReorderingAllowed(false);
            
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        TextField_DBF_path = new javax.swing.JTextField();
        Btn_Browse_DBF = new javax.swing.JButton();
        Btn_OK_DBF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_DBF = new javax.swing.JTable();
        Btn_Save_DBF = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Order = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TextField_JmlOrder = new javax.swing.JTextField();
        Btn_Add_Order = new javax.swing.JButton();
        Combox_KodeKonter_AddOrder = new javax.swing.JComboBox();
        Combox_KodeBarang_AddOrder = new javax.swing.JComboBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        TextField_KodeOrder_NotFound = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TextField_Jumlah_NotFound = new javax.swing.JTextField();
        Btn_NotFound = new javax.swing.JButton();
        Btn_Save_Order = new javax.swing.JButton();
        Btn_Print_CariGudang = new javax.swing.JButton();
        Btn_Print_Order1 = new javax.swing.JButton();
        Btn_Delete_RowOrder = new javax.swing.JButton();
        Btn_Clear_Order = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        Btn_Browse_Excel = new javax.swing.JButton();
        Btn_OK_Excel = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Excel = new javax.swing.JTable();
        Btn_Save_Excel = new javax.swing.JButton();
        Btn_AddItem_Excel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        Combox_Konter_Kirim = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        Btn_Send_Trasaction = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table_Transaction = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        Combox_KodeKonter_Mutasi = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Btn_View_Mutasi = new javax.swing.JButton();
        DatePick_Mulai_Trans = new org.jdesktop.swingx.JXDatePicker();
        DatePick_Akhir_Trans = new org.jdesktop.swingx.JXDatePicker();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Combox_ViewData = new javax.swing.JComboBox();
        Btn_OK_ViewDat = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        Table_ViewData = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        Combox_ListViewData = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        TextF_namalist_viewdata = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Btn_SaveList_viewdata = new javax.swing.JButton();
        Btn_Clr_ViewData = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        JTextField_TotalNominal = new javax.swing.JTextField();
        Btn_SaveList_viewdata1 = new javax.swing.JButton();
        Btn_Delete_Row = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        textfield_username = new javax.swing.JTextField();
        Btn_login = new javax.swing.JButton();
        textfield_password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Btn_Browse_DBF.setText("Browse");
        Btn_Browse_DBF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Browse_DBFActionPerformed(evt);
            }
        });

        Btn_OK_DBF.setText("OK");
        Btn_OK_DBF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_OK_DBFActionPerformed(evt);
            }
        });

        Table_DBF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Kategori", "Harga TPG", "Disc Member"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_DBF.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(Table_DBF);

        Btn_Save_DBF.setText("Save");
        Btn_Save_DBF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Save_DBFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(TextField_DBF_path, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Btn_Browse_DBF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Btn_OK_DBF))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(Btn_Save_DBF)))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextField_DBF_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Browse_DBF)
                    .addComponent(Btn_OK_DBF))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Save_DBF)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Update Catalogue", jPanel1);

        Table_Order.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Konter", "Kode Barang", "Nama Barang", "Jumlah", "Sisa Stok"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        jScrollPane2.setViewportView(Table_Order);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tambah Barang", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel1.setText("Kode Konter     :");

        jLabel2.setText("Kode Barang    :");

        jLabel3.setText("Jumlah             :");

        TextField_JmlOrder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextField_JmlOrderKeyPressed(evt);
            }
        });

        Btn_Add_Order.setText("Add");
        Btn_Add_Order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Add_OrderActionPerformed(evt);
            }
        });

        Combox_KodeBarang_AddOrder.setEditable(true);
        Combox_KodeBarang_AddOrder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Combox_KodeBarang_AddOrderKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Combox_KodeBarang_AddOrder, javax.swing.GroupLayout.Alignment.LEADING, 0, 223, Short.MAX_VALUE)
                        .addComponent(Combox_KodeKonter_AddOrder, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextField_JmlOrder))
                    .addComponent(Btn_Add_Order))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Combox_KodeKonter_AddOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Combox_KodeBarang_AddOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(TextField_JmlOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Btn_Add_Order)
                .addGap(7, 7, 7))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Not Found", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel4.setText("Kode Order     :");

        TextField_KodeOrder_NotFound.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextField_KodeOrder_NotFoundKeyPressed(evt);
            }
        });

        jLabel5.setText("Jumlah            :");

        TextField_Jumlah_NotFound.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextField_Jumlah_NotFoundKeyPressed(evt);
            }
        });

        Btn_NotFound.setText("Not Found");
        Btn_NotFound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_NotFoundActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(TextField_KodeOrder_NotFound))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(TextField_Jumlah_NotFound, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Btn_NotFound, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TextField_KodeOrder_NotFound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(TextField_Jumlah_NotFound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(Btn_NotFound)
                .addContainerGap())
        );

        Btn_Save_Order.setText("Save");
        Btn_Save_Order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Save_OrderActionPerformed(evt);
            }
        });

        Btn_Print_CariGudang.setText("Cari Gudang");
        Btn_Print_CariGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Print_CariGudangActionPerformed(evt);
            }
        });

        Btn_Print_Order1.setText("Order Pusat");
        Btn_Print_Order1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Print_Order1ActionPerformed(evt);
            }
        });

        Btn_Delete_RowOrder.setText("Delete");
        Btn_Delete_RowOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Delete_RowOrderActionPerformed(evt);
            }
        });

        Btn_Clear_Order.setText("Clear");
        Btn_Clear_Order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Clear_OrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Btn_Delete_RowOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Btn_Clear_Order))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(Btn_Save_Order)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Btn_Print_CariGudang)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Btn_Print_Order1))
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Btn_Save_Order)
                            .addComponent(Btn_Print_CariGudang)
                            .addComponent(Btn_Print_Order1)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Delete_RowOrder)
                    .addComponent(Btn_Clear_Order))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jPanel6.getAccessibleContext().setAccessibleName("Tambah Order");

        jTabbedPane1.addTab("Order", jPanel2);

        Btn_Browse_Excel.setText("Browse");
        Btn_Browse_Excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Browse_ExcelActionPerformed(evt);
            }
        });

        Btn_OK_Excel.setText("OK");
        Btn_OK_Excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_OK_ExcelActionPerformed(evt);
            }
        });

        Table_Excel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Jumlah"
            }
        ));
        jScrollPane3.setViewportView(Table_Excel);

        Btn_Save_Excel.setText("Check Out");
        Btn_Save_Excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Save_ExcelActionPerformed(evt);
            }
        });

        Btn_AddItem_Excel.setText("Add Item");
        Btn_AddItem_Excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AddItem_ExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Browse_Excel)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_OK_Excel))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Btn_AddItem_Excel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Btn_Save_Excel, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Browse_Excel)
                    .addComponent(Btn_OK_Excel))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(Btn_AddItem_Excel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Btn_Save_Excel))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Update Daily", jPanel3);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        jPanel7.setToolTipText("");

        jLabel7.setText("Kode Konter   :");

        Btn_Send_Trasaction.setText("Send");
        Btn_Send_Trasaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Send_TrasactionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(Combox_Konter_Kirim, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(Btn_Send_Trasaction)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Combox_Konter_Kirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(Btn_Send_Trasaction))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Table_Transaction.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal Transaksi", "Barang Keluar", "Setoran"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(Table_Transaction);

        jLabel13.setText("Kode Konter   ");

        jLabel14.setText("Tanggal Mulai ");

        jLabel15.setText("Tangal Akhir");

        Btn_View_Mutasi.setText("View");
        Btn_View_Mutasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_View_MutasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(Combox_KodeKonter_Mutasi, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Btn_View_Mutasi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(DatePick_Mulai_Trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DatePick_Akhir_Trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 183, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(Combox_KodeKonter_Mutasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_View_Mutasi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(DatePick_Mulai_Trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(DatePick_Akhir_Trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel7.getAccessibleContext().setAccessibleName("Kirim Barang Ke Konter");

        jTabbedPane1.addTab("Transaction", jPanel4);

        jLabel6.setText("Kode Barang       : ");

        Combox_ViewData.setEditable(true);
        Combox_ViewData.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kode_Barang 1", "Kode_Barang 2", "Kode_Barang 3", "Kode_Barang 4" }));
        Combox_ViewData.setSelectedItem("");
        Combox_ViewData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Combox_ViewDataKeyPressed(evt);
            }
        });

        Btn_OK_ViewDat.setText("Add");
        Btn_OK_ViewDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_OK_ViewDatActionPerformed(evt);
            }
        });

        Table_ViewData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Kategori", "Harga TPG", "Diskon Member", "Stok"
            }
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        jScrollPane6.setViewportView(Table_ViewData);

        jLabel8.setText("Nama List            :");

        Combox_ListViewData.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Load");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setText("Nama List       : ");

        Btn_SaveList_viewdata.setText("Save");
        Btn_SaveList_viewdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SaveList_viewdataActionPerformed(evt);
            }
        });

        Btn_Clr_ViewData.setText("Clear");
        Btn_Clr_ViewData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Clr_ViewDataActionPerformed(evt);
            }
        });

        jLabel10.setText("Total Nominal     : ");

        Btn_SaveList_viewdata1.setText("Refresh");
        Btn_SaveList_viewdata1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SaveList_viewdata1ActionPerformed(evt);
            }
        });

        Btn_Delete_Row.setText("Delete");
        Btn_Delete_Row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Delete_RowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(15, 15, 15)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Combox_ViewData, 0, 253, Short.MAX_VALUE)
                            .addComponent(Combox_ListViewData, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Btn_OK_ViewDat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Btn_Delete_Row)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Btn_Clr_ViewData)
                        .addGap(51, 51, 51))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(JTextField_TotalNominal))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextF_namalist_viewdata, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Btn_SaveList_viewdata1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Btn_SaveList_viewdata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(163, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Combox_ListViewData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Combox_ViewData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_OK_ViewDat)
                    .addComponent(Btn_Clr_ViewData)
                    .addComponent(Btn_Delete_Row))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_SaveList_viewdata)
                    .addComponent(TextF_namalist_viewdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(JTextField_TotalNominal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_SaveList_viewdata1))
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("View Data", jPanel5);

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel26.setText("Username");
        jPanel15.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel27.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel27.setText("Password");
        jPanel15.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        textfield_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textfield_usernameKeyPressed(evt);
            }
        });
        jPanel15.add(textfield_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 140, -1));

        Btn_login.setText("Login");
        Btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_loginActionPerformed(evt);
            }
        });
        jPanel15.add(Btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, -1, -1));

        textfield_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textfield_passwordKeyPressed(evt);
            }
        });
        jPanel15.add(textfield_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 140, -1));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(354, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(186, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Admin", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Button Browse pada update katalog
    private void Btn_Browse_DBFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Browse_DBFActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("dbf","DBF");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION ){
            DBFFile = chooser.getSelectedFile();
            dbfFile = DBFFile.getAbsolutePath();
            TextField_DBF_path.setText(dbfFile);
            updKatalogDate = Strings.GetDate(DBFFile);
            if (updKatalogDate == null){
                JOptionPane.showMessageDialog(null, "Error: Wrong dbf file");
                DBFFile = null;
                dbfFile = null;
            }
        }
    }//GEN-LAST:event_Btn_Browse_DBFActionPerformed

    //Button Browse pada Update Daily
    private void Btn_Browse_ExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Browse_ExcelActionPerformed
       JFileChooser chooser = new JFileChooser();
       FileNameExtensionFilter filter = new FileNameExtensionFilter("xls","EXCEL");
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            excelFile = chooser.getSelectedFile();
            XLSFile = excelFile.getAbsolutePath();
            jTextField2.setText(XLSFile);
        }
    }//GEN-LAST:event_Btn_Browse_ExcelActionPerformed

    //Button Ok Update Katalog 
    private void Btn_OK_DBFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_OK_DBFActionPerformed
        if (DBFFile != null) {
            try {
                MainApp.ReadDBF(dbfFile, updKatalogDate);
                String[] columnNames = {"Kode Barang", "Nama Barang", "Kategori", "Harga TPG", "Disc Member"};
                TableModel model = new DefaultTableModel(columnNames, MainApp.InputKatalogData.BasicData.size());
                for (int row = 0; row < MainApp.InputKatalogData.BasicData.size(); row++) {
                    model.setValueAt(MainApp.InputKatalogData.BasicData.get(row).kode_barang, row, 0);
                    model.setValueAt(MainApp.InputKatalogData.BasicData.get(row).nama_barang, row, 1);
                    model.setValueAt(MainApp.InputKatalogData.BasicData.get(row).kategori, row, 2);
                    model.setValueAt(MainApp.InputKatalogData.BasicData.get(row).harga_tpg, row, 3);
                    model.setValueAt(MainApp.InputKatalogData.BasicData.get(row).disc_member, row, 4);
                }
                Table_DBF.setModel(model);
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, "Error: " + e);
            }
        } else  {
            JOptionPane.showMessageDialog(null, "Error: File not found");
        }
        
    }//GEN-LAST:event_Btn_OK_DBFActionPerformed
    
    //Button Save Update Katalog
    private void Btn_Save_DBFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Save_DBFActionPerformed
        // TODO add your handling code here:
        try {
            MainApp.CheckDatabarang();
            File FileToSave = MainApp.XLSUpdateDatabarang();
            CreateFile(FileToSave,"KatalogDiff");
            MainApp.UpdateDatabarang();
            MainApp.GetItemList();
            setVectorBarang();
            JOptionPane.showMessageDialog(null, "Katalog berhasil diupdate");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        
    }//GEN-LAST:event_Btn_Save_DBFActionPerformed
   
    //Button OK Update Daily
    private void Btn_OK_ExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_OK_ExcelActionPerformed
        // TODO add your handling code here:
        if (excelFile.exists()) {
            try {
                addedUpdStockEdit = 0;
                MainApp.OpenXLSFile(XLSFile);
                String [] columnNames = {"Kode Barang","Nama Barang","Jumlah"};
                TableModel model_xls = new DefaultTableModel(columnNames, MainApp.updStockData.size());
                for (int row = 0; row < MainApp.updStockData.size(); row++) {
                    model_xls.setValueAt(MainApp.updStockData.get(row).kode_barang, row, 0);
                    for (int i = 0; i < MainApp.ItemList.size(); i ++){
                        if (MainApp.ItemList.get(i).kode_barang.equals(MainApp.updStockData.get(row).kode_barang)){
                            model_xls.setValueAt(MainApp.ItemList.get(i).nama_barang, row, 1);
                            break;
                        }
                    }
                    model_xls.setValueAt(MainApp.updStockData.get(row).jumlah_barang, row, 2);
                }
                Table_Excel.setModel(model_xls);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: Wrong type of file");
            }

        } else {
            JOptionPane.showMessageDialog(null, "File does not exist");
        }
                                       
    }//GEN-LAST:event_Btn_OK_ExcelActionPerformed

    private void Btn_Save_ExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Save_ExcelActionPerformed
        // TODO add your handling code here:
//        System.out.println(Table_Excel.getModel().getValueAt(0,0));
        editUpdStockData = new ArrayList();
        // get the data from list
        for (int i = 0; i < Table_Excel.getModel().getRowCount(); i++){
            StockData constructor = new StockData();
            constructor.kode_barang = Table_Excel.getModel().getValueAt(i,0).toString();
            constructor.jumlah_barang = Integer.parseInt(Table_Excel.getModel().getValueAt(i,2).toString());
            editUpdStockData.add(constructor);
        }
        // save the differences in excel
        File FileToSave = MainApp.XLSEditedNewStock(editUpdStockData, MainApp.updStockData);
        CreateFile(FileToSave,"DiffDaily");
        // check if any order is delivered today
        FileToSave = MainApp.CheckNewStock(editUpdStockData);
        CreateFile(FileToSave,"ComingOrder");
        MainApp.UpdateNewStock(editUpdStockData);
        
        String [] columnNames = {"Kode Barang","Nama Barang","Jumlah"};
        TableModel model_xls = new DefaultTableModel(columnNames, 0);
        Table_Excel.setModel(model_xls);
        
        MainApp.DeleteOldOrder();
        
        JOptionPane.showMessageDialog(null, "Stok Barang Berhasil Diupdate");
        
    }//GEN-LAST:event_Btn_Save_ExcelActionPerformed

    private void Btn_AddItem_ExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AddItem_ExcelActionPerformed
        // TODO add your handling code here:
        try {
            addedUpdStockEdit += 1;
            String [] columnNames = {"Kode Barang","Nama Barang","Jumlah"};
            TableModel model_xls = new DefaultTableModel(columnNames, MainApp.updStockData.size() + addedUpdStockEdit);
            for (int row = 0; row < Table_Excel.getModel().getRowCount(); row++) {
                model_xls.setValueAt(Table_Excel.getModel().getValueAt(row,0), row, 0);
                model_xls.setValueAt(Table_Excel.getModel().getValueAt(row,1), row, 1);
                model_xls.setValueAt(Table_Excel.getModel().getValueAt(row,2), row, 2);
            }
            Table_Excel.setModel(model_xls);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
    }//GEN-LAST:event_Btn_AddItem_ExcelActionPerformed

    private void Btn_OK_ViewDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_OK_ViewDatActionPerformed
        // TODO add your handling code here:
        view_databarang_action();
    }//GEN-LAST:event_Btn_OK_ViewDatActionPerformed

    private void Btn_Add_OrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Add_OrderActionPerformed
        add_order_action();
    }//GEN-LAST:event_Btn_Add_OrderActionPerformed

    private void Btn_Save_OrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Save_OrderActionPerformed
        // TODO add your handling code here:
        WLOrder = new ArrayList();
        for (int i = 0; i < Table_Order.getModel().getRowCount(); i++){
            PreOrderData constructor = new PreOrderData();
            constructor.kode_konter = Table_Order.getModel().getValueAt(i,0).toString();
            constructor.kode_barang = Table_Order.getModel().getValueAt(i,1).toString();
            constructor.jumlah_barang = Integer.parseInt(Table_Order.getModel().getValueAt(i,3).toString());
            WLOrder.add(constructor);
        }
        MainApp.GetCurrentTime();
        for (int i = 0; i < WLOrder.size(); i ++){
            MainApp.CreateOrder(WLOrder.get(i).kode_barang, WLOrder.get(i).kode_konter, WLOrder.get(i).jumlah_barang, MainApp.currentDate);
        }
        MainApp.OrderCheckStock();
        String [] columnNames = {"Kode Konter","Kode Barang","Nama Barang", "Jumlah"};
        TableModel model_WLOrder = new DefaultTableModel(columnNames, 0);
        Table_Order.setModel(model_WLOrder); 
        numberWaitingList = 0;
        JOptionPane.showMessageDialog(null, "Data Order Berhasil Disimpan");
    }//GEN-LAST:event_Btn_Save_OrderActionPerformed

    private void Btn_Print_CariGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Print_CariGudangActionPerformed
        // TODO add your handling code here:
        
        File FileToSave = MainApp.SplitPrintAvailable();
        CreateFile(FileToSave,"CariGudang");
       
    }//GEN-LAST:event_Btn_Print_CariGudangActionPerformed

    private void Btn_NotFoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_NotFoundActionPerformed
        // TODO add your handling code here:
        if (!TextField_KodeOrder_NotFound.getText().isEmpty() && !TextField_Jumlah_NotFound.getText().isEmpty()){
            String KodeOrderNF = TextField_KodeOrder_NotFound.getText();
            int JmlBarangNF = Integer.parseInt(TextField_Jumlah_NotFound.getText());
            try {
                MainApp.NotFoundGudang(KodeOrderNF, JmlBarangNF);
                JOptionPane.showMessageDialog(null, "Not Found data order berhasil diupdate");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
    }//GEN-LAST:event_Btn_NotFoundActionPerformed

    private void Btn_Send_TrasactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Send_TrasactionActionPerformed
        // TODO add your handling code here:
        if (Combox_Konter_Kirim.getSelectedIndex() != -1){
            String kodeKontersend = MainApp.KonterList.get(Combox_Konter_Kirim.getSelectedIndex()).kode_konter;
            MainApp.SendToKonter(kodeKontersend);
            File FileToSave = MainApp.ExcelSendToKonter(MainApp.ItemsReady);
            CreateFile(FileToSave,"Trasaction");
            MainApp.SaveTransaksiKirimBarang(MainApp.ItemsReady, kodeKontersend);
        }else{
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        }
    }//GEN-LAST:event_Btn_Send_TrasactionActionPerformed

    private void Btn_View_MutasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_View_MutasiActionPerformed
        // TODO add your handling code here:
        Date start = DatePick_Mulai_Trans.getDate();
        Date finish = DatePick_Akhir_Trans.getDate();
        String kode_konter = MainApp.KonterList.get(Combox_KodeKonter_Mutasi.getSelectedIndex()).kode_konter;                
        File FileToSave = MainApp.ViewMutasi(kode_konter,start,finish);
        CreateFile(FileToSave,"Mutasi");
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String [] columnNames = {"Tanggal","Barang Keluar","Setoran"};
        TableModel model_xls = new DefaultTableModel(columnNames, MainApp.MutationReport.size());
        for (int row = 0; row < MainApp.MutationReport.size(); row++) {
            model_xls.setValueAt(fmt.format(MainApp.MutationReport.get(row).tanggal_mutasi), row, 0);
            model_xls.setValueAt(MainApp.MutationReport.get(row).barang_keluar, row, 1);
            model_xls.setValueAt(MainApp.MutationReport.get(row).setoran, row, 2);
        }
        Table_Transaction.setModel(model_xls);
    }//GEN-LAST:event_Btn_View_MutasiActionPerformed

    private boolean admin_login(String username, String password){
        try{           
            Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sophiemartin?" + "user=root&password=");     
            PreparedStatement pst = conn.prepareStatement("Select * from admin where username=? and password=?");
            pst.setString(1, username); 
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();                        
            if(rs.next())            
                return true;    
            else
                return false;            
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }       
        
    }
    
    private void Btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_loginActionPerformed
        // TODO add your handling code here:
        NewWindowAdmin NWA;
        if(textfield_username.getText().length()==0)  
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        else if(textfield_password.getPassword().length==0)  
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        else{
       String user = textfield_username.getText();   
       char[] pass = textfield_password.getPassword(); 
       String pwd = String.copyValueOf(pass);  
       if(admin_login(user,pwd)){
           jTabbedPane1.setVisible(false);
           NWA = new NewWindowAdmin(MainApp);
           NWA.setVisible(true);  
       }
       else
          JOptionPane.showMessageDialog(null, "Incorrect Login Credentials");
    
        }

    }//GEN-LAST:event_Btn_loginActionPerformed

    
    private void textfield_usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_usernameKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
           if(textfield_username.getText().length()==0)  
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        else if(textfield_password.getPassword().length==0)  
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        else{
       
       String user = textfield_username.getText();   
       char[] pass = textfield_password.getPassword(); 
       String pwd = String.copyValueOf(pass);  
       if(admin_login(user,pwd)){
           NewWindowAdmin NWA = new NewWindowAdmin(MainApp);
           NWA.setVisible(true);       
       }           
       else
          JOptionPane.showMessageDialog(null, "Incorrect Login Credentials");
        }
    }//GEN-LAST:event_textfield_usernameKeyPressed

    private void textfield_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_passwordKeyPressed
        // TODO add your handling code here:
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
           if(textfield_username.getText().length()==0)  
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        else if(textfield_password.getPassword().length==0)  
            JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
        else{
            
       String user = textfield_username.getText();   
       char[] pass = textfield_password.getPassword(); 
       String pwd = String.copyValueOf(pass);  
       if(admin_login(user,pwd)){
           NewWindowAdmin NWA = new NewWindowAdmin(MainApp);
           NWA.setVisible(true); 
       }
       else
          JOptionPane.showMessageDialog(null, "Incorrect Login Credentials");
        }
    }//GEN-LAST:event_textfield_passwordKeyPressed

    private void Btn_SaveList_viewdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SaveList_viewdataActionPerformed
        // TODO add your handling code here:
        // get the list name
        String nama_list = TextF_namalist_viewdata.getText();
        if (nama_list.contains(" ")|| nama_list.isEmpty()){
            JOptionPane.showMessageDialog(null, "Save List name cannot be empty or contain spacebar");
        } else {
            MainApp.getListView();
            int listfound = 0;
            for (int i = 0; i < MainApp.ListViewData.size(); i ++){
                if (MainApp.ListViewData.get(i).namaList.trim().equals(nama_list.trim())){
                    listfound = 1;
                    ViewListData ViewList = new ViewListData();
                    ViewList.namaList = nama_list;
                    ViewList.listKodeBarang = new ArrayList<>();
                    for (int j = 0; j < Table_ViewData.getModel().getRowCount(); j ++){
                        ViewList.listKodeBarang.add(Table_ViewData.getModel().getValueAt(j,0).toString());
                    }
                    MainApp.ListViewData.remove(i);
                    MainApp.ListViewData.add(ViewList);
                    MainApp.storeListView();
                    MainApp.getListView();
                    setVectorViewList();
                    break;
                }
            }
            if(listfound == 0){
                ViewListData ViewList = new ViewListData();
                ViewList.namaList = nama_list;
                ViewList.listKodeBarang = new ArrayList<>();
                for (int i = 0; i < Table_ViewData.getModel().getRowCount(); i ++){
                    ViewList.listKodeBarang.add(Table_ViewData.getModel().getValueAt(i,0).toString());
                }
                MainApp.ListViewData.add(ViewList);
                MainApp.storeListView();
                MainApp.getListView();
                setVectorViewList();
                JOptionPane.showMessageDialog(null, "List Berhasil Tersimpan");
            }else {
                JOptionPane.showMessageDialog(null, "List Berhasil Diupdate");
            }
        }
        MainApp.getListView();
        setVectorViewList();
        
    }//GEN-LAST:event_Btn_SaveList_viewdataActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String [] columnNamesdumy = {"Kode Barang","Nama Barang", "Kategori", "Harga TPG", "Diskon Member", "Stok"};
        TableModel model_viewdata = new DefaultTableModel (columnNamesdumy, 0);
        Table_ViewData.setModel(model_viewdata);
        String list_name = Combox_ListViewData.getSelectedItem().toString();
        TextF_namalist_viewdata.setText(list_name);
        for (int i = 0; i < MainApp.ListViewData.size(); i++){
            if (MainApp.ListViewData.get(i).namaList.trim().equals(list_name)){
                for (int j = 0; j < MainApp.ListViewData.get(i).listKodeBarang.size(); j++){
                    String kode_barang = MainApp.ListViewData.get(i).listKodeBarang.get(j);
                    MainApp.ViewAllDataBarang(kode_barang);
                    String [] columnNames = {"Kode Barang","Nama Barang", "Kategori", "Harga TPG", "Diskon Member", "Stok"};
                    model_viewdata = new DefaultTableModel (columnNames, Table_ViewData.getModel().getRowCount() + 1);
                    for(int k = 0; k < Table_ViewData.getModel().getRowCount(); k++){
                        model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(k, 0),k,0);
                        model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(k, 1),k,1);
                        model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(k, 2),k,2);
                        model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(k, 3),k,3);
                        model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(k, 4),k,4);
                        model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(k, 5),k,5);
                    }   
                    model_viewdata.setValueAt(kode_barang,Table_ViewData.getModel().getRowCount(), 0);
                    model_viewdata.setValueAt(MainApp.ViewDataBarang.nama_barang, Table_ViewData.getModel().getRowCount(), 1);
                    model_viewdata.setValueAt(MainApp.ViewDataBarang.kategori, Table_ViewData.getModel().getRowCount(), 2);
                    model_viewdata.setValueAt(MainApp.ViewDataBarang.harga_tpg, Table_ViewData.getModel().getRowCount(), 3);
                    model_viewdata.setValueAt(MainApp.ViewDataBarang.disc_member, Table_ViewData.getModel().getRowCount(), 4);
                    model_viewdata.setValueAt(MainApp.ViewDataBarang.stock, Table_ViewData.getModel().getRowCount(), 5);
                    Table_ViewData.setModel(model_viewdata);
                    Table_ViewData.setAutoCreateRowSorter(true);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Combox_KodeBarang_AddOrderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Combox_KodeBarang_AddOrderKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            add_order_action();
        }
    }//GEN-LAST:event_Combox_KodeBarang_AddOrderKeyPressed

    private void TextField_JmlOrderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextField_JmlOrderKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            add_order_action();
        }
    }//GEN-LAST:event_TextField_JmlOrderKeyPressed

    private void TextField_Jumlah_NotFoundKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextField_Jumlah_NotFoundKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (!TextField_KodeOrder_NotFound.getText().isEmpty() && !TextField_Jumlah_NotFound.getText().isEmpty()){
                String KodeOrderNF = TextField_KodeOrder_NotFound.getText();
                int JmlBarangNF = Integer.parseInt(TextField_Jumlah_NotFound.getText());
                try {
                    MainApp.NotFoundGudang(KodeOrderNF, JmlBarangNF);
                    JOptionPane.showMessageDialog(null, "Not Found data order berhasil diupdate");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error : " + e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
            } 
        }
    }//GEN-LAST:event_TextField_Jumlah_NotFoundKeyPressed

    private void TextField_KodeOrder_NotFoundKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextField_KodeOrder_NotFoundKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (!TextField_KodeOrder_NotFound.getText().isEmpty() && !TextField_Jumlah_NotFound.getText().isEmpty()){
                String KodeOrderNF = TextField_KodeOrder_NotFound.getText();
                int JmlBarangNF = Integer.parseInt(TextField_Jumlah_NotFound.getText());
                try {
                    MainApp.NotFoundGudang(KodeOrderNF, JmlBarangNF);
                    JOptionPane.showMessageDialog(null, "Not Found data order berhasil diupdate");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error : " + e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
            } 
        }
    }//GEN-LAST:event_TextField_KodeOrder_NotFoundKeyPressed

    private void Combox_ViewDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Combox_ViewDataKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            view_databarang_action();
        }
    }//GEN-LAST:event_Combox_ViewDataKeyPressed

    private void Btn_Clr_ViewDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Clr_ViewDataActionPerformed
        // TODO add your handling code here:
        String [] columnNamesdumy = {"Kode Barang","Nama Barang", "Kategori", "Harga TPG", "Diskon Member", "Stok"};
        TableModel model_viewdata = new DefaultTableModel (columnNamesdumy, 0);
        Table_ViewData.setModel(model_viewdata);
    }//GEN-LAST:event_Btn_Clr_ViewDataActionPerformed

    private void Btn_SaveList_viewdata1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SaveList_viewdata1ActionPerformed
        // TODO add your handling code here:
        JTextField_TotalNominal.setText(Integer.toString(MainApp.GetNominalNetBarang()));
        JTextField_TotalNominal.setEditable(false);
        
    }//GEN-LAST:event_Btn_SaveList_viewdata1ActionPerformed

    private void Btn_Print_Order1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Print_Order1ActionPerformed
        // TODO add your handling code here:
        MainApp.SplitPrintUnavailable();
        File FileToSave;
        FileToSave = MainApp.TXTOrderPusat;
        CreateFile(FileToSave,"OrderPusat");
        
    }//GEN-LAST:event_Btn_Print_Order1ActionPerformed

    private void Btn_Delete_RowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Delete_RowActionPerformed
        // TODO add your handling code here:
        int selecteditem = Table_ViewData.getSelectedRow();
        if (selecteditem != -1){
            String [] columnNames = {"Kode Barang","Nama Barang", "Kategori", "Harga TPG", "Diskon Member", "Stok"};
            TableModel model_viewdata = new DefaultTableModel (columnNames, Table_ViewData.getModel().getRowCount() - 1);
            for(int i = 0; i < Table_ViewData.getModel().getRowCount(); i++){
                if (i != selecteditem){
                    int index;
                    if (i > selecteditem) index = i - 1;
                    else index = i;
                    model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 0),index,0);
                    model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 1),index,1);
                    model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 2),index,2);
                    model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 3),index,3);
                    model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 4),index,4);
                    model_viewdata.setValueAt(Table_ViewData.getModel().getValueAt(i, 5),index,5);
                } 
            }
            Table_ViewData.setModel(model_viewdata);
            Table_ViewData.setAutoCreateRowSorter(true);
            Table_ViewData.setRowSelectionAllowed(true);
            Table_ViewData.getTableHeader().setReorderingAllowed(false);
        }
    }//GEN-LAST:event_Btn_Delete_RowActionPerformed

    private void Btn_Delete_RowOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Delete_RowOrderActionPerformed
        // TODO add your handling code here:
        int selecteditem = Table_Order.getSelectedRow();
        if (selecteditem != -1){
            String [] columnNames = {"Kode Konter","Kode Barang","Nama Barang", "Jumlah", "Sisa Stok"};
            TableModel model_viewdata = new DefaultTableModel (columnNames, Table_Order.getModel().getRowCount() - 1);
            for (int i = 0; i < Table_Order.getModel().getRowCount(); i ++){
                int index;
                if ( i > selecteditem) index = i - 1;
                else index = i;
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 0),index,0);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 1),index,1);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 2),index,2);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 3),index,3);
                model_viewdata.setValueAt(Table_Order.getModel().getValueAt(i, 4),index,4);
            }
            Table_Order.setModel(model_viewdata);
            Table_Order.setAutoCreateRowSorter(true);
            Table_Order.setRowSelectionAllowed(true);
            Table_Order.getTableHeader().setReorderingAllowed(false);
            
        } 
    }//GEN-LAST:event_Btn_Delete_RowOrderActionPerformed

    private void Btn_Clear_OrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Clear_OrderActionPerformed
        // TODO add your handling code here:
        String [] columnNames = {"Kode Konter","Kode Barang","Nama Barang", "Jumlah", "Sisa Stok"};
        TableModel model_viewdata = new DefaultTableModel (columnNames, 0);
        Table_Order.setModel(model_viewdata);
        Table_Order.setAutoCreateRowSorter(true);
        Table_Order.setRowSelectionAllowed(true);
        Table_Order.getTableHeader().setReorderingAllowed(false);
    }//GEN-LAST:event_Btn_Clear_OrderActionPerformed


//
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(sophieForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sophieForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sophieForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sophieForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
             public void run() {
                sophieForm sf = new sophieForm();
                sf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                sf.setVisible(true);
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_AddItem_Excel;
    private javax.swing.JButton Btn_Add_Order;
    private javax.swing.JButton Btn_Browse_DBF;
    private javax.swing.JButton Btn_Browse_Excel;
    private javax.swing.JButton Btn_Clear_Order;
    private javax.swing.JButton Btn_Clr_ViewData;
    private javax.swing.JButton Btn_Delete_Row;
    private javax.swing.JButton Btn_Delete_RowOrder;
    private javax.swing.JButton Btn_NotFound;
    private javax.swing.JButton Btn_OK_DBF;
    private javax.swing.JButton Btn_OK_Excel;
    private javax.swing.JButton Btn_OK_ViewDat;
    private javax.swing.JButton Btn_Print_CariGudang;
    private javax.swing.JButton Btn_Print_Order1;
    private javax.swing.JButton Btn_SaveList_viewdata;
    private javax.swing.JButton Btn_SaveList_viewdata1;
    private javax.swing.JButton Btn_Save_DBF;
    private javax.swing.JButton Btn_Save_Excel;
    private javax.swing.JButton Btn_Save_Order;
    private javax.swing.JButton Btn_Send_Trasaction;
    private javax.swing.JButton Btn_View_Mutasi;
    private javax.swing.JButton Btn_login;
    private javax.swing.JComboBox Combox_KodeBarang_AddOrder;
    private javax.swing.JComboBox Combox_KodeKonter_AddOrder;
    private javax.swing.JComboBox Combox_KodeKonter_Mutasi;
    private javax.swing.JComboBox Combox_Konter_Kirim;
    private javax.swing.JComboBox<String> Combox_ListViewData;
    private javax.swing.JComboBox Combox_ViewData;
    private org.jdesktop.swingx.JXDatePicker DatePick_Akhir_Trans;
    private org.jdesktop.swingx.JXDatePicker DatePick_Mulai_Trans;
    private javax.swing.JTextField JTextField_TotalNominal;
    private javax.swing.JTable Table_DBF;
    private javax.swing.JTable Table_Excel;
    private javax.swing.JTable Table_Order;
    private javax.swing.JTable Table_Transaction;
    private javax.swing.JTable Table_ViewData;
    private javax.swing.JTextField TextF_namalist_viewdata;
    private javax.swing.JTextField TextField_DBF_path;
    private javax.swing.JTextField TextField_JmlOrder;
    private javax.swing.JTextField TextField_Jumlah_NotFound;
    private javax.swing.JTextField TextField_KodeOrder_NotFound;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPasswordField textfield_password;
    private javax.swing.JTextField textfield_username;
    // End of variables declaration//GEN-END:variables

   
}





