/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbf;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;

/**
 *
 * @author Seb
 */
public class KatalogData {
    public ArrayList<RawKatalogData> BasicData;
    public Date DateData;
    DateFormat fmt;
    
    //constructor to init all value
    public KatalogData(String dateString){
        BasicData     = new ArrayList<RawKatalogData>();
        fmt           = new SimpleDateFormat("yyyy-MM-dd");
        DateData      = null;  
        try{
            DateData = fmt.parse(dateString);
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }
}
