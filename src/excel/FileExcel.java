/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;
import java.util.Date;
import java.text.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import application.CariGudangReportData;
import application.ComingOrderReportData;
import application.MutationReportData;
import java.util.Collections;
import java.util.Comparator;
import mysql.ItemReadyData;
import mysql.KodeNamaKonter;
import mysql.PopulerData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellUtil;
//import static org.apache.xmlbeans.impl.jam.internal.javadoc.JavadocRunner.start;
//import static org.apache.xmlbeans.impl.schema.StscState.start;
import sophie.DeadStyleData;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.
/**
 *
 * @author Sebast
 */

public class FileExcel {
    public ArrayList<StockData> updStockData;
    private ArrayList<UpdKatalog> update_katalog;
    public ArrayList<File> ExcelCariGudang;
    
    DateFormat fmt;
    
    public FileExcel(){
        updStockData = new ArrayList<StockData>();
        update_katalog = new ArrayList<UpdKatalog>();
        fmt = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    public void get_daily_data(String pathfile){
        File excelfile = new File(pathfile);
        updStockData = new ArrayList<>();
        if (excelfile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(excelfile);
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheetAt(0);
                int flagfound;
                for (int i = 10; i < (sheet.getLastRowNum() - 1); i++){
                    if(!sheet.getRow(i).getCell(13).getStringCellValue().isEmpty()){
                        if(Integer.valueOf((int)Math.round(sheet.getRow(i).getCell(18).getNumericCellValue())) != 0){ // if price 0
                            flagfound = 0;
                            for (int j = 0; j < updStockData.size(); j++){ // in case there are many incoming data for same item
                                if(updStockData.get(j).kode_barang.equals(sheet.getRow(i).getCell(13).getStringCellValue())){
                                    updStockData.get(j).jumlah_barang += Integer.valueOf((int)Math.round(sheet.getRow(i).getCell(19).getNumericCellValue()));
                                    flagfound = 1;
                                    break;
                                }
                            }
                            if (flagfound == 0){ // if already found, doesn't need to create new entry
                                StockData constructor = new StockData();
                                constructor.kode_barang     = sheet.getRow(i).getCell(13).getStringCellValue();
                                constructor.jumlah_barang   = Integer.valueOf((int)Math.round(sheet.getRow(i).getCell(19).getNumericCellValue()));
                                updStockData.add(constructor);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("error : " + e);
            }
        } else {
            System.out.println("error file doesn't exist");
        }
    }
    
    public File excel_create_katalog_update(ArrayList<UpdKatalog> newKatalogUpdate){
        if (newKatalogUpdate.size() != 0){
            DateFormat time = new SimpleDateFormat("hhmm");
            String fileName = "KatalogDiff_"+ fmt.format(newKatalogUpdate.get(0).update_date_new) + "_" + time.format(newKatalogUpdate.get(0).update_date_new) + ".xls";
            File ExcelKatalogDiff = new File (fileName);
            HSSFWorkbook workbook   = new HSSFWorkbook();
            HSSFSheet sheet         = workbook.createSheet();
            
            // set page
            sheet.getPrintSetup().setLandscape(true);
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );
            
            // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();
            
            
            
            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL PERUBAHAN HARGA");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,5));
            
            
            // create file info
            header  = sheet.createRow(3);
            cell    = header.createCell(2);
            cell.setCellValue("Tanggal Update : ");
            cell    = header. createCell(3);
            cell.setCellValue(fmt.format(newKatalogUpdate.get(0).update_date_new));
            
            // create the header
            datastyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(6);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Barang");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Harga TPG Lama");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Tanggal Update Lama");
            cell    = header.createCell(4);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Harga TPG Baru");
            cell    = header.createCell(5);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Tanggal Update Baru");
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            
            for (int i = 0; i < newKatalogUpdate.size(); i++){
                header  = sheet.createRow(i + 7);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(newKatalogUpdate.get(i).kode_barang);
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(newKatalogUpdate.get(i).harga_tpg_old);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(fmt.format(newKatalogUpdate.get(i).update_date_old));
                cell    = header.createCell(4);
                cell.setCellStyle(datastyle);
                cell.setCellValue(newKatalogUpdate.get(i).harga_tpg_new);
                cell    = header.createCell(5);
                cell.setCellStyle(datastyle);
                cell.setCellValue(fmt.format(newKatalogUpdate.get(i).update_date_new));
            }
            try{
                // String pathname = "D:\\Document\\Dropbox\\sophie\\DB\\update_" + fmt.format(newKatalogUpdate.get(0).update_date_new) + ".xls";
                FileOutputStream out = new FileOutputStream(ExcelKatalogDiff);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExcelKatalogDiff = null;
            } catch (IOException e) {
                e.printStackTrace();
                ExcelKatalogDiff = null;
            }
            return ExcelKatalogDiff;
        } else
        return null;
    }
    
    public File excel_create_diff_upd_stock(ArrayList <DiffUpdStock> diffinput, Date currtime){
        if (diffinput.size()!= 0){
            DateFormat time = new SimpleDateFormat("hhmm");
            String fileName = "DailyDiff_"+ fmt.format(currtime) + "_" + time.format(currtime) +".xls";
            File ExcelDailyDiff = new File (fileName);
            HSSFWorkbook workbook   = new HSSFWorkbook();
            HSSFSheet sheet         = workbook.createSheet();

            // set page
            sheet.getPrintSetup().setLandscape(false);
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );
            
            // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();
        
            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL UPDATE DAILY STOK");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
        
            // create file info
            header  = sheet.createRow(3);
            cell    = header.createCell(2);
            cell.setCellValue("Tanggal Stock : ");
            cell    = header. createCell(3);
            cell.setCellValue(currtime);
            
            // create the header
            headerstyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(6);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Unedited Kode Barang");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Unedited Jumlah Barang");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Edited Kode Barang");
            cell    = header.createCell(4);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Edited Jumlah Barang");
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            
            for (int i = 0; i < diffinput.size(); i++){
                header     = sheet.createRow(i + 7);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(diffinput.get(i).oriKodeBarang);
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(diffinput.get(i).oriJumlahBarang);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(diffinput.get(i).edtKodeBarang);
                cell    = header.createCell(4);
                cell.setCellStyle(datastyle);
                cell.setCellValue(diffinput.get(i).oriJumlahBarang);
                
            }
            try{
                // String pathname = "D:\\Document\\Dropbox\\sophie\\DB\\update_" + fmt.format(newKatalogUpdate.get(0).update_date_new) + ".xls";
                FileOutputStream out = new FileOutputStream(ExcelDailyDiff);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExcelDailyDiff = null;
            } catch (IOException e) {
                e.printStackTrace();
                ExcelDailyDiff = null;
            }
            return ExcelDailyDiff;
        } 
        return null;
    }
    
