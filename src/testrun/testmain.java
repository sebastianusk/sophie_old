/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testrun;
import excel.*;
import java.awt.Desktop;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Seb
 */
public class testmain {
    public static void main (String args[])
    {
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString;
//        sophie_process process = new sophie_process();
//        cataloginput datain = new cataloginput();
//        datain.kode_barang      = "k010";
//        datain.nama_barang      = "kolor";
//        datain.harga_tpg        = 10000;
//        datain.disc_member      = 10;
//        datain.kategori         = "pakaian";
//        datain.parseDate("2015-1-30");
//        process.update_catalog(datain);
        
//        orderinput orderin = new orderinput();
//        orderin.kode_order      = "1";
//        orderin.kode_barang     = "k004";
//        orderin.kode_konter     = 1;
//        orderin.jumlah_barang   = 2;
//        orderin.parseDate("2015-12-5");
//        process.add_order(orderin);
//        
//        orderin.kode_order      = "2";
//        orderin.kode_barang     = "k004";
//        orderin.kode_konter     = 1;
//        orderin.jumlah_barang   = 2;
//        orderin.parseDate("2015-12-5");
//        process.add_order(orderin);
//        
//        orderin.kode_order      = "3";
//        orderin.kode_barang     = "k004";
//        orderin.kode_konter     = 1;
//        orderin.jumlah_barang   = 2;
//        orderin.parseDate("2015-12-5");
//        process.add_order(orderin);
//        
//        orderin.kode_order      = "4";
//        orderin.kode_barang     = "k004";
//        orderin.kode_konter     = 1;
//        orderin.jumlah_barang   = 3;
//        orderin.parseDate("2015-12-5");
//        process.add_order(orderin);



        //process.print_order();

//        orderinput orderin = new orderinput();
//        orderin.kode_order      = "1";
//        orderin.kode_barang     = "k004";
//        orderin.kode_konter     = 1;
//        orderin.jumlah_barang   = 2;
//        orderin.parseDate("2015-12-5");
//        process.notfound(orderin);
//        
//        orderin.kode_order      = "2";
//        orderin.kode_barang     = "k004";
//        orderin.kode_konter     = 1;
//        orderin.jumlah_barang   = 1;
//        orderin.parseDate("2015-12-5");
//        process.notfound(orderin);

//        process.coming_order();
        
//        process.ready_item();
//        System.out.println(process.itemsready.get(1).kode_konter);
//        System.out.println(process.itemsready.get(1).kode_barang);
//        System.out.println(process.itemsready.get(1).jumlah_barang);
        
        
//        DateFormat          fmt = new SimpleDateFormat("yyyy-MM-dd");
//        Date testdate;
//        int kode_konter = 1;
//        int nominal     = 2000000;
//        try {
//            testdate = fmt.parse("2015-12-9");
////            process.sent_item(kode_konter,nominal,testdate);
//            process.counter_payment(kode_konter, nominal, testdate);
//        } catch (Exception e) {System.out.println("error date");}
//        
//        
        

///* DBF MAIN TEST */
//        dbf_access test = new dbf_access();
//        test.connectdbf("D:\\SophieMartin\\TryFrame1\\src\\dbffiles\\DBF KATALOG SUNDAY 150813.dbf");
//        System.out.println(test.input_data.size());
//        System.out.println(test.input_data.get(1).kode_barang);
//        System.out.println(test.input_data.get(1).harga_tpg);
//        System.out.println(test.input_data.get(1).disc_member);
        
  
/* EXCEL FILE TEST */
/* Read update daily data */
//        FileExcel uji_coba = new FileExcel();
//        File excelFile = new File("D:\\Document\\Dropbox\\sophie\\DB\\DaftarPenerimaanBarangBC.xls");
//        //File excelFile = new File("D:\\Documents\\oprek\\Database\\PROGRAM\\DAILY INVOICE REPORT\\150518\\DaftarPenerimaanBarangBC.xls");
//        uji_coba.get_daily_data(excelFile);
//        System.out.println(uji_coba.data_daily.get(103).kode_barang);
//        System.out.println(uji_coba.data_daily.get(103).harga_barang);
//        System.out.println(uji_coba.data_daily.get(103).jumlah_barang);

// Write update katalog data
//        ArrayList<katalog_update> cobaah = new ArrayList<katalog_update>();
//        katalog_update item_1 = new katalog_update();
//        item_1.kode_barang  = "k0001";
//        item_1.HargaLama    = 30000;
//        item_1.HargaLama    = 35000;
//        item_1.parseNewDate("2015-12-5");
//        item_1.parseOldDate("2015-11-5");
//        cobaah.add(item_1);
//        item_1 = null;
//        katalog_update item_2 = new katalog_update();
//        item_2.kode_barang  = "k0002";
//        item_2.HargaLama    = 75000;
//        item_2.HargaLama    = 80000;
//        item_2.parseNewDate("2015-10-5");
//        item_2.parseOldDate("2015-09-5");
//        cobaah.add(item_2);
//        item_2 = null;
//        uji_coba.excel_create_katalog_update(cobaah);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString;  
        
        FileExcel test = new FileExcel();
        if (new File("D:\\cuk.xls").exists()){
            new File ("D:\\cuk.xls").delete();
        }
        
        File choosen = new File("D:\\cuk.xls");
        
        ArrayList<DiffUpdStock> data = new ArrayList();
        DiffUpdStock raw = new DiffUpdStock();
        raw.oriKodeBarang = "UASU";
        raw.edtKodeBarang = "UASU2";
        raw.oriJumlahBarang = 5;
        raw.edtJumlahBarang = 3;
        Date currtime;
        
//        try{
//            currtime = sdf.parse("2016-01-18");
//        } catch (Exception e){
//            System.out.println(e);
//        }
        
//        data.add(raw);
//        
//        
//        File ujicoba = test.excel_create_diff_upd_stock(data,"2016-01-18");
//        ujicoba.renameTo(choosen);
//        
//        try{
//            Desktop.getDesktop().open(choosen);
//        } catch (Exception e){
//            System.out.println(e);
//        }
        
//        File coba = new File("D://SophieProject");
//        coba.mkdirs();
      String contohString = "makan";
      System.out.println(contohString.substring(1,3));
        
        
        
        


    }
}
