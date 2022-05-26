
package Actions;

import DisplayFrame.InvoD;
import DisplayFrame.LineD;
import DisplayFrame.SIG;
import InvoiseData.IDTabelModel;
import InvoiseData.IMDTableModel;
import InvoiseData.InvoiceDetials;
import InvoiseData.InvoiceMainData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Action1 implements ActionListener , ListSelectionListener{
private SIG sig;
InvoD invDailog;
LineD lineDailog;
    public Action1(SIG sig) {
        this.sig=sig;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
        case "Load File":
            LoadFile();
            break;
        case "Save File":
            SaveFile();
            break;
        case "Create New Invoice":
            CreateNewInvoice();
            break;
        case "Delete Invoice":
            DeleteInvoice();
            break;
        case "Create New Item":
            CreateNewItem();
            break;
        case "Delete Item":
           deleteItem();
            break;
        case "MainInvoiceOk":
            MainInvoiceOk();
            break;
        case "MainInvoiceCancel":
            MainInvoiceCancel();
            break;
        case "DetialInvoiceOk":
            DetialInvoiceOk();
            break;
        case "DetialInvoiceCancel":
            DetialInvoiceCancel();
            break;
            
        }
    }
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {

        int index=sig.getMainInvoice().getSelectedRow();
       // System.out.println(index);
       if(index !=-1){
        InvoiceMainData inv=sig.getInvoices().get(index);
        sig.getInvoiseNoLab().setText(""+inv.getNum());
        sig.getInvoiseDateLab().setText(inv.getDate());
        sig.getCustomerNameLab().setText(inv.getName());
        sig.getInvoiseTotalLab().setText(""+inv.getInvTotal());
        IDTabelModel idtm=new IDTabelModel(inv.getDetials());
        sig.getDetailsinvoice().setModel(idtm);
        idtm.fireTableDataChanged();
    }
    }
    
    private void LoadFile() {
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(sig);
            if (result == JFileChooser.APPROVE_OPTION) {
                File hf = fc.getSelectedFile();
                Path hp = Paths.get(hf.getAbsolutePath());
                List<String> hls = Files.readAllLines(hp);
               // System.out.println("Invoices have been read");
               
                ArrayList<InvoiceMainData> invoiceArray = new ArrayList<>();
                for (String hl : hls) {
                    try {
                        String[] parts = hl.split(",");
                        int num = Integer.parseInt(parts[0]);
                        String date = parts[1];
                        String name = parts[2];

                        InvoiceMainData invoice = new InvoiceMainData(num, date, name);
                        invoiceArray.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(sig, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Check point");
                result = fc.showOpenDialog(sig);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lf = fc.getSelectedFile();
                    Path lp = Paths.get(lf.getAbsolutePath());
                    List<String> lls = Files.readAllLines(lp);
                    System.out.println("Lines have been read");
                    for (String ll : lls) {
                        try {
                            String[] lps = ll.split(",");
                            int num = Integer.parseInt(lps[0]);
                            String name = lps[1];
                            double price = Double.parseDouble(lps[2]);
                            int count = Integer.parseInt(lps[3]);
                            InvoiceMainData inv = null;
                            for (InvoiceMainData invoice : invoiceArray) {
                                if (invoice.getNum() == num) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            InvoiceDetials line = new InvoiceDetials(name, count, price, inv);
                            inv.getDetials().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(sig, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                   // System.out.println("Check point");
                }
                sig.setInvoices(invoiceArray);
                IMDTableModel invoicesTableModel = new IMDTableModel(invoiceArray);
                sig.setIMDTableModel(invoicesTableModel);
                sig.getMainInvoice().setModel(invoicesTableModel);
                sig.getIMDTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(sig, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


  
    private void SaveFile() {
        ArrayList<InvoiceMainData> invoices=sig.getInvoices();
        String invMain="";
        String invDetial="";
        
        for(InvoiceMainData invoice:invoices){
        
            String s1=invoice.getAsCSV();
            invMain+=s1;
            invMain+="\n";
            
            for(InvoiceDetials detial:invoice.getDetials()){
                
            String s2=detial.getAsCSV();
            invDetial+=s2;
            invDetial+="\n";
            }
            
          
        }
         try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(sig);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(invMain);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(sig);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(invDetial);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
        
    }

    private void CreateNewInvoice() {
         invDailog=new InvoD(sig);
        invDailog.setVisible(true);
        
    }

    private void DeleteInvoice() {
      int selectedRow = sig.getMainInvoice().getSelectedRow();
      if(selectedRow != -1){
      sig.getInvoices().remove(selectedRow);
      sig.getIMDTableModel().fireTableDataChanged();
      }
        
        
    }

    private void CreateNewItem() {
        lineDailog=new LineD(sig);
        lineDailog.setVisible(true);
    }

    /*private void DeleteItem() {
        int selectedMainRow=sig.getMainInvoice().getSelectedRow();
        int selectedRow=sig.getDetailsinvoice().getSelectedRow();
        if(selectedMainRow != -1 && selectedRow != -1){
        
            InvoiceMainData invoice=sig.getInvoices().get(selectedMainRow);
            invoice.getDetials().remove(selectedRow);
            IDTabelModel invdetaiTM=new IDTabelModel(invoice.getDetials());
            sig.getDetailsinvoice().setModel(invdetaiTM);
            invdetaiTM.fireTableDataChanged();
            sig.getIMDTableModel().fireTableDataChanged();
        }
    }*/
    
    private void deleteItem() {
        int selectedRow = sig.getDetailsinvoice().getSelectedRow();

        if (selectedRow != -1) {
            IDTabelModel linesTableModel = (IDTabelModel) sig.getDetailsinvoice().getModel();
            linesTableModel.getDetials().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            sig.getIMDTableModel().fireTableDataChanged();
        }
    }


    private void MainInvoiceOk() {
   
        String date = invDailog.getInvoiceDate().getText();
        String customer = invDailog.getCustomerName().getText();
        int num = sig.getNextNumOfMI();
        try {
            String[] dateParts = date.split("-");  // 
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(sig, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(sig, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InvoiceMainData invoice = new InvoiceMainData(num, date, customer);
                    sig.getInvoices().add(invoice);
                    sig.getIMDTableModel().fireTableDataChanged();
                    invDailog.setVisible(false);
                    invDailog.dispose();
                    invDailog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(sig, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void MainInvoiceCancel() {
         invDailog.setVisible(false);
         invDailog.dispose();
         invDailog=null;

        
    }

    private void DetialInvoiceOk() {
        String item=lineDailog.getIname().getText();
        int count=Integer.parseInt(lineDailog.getIcount().getText());
        double price=Double.parseDouble(lineDailog.getIprice().getText());
        int selectedRow=sig.getMainInvoice().getSelectedRow();
        if (selectedRow!=-1) {
            InvoiceMainData invoice=sig.getInvoices().get(selectedRow);
            InvoiceDetials detial=new InvoiceDetials( item, count, price, invoice);
            invoice.getDetials().add(detial);
            IDTabelModel iDTabelModel=(IDTabelModel) sig.getDetailsinvoice().getModel();
            //iDTabelModel.getDetials().add(detial);
            iDTabelModel.fireTableDataChanged();
            sig.getIMDTableModel().fireTableDataChanged();
        }
        
        

         lineDailog.setVisible(false);
        lineDailog.dispose();
        lineDailog=null;

    }

    private void DetialInvoiceCancel() {
        lineDailog.setVisible(false);
        lineDailog.dispose();
        lineDailog=null;

        
    }

}
