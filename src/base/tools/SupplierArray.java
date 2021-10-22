package base.tools;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SupplierArray {
    private ArrayList<Supplier> Suppliers = new ArrayList<Supplier>();
    public void addSupplier(Supplier s){
        Suppliers.add(s);
    }
    public void doAction(){
        for(int i=0;i<Suppliers.size();i++){
            Suppliers.get(i).get();
        }
    }
}
