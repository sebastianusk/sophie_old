package application;

import excel.UpdKatalog;
import mysql.*;
import excel.*;
import dbf.*;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import sophie.DeadStyleData;
import stringedit.StringTools;
import stringedit.ViewListData;



/**
 * complete class for application layer
 * @author Seb
 */
public class apps {
    // object for connection data
    FileMySQL       mysqlfiles;
    FileExcel       excelfiles;
    FileDBF         dbffiles;
    
    public DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    public Date currentDate;
    
    // PARAMETER FOR STORING DATA
    // GENERAL PARAMETER (will be used by many application)
    public ArrayList<KodeNamaKategori> ItemList; // item list
    public ArrayList<KodeNamaKonter> KonterList;
    public ArrayList<ViewListData> ListViewData;
    
    // 1. update katalog
    public KatalogData InputKatalogData;
    public ArrayList<UpdKatalog> updKatalogData;
    public Date updKatalogDate;
    
    // 2. add order
    public ArrayList<OrderData> InputOrderData;
    public int ordercounter;
    
    // split print
    // variable to store split print -> cari gudang atau order pusat
    public ArrayList<OrderData> CariGudang;
    public ArrayList<OrderData> OrderPusat;
    public ArrayList<File>  ExcelCariGudang;
    
    public File TXTOrderPusat;
    
    // 3. morning daily update
    // daily update
    public ArrayList<StockData> updStockData;
    
    // variable to store all order in order pusat status
    public ArrayList<OrderData> AllOrderPusat;
    
    // variable to store order that is coming and ready
    public ArrayList<OrderData> ComingOrder;
    
    // variable to store difference edited new stock and fresh from xls
    public ArrayList<DiffUpdStock> diffStockData;
    
    // 4. trasaction
    // variable to store data transaksi that is ready
    public ArrayList<ItemReadyData> ItemsReady;
    // variable to store transaction
    public ArrayList<TransactionData> Mutation;
    // variable to report mutation in form
    public ArrayList<MutationReportData> MutationReport;
    
    
    // 5. view databarang
    // variable to view databarang
    public ItemsData ViewDataBarang;
    
    // 6. popoular item
    // variable to view popular item
    public ArrayList<PopulerData> ViewPopularData;
    
    
    public apps() throws Exception{
        try {
            mysqlfiles      = new FileMySQL();
            excelfiles      = new FileExcel();
            dbffiles        = new FileDBF();
            InputOrderData  = new ArrayList<OrderData>();
            ordercounter    = mysqlfiles.GetOrderCounter();
        } catch (Exception e){
            throw new Exception(e);
        }
    }
    
    // METHOD FOR APPLICATION
    // FILL GENERIC PARAMETER
    public void GetItemList(){
        mysqlfiles.GetItemList();
        ItemList = mysqlfiles.ItemList;
    }
    
    public void GetKonterList(){
        mysqlfiles.GetKonterList();
        KonterList = mysqlfiles.KonterList;
    }
    
    public void GetCurrentTime(){
        currentDate = new Date();
    }
    
    public String GetDefDir(){
        return mysqlfiles.GetDefDIR();
    }
    
    public void StoreDefDir(String path){
        mysqlfiles.StoreDefDIR(path);
    }
    
    public String getNamaBarang(String kode_barang){
        for (int i = 0; i < ItemList.size(); i++){
            if (ItemList.get(i).kode_barang.trim().equals(kode_barang))
                return ItemList.get(i).nama_barang;
        }
        return null;
    }
    
    public void storeListView(){
        StringTools tools = new StringTools();
        tools.createViewList(ListViewData);
    }
    
    public void getListView(){
        StringTools tools = new StringTools();
        ListViewData = tools.getViewList();
        if (ListViewData == null) ListViewData = new ArrayList<>();
    }
    
    // A. UPDATE KATALGO
    //  1. Read DBF file, get the data
    public void ReadDBF(String file_name, String katalog_date){
        dbffiles.openfile(file_name);
        dbffiles.readfile(katalog_date);
        InputKatalogData = dbffiles.NewKatalogData;
    }
    
    // 2. check update databarang from database, comparing with new dbf file
    public void CheckDatabarang(){
        mysqlfiles.PrepareUpdateKatalog(InputKatalogData);
        updKatalogDate = InputKatalogData.DateData;
        for (int i = 0; i < InputKatalogData.BasicData.size(); i++){
            mysqlfiles.CheckUpdateKatalog(InputKatalogData, i, updKatalogDate);
        }
        updKatalogData = mysqlfiles.updKatalogData;
    }
    
    // 3. create excel file
    public File XLSUpdateDatabarang(){
        return excelfiles.excel_create_katalog_update(updKatalogData);
    }
    
