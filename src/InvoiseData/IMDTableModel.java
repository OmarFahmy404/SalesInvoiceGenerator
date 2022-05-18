
package InvoiseData;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class IMDTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceMainData> invoices;
    private String[] colus={"No.","Date","Customer","Total"};

    public IMDTableModel(ArrayList<InvoiceMainData> invoices) {
        this.invoices = invoices;
    }
    

    @Override
    public int getRowCount() {
        return invoices.size();
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
        InvoiceMainData IMD=invoices.get(rowIndex);
        switch(columnIndex){
        
            case 0:return IMD.getNum();
            case 1:return IMD.getDate();
            case 2: return IMD.getName();
            case 3: return IMD.getInvTotal();
            default:return "";
        }
    }
    
}
