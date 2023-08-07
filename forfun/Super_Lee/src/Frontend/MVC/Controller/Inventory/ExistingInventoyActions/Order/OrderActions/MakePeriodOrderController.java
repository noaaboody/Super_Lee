package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Order.OrderActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Order.OrderActions.MakePeriodOrderView;
import Frontend.MVC.View.Inventory.InventoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MakePeriodOrderController implements ActionListener {
    private MakePeriodOrderView view;
    private int invID;
    private FactoryModel factoryModel;
    private String worker;

    public MakePeriodOrderController(FactoryModel factoryModel, MakePeriodOrderView view, int invID, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = view;
    }

    public String getSupplierId(){
        return view.supplierIdField.getText();
    }

    public String getItemId1(){
        return view.itemIdField1.getText();
    }

    public String getItemId2(){
        return view.itemIdField2.getText();
    }

    public String getItemId3(){
        return view.itemIdField3.getText();
    }

    public String getItemId4(){
        return view.itemIdField4.getText();
    }

    public String getItemId5(){
        return view.itemIdField5.getText();
    }

    public String getItemId6(){
        return view.itemIdField6.getText();
    }

    public String getItemId7(){
        return view.itemIdField7.getText();
    }

    public String getItemId8(){
        return view.itemIdField8.getText();
    }

    public String getQuantity1(){
        return view.quantityField1.getText();
    }

    public String getQuantity2(){
        return view.quantityField2.getText();
    }

    public String getQuantity3(){
        return view.quantityField3.getText();
    }

    public String getQuantity4(){
        return view.quantityField4.getText();
    }

    public String getQuantity5(){
        return view.quantityField5.getText();
    }

    public String getQuantity6(){
        return view.quantityField6.getText();
    }

    public String getQuantity7(){
        return view.quantityField7.getText();
    }

    public String getQuantity8(){
        return view.quantityField8.getText();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.makeOrderButton){
            Map<Integer, Integer> missingItems = new HashMap<>();
            boolean valid = true;
            String output;
            if(!isNumeric(getSupplierId())){
                valid = false;
            }
            if(!getItemId1().isEmpty() & !getQuantity1().isEmpty()){
                if(isNumeric(getItemId1()) & isNumeric(getQuantity1())){
                    missingItems.put(Integer.parseInt(getItemId1()), Integer.parseInt(getQuantity1()));
                }
                else{
                    valid = false;
                }
            }
            if(!getItemId2().isEmpty() & !getQuantity2().isEmpty()){
                if(isNumeric(getItemId2()) & isNumeric(getQuantity2())){
                    missingItems.put(Integer.parseInt(getItemId2()), Integer.parseInt(getQuantity2()));
                }
                else{
                    valid = false;
                }
            }
            if(!getItemId3().isEmpty() & !getQuantity3().isEmpty()){
                if(isNumeric(getItemId3()) & isNumeric(getQuantity3())){
                    missingItems.put(Integer.parseInt(getItemId3()), Integer.parseInt(getQuantity3()));
                }
                else{
                    valid = false;
                }
            }
            if(!getItemId4().isEmpty() & !getQuantity4().isEmpty()){
                if(isNumeric(getItemId4()) & isNumeric(getQuantity4())){
                    missingItems.put(Integer.parseInt(getItemId4()), Integer.parseInt(getQuantity4()));
                }
                else{
                    valid = false;
                }
            }
            if(!getItemId5().isEmpty() & !getQuantity5().isEmpty()){
                if(isNumeric(getItemId5()) & isNumeric(getQuantity5())){
                    missingItems.put(Integer.parseInt(getItemId5()), Integer.parseInt(getQuantity5()));
                }
                else{
                    valid = false;
                }
            }
            if(!getItemId6().isEmpty() & !getQuantity6().isEmpty()){
                if(isNumeric(getItemId6()) & isNumeric(getQuantity6())){
                    missingItems.put(Integer.parseInt(getItemId6()), Integer.parseInt(getQuantity6()));
                }
                else{
                    valid = false;
                }
            }
            if(!getItemId7().isEmpty() & !getQuantity7().isEmpty()){
                if(isNumeric(getItemId7()) & isNumeric(getQuantity7())){
                    missingItems.put(Integer.parseInt(getItemId7()), Integer.parseInt(getQuantity7()));
                }
                else{
                    valid = false;
                }
            }
            if(!getItemId8().isEmpty() & !getQuantity8().isEmpty()){
                if(isNumeric(getItemId8()) & isNumeric(getQuantity8())){
                    missingItems.put(Integer.parseInt(getItemId8()), Integer.parseInt(getQuantity8()));
                }
                else{
                    valid = false;
                }
            }
            if(valid & !missingItems.isEmpty()){
                output = factoryModel.makePeriodReport(invID, missingItems, Integer.parseInt(getSupplierId()));
            }
            else{
                output = "invalid input";
            }
            view.finish(output, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