    // 4. update the databarang from updated katalog and new katalog
    public void UpdateDatabarang() throws Exception{
        try{
            mysqlfiles.UpdateKatalog(updKatalogData);
            mysqlfiles.NewKatalog(updKatalogDate);
        } catch (Exception e){
            throw e;
        }
    }
    
    // B. ADD ORDER
    // 1. add data to the arraylist of order
    public void CreateOrder(String kode_barang, String kode_konter, int jumlah, Date order_date){
        OrderData constructor = new OrderData();
        constructor.kode_konter     = kode_konter;
        constructor.kode_barang     = kode_barang;
        constructor.jumlah_barang   = jumlah;
        constructor.tanggal_masuk   = order_date;
        InputOrderData.add(constructor);
    }
    
    // 2. chek stok from the database, it will decide the order is available or not
    public void OrderCheckStock(){
        for (int i = 0; i < InputOrderData.size(); i++){
            ordercounter += 1;
            InputOrderData.get(i).kode_order = String.valueOf(ordercounter + 1);
            mysqlfiles.AddOrder(InputOrderData.get(i));
            mysqlfiles.SetOrderCounter(ordercounter);
            mysqlfiles.PopCount(InputOrderData.get(i));
        }
        InputOrderData = new ArrayList<OrderData>();
    }
    
    // 3. get data from available or not available from order table and set them to cari gudang and order pusat
    public File SplitPrintAvailable(){
        mysqlfiles.PrintOrderAvailable();
        CariGudang = mysqlfiles.CariGudang;
        GetCurrentTime();
        ArrayList<CariGudangReportData> DataReport = new ArrayList<>();
        for (int i = 0; i < CariGudang.size(); i ++){
            CariGudangReportData constructor = new CariGudangReportData();
            constructor.kode_order  = CariGudang.get(i).kode_order;
            constructor.kode_konter = CariGudang.get(i).kode_konter;
            constructor.kode_barang = CariGudang.get(i).kode_barang;
            constructor.jumlah = CariGudang.get(i).jumlah_barang;
            mysqlfiles.RetrieveDataBarang(CariGudang.get(i).kode_barang);
            constructor.nama_barang = mysqlfiles.ViewDataBarang.nama_barang;
            constructor.kategori = mysqlfiles.ViewDataBarang.kategori;
            constructor.harga_tpg = mysqlfiles.ViewDataBarang.harga_tpg;
            constructor.disc    = mysqlfiles.ViewDataBarang.disc_member;
            DataReport.add(constructor);
        }
        return excelfiles.excel_create_cari_gudang(DataReport,currentDate);
        // ExcelCariGudang = excelfiles.ExcelCariGudang;
        
        
    }
    
    public void SplitPrintUnavailable(){
        mysqlfiles.PrintOrderUnavailable();
        OrderPusat = mysqlfiles.OrderPusat;
        GetCurrentTime();

        StringTools txteditor = new StringTools ();
        TXTOrderPusat = txteditor.TXTCreateOrderPusat(OrderPusat, currentDate);
    }
    
    
    // 4. set the order with status cari gudan to not found
    public void NotFoundGudang(String kode_order, int jumlah) throws Exception{
        OrderData temp = new OrderData();
        temp.kode_order = kode_order;
        temp.jumlah_barang = jumlah;
        try {
            mysqlfiles.NotFound(temp);
        } catch (Exception e){
            throw new Exception (e);
        }
    }
    
    // C. UPDATE DAILY
    // 1. open xls file
    public void OpenXLSFile(String pathfile){
        excelfiles.get_daily_data(pathfile);
        updStockData = excelfiles.updStockData;
    }
    
    
    // 2. check edited stock update
    public File XLSEditedNewStock(ArrayList<StockData> edited, ArrayList<StockData> original){
        GetCurrentTime();
        diffStockData = new ArrayList();
        for (int i = 0; i < original.size(); i++){
            if (original.get(i).jumlah_barang != edited.get(i).jumlah_barang ||
                    !original.get(i).kode_barang.equals(edited.get(i).kode_barang)){
                DiffUpdStock constructor = new DiffUpdStock();
                constructor.oriKodeBarang = original.get(i).kode_barang;
                constructor.oriJumlahBarang = original.get(i).jumlah_barang;
                constructor.edtKodeBarang = edited.get(i).kode_barang;
                constructor.edtJumlahBarang = edited.get(i).jumlah_barang;
                diffStockData.add(constructor);
            }
        }
        if (original.size() < edited.size()){
            for (int i = 0; i < edited.size() - original.size(); i ++){
                DiffUpdStock constructor = new DiffUpdStock();
                constructor.edtKodeBarang = edited.get(i + original.size()).kode_barang;
                constructor.edtJumlahBarang = edited.get(i + original.size()).jumlah_barang;
                diffStockData.add(constructor);
            }
        }
        
        return excelfiles.excel_create_diff_upd_stock(diffStockData, currentDate);
    }
    
