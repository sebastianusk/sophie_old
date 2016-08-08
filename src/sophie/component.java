/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sophie;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author chody
 */
class component {
    private final TableCellRenderer __defaultRenderer;

  public component(TableCellRenderer renderer) {
    __defaultRenderer = renderer;
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
						 boolean isSelected,
						 boolean hasFocus,
						 int row, int column)
  {
    if(value instanceof Component)
      return (Component)value;
    return __defaultRenderer.getTableCellRendererComponent(
	   table, value, isSelected, hasFocus, row, column);
  }
}
