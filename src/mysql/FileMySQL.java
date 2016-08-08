package mysql;
import excel.UpdKatalog;
import dbf.KatalogData;
import excel.StockData;
import java.util.Date;
import java.util.ArrayList;
import java.util.Random;



/**
 * class to update the database. inherit from database
 * @author Seb
 */
public  class FileMySQL extends FileMySQLQueries{
    // variable for editing
    ArrayList<ItemsData> newKatalogData; // katalog data that is new
    public ArrayList<UpdKatalog> updKatalogData; // katalog data that is updated
    
    public ArrayList<TransactionData>      data_debt;
    public ArrayList<KodeNamaKategori> ItemList;
    public ArrayList<KodeNamaKonter>   KonterList;

    // variable to store split print -> cari gudang atau order pusat
    public ArrayList<OrderData> CariGudang;
    public ArrayList<OrderData> OrderPusat;
    
    // variable to store all order in order pusat status
    public ArrayList<OrderData> AllOrderPusat;
    
    // variable to store order in order coming status
    public ArrayList<OrderData> ComingOrder;
    
    // variable to store data transaksi that is ready
    public ArrayList<ItemReadyData> ItemsReady;
    
    // variable to view databarang
    public ItemsData ViewDataBarang;
    
    // variable to store transaction
    public ArrayList<TransactionData> Mutation;
    
    // variable to store popular data
    public ArrayList<PopulerData> ViewPopularData;
    
    public FileMySQL() throws Exception{
    }
    
    // method to get item list
    public void GetItemList(){
        ItemList = null;
        ItemList = new ArrayList<KodeNamaKategori>();
        ItemList();
        while(next_data()){
            KodeNamaKategori constructor = new KodeNamaKategori();
            constructor.kode_barang = get_result_String("kode_barang");
            constructor.nama_barang = get_result_String("nama_barang");
            constructor.kategori    = get_result_String("kategori");
            ItemList.add(constructor);
        }
    }
    
    public void GetKonterList(){
        KonterList = null;
        KonterList = new ArrayList<KodeNamaKonter>();
        KonterList();
        while(next_data()){
            KodeNamaKonter constructor = new KodeNamaKonter();
            constructor.kode_konter   = get_result_String("kode_konter");
            constructor.nama_konter   = get_result_String("nama_konter");
            constructor.hutang_konter = get_result_int ("hutang_konter");
            KonterList.add(constructor);
        }
    }
    
    // method to get order counter
    public int GetOrderCounter(){
        FetchOrderCounter();
        next_data();
        return get_result_int("nilai");
    }
    
    public void SetOrderCounter(int counter){
        StoreOrdercounter(counter);
    }
    
    // method to prepare update catalgo
    public void PrepareUpdateKatalog(KatalogData data_input){
        newKatalogData = new ArrayList<ItemsData>();
        updKatalogData = new ArrayList<UpdKatalog>();
    }
    
    // method to find update data catalog (for each data)
    public void CheckUpdateKatalog(KatalogData data_input, int index, Date new_catalog_date){
        //check if data with kode barang already there
        get_databarang(data_input.BasicData.get(index).kode_barang);
        if(next_data()){
            Date old_catalog_date = get_result_Date("tanggal_update");
            if (old_catalog_date.before(new_catalog_date) && 
                    (data_input.BasicData.get(index).harga_tpg != get_result_int("harga_tpg") ||
                    data_input.BasicData.get(index).disc_member != get_result_int ("disc_member")||
                    data_input.BasicData.get(index).nama_barang != get_result_String("nama_barang")||
                    data_input.BasicData.get(index).kategori != get_result_String ("kategori"))){
                UpdKatalog constructor = new UpdKatalog();
                constructor.kode_barang = data_input.BasicData.get(index).kode_barang;
                constructor.harga_tpg_new = data_input.BasicData.get(index).harga_tpg;
                constructor.harga_tpg_old = get_result_int("harga_tpg");
                constructor.update_date_new = new_catalog_date;
                constructor.update_date_old = old_catalog_date;
                constructor.disc_member = data_input.BasicData.get(index).disc_member;
                constructor.nama_barang = data_input.BasicData.get(index).nama_barang;
                constructor.kategori = data_input.BasicData.get(index).kategori;
                updKatalogData.add(constructor);
            }
        } else {
            ItemsData constructor = new ItemsData();
            constructor.kode_barang = data_input.BasicData.get(index).kode_barang;
            constructor.nama_barang = data_input.BasicData.get(index).nama_barang;
            constructor.kategori = data_input.BasicData.get(index).kategori;
            constructor.harga_tpg = data_input.BasicData.get(index).harga_tpg;
            constructor.disc_member = data_input.BasicData.get(index).disc_member;
            newKatalogData.add(constructor);
        }
    }
    