    // 3. check wether any order with status order pusat came with the new stock
    public File CheckNewStock(ArrayList<StockData> newUpdStockData){
        mysqlfiles.FindOrderPusat();
        mysqlfiles.IfOrderComing(newUpdStockData);
        ComingOrder = mysqlfiles.ComingOrder;
        GetCurrentTime();
        ArrayList<ComingOrderReportData> comingdata = new ArrayList<>();
        for (int i = 0; i < ComingOrder.size(); i ++){
            ComingOrderReportData constructor = new ComingOrderReportData();
            constructor.kode_konter = ComingOrder.get(i).kode_konter;
            constructor.kode_barang = ComingOrder.get(i).kode_barang;
            constructor.jumlah_barang = ComingOrder.get(i).jumlah_barang;
            for (int j = 0; j < ItemList.size(); j ++){
                if (ComingOrder.get(i).kode_barang.trim().equals(ItemList.get(j).kode_barang.trim())){
                    constructor.nama_barang = ItemList.get(j).nama_barang;
                    constructor.kategori = ItemList.get(j).kategori;
                    break;
                }
            }
            comingdata.add(constructor);
        }
        
        return excelfiles.excel_create_order_pusat_coming(comingdata, currentDate);
//        return null;
        
    }
    
    // 4. add the new stock into the database
    public void UpdateNewStock(ArrayList<StockData> newUpdStockData){
        for (int i = 0; i < newUpdStockData.size(); i++){
            mysqlfiles.MorningStock(newUpdStockData.get(i));
        }
    }
    
    
    // D. TRANSACTION
    // 5. function for transaction section
    public void SendToKonter(String kode_konter){
        mysqlfiles.GetOrderReady(kode_konter);
        ItemsReady = mysqlfiles.ItemsReady;
    }
    
    public File ExcelSendToKonter (ArrayList<ItemReadyData> ItemsReadyData){
        GetCurrentTime();
        ArrayList<ItemReadyData> ItemReadyGroup = new ArrayList<>();
        int noitem = 0;
        boolean itemfound = false;
        for (int i = 0; i < ItemsReadyData.size(); i ++){
            for (int j = 0; j < noitem; j ++){
                if (ItemsReadyData.get(i).kode_barang.trim().equals(ItemReadyGroup.get(j).kode_barang.trim())){
                    ItemReadyGroup.get(j).jumlah_barang += ItemsReadyData.get(i).jumlah_barang;
                    itemfound = true;
                    break;
                }
                itemfound = false;
            }
            if (itemfound == false){
                ItemReadyData constructor = new ItemReadyData();
                constructor.kode_order = ItemsReadyData.get(i).kode_order;
                constructor.kode_konter = ItemsReadyData.get(i).kode_konter;
                constructor.kode_barang = ItemsReadyData.get(i).kode_barang;
                constructor.nama_barang = ItemsReadyData.get(i).nama_barang;
                constructor.jumlah_barang = ItemsReadyData.get(i).jumlah_barang;
                constructor.kategori = ItemsReadyData.get(i).kategori;
                constructor.harga_tpg = ItemsReadyData.get(i).harga_tpg;                
                constructor.disc_member = ItemsReadyData.get(i).disc_member;
                ItemReadyGroup.add(constructor);
                noitem++;
            }
        }
        return excelfiles.excel_create_kirim_barang(ItemReadyGroup, currentDate);
        
    }
    
    public void SaveTransaksiKirimBarang(ArrayList<ItemReadyData> item_sent, String kode_konter){
        double nominal = 0;
        double price = 0;
        double disc = 0;
        for (int i = 0; i < item_sent.size(); i ++ ){
            disc = ((100 - item_sent.get(i).disc_member) / 100.0);
            price = (double)item_sent.get(i).harga_tpg;
            nominal +=  price * disc * item_sent.get(i).jumlah_barang;
        }
        GetCurrentTime();
        mysqlfiles.CounterGetItem(kode_konter,(int)nominal,currentDate);
        for (int i = 0; i < item_sent.size(); i ++){
            mysqlfiles.DeleteOrder(item_sent.get(i).kode_order);            
        }
    }
    
    public void TerimaBayar (String kode_konter, int nominal){
        GetCurrentTime();
        mysqlfiles.CounterPayment(kode_konter, nominal, currentDate);
    }
    
