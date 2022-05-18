
package DisplayFrame;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoD extends JDialog{
    private JTextField customerName;
    private JTextField invoiceDate;
    private JLabel customerNameLab;
    private JLabel invoiceDateLab;
    private JButton okBtn;
    private JButton cancelBtn;
    
    
    public InvoD(SIG sig){
    
        customerNameLab=new JLabel("Customer Name : ");
        customerName=new JTextField(20);
        invoiceDateLab=new JLabel("Invoice Date : ");
        invoiceDate=new JTextField(20);
        okBtn=new JButton("OK");
        cancelBtn=new JButton("Cancel");
        okBtn.setActionCommand("MainInvoiceOk");
        cancelBtn.setActionCommand("MainInvoiceCancel");
        okBtn.addActionListener(sig.getAction());
        cancelBtn.addActionListener(sig.getAction());
        setLayout(new GridLayout(3, 2));
        add(customerNameLab);
        add(customerName);
        add(invoiceDateLab);
        add(invoiceDate);
        add(okBtn);
        add(cancelBtn);
        pack();   
    }

    public JTextField getCustomerName() {
        return customerName;
    }

    public JTextField getInvoiceDate() {
        return invoiceDate;
    }
    
    
}
