
package InvoiseData;

public class InvoiceDetials {
  
    private String item;
    private int count;
    private double price;
    private InvoiceMainData invoice;

    public InvoiceDetials() {
    }

    public InvoiceDetials( String item, int count, double price,InvoiceMainData invoice) {
       
        this.item = item;
        this.count = count;
        this.price = price;
        this.invoice =invoice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

 

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

      
    
   
    public void setCount(int count) {
        this.count = count;
    }
    public double getDetialsTotal(){
    
        return price*count;
    }

    public InvoiceMainData getInvoice() {
        return invoice;
    }

     public String getAsCSV() {
        return invoice.getNum() + "," + item + "," + price + "," + count;
    }
   
}
