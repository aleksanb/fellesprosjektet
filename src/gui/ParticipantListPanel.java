package gui;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import db.User;


public class ParticipantListPanel {  
   public static void main(String args[]) {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      User user1 = new User();
      user1.setName("Kathrine Steffensen");
      JList list = new JList(new CheckListItem[] {new CheckListItem(user1)});
      list.setCellRenderer(new CheckListRenderer());
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent event) {
            JList list = (JList) event.getSource();
            
            // Get index of item clicked
            
            int index = list.locationToIndex(event.getPoint());
            CheckListItem item = (CheckListItem)
               list.getModel().getElementAt(index);
            
            // Toggle selected state
            
            item.setSelected(! item.isSelected());
            
            // Repaint cell
            
            list.repaint(list.getCellBounds(index, index));
         }
      });   

      frame.getContentPane().add(new JScrollPane(list));
      frame.pack();
      frame.setVisible(true);
   }
}

class CheckListItem {
   protected User user;
   protected boolean isSelected = false;

   public CheckListItem(User u) {
      this.user = user;
   }

   public boolean isSelected() {
      return isSelected;
   }

   public void setSelected(boolean isSelected) {
      this.isSelected = isSelected;
   }   
}
 // Handles rendering cells in the list using a check box

class CheckListRenderer extends JCheckBox implements ListCellRenderer {
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
      setEnabled(list.isEnabled());
      setSelected(((CheckListItem)value).isSelected());
      setFont(list.getFont());
      setBackground(list.getBackground());
      setForeground(list.getForeground());
      //setText(((CheckListItem)value).getName());		//Need to make CheckListItem a user, or at least make it able to use methods of db.User. Herp
      return this;
   }
} 