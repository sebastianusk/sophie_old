package mysql;
import dbf.KatalogData;
import excel.UpdKatalog;
import java.sql.*;
import java.util.Date;
import java.text.*;

public abstract class FileMySQLQueries {
    // variable needed to initialize the connection with database
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    
    // variable needed to store the result
    protected ResultSet result;
        
    
    // variable needed to edit date format
    DateFormat fmt;
    
    // Constructor that will initialize the connection with database
    protected FileMySQLQueries() throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sophiemartin","root","");
            // con=DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=rootpassword");
            st = con.createStatement();
            fmt = new SimpleDateFormat("yyyy-MM-dd");
            result = null;
        }catch(Exception e){
            throw new Exception(e);
        }
    }
    
    // Method to select next data
    // jdbc see result for each row
    // this method need to be called to see next row data
    // can return boolean, if data not exist, return false
    protected boolean next_data (){
        try{
            return result.next();
        } catch (Exception e){
            System.out.println("Error : " + e);
            return false;
        }
    }
    
    
    /* ----- GENERIC METHOD TO GET RESULT -----------*/ 
    
    // Method to get String return
    protected String get_result_String (String column){
        try{
            return result.getString(column);
        }catch(Exception e){
            System.out.println("Error : "+ e);
            return "";
        }
    }
    
    // Method to get integer result
    protected int get_result_int (String column){
        try{
            return result.getInt(column);
        }catch(Exception e){
            System.out.println("Error : "+ e);
            return 0;
        }
    }
    
    // Method to get Date result
    protected Date get_result_Date (String column){
        try{
            return result.getDate(column);
        }catch(Exception e){
            System.out.println("Error : "+ e);
            return null;
        }
    }
    
    /* ----- GENERIC METHOD TO RUN DEFAULT QUERIES ON TABLE-----------*/ 
    
    protected void ItemList(){
        try{
            String sql = "SELECT kode_barang, nama_barang, kategori FROM databarang";
            result=st.executeQuery(sql);
        }catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void KonterList(){
        try{
            String sql = "SELECT * FROM datakonter";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void FetchOrderCounter(){
        try{
            String sql = "SELECT * FROM `non_volatile_mem` WHERE parameter = 'ordercounter';";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("error : " + e);
        }
    }
    
    protected void StoreOrdercounter(int i){
        try{
            String sql = "UPDATE `non_volatile_mem` SET `nilai`='"+i+"' WHERE `non_volatile_mem`.`parameter` = 'ordercounter';";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("error : " + e);
        }
    }
    
    // 1. DATABARANG
    // method to get data from databarang
    protected void get_databarang(String kode_barang){
        try{
            String sql = "SELECT * FROM databarang WHERE kode_barang = '"+kode_barang+"';";
            result=st.executeQuery(sql);
        }catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
    
    protected void get_all_databarang(){
        try{
            String sql = "SELECT * FROM databarang;";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // 2. DATAKONTER
    // method to get data from datakonter
    protected void get_datakonter(String kode_konter){
        try{
            String sql = "SELECT * FROM datakonter WHERE kode_konter = '"+kode_konter+"';";
            result=st.executeQuery(sql);
        }catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
    
    
    // 3. DATA ORDER
    // method to get data from dataorder
    // by kode order
    protected void get_dataorder(String kode_order){
        try{
            String sql = "SELECT * FROM dataorder WHERE kode_order = '"+kode_order+"';";
            result=st.executeQuery(sql);
        }catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
    
    // by status order
    protected void get_dataorder(int status_order){
        try{
            String sql = "SELECT * FROM dataorder WHERE status_order = "+status_order+" ORDER BY DATE(tgl_masuk) ASC ;";
            result=st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    
    // get special case for print (get order with 2 status order)
    protected void get_dataorder(int status_order1,int status_order2){
        try{
            String sql = "SELECT * FROM dataorder WHERE status_order = "+status_order1+" OR status_order = "+status_order2+";";
            result=st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    
    // get special case for print (get order with 3 status order)
    protected void get_dataorder(int status_order1,int status_order2,int status_order3){
        try{
            String sql = "SELECT * FROM dataorder WHERE status_order = "+status_order1+" OR status_order = "+status_order2+" OR status_order = "+status_order3+";";
            result=st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // get special case for print - order status available, sort by kode konter
    protected void get_available_order(){
        try{
            String sql = "SELECT * FROM dataorder WHERE status_order = 1 ORDER BY kode_konter, kode_barang;";
            result=st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // get special case for print - order status not available and not found in the storage sort by kode barang
    protected void get_unavailable_order(){
        try{
            String sql = "SELECT kode_barang, SUM(jumlah_barang) AS 'jumlah_order' FROM dataorder WHERE status_order = 2 OR status_order = 5 GROUP BY kode_barang";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    
    // method to get data from datatransaksi
    protected void get_datatransaksi(String kode_transaksi){
        try{
            String sql = "SELECT * FROM datatransaksi WHERE kode_konter = '"+kode_transaksi+"';";
            result=st.executeQuery(sql);
        }catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
    
    // method to get data from status order
    protected void statusorder(String status_order){
        try{
            String sql = "SELECT * FROM statusorder WHERE kode_konter = '"+status_order+"';";
            result=st.executeQuery(sql);
        }catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
    
    // 4. morning stok come
    // Special case ater morning stok came, check if any order from yesterday come
    protected void count_order_pusat(){
        try{
            String sql = "SELECT * FROM dataorder WHERE status_order = 4)";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    

    
    // 5. transaksi
   
    // special case to get order with status cari gudang and barang datang
    protected void items_ready (String kode_konter){
        try {
            String sql = "SELECT kode_order, kode_konter, dataorder.kode_barang, nama_barang, jumlah_barang, kategori, harga_tpg, disc_member FROM dataorder INNER JOIN databarang ON dataorder.kode_barang = databarang.kode_barang WHERE (status_order = 3 or status_order = 6) and kode_konter = \"" + kode_konter + "\" ORDER BY disc_member DESC, kategori ASC;";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
    
    // 5. TRANSASKSI
    // method to count the debt
    protected void count_debt(){
        try {
            String sql = "SELECT kode_konter, SUM(nominal_transaksi) FROM datatransaksi GROUP BY kode_konter;";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void get_mutasi(String kode_konter, Date Start, Date End){
        try{
            String sql = "SELECT * FROM `datatransaksi` WHERE (tanggal_transaksi BETWEEN '" + fmt.format(Start) + "' AND '" + fmt.format(End) + "') AND kode_konter = \"" + kode_konter +"\";";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
        
    /* --- METHOD TO UPDATE THE DATABASE ---- */
    // 1. Update from catalog
    // create new record of databarang
    protected void new_databarang (ItemsData datain, Date new_date){
        try{
            if (datain.disc_member == 10) datain.disc_member = 5;
            String sql = "INSERT INTO databarang VALUES ('"+datain.kode_barang+"',\""+datain.nama_barang+"\",'"+datain.harga_tpg+"','"+datain.disc_member+"','"+datain.kategori+"','"+fmt.format(new_date)+"',0)";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
    
    // update harga and discount
    protected void update_databarang(UpdKatalog datain) throws Exception {
        try{
            if (datain.disc_member == 10) datain.disc_member = 5;
            String sql = "UPDATE `databarang` SET `harga_tpg`='"+datain.harga_tpg_new+"',`disc_member`= '"+datain.disc_member+"',`tanggal_update`='"+fmt.format(datain.update_date_new)+
                    "',`nama_barang`= \"" + datain.nama_barang + "\",`kategori`= '" + datain.kategori + "' WHERE `databarang`.`kode_barang` = \""+datain.kode_barang+"\";";
            st.executeUpdate(sql);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
    
    // update stok decrement
    protected void stok_update_dec (String kode_barang, int jumlah_barang){
        try {
            String sql = "UPDATE `databarang` SET `stok` = (stok - "+jumlah_barang+") WHERE `databarang`.`kode_barang` = '"+kode_barang+"'";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // update stok increment
    protected void stok_update_inc (String kode_barang, int jumlah_barang){
        try {
            String sql = "UPDATE `databarang` SET `stok` = (stok + "+jumlah_barang+") WHERE `databarang`.`kode_barang` = '"+kode_barang+"'";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // update stok value direct
    protected void stok_update_direct (String kode_barang, int jumlah_barang){
        try {
            String sql = "UPDATE `databarang` SET `stok` = "+jumlah_barang+" WHERE `databarang`.`kode_barang` = '"+kode_barang+"'";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    //2. Order
    // add new order
    protected void add_dborder(OrderData orderin){
        try{
            // add order table
            String sql = "INSERT INTO `dataorder` VALUES ('"+orderin.kode_order+"','"+orderin.kode_konter+"','"+orderin.kode_barang+"','"+orderin.jumlah_barang+"','"+orderin.status_order+"','"+fmt.format(orderin.tanggal_masuk)+"');";
            st.executeUpdate(sql);
            if(orderin.status_order == 1){ // if order available, get stok out from database
                stok_update_dec(orderin.kode_barang, orderin.jumlah_barang);
            }
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // change status_order
    protected void change_all_order_status (int status_new, int status_old){
        try{
            String sql = "UPDATE `dataorder` SET `status_order` = "+status_new+" WHERE `dataorder`.`status_order` = "+status_old+";";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // change status order
    protected void change_order_status(OrderData order_input, int new_status){
        try {
            String sql = "UPDATE `dataorder` SET `status_order` = " + new_status + " WHERE `dataorder`.`kode_order` = '"+order_input.kode_order+"';";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    
    // special case to change jumlah_barang from order
    protected void change_jumlah_barang (String kode_order, int jumlah_barang){
        try {
            String sql = "UPDATE `dataorder` SET `jumlah_barang` = "+jumlah_barang+" WHERE `dataorder`.`kode_order` = '"+kode_order+"';";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    
    // 4. TRANSAKSI
    // tambah transaski
    protected void add_transaksi (String kode_konter, int nominal, Date tanggal_transaksi){
        try {
            String sql = "INSERT INTO datatransaksi VALUES (NULL, '"+kode_konter+"','"+fmt.format(tanggal_transaksi)+"', '"+nominal+"');";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void update_hutang(String kode_konter, int nominal){
        try {
            String sql = "UPDATE datakonter SET hutang_konter = (hutang_konter + ( " + nominal + ")) WHERE kode_konter = \"" + kode_konter + "\";";
            //String sql = "UPDATE 'datakonter' SET 'hutang_konter' = 'hutangkonter' + " + nominal + " WHERE 'datakonter'.'kode_konter' = \"" + kode_konter + "\";";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    
    // POPULAR ITEM
    // tambah data popular
    protected void add_popular (String kode_barang, String kode_konter, int jumlah, Date tanggal_pesan){
        try {
            String sql = "INSERT INTO popularcount VALUES (NULL,\""+kode_barang.trim()+"\",'"+ kode_konter +"','" + jumlah + "','"+fmt.format(tanggal_pesan)+"');";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void get_popular(Date start_date, Date end_date){
        try {
            String sql = "SELECT kode_barang, COUNT(DISTINCT(kode_konter)) AS 'JumlahKonter', SUM(jumlah) AS 'JumlahOrder' FROM `popularcount` WHERE tanggal_pesan BETWEEN '"+ fmt.format(start_date) + "' AND '" + fmt.format(end_date) + "' GROUP BY kode_barang;";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    
    
    // DELETE DATA FROM DATABASE
    protected void delete_row (String nama_tabel, String primary){
        try {
            String sql = "DELETE FROM " + nama_tabel + " WHERE kode_order = \"" + primary + "\";";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void delete_row (String nama_tabel, int primary){
        try {
            String sql = "DELETE FROM " + nama_tabel + " WHERE kode_order = " + primary + ";";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // KONTER EDIT
    protected void add_konter (String kode_konter, String nama_konter, int hutang_konter){
        try {
            String sql = "INSERT INTO datakonter VALUES (\"" + kode_konter + "\", \"" + nama_konter + "\", "+ hutang_konter + ");";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void clear_konter (String kode_konter){
        try {
            String sql = "DELETE FROM datakonter WHERE kode_konter = \"" + kode_konter + "\";";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    // method for managing default DIR
    protected void getDIR(){
        try{
            String sql = "SELECT * FROM non_volatile_mem_string WHERE parameter = 'DefDir';";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void storeDIR(String path){
        try{
            String sql = "UPDATE `non_volatile_mem_string` SET `value` = \""+path+"\" WHERE `non_volatile_mem_string`.`parameter` = 'DefDir';";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void clean_admin(){
        try{
            String sql = "TRUNCATE admin;";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void create_admin (String user_name, String password){
        try {
            String sql = "INSERT INTO `admin` (`username`, `password`) VALUES ('" + user_name + "', '" + password + "');";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void storedurationdelete(int dur){
        try {
            String sql = "UPDATE `non_volatile_mem` SET `nilai` = '" + dur + "' WHERE `non_volatile_mem`.`parameter` = 'durdelete';";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void getdurationdelete(){
        try {
            String sql = "SELECT * FROM `non_volatile_mem`;";
            result = st.executeQuery(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    protected void delete_old_order(int dur){
        try{
            String sql = "DELETE FROM dataorder WHERE tgl_masuk < (NOW() - INTERVAL " + dur + " DAY) AND status_order = 4;";
            st.executeUpdate(sql);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
         
    }
    
    
    
    
    
    
    // dummy method buat coba2
    
    public void testdata(){
        try{
            String sql="select * from statusorder";
            result=st.executeQuery(sql);
            result.next();
            String stok = result.getString("keterangan");
            System.out.println(stok);
            result.next();
            stok = result.getString("keterangan");
            System.out.println(stok);
            result.next();
            stok = result.getString("keterangan");
            System.out.println(stok);
        }catch (Exception e){
            System.out.println("Error : "+ e);
        }
    }
}