    // method to update data catalog
    public void UpdateKatalog(ArrayList<UpdKatalog> updated_updKatalogData) throws Exception{
        try{
            for (int i = 0; i < updated_updKatalogData.size(); i++){
                update_databarang(updated_updKatalogData.get(i));
            }
        } catch (Exception e){
            throw new Exception (e);
        }
    }
    
    public void NewKatalog(Date upd_date){
        for (int i = 0; i < newKatalogData.size(); i++){
            if (!newKatalogData.get(i).kode_barang.isEmpty())
                new_databarang(newKatalogData.get(i),upd_date);
        }
    }
    
    // method to add new order
    public void AddOrder (OrderData order_input){
        int available_stok;
        // check if stok still available
        get_databarang(order_input.kode_barang);
        if(next_data()){
            available_stok = get_result_int("stok");
            if (available_stok == 0){ // if stok empty, set to unavailable
                order_input.status_order = 2;
                add_dborder(order_input);
            }else if(available_stok >= order_input.jumlah_barang){ // if stok enough, set to available
                order_input.status_order = 1;
                add_dborder(order_input);
            } else if(available_stok < order_input.jumlah_barang){ // if stok not enough, split order to available and not available
                // ke pusat = order ke pusat, order input = barang available
                OrderData ke_pusat = new OrderData(order_input);
                ke_pusat.kode_order += "_ex";
                ke_pusat.jumlah_barang = order_input.jumlah_barang - available_stok;
                ke_pusat.status_order = 2;
                order_input.jumlah_barang = available_stok;
                order_input.status_order = 1;
                add_dborder(ke_pusat);
                add_dborder(order_input);
            }
        } else {
            System.out.println("Kode barang tidak tersedia di katalog");
        }
    }
    
    // method to print the available, unavailable, and not found - set them to search in storage and order ke pusat
    public void PrintOrderAvailable(){
        CariGudang = new ArrayList<OrderData>();
        get_available_order();
        while(next_data()){
            OrderData constructor = new OrderData();
            constructor.kode_order      = get_result_String("kode_order");
            constructor.kode_konter     = get_result_String("kode_konter");
            constructor.kode_barang     = get_result_String("kode_barang");
            constructor.jumlah_barang   = get_result_int("jumlah_barang");
            CariGudang.add(constructor);
        }
        change_all_order_status(3, 1);
        
    }      
    
    public void PrintOrderUnavailable (){
        OrderPusat = new ArrayList<OrderData>();
        get_unavailable_order();
        while (next_data()){
            OrderData constructor = new OrderData();
            constructor.kode_barang     = get_result_String("kode_barang");
            constructor.jumlah_barang   = get_result_int("jumlah_order");
            OrderPusat.add(constructor);
        }
        change_all_order_status(4, 2);
        change_all_order_status(4, 5);
    }
    
