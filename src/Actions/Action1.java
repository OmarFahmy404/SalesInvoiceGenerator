
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
            DeleteItem();
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
        JFileChooser fc=new JFileChooser();
       int result= fc.showOpenDialog(sig);
        if (result==JFileChooser.APPROVE_OPTION) {
            File f=fc.getSelectedFile();
            Path p=Paths.get(f.getAbsolutePath() );
             List<String> lines = null;
            try {
                 lines=Files.readAllLines(p);
            } catch (IOException ex) {
               ex.getStackTrace();
            }
            ArrayList<InvoiseData.InvoiceMainData> invoiceArray=new ArrayList<>();
            for (String line : lines) {
                String[] splitParts=line.split(",");
                int invoNum=Integer.parseInt(splitParts[0]);
                String invoDate=splitParts[1];
                String name=splitParts[2];
                InvoiceMainData IMD=new InvoiceMainData(invoNum, invoDate, name);
                invoiceArray.add(IMD);}
//                System.out.println("check point");
                
                
                
                 JFileChooser lfc=new JFileChooser();
                  int lresult= lfc.showOpenDialog(sig);
                if (result==JFileChooser.APPROVE_OPTION) {
            File lf=lfc.getSelectedFile();
            Path lp=Paths.get(lf.getAbsolutePath() );
             List<String> llines = null;
            try {
                 llines=Files.readAllLines(lp);
            } catch (IOException ex) {
               ex.getStackTrace();
            }
           // ArrayList<InvoiseData.InvoiceMainData> invoiceArray=new ArrayList<>();
            for (String lline : llines) {
                String[] lsplitParts=lline.split(",");
                int num=Integer.parseInt(lsplitParts[0]);
                String item=lsplitParts[1];
                int count =Integer.parseInt(lsplitParts[3]);
                double price=Integer.parseInt(lsplitParts[2]);
       //                         System.out.println("check point");

                InvoiceMainData inv=null;
                for(InvoiceMainData invoice:invoiceArray){
                    if (invoice.getNum()==num) {
                        inv = invoice;
                        //break;
                    }
                        
                    }
               // System.out.println("check point");
                InvoiceDetials ID=new InvoiceDetials( item, count, price, inv);
                inv.getDetials().add(ID);
               // System.out.println("check point");
                }
                }
                sig.setInvoices(invoiceArray);
                IMDTableModel imdTableModel=new IMDTableModel(invoiceArray);
                sig.setIMDTableModel(imdTableModel);
                sig.getMainInvoice().setModel(imdTableModel);        
                sig.getIMDTableModel().fireTableDataChanged();
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

    private void DeleteItem() {
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
    }

    private void MainInvoiceOk() {
        String Date=invDailog.getInvoiceDate().getText();
        String Name=invDailog.getCustomerName().getText();
        int number=sig.getNextNumOfMI();
        InvoiceMainData invoice= new InvoiceMainData(number, Date, Name);
        sig.getInvoices().add(invoice);
        sig.getIMDTableModel().fireTableDataChanged();
        invDailog.dispose();
        invDailog=null;
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