    public File excel_create_order_pusat_coming(ArrayList<ComingOrderReportData> comingorder, Date comingorderdate){
        if (comingorder.size() != 0){
            DateFormat time = new SimpleDateFormat("hhmm");
            String fileName = "ComingOrder_" + fmt.format(comingorderdate)+ "_" + time.format(comingorderdate) + ".xls";
            File ExcelComingOrder = new File (fileName);
            HSSFWorkbook workbook   = new HSSFWorkbook();
            HSSFSheet sheet         = workbook.createSheet();
            
            // set page
            sheet.getPrintSetup().setLandscape(false);
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );
            
             // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();
            
            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL COMING ORDER");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,5));
            
            // create file info
            header  = sheet.createRow(3);
            cell    = header.createCell(2);
            cell.setCellValue("Tanggal Stock : ");
            cell    = header. createCell(3);
            cell.setCellValue(fmt.format(comingorderdate));
            
            // create the header
            headerstyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(6);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Konter");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Barang");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Jumlah");
            cell    = header.createCell(4);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Nama Barang");
            cell    = header.createCell(5);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kategori");

            
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            
            // Sorting
            Collections.sort(comingorder, new Comparator<ComingOrderReportData>() {
                    @Override
                    public int compare(ComingOrderReportData data2, ComingOrderReportData data1)
                    {
                        return  data1.nama_barang.compareTo(data2.nama_barang);
                    }
                });
             
