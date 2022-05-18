
package InvoiseData;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class IDTabelModel extends AbstractTableModel{
    private ArrayList<InvoiceDetials> detials;
    private String[] colus={"No.","Item Name","Item Price","Count","Item Total"};

    public IDTabelModel(ArrayList<InvoiceDetials> detials) {
        this.detials = detials;
    }

    public ArrayList<InvoiceDetials> getDetials() {
        return detials;
    }
    
    

    @Override
    public int getRowCount() {

        return detials.size();
    }

    @Override
    public int getColumnCount() {

        return colus.length;
    }

    @Override
    public String getColumnName(int column) {
        return colus[column];
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        InvoiceDetials detial=detials.get(rowIndex);
        switch(columnIndex){
        
            case 0:return detial.getInvoice().getNum();
            case 1:return detial.getItem();
            case 2:return detial.getPrice();
            case 3:return detial.getCount();
            case 4:return detial.getDetialsTotal();
            default:return "";
        }
    }
    
}
