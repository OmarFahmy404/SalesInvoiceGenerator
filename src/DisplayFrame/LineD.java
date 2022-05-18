
package DisplayFrame;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LineD extends JDialog{
    private JTextField Iname;
    private JTextField Icount;
    private JTextField Iprice;
    private JLabel InameLab;
    private JLabel IcountLab;
    private JLabel IpriceLab;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public LineD(SIG sig) {
        Iname = new JTextField(20);
        InameLab = new JLabel("Item Name");
        
        Icount = new JTextField(20);
        IcountLab = new JLabel("Item Count");
        
        Iprice = new JTextField(20);
        IpriceLab = new JLabel("Item Price");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("DetialInvoiceOk");
        cancelBtn.setActionCommand("DetialInvoiceCancel");
        
        okBtn.addActionListener(sig.getAction());
        cancelBtn.addActionListener(sig.getAction());
        setLayout(new GridLayout(4, 2));
        
        add(InameLab);
        add(Iname);
        add(IcountLab);
        add(Icount);
        add(IpriceLab);
        add(Iprice);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getIname() {
        return Iname;
    }

    public JTextField getIcount() {
        return Icount;
    }

    public JTextField getIprice() {
        return Iprice;
    }

 
}