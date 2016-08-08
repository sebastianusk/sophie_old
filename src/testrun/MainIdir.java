/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testrun;
import stringedit.StringTools;
import java.io.File;
/**
 *
 * @author chody
 */
public class MainIdir {
    public static void main (String args[])
    {
        StringTools testing = new StringTools();
//        File contoh = new File("C:\\Users\\chody\\Desktop\\DBF KATALOG PROMO 150707.dbf");
//        String hasiltanggal = testing.GetDate(contoh);
//        System.out.println(hasiltanggal);
        String contoh = "aaarrrggg \" babi \" wasuuu";
        System.out.println(testing.FormatPetik(contoh));
    }
}
