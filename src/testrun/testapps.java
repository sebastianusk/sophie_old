/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testrun;
import application.apps;
import application.*;
import java.util.Date;
import java.text.*;
import java.util.ArrayList;
import stringedit.ViewListData;

/**
 *
 * @author Seb
 */
public class testapps {
    public static void main (String args[]){
        apps coba2;
        try{
            coba2 = new apps();
        
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
        // coba2.GetItemList();
        /*
        // UPDATE KATALOG
        coba2.ReadDBF("D:\\Document\\Dropbox\\sophie\\DB\\DBF KATALOG SUNDAY 151109.dbf", "2015-12-01");
        coba2.CheckDatabarang();
        coba2.XLSUpdateDatabarang();
        coba2.UpdateDatabarang();
        // FIX AND TESTED
        */
        // ADD ORDER
//        try{
//            coba2.CreateOrder("A0664", 1, 5, sdf.parse("2015-12-30"));
//            coba2.CreateOrder("A2101", 1, 5, sdf.parse("2015-12-30"));
//            coba2.CreateOrder("E583B5", 1, 5, sdf.parse("2015-12-30"));
//        } catch (Exception e){
//            System.out.println("Error : " + e);
//        }
//        
//        coba2.OrderCheckStock();

//        coba2.SplitPrint();
//        
//        for(int i = 0; i < coba2.CariGudang.size(); i++){
//            System.out.println(coba2.CariGudang.get(i).kode_order);
//            System.out.println(coba2.CariGudang.get(i).kode_konter);
//            System.out.println(coba2.CariGudang.get(i).kode_barang);
//            System.out.println(coba2.CariGudang.get(i).jumlah_barang);
//        }
//        System.out.println();
//        for(int i = 0; i < coba2.OrderPusat.size(); i++){
//            System.out.println(coba2.OrderPusat.get(i).kode_barang);
//            System.out.println(coba2.OrderPusat.get(i).jumlah_barang);
//        }
//        coba2.NotFoundGudang("10", 3);
        
//        coba2.OpenXLSFile("D:\\Document\\Dropbox\\sophie\\DB\\DaftarPenerimaanBarangBC.xls");
//        coba2.CheckNewStock();
//        coba2.UpdateNewStock();

//        coba2.SendToKonter("1");
//        for (int i = 0; i < coba2.ItemsReady.size(); i++){
//            System.out.println(coba2.ItemsReady.get(i).kode_konter);
//            System.out.println(coba2.ItemsReady.get(i).kode_barang);
//            System.out.println(coba2.ItemsReady.get(i).jumlah_barang);
//        }

            ArrayList<ViewListData> test = new ArrayList<>();
//            ViewListData constructor = new ViewListData();
//            constructor.namaList = "Sepatu";
//            constructor.listKodeBarang = new ArrayList<>();
//            constructor.listKodeBarang.add("K003");
//            constructor.listKodeBarang.add("K004");
//            constructor.listKodeBarang.add("K005");
//            constructor.listKodeBarang.add("K006");
//            constructor.listKodeBarang.add("K007");
//            test.add(constructor);
//            constructor = new ViewListData();
//            constructor.namaList = "Kaus";
//            constructor.listKodeBarang = new ArrayList<>();
//            constructor.listKodeBarang.add("M003");
//            constructor.listKodeBarang.add("M004");
//            constructor.listKodeBarang.add("M005");
//            constructor.listKodeBarang.add("M006");
//            constructor.listKodeBarang.add("M007");
//            test.add(constructor);
//            coba2.storeListView(test);
//            test = coba2.getListView();
            for (int i = 0; i < test.size(); i ++){
                for (int j = 0; j < test.get(i).listKodeBarang.size(); j++){
                    System.out.println("   " + test.get(i).listKodeBarang.get(j));
                }
            }
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
        
    }
}
