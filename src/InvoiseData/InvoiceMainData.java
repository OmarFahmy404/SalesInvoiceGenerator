
package InvoiseData;

import java.util.ArrayList;

public class InvoiceMainData {
    private int num;
    private String date;
    private String name;
    private ArrayList<InvoiceDetials> detials;

    public InvoiceMainData() {
    }

    public InvoiceMainData(int num, String date, String name) {
        this.num = num;
        this.date = date;
        this.name = name;
    }

    public ArrayList<InvoiceDetials> getDetials() {
        if (detials == null) {
            detials=new ArrayList<>();
        }
        return detials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public double getInvTotal(){
        double total=0.0;
        for(InvoiceDetials detial:getDetials()){
            total+=detial.getDetialsTotal();
        }
        return total;
    }
   

    @Override
    public String toString() {
        return "Invoice{" + "num=" + num + ", date=" + date + ", customer=" + name + '}';
    }

      public String getAsCSV() {
        return num + "," + date + "," + name;
    }
  
      
    
}