    // method to set not found order that must be search in the storage
    public void NotFound(OrderData order_input)  throws Exception{
        // look for kode_order if its exist and must be in status cari gudang
        get_dataorder(order_input.kode_order);
        if(next_data()){
            if(get_result_int("status_order") == 3){
                // if exist and the status is cari gudang, check the number not found 
                if (order_input.jumlah_barang > get_result_int("jumlah_barang")){ // not found more than the order itself, error
                    System.out.println("Error : jumlah barang yang tidak ditemukan melebihi barang yang harus dicari");
                } else if (order_input.jumlah_barang == get_result_int("jumlah_barang")){ // not found exactly the order itself
                    // set order to not found
                    change_order_status(order_input,5);
                    // update stok
                    stok_update_inc(order_input.kode_barang, order_input.jumlah_barang);
                } else if (order_input.jumlah_barang < get_result_int("jumlah_barang")){ // partly found
                    // fill other order information
                    order_input.kode_konter = get_result_String("kode_konter");
                    order_input.tanggal_masuk = get_result_Date("tgl_masuk");
                    order_input.kode_barang = get_result_String("kode_barang");
                    // change jumlah barang in old order
                    change_jumlah_barang(order_input.kode_order,get_result_int("jumlah_barang") - order_input.jumlah_barang);
                    order_input.kode_order = order_input.kode_order + "_nf";
                    order_input.status_order= 5;
                    // create new order with status order 5
                    add_dborder(order_input);
                    stok_update_inc(order_input.kode_barang, order_input.jumlah_barang);
                }
            } else {
                throw new Exception ("Order tidak dalam status cari gudang");
//                System.out.println("Error : Order tidak dalam status cari gudang");
            } 
        } else {
            throw new Exception ("Order tidak ditemukan");
//            System.out. println("Error : Order tidak ditemukan");
        }
    }
    
    // method to get morning stok
    public void MorningStock(StockData stok_input){
        //check if stok input already in data_barang
        get_databarang(stok_input.kode_barang);
        if(next_data()){
            // add the stok for the kode_barang
            stok_update_inc(stok_input.kode_barang, stok_input.jumlah_barang);
        } else {
            System.out.println("Kode barang " + stok_input.kode_barang +  " tidak ada di data");
        }
    }
    
    // method to check if yesterday order already came
    public void FindOrderPusat(){
        AllOrderPusat = new ArrayList<OrderData>();
        // copy data order with status order 4 to check with new stok
        get_dataorder(4);
        while (next_data()){
            OrderData construct = new OrderData();
            construct.kode_order    = get_result_String("kode_order");
            construct.kode_konter   = get_result_String("kode_konter");
            construct.kode_barang   = get_result_String("kode_barang");
            construct.status_order  = get_result_int("status_order");
            construct.jumlah_barang = get_result_int("jumlah_barang");
            construct.tanggal_masuk = get_result_Date("tgl_masuk");
            AllOrderPusat.add(construct);
        }
    }
    