    public File ViewMutasi (String kode_konter, Date Start, Date End){
        mysqlfiles.MutasiView(kode_konter, Start, End);
        Mutation = mysqlfiles.Mutation;
        MutationReport = new ArrayList();
        int datefound;
        MutationReportData constructor;
        for (int i = 0; i < Mutation.size(); i ++){
            datefound = 0;
            for (int j = 0; j < MutationReport.size(); j ++){
                if (Mutation.get(i).tanggal_transaksi.equals(MutationReport.get(j).tanggal_mutasi)){
                    if (Mutation.get(i).nominal > 0){
                        MutationReport.get(j).barang_keluar += Mutation.get(i).nominal;
                    } else {
                        MutationReport.get(j).setoran += -(Mutation.get(i).nominal);
                    }
                    datefound = 1;
                    break;
                }
            }
            if (datefound == 0){
                constructor = new MutationReportData();
                MutationReport.add(constructor);
                constructor.tanggal_mutasi = Mutation.get(i).tanggal_transaksi;
                if (Mutation.get(i).nominal > 0){
                    constructor.barang_keluar = Mutation.get(i).nominal;
                } else{
                    constructor.setoran = -(Mutation.get(i).nominal);
                }
            }
        }
        int counter = 0;
        for (counter = 0; counter < KonterList.size(); counter ++){
            if (KonterList.get(counter).kode_konter.trim().equals(kode_konter.trim()))
                break;
        }
        GetCurrentTime();
        return excelfiles.excel_mutation_report(MutationReport, KonterList.get(counter), Start, End, currentDate);
        
    }
    
    // E. VIEW DATA
    // Function to get all data
    public void ViewAllDataBarang(String KodeBarang){
        mysqlfiles.RetrieveDataBarang(KodeBarang);
        ViewDataBarang = mysqlfiles.ViewDataBarang;
    }
    
    // F. SETTING TAB
    public void ChangeStockData(String kodebarang, int new_stock){
        mysqlfiles.UpdateDirectStock(kodebarang,new_stock);
    }
    
    public void AddStockManual(String kodebarang, String namabarang, String kategori,
            int hargatpg, int disc, int stok){
        GetCurrentTime();
        KatalogData ManualInput = new KatalogData(dtf.format(currentDate));
        RawKatalogData constructor = new RawKatalogData();
        constructor.kode_barang = kodebarang;
        constructor.nama_barang = namabarang;
        constructor.kategori    = kategori;
        constructor.harga_tpg   = hargatpg;
        constructor.disc_member = disc;
        ManualInput.BasicData.add(constructor);
        mysqlfiles.PrepareUpdateKatalog(ManualInput);
        mysqlfiles.CheckUpdateKatalog(ManualInput, 0, currentDate);
        mysqlfiles.NewKatalog(currentDate);
        mysqlfiles.UpdateDirectStock(constructor.kode_barang,stok);
    }
    
    public void AddKonterManual (String kode_konter, String nama_konter, int hutang_konter){
        mysqlfiles.AddNewKonter(kode_konter, nama_konter, hutang_konter);
    }
    
    public void RemoveKonterManual (String kode_konter){
        GetKonterList();
        mysqlfiles.ClearCounter(kode_konter);        
    }
    
    public File ViewPopularData(Date StartDate, Date EndDate){
        mysqlfiles.PopView(StartDate, EndDate);
        ViewPopularData = mysqlfiles.ViewPopularData;
        return excelfiles.excel_create_popular(ViewPopularData, StartDate, EndDate);
    }
    
    // G. Dead Style
    
    public File ViewDeadStylePrint(ArrayList<DeadStyleData> DSData, int discDS){
        GetCurrentTime();
        return excelfiles.excel_create_dead_style(DSData, currentDate, discDS);
    }
    
    public void DecrementDeadStyle(ArrayList<DeadStyleData> DSData){
        for (int i = 0; i < DSData.size(); i ++){
            mysqlfiles.DecrementStockDeadStyle(DSData.get(i).kode_barang, DSData.get(i).Jumlah);
        }
    }
    
    public void ChangeAdmin (String user_name, String password){
        mysqlfiles.change_user_admin(user_name,password);
    }
    
    public void StoreDurOrderDel (int dur){
        mysqlfiles.StoreDurOrderDel(dur);
    }
    
    public int GetDurOrderDel(){
        return mysqlfiles.GetDurOrderDel();
    }
    
    public void DeleteOldOrder(){
        mysqlfiles.deleteoldorder(GetDurOrderDel());
    }
    
    public int GetNominalNetBarang(){
        return mysqlfiles.GetNominalTotal();
        
    }

    public ViewListData readListViewData(String txtFilePath) {
        StringTools tools = new StringTools();
        return tools.getViewList(txtFilePath);
        
    }

}
