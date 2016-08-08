/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sophie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author chody
 */
class MyComponent extends JPanel {
     private JButton button;
    private JCheckBox checkBox;
    private JLabel label1;
    private JLabel label2;

    private boolean state;

    public MyComponent() {
 
        button = new JButton("A");
        checkBox = new JCheckBox("B");
        label1 = new JLabel("1");
        label2 = new JLabel("2");

        this.add(button);
        this.add(checkBox);
        this.add(label1);
        this.add(label2);

        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(button,button.hashCode());
            }

            //@Override
            /*public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }*/
        });
    }
 
    public JCheckBox getCheckBox()
    {
        return checkBox;
    }
 
    public void setCheckBox(JCheckBox checkBox)
    {
        this.checkBox = checkBox;
    }
 
    public void setState(boolean state)
    {
        this.state = state;
        checkBox.setSelected(state);
    }
}