    public void IfOrderComing (ArrayList<StockData> updStockData){
        ComingOrder = new ArrayList<OrderData>();
        for (int i = 0; i < updStockData.size(); i++){
            ArrayList<dataOrderWL> orderWLcomingorder = new ArrayList<>();
            for (int j = 0; j < AllOrderPusat.size(); j ++){
                if (AllOrderPusat.get(j).kode_barang.trim().equals(updStockData.get(i).kode_barang.trim())){
                    OrderData constructor = new OrderData();
                    constructor.kode_barang = AllOrderPusat.get(j).kode_barang;
                    constructor.kode_konter = AllOrderPusat.get(j).kode_konter;
                    constructor.kode_order = AllOrderPusat.get(j).kode_order;
                    constructor.jumlah_barang = AllOrderPusat.get(j).jumlah_barang;
                    constructor.status_order = AllOrderPusat.get(j).status_order;
                    constructor.tanggal_masuk = AllOrderPusat.get(j).tanggal_masuk;
                    int datefound = 0;
                    for (int k = 0; k < orderWLcomingorder.size(); k++){
                        if (orderWLcomingorder.get(k).dategroup.equals(AllOrderPusat.get(j).tanggal_masuk)){
                            orderWLcomingorder.get(k).dataorder.add(constructor);
                            datefound = 1;
                            break;
                        }
                    }
                    if (datefound == 0){
                        dataOrderWL newgroup = new dataOrderWL();
                        newgroup.dataorder = new ArrayList<>();
                        newgroup.dategroup = (Date) AllOrderPusat.get(j).tanggal_masuk.clone();
                        newgroup.dataorder.add(constructor);
                        orderWLcomingorder.add(newgroup);
                    }
                    datefound = 0;
                }
            }
            
            for (int j = 0; j < orderWLcomingorder.size(); j ++){
                while (orderWLcomingorder.get(j).dataorder.size() != 0){
                    Random rand = new Random();
                    int getorder = rand.nextInt(orderWLcomingorder.get(j).dataorder.size());
                    int sent_item = (updStockData.get(i).jumlah_barang < orderWLcomingorder.get(j).dataorder.get(getorder).jumlah_barang) ? updStockData.get(i).jumlah_barang : orderWLcomingorder.get(j).dataorder.get(getorder).jumlah_barang;
                    int ncom_item = orderWLcomingorder.get(j).dataorder.get(getorder).jumlah_barang - sent_item;
                    updStockData.get(i).jumlah_barang -= sent_item;
                    change_order_status (orderWLcomingorder.get(j).dataorder.get(getorder),6);
                    change_jumlah_barang(orderWLcomingorder.get(j).dataorder.get(getorder).kode_order,sent_item);
                    orderWLcomingorder.get(j).dataorder.get(getorder).jumlah_barang = sent_item;
                    ComingOrder.add(orderWLcomingorder.get(j).dataorder.get(getorder));
                    if(ncom_item != 0){
                        OrderData notcoming = new OrderData(orderWLcomingorder.get(j).dataorder.get(getorder));
                        notcoming.kode_order += "_ncom";
                        notcoming.jumlah_barang = ncom_item;
                        add_dborder(notcoming);
                    }
                    orderWLcomingorder.get(j).dataorder.remove(getorder);
                    if (updStockData.get(i).jumlah_barang == 0) break;
                }
                if (updStockData.get(i).jumlah_barang == 0) break;
            }
        }
        /*
        ComingOrder = new ArrayList<OrderData>();
        for (int i = 0; i < AllOrderPusat.size();i++){
            for (int j = 0; j < updStockData.size(); j++){
                if (AllOrderPusat.get(i).kode_barang.equals(updStockData.get(j).kode_barang)){
                    int sent_item = (updStockData.get(j).jumlah_barang < AllOrderPusat.get(i).jumlah_barang) ? updStockData.get(j).jumlah_barang : AllOrderPusat.get(i).jumlah_barang;
                    int ncom_item = AllOrderPusat.get(i).jumlah_barang - sent_item;
                    updStockData.get(j).jumlah_barang -= sent_item;
                    change_order_status (AllOrderPusat.get(i),6);
                    change_jumlah_barang(AllOrderPusat.get(i).kode_order,sent_item);
                    AllOrderPusat.get(i).jumlah_barang = sent_item;
                    ComingOrder.add(AllOrderPusat.get(i));
                    if(ncom_item != 0){
                        OrderData notcoming = new OrderData(AllOrderPusat.get(i));
                        notcoming.kode_order += "_ncom";
                        notcoming.jumlah_barang = ncom_item;
                        add_dborder(notcoming);
                    }
                    break;
                }
            }
        }
        */
    }
    
    // method to check item ready to be delivered
    public void GetOrderReady(String kode_konter){
        ItemsReady = new ArrayList<ItemReadyData>();
        items_ready(kode_konter);
        while (next_data()){
            ItemReadyData constructor = new ItemReadyData();
            constructor.kode_order      = get_result_String("kode_order");
            constructor.kode_konter     = get_result_String("kode_konter");
            constructor.kode_barang     = get_result_String("kode_barang");
            constructor.nama_barang     = get_result_String("nama_barang");
            constructor.jumlah_barang   = get_result_int("jumlah_barang");
            constructor.kategori        = get_result_String("kategori");
            constructor.harga_tpg       = get_result_int("harga_tpg");
            constructor.disc_member     = get_result_int("disc_member");
            ItemsReady.add(constructor);
        }
    }
    
    // method to add the transaction when item send to counter
    public void CounterGetItem (String kode_konter, int nominal, Date tanggal_transaksi){
        // get the transaction data, add on tabel transasksi
        add_transaksi(kode_konter, nominal, tanggal_transaksi);
        update_hutang(kode_konter, nominal);
    }
    
