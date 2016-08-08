/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sophie;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author chody
 */
public class List_Kode_Barang extends KeyAdapter{
    JComboBox jComboBox2;
    Vector list;
    String storedtext;
     
    public List_Kode_Barang(JComboBox jComboBox2, Vector list){
        this.jComboBox2 = jComboBox2;
        this.list = list;
    }
     
    @Override
    public void keyTyped(final KeyEvent e){
        EventQueue.invokeLater(new Runnable(){
             
            @SuppressWarnings("unchecked")
            @Override
            public void run(){
                String text = ((JTextField)e.getSource()).getText();
                if (!text.trim().equals(storedtext)){
                    jComboBox2.setModel(new DefaultComboBoxModel(getFilteredList(text)));
                    jComboBox2.setSelectedIndex(-1);
                    ((JTextField) jComboBox2.getEditor().getEditorComponent()).setText(text);
                    jComboBox2.showPopup();
                    storedtext = text;
                }
                
            }
        });
    }
     
    public Vector getFilteredList(String text){
        Vector listResult = new Vector();
        for(int i=0; i < list.size(); i++){
            if(list.get(i).toString().contains(text)){
                listResult.add(list.get(i).toString());
            }
        }
        return listResult;
    }
     
    
}
