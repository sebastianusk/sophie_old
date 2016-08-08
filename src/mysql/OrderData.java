package mysql;
import java.util.Date;
import java.text.*;

/**
 * class to input data from catalog
 * @author Seb
 */
public class OrderData {
    public String       kode_order;
    public String       kode_konter;
    public String       kode_barang;
    public int          jumlah_barang;
    public int          status_order;
    public Date         tanggal_masuk;
    DateFormat          fmt;
    //constructor to init all value
    public OrderData(){
        kode_order          = "";
        kode_konter         = "";
        kode_barang         = "";
        jumlah_barang       = 0;
        status_order        = 0;
        tanggal_masuk       = null;
        fmt                 = new SimpleDateFormat("yyyy-MM-dd");
    }
    public OrderData(OrderData copy){
        kode_order          = copy.kode_order;
        kode_konter         = copy.kode_konter;
        kode_barang         = copy.kode_barang;
        jumlah_barang       = copy.jumlah_barang;
        status_order        = copy.status_order;
        tanggal_masuk       = new Date(copy.tanggal_masuk.getTime());
        fmt                 = new SimpleDateFormat("yyyy-MM-dd");
    }
    public void ParseDate(String dateString){
        try{
            tanggal_masuk = fmt.parse(dateString);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
}