    // method to add the transaction when item counter make payment
    public void CounterPayment(String kode_konter,int nominal, Date tanggal_transaksi){
        // get the transaction data, add on table transaksi
        add_transaksi(kode_konter, -nominal, tanggal_transaksi);
        update_hutang(kode_konter, -nominal);
    }
    
    // method to delete order
    public void DeleteOrder (String kode_order){
        delete_row("dataorder", kode_order);
    }
    
    // method to get debt of the counter
    public void get_debt_all_counter(){
        data_debt = new ArrayList<TransactionData>();
        count_debt();
    }
    
    // method to get the complete DataBarang Table from kode_barang
    public void RetrieveDataBarang(String kode_barang){
        ViewDataBarang = new ItemsData();
        get_databarang(kode_barang);
        next_data();
        ViewDataBarang.kode_barang = kode_barang;
        ViewDataBarang.nama_barang = get_result_String("nama_barang");
        ViewDataBarang.kategori = get_result_String("kategori");
        ViewDataBarang.harga_tpg = get_result_int("harga_tpg");
        ViewDataBarang.disc_member = get_result_int("disc_member");
        ViewDataBarang.stock = get_result_int("stok");
        
    }


    // F. POPULARITY COUNT
    // Function to add transaction to popular table
    public void PopCount(OrderData input){
        add_popular(input.kode_barang, input.kode_konter, input.jumlah_barang, input.tanggal_masuk);
    }
    
    public void PopView(Date start, Date finish){
        get_popular(start,finish);
        ViewPopularData = new ArrayList<>();
        while (next_data()){
            PopulerData constructor = new PopulerData();
            constructor.kode_barang     = get_result_String("kode_barang");
            constructor.jumlah_order    = get_result_int ("JumlahOrder");
            constructor.jumlah_konter   = get_result_int ("JumlahKonter");
            ViewPopularData.add(constructor);
        }
        for (int i = 0; i < ViewPopularData.size(); i ++){
            get_databarang(ViewPopularData.get(i).kode_barang);
            next_data();
            ViewPopularData.get(i).stock = get_result_int("stok");
        }
        
    }
    
    public void MutasiView(String kode_konter, Date Start, Date End){
        Mutation = new ArrayList();
        get_mutasi (kode_konter, Start, End);
        while(next_data()){
            TransactionData constructor = new TransactionData();
            constructor.nominal = get_result_int("nominal_transaksi");
            constructor.tanggal_transaksi = get_result_Date("tanggal_transaksi");
            Mutation.add(constructor);
        }
    }
    
    // F. SETTING DATA
    public void UpdateDirectStock(String kode_barang, int new_stock){
        stok_update_direct (kode_barang, new_stock);
    }
    
    public void AddNewKonter (String kode_konter, String nama_konter, int hutang_konter){
        add_konter(kode_konter, nama_konter, hutang_konter);
    }
    
    public void ClearCounter(String kode_konter){
        clear_konter(kode_konter);
    }
    
    // DEADSTYLE
    public void DecrementStockDeadStyle(String kode_barang, int jumlah){
        stok_update_dec(kode_barang, jumlah);
    }
    
    // Default DIR
    public String GetDefDIR(){
        getDIR();
        next_data();
        return get_result_String("value");
    }
    
    public void StoreDefDIR(String path){
        storeDIR(path);
    }
    
    public void change_user_admin(String user_name, String pass){
        clean_admin();
        create_admin(user_name,pass);
    }
    
    public void StoreDurOrderDel(int dur){
        storedurationdelete(dur);
    }
    
    public int GetDurOrderDel(){
        getdurationdelete();
        next_data();
        return get_result_int("nilai");
    }
    
    public void deleteoldorder(int dur){
        delete_old_order(dur);
    }
    
    public int GetNominalTotal(){
        get_all_databarang();
        int TotalNominal = 0;
        while (next_data()){
            TotalNominal += (int) ((double)get_result_int("harga_tpg") * (100 - (double)get_result_int("disc_member")) 
                    / (double) 100 * (double) get_result_int("stok"));            
        }
        return TotalNominal;
    }
    
    
    
}
