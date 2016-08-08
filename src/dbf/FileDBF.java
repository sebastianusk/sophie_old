/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbf;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import stringedit.StringTools;
/**
 *
 * @author Seb
 */
public class FileDBF {
    // PARAMETER
    // Public
    // 1. NewKatalogData    : for export result that have been read
    public KatalogData NewKatalogData;
    StringTools Strings;
    
    // Private
    // 1. table             : for store data dbf
    Table table;
    
    public FileDBF(){
        Strings = new StringTools();
    }
    
    // open file
    public void openfile(String Filename){
        table = new Table(new File(Filename));
        try {
            table.open(IfNonExistent.ERROR);
        } catch (Exception e){
            System.out.println("Failed to read DBF data");
            System.out.println("Error : " + e);
        }
    }
    
    // read file, store result in NewKatalogData
    public void readfile(String KatalogDate){
        NewKatalogData = new KatalogData(KatalogDate);
        
        List<Field> fields = table.getFields();
        
        Iterator<Record> recordIterator = table.recordIterator();
        
        
        while(recordIterator.hasNext())
        {
            Record record = recordIterator.next();
            try{
                byte[] rawValue;
                RawKatalogData constructor = new RawKatalogData();
                rawValue = record.getRawValue(fields.get(1));
                constructor.kode_barang = new String(rawValue).trim();
                if (!constructor.kode_barang.isEmpty()){
                    rawValue = record.getRawValue(fields.get(0));
                    constructor.nama_barang = Strings.FormatPetik(new String(rawValue).trim());
                    rawValue = record.getRawValue(fields.get(7));
                    constructor.kategori = new String(rawValue).trim();
                    rawValue = record.getRawValue(fields.get(2));
                    constructor.harga_tpg = Integer.parseInt(new String(rawValue).trim());
                    rawValue = record.getRawValue(fields.get(4));
                    constructor.disc_member = Integer.parseInt(new String(rawValue).trim());
                    NewKatalogData.BasicData.add(constructor);
                }
            } catch (DbfLibException dbflibException){
                System.out.println("Problem getting raw value");
                dbflibException.printStackTrace();
            }
        }
    }
    
}