            for (int i = 0; i < comingorder.size(); i++){
                header  = sheet.createRow(i + 7);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(comingorder.get(i).kode_konter);
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(comingorder.get(i).kode_barang);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(comingorder.get(i).jumlah_barang);
                cell    = header.createCell(4);
                cell.setCellStyle(datastyle);
                cell.setCellValue(comingorder.get(i).nama_barang);
                cell    = header.createCell(5);
                cell.setCellStyle(datastyle);
                cell.setCellValue(comingorder.get(i).kategori);
                
                
            }
            
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            
            try{
                // String pathname = "D:\\Document\\Dropbox\\sophie\\DB\\update_" + fmt.format(newKatalogUpdate.get(0).update_date_new) + ".xls";
                FileOutputStream out = new FileOutputStream(ExcelComingOrder);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExcelComingOrder = null;
            } catch (IOException e) {
                e.printStackTrace();
                ExcelComingOrder = null;
            }
            return ExcelComingOrder;
        } else {
            return null;
        }
    }
    
    public File excel_create_popular(ArrayList<PopulerData> PopulerItem, Date startdate, Date enddate){
        if (PopulerItem.size() != 0){
            String fileName = "PopularItem_" + fmt.format(startdate) + "-" + fmt.format(enddate) + ".xls";
            File ExcelPopular = new File (fileName);
            HSSFWorkbook workbook   = new HSSFWorkbook();
            HSSFSheet sheet         = workbook.createSheet();
            
            // set page
            sheet.getPrintSetup().setLandscape(false);
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );
            
             // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();
            
            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL ORDER POPULAR");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
            
            // create file info
            header  = sheet.createRow(3);
            cell    = header.createCell(1);
            cell.setCellValue("Tanggal : ");
            cell    = header.createCell(2);
            cell.setCellValue(fmt.format(startdate));
            cell    = header.createCell(3);
            cell.setCellValue(" - ");
            cell    = header.createCell(4);
            cell.setCellValue(fmt.format(enddate));
            
            // create the header
            headerstyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(6);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Barang");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Jumlah Order");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Jumlah Konter");
            cell    = header.createCell(4);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Stok");
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
             
            
            for (int i = 0; i < PopulerItem.size(); i++){
                header  = sheet.createRow(i + 7);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(PopulerItem.get(i).kode_barang);
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(PopulerItem.get(i).jumlah_order);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(PopulerItem.get(i).jumlah_konter);
                cell    = header.createCell(4);
                cell.setCellStyle(datastyle);
                cell.setCellValue(PopulerItem.get(i).stock);
            }
            
            try{
                FileOutputStream out = new FileOutputStream(ExcelPopular);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExcelPopular = null;
            } catch (IOException e) {
                e.printStackTrace();
                ExcelPopular = null;
            }
            return ExcelPopular;
        } else {
            return null;
        }
    }
    
    public File excel_create_kirim_barang(ArrayList<ItemReadyData> ItemsReadyData, Date currentdate){
        if (ItemsReadyData.size() != 0){
            DateFormat time = new SimpleDateFormat("hhmm");
            String fileName = "Kirim_" + fmt.format(currentdate) + "_" + time.format(currentdate) + "_konter_" + ItemsReadyData.get(0).kode_konter + ".xls";
            File ExcelKirimBarang = new File (fileName);
            HSSFWorkbook workbook   = new HSSFWorkbook();
            HSSFSheet sheet         = workbook.createSheet();
            
            // set page
            sheet.getPrintSetup().setLandscape(true);
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );
            
             // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();
            
            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL KIRIM BARANG");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,9));
            
            // create file info
            // create file info
            header  = sheet.createRow(3);
            cell    = header.createCell(2);
            cell.setCellValue("Tanggal : ");
            cell    = header. createCell(3);
            cell.setCellValue(fmt.format(currentdate));
            
            header  = sheet.createRow(4);
            cell    = header.createCell(2);
            cell.setCellValue("Konter : ");
            cell    = header. createCell(3);
            cell.setCellValue(ItemsReadyData.get(0).kode_konter);
            
            // create the header
            headerstyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(7);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Barang");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Jumlah");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Nama Barang");
            cell    = header.createCell(4);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kategori");
            cell    = header.createCell(5);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Harga TPG");
            cell    = header.createCell(6);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Discount");
            cell    = header.createCell(7);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Harga Net");
            cell    = header.createCell(8);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Total TPG");
            cell    = header.createCell(9);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Total Net");

            
                
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            HSSFDataFormat df = workbook.createDataFormat();
            datastyle.setDataFormat(df.getFormat("#,###"));
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            
            double net       = 0;
            double total_tpg = 0;
            double total_net = 0;
            double sum_total_tpg = 0;
            double sum_total_net = 0;
            int j;

            // fill the data
            for (j = 0; j < ItemsReadyData.size(); j ++){
                net       = (double)ItemsReadyData.get(j).harga_tpg * (100.0 - (double) ItemsReadyData.get(j).disc_member) / 100.0;
                total_tpg = (double)ItemsReadyData.get(j).harga_tpg * (double)ItemsReadyData.get(j).jumlah_barang;
                total_net = (double) net * (double)ItemsReadyData.get(j).jumlah_barang;
                sum_total_tpg += total_tpg;
                sum_total_net += total_net;
                
               
                header  = sheet.createRow(8 + j);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(ItemsReadyData.get(j).kode_barang);
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(ItemsReadyData.get(j).jumlah_barang);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(ItemsReadyData.get(j).nama_barang);
                cell    = header.createCell(4);
                cell.setCellStyle(datastyle);
                cell.setCellValue(ItemsReadyData.get(j).kategori);
                cell    = header.createCell(5);
                cell.setCellStyle(datastyle);
                cell.setCellValue(ItemsReadyData.get(j).harga_tpg);
                cell    = header.createCell(6);
                cell.setCellStyle(datastyle);
                cell.setCellValue(ItemsReadyData.get(j).disc_member);
                cell    = header.createCell(7);
                cell.setCellStyle(datastyle);
                cell.setCellValue(net);
                cell    = header.createCell(8);
                cell.setCellStyle(datastyle);
                cell.setCellValue(total_tpg);
                cell    = header.createCell(9);
                cell.setCellStyle(datastyle);
                cell.setCellValue(total_net);
            }
            
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);
            
            datastyle.setFont(boldfont);
            header = sheet.createRow(j + 8);
            cell = header.createCell(7);
            cell.setCellStyle(datastyle);
            cell.setCellValue("TOTAL");
            cell = header.createCell(8);
            cell.setCellStyle(datastyle);
            cell.setCellValue(sum_total_tpg);
            cell = header.createCell(9);
            cell.setCellStyle(datastyle);
            cell.setCellValue(sum_total_net);
             
            try{
                FileOutputStream out = new FileOutputStream(ExcelKirimBarang);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExcelKirimBarang = null;
            } catch (IOException e) {
                e.printStackTrace();
                ExcelKirimBarang = null;
            }
            return ExcelKirimBarang;
        } else {
            return null;
        }
    }
    
    public File excel_create_cari_gudang(ArrayList<CariGudangReportData> CariGudang, Date waktuprint){
        // find number of counter, save in Counter Index
        if (!CariGudang.isEmpty()){
            DateFormat time = new SimpleDateFormat("hhmm");
            String fileName = "CariGudang_" + fmt.format(waktuprint) + "_" + time.format(waktuprint) + ".xls";
            File FileCariGudang = new File (fileName);
                
            HSSFWorkbook workbook;
            HSSFSheet sheet;
                
            workbook = new HSSFWorkbook();
            sheet    = workbook.createSheet();

            // set page
            HSSFPrintSetup ps = sheet.getPrintSetup();
            ps.setLandscape(true);
            ps.setFitHeight((short)1);
            ps.setFitWidth((short)1);
            sheet.setFitToPage(true);
            
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);
            

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );

            // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();

            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL CARI GUDANG");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));

            // create file info
            header  = sheet.createRow(4);
            cell    = header.createCell(1);
            cell.setCellValue("Tanggal : ");
            cell    = header.createCell(2);
            cell.setCellValue(fmt.format(waktuprint));

            header  = sheet.createRow(5);
            cell    = header.createCell(1);
            cell.setCellValue("Jam : ");
            cell    = header.createCell(2);
            cell.setCellValue(time.format(waktuprint));

            // create the header
            headerstyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(7);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Order");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Konter");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Barang");
            cell    = header.createCell(4);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Jumlah");
            cell    = header.createCell(5);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Nama Barang");
            cell    = header.createCell(6);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kategori");
            cell    = header.createCell(7);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("HargaTPG");
            cell    = header.createCell(8);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Disc");
            cell    = header.createCell(9);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Harga Net");
            cell    = header.createCell(10);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Total Net");

            
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            HSSFDataFormat df = workbook.createDataFormat();
            datastyle.setDataFormat(df.getFormat("#,###"));
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            
            int row_num = 0;
            
            double net       = 0;
            double total_tpg = 0;
            double total_net = 0;
            double sum_total_tpg = 0;
            double sum_total_net = 0;
            
            int j;
            for (j = 0; j < CariGudang.size(); j ++){
                net       = (double)CariGudang.get(j).harga_tpg * (100.0 - (double) CariGudang.get(j).disc) / 100.0;
                total_net = (double) net * (double)CariGudang.get(j).jumlah;
                sum_total_tpg += total_tpg;
                sum_total_net += total_net;
                
                header  = sheet.createRow(8 + row_num);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).kode_order);
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).kode_konter);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).kode_barang);
                cell    = header.createCell(4);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).jumlah);
                cell    = header.createCell(5);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).nama_barang);
                cell    = header.createCell(6);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).kategori);
                cell    = header.createCell(7);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).harga_tpg);
                cell    = header.createCell(8);
                cell.setCellStyle(datastyle);
                cell.setCellValue(CariGudang.get(j).disc);
                cell    = header.createCell(9);
                cell.setCellStyle(datastyle);
                cell.setCellValue(net);
                cell    = header.createCell(10);
                cell.setCellStyle(datastyle);
                cell.setCellValue(total_net);
                row_num++;
            }
            
            datastyle.setFont(boldfont);
            header = sheet.createRow(j + 8);
            cell = header.createCell(9);
            cell.setCellStyle(datastyle);
            cell.setCellValue("TOTAL");
            cell = header.createCell(10);
            cell.setCellStyle(datastyle);
            cell.setCellValue(sum_total_net);
            
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);
            sheet.autoSizeColumn(10);
            
            try{
                FileOutputStream out = new FileOutputStream(FileCariGudang);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                FileCariGudang = null;
            } catch (IOException e) {
                e.printStackTrace();
                FileCariGudang = null;
            }
            return FileCariGudang;
        } else {
            return null;
        }
    }
    
    public File excel_create_dead_style(ArrayList<DeadStyleData> DSData, Date currentdate, int discDS){
        if (DSData.size() != 0){
            DateFormat time = new SimpleDateFormat("hhmm");
            String fileName = "DeadStyle_" + fmt.format(currentdate) + "_" + time.format(currentdate) + ".xls";
            File ExcelDeadStyle= new File (fileName);
            HSSFWorkbook workbook   = new HSSFWorkbook();
            HSSFSheet sheet         = workbook.createSheet();
            
            // set page
            sheet.getPrintSetup().setLandscape(false);
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );
            
             // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();
            
            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL DEAD STYLE");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,9));
            
            // create file info
            // create file info
            header  = sheet.createRow(3);
            cell    = header.createCell(2);
            cell.setCellValue("Tanggal : ");
            cell    = header. createCell(3);
            cell.setCellValue(fmt.format(currentdate));
            
            header  = sheet.createRow(4);
            cell    = header.createCell(2);
            cell.setCellValue("Jam : ");
            cell    = header. createCell(3);
            cell.setCellValue(time.format(currentdate));
            
            header  = sheet.createRow(5);
            cell    = header.createCell(2);
            cell.setCellValue("Diskon : ");
            cell    = header. createCell(3);
            cell.setCellValue(discDS);
                     
            // create the header
            headerstyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(7);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kode Barang");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Nama Barang");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Jumlah");
            cell    = header.createCell(4);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Kategori");
            cell    = header.createCell(5);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Harga TPG");
            cell    = header.createCell(6);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Discount");
            cell    = header.createCell(7);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Harga Net");
            cell    = header.createCell(8);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Total TPG");
            cell    = header.createCell(9);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Total Net");
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);
            
                
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            
            double net       = 0;
            double total_tpg = 0;
            double total_net = 0;
            double sum_total_tpg = 0;
            double sum_total_net = 0;
            int j;
            double dsdisc;
            
            // fill the data
            for (j = 0; j < DSData.size(); j ++){
                dsdisc = (DSData.get(j).disc_member == 10) ? 0 : discDS;
                net       = (double)DSData.get(j).harga_tpg * (100.0 - dsdisc) / 100.0;
                total_tpg = (double)DSData.get(j).harga_tpg * (double)DSData.get(j).Jumlah;
                total_net = (double) net * (double)DSData.get(j).Jumlah;
                sum_total_tpg += total_tpg;
                sum_total_net += total_net;
                
                header  = sheet.createRow(8 + j);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(DSData.get(j).kode_barang);
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(DSData.get(j).nama_barang);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(DSData.get(j).Jumlah);
                cell    = header.createCell(4);
                cell.setCellStyle(datastyle);
                cell.setCellValue(DSData.get(j).kategori);
                cell    = header.createCell(5);
                cell.setCellStyle(datastyle);
                cell.setCellValue(DSData.get(j).harga_tpg);
                cell    = header.createCell(6);
                cell.setCellStyle(datastyle);
                cell.setCellValue(DSData.get(j).disc_member);
                cell    = header.createCell(7);
                cell.setCellStyle(datastyle);
                cell.setCellValue(net);
                cell    = header.createCell(8);
                cell.setCellStyle(datastyle);
                cell.setCellValue(total_tpg);
                cell    = header.createCell(9);
                cell.setCellStyle(datastyle);
                cell.setCellValue(total_net);
            } 
            
            datastyle.setFont(boldfont);
            header = sheet.createRow(j + 8);
            cell = header.createCell(7);
            cell.setCellStyle(datastyle);
            cell.setCellValue("TOTAL");
            cell = header.createCell(8);
            cell.setCellStyle(datastyle);
            cell.setCellValue(sum_total_tpg);
            cell = header.createCell(9);
            cell.setCellStyle(datastyle);
            cell.setCellValue(sum_total_net);
             
            try{
                FileOutputStream out = new FileOutputStream(ExcelDeadStyle);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExcelDeadStyle = null;
            } catch (IOException e) {
                e.printStackTrace();
                ExcelDeadStyle = null;
            }
            return ExcelDeadStyle;
        } else {
            return null;
        }
    }
    
    public File excel_mutation_report(ArrayList<MutationReportData> datamutation, KodeNamaKonter konterinfo, Date Start, Date End, Date CurrentDate){
        if (datamutation.size() != 0){
            DateFormat time = new SimpleDateFormat("hhmm");
            String fileName = "Mutation_" + konterinfo.nama_konter + "_" + fmt.format(CurrentDate) + "_" + time.format(CurrentDate) + ".xls";
            File ExcelMutation = new File (fileName);
            HSSFWorkbook workbook   = new HSSFWorkbook();
            HSSFSheet sheet         = workbook.createSheet();
            
            // set page
            sheet.getPrintSetup().setLandscape(false);
            
              //Set Header Information 
            Header headerPage = sheet.getHeader();
            headerPage.setCenter(HeaderFooter.page());
            headerPage.setRight(fileName);

            //Set Footer Information with Page Numbers
            Footer footerPage = sheet.getFooter();
            footerPage.setCenter("Page " + HeaderFooter.page() + " of " + 
            HeaderFooter.numPages() );
            
             // prepare variable to edit the xls
            HSSFRow header;
            HSSFCell cell;
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            HSSFCellStyle headerstyle = workbook.createCellStyle();
            HSSFCellStyle datastyle = workbook.createCellStyle();
            HSSFFont boldfont = workbook.createFont();
            HSSFFont normalfont = workbook.createFont();
            
            // create the title 
            header  = sheet.createRow(1);
            cell    = header.createCell(1);
            boldfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlestyle.setFont(boldfont);
            titlestyle.setAlignment(CellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
            titlestyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
            cell.setCellStyle(titlestyle);
            cell.setCellValue("TABEL MUTATION REPORT");
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,3));
            
            // create file info
            // create file info
            header  = sheet.createRow(3);
            cell    = header.createCell(2);
            cell.setCellValue("Konter : ");
            cell    = header. createCell(3);
            cell.setCellValue(konterinfo.nama_konter);
            
            header  = sheet.createRow(4);
            cell    = header.createCell(2);
            cell.setCellValue("Tanggal : ");
            cell    = header. createCell(3);
            cell.setCellValue(fmt.format(Start));
            cell    = header. createCell(4);
            cell.setCellValue("-");
            cell    = header. createCell(5);
            cell.setCellValue(fmt.format(End));
            
                    
            // create the header
            headerstyle.setFont(boldfont);
            headerstyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerstyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            header  = sheet.createRow(7);
            cell    = header.createCell(1);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Tanggal");
            cell    = header.createCell(2);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Barang Masuk");
            cell    = header.createCell(3);
            cell.setCellStyle(headerstyle);
            cell.setCellValue("Setoran Masuk");
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            
            
                
            normalfont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            datastyle.setFont(normalfont);
            datastyle.setAlignment(CellStyle.ALIGN_RIGHT);
            datastyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            datastyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            
            int i;
            // fill the data
            for ( i = 0; i < datamutation.size(); i++){
                header  = sheet.createRow(8 + i);
                cell    = header.createCell(1);
                cell.setCellStyle(datastyle);
                cell.setCellValue(fmt.format(datamutation.get(i).tanggal_mutasi));
                cell    = header.createCell(2);
                cell.setCellStyle(datastyle);
                cell.setCellValue(datamutation.get(i).barang_keluar);
                cell    = header.createCell(3);
                cell.setCellStyle(datastyle);
                cell.setCellValue(datamutation.get(i).setoran);
            } 
            
            datastyle.setFont(boldfont);
            header = sheet.createRow(i + 10);
            cell = header.createCell(1);
            cell.setCellStyle(datastyle);
            cell.setCellValue("Hutang Konter");
            cell = header.createCell(2);
            cell.setCellStyle(datastyle);
            cell.setCellValue(fmt.format(CurrentDate));
            cell = header.createCell(3);
            cell.setCellStyle(datastyle);
            cell.setCellValue(konterinfo.hutang_konter);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
             
            try{
                FileOutputStream out = new FileOutputStream(ExcelMutation);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExcelMutation = null;
            } catch (IOException e) {
                e.printStackTrace();
                ExcelMutation = null;
            }
            return ExcelMutation;
        } else {
            return null;
        }
    }
    
    public File create_excel_test(){
        File Testing = new File ("ujicoba.xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
            
        // Create header form
        // tanggal
        
        
        HSSFRow header = sheet.createRow(2);
        HSSFCell cell  = header.createCell(3);
        cell.setCellValue("Tanggal Jadi Kerjaan : ");
        sheet.autoSizeColumn(3);
        cell  = header.createCell(4);
        cell.setCellValue(" 10 Februari 2016 ");
        sheet.autoSizeColumn(4);
        
        try{
            FileOutputStream out = new FileOutputStream(Testing);
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Testing = null;
        } catch (IOException e) {
            e.printStackTrace();
            Testing = null;
        }
        return Testing;
    }
}


