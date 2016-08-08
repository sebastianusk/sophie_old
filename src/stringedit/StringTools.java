/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stringedit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;
import mysql.OrderData;


/**
 *
 * @author chody
// */
public class StringTools {
    public String GetDate(File input){    
        String fullname = input.getName();
        String numeric = fullname.replaceAll("[^\\d.]", "");
        if (numeric.equals(".")) return null;
        String StringDate = "20" + numeric.charAt(0) + numeric.charAt(1) + "-"
                        + numeric.charAt(2) + numeric.charAt(3) + "-"
                        + numeric.charAt(4) + numeric.charAt(5);
        return StringDate;
    }
    public String FormatPetik(String input){
        return input.replaceAll("\"", "\'");
    }
    
    public File TXTCreateOrderPusat(ArrayList<OrderData> OrderPusat, Date waktu_order){
        if (OrderPusat.size() != 0){
            DateFormat fmt = new SimpleDateFormat("yy-MM-dd_hhmm");
            DateFormat time = new SimpleDateFormat("hhmm");
            File outputtxt = new File ("Order_pusat_" + fmt.format(waktu_order)+"_" + time.format(waktu_order) + ".txt");
            try{
                PrintWriter out = new PrintWriter(outputtxt,"UTF-8");
                for (int i = 0; i < OrderPusat.size(); i++){
                    out.println(OrderPusat.get(i).kode_barang + " " + OrderPusat.get(i).jumlah_barang);
                }
                out.close();
                return outputtxt;
            } catch (Exception e){
                System.out.println("Error = " + e);
                return null;
            }
        } else {
            return null;
        }
    }
    
    public void createViewList(ArrayList<ViewListData> datalist){
        if (!datalist.isEmpty()){
            File txtviewlist = new File ("Src/");
            if (!txtviewlist.exists()){
                
                txtviewlist.mkdirs();
            }
            txtviewlist = new File ("Src/ViewList.txt");
            if (txtviewlist.exists()) txtviewlist.delete();
            txtviewlist = new File ("Src/ViewList.txt");
            try{
                PrintWriter out = new PrintWriter(txtviewlist,"UTF-8");
                for (int i = 0; i < datalist.size(); i ++){
                    out.print(datalist.get(i).namaList + " ");
                    for (int j = 0; j < datalist.get(i).listKodeBarang.size(); j ++){
                        out.print(datalist.get(i).listKodeBarang.get(j) + " ");
                    }
                    out.println();
                }
                out.close();
            } catch (Exception e){
                System.out.println("Error = " + e);
            }
        }
    }
    
    public ArrayList<ViewListData> getViewList(){
        //Create object of FileReader
        try{
            File txtfile = new File ("Src/ViewList.txt");
            
            if (txtfile.exists()){
                FileReader inputFile = new FileReader(txtfile);
                //Instantiate the BufferedReader Class
                BufferedReader bufferReader = new BufferedReader(inputFile);
                //Variable to hold the one line data
                String line;
                // Arraylist result
                ArrayList<ViewListData> output = new ArrayList<>();
                // Read file line by line and print on the console
                while ((line = bufferReader.readLine()) != null)   {
                    String data[] = line.split(" ");
                    ViewListData constructor = new ViewListData();
                    constructor.namaList = data[0];
                    constructor.listKodeBarang = new ArrayList<>();
                    for (int i = 1; i < data.length; i ++){
                        constructor.listKodeBarang.add(data[i]);
                    }
                    output.add(constructor);
                }
                //Close the buffer reader
                bufferReader.close();
                return output;
            }else
                return null;
        } catch (Exception e){
            System.out.println("Error : " + e);
            return null;
        }
    }
}
