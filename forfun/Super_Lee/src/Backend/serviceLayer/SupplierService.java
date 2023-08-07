package Backend.serviceLayer;

import Backend.businessLayer.Item;
import Backend.businessLayer.Suppliers.PaymentConditions;
import Backend.businessLayer.Suppliers.SupplierAgreement;
import Backend.businessLayer.Suppliers.SupplierCard;
import Backend.businessLayer.Suppliers.SupplierController;

import java.time.DayOfWeek;
import java.util.List;


public class SupplierService {
    SupplierController supplierController;

    public SupplierService(SupplierController suppliers)
    {
        this.supplierController=suppliers;
    }

    /**
     * *this method is used when adding a new supplier to the system
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param isMobile  specifying if the supplier is delivering or should an order be picked up
     * @param bankAccount specifying the suppliers bank account number
     * @param paymentCondition specifying the supplier's payment condition
     * @ return temporary return a string specifying the supplier's id, later returns a json
     * */
    public String addSupplierCard(int supplierId, String name, boolean isMobile, int bankAccount, PaymentConditions paymentCondition, boolean isConstantSupply, List<DayOfWeek> daysOfSupply)
    {
        try {
            return supplierController.addSupplierCard(supplierId,name, isMobile, bankAccount, paymentCondition,isConstantSupply,daysOfSupply);
        }
        catch (Exception e)
        {return e.getMessage();}
    }
    public boolean isValidIdForAdd(int supplierId) {return  !supplierController.isSupplierExists(supplierId);}

    public boolean isValidIdForEdit(int supplierId) {return  supplierController.isSupplierExists(supplierId);}


        /**
         *
         * @param itemId the item id
         * @param itemName the items name
         * @param manufacturer the manufacturer if the item
         * @return string indicator
         */
        public String addItemToSystem(int itemId,String itemName, String manufacturer)
        {
            try{  return supplierController.addItemToSystem(itemId,itemName,manufacturer);}
            catch (Exception e){return e.getMessage();}
        }

        /**
         *
         * @param supplierId the supplier the item needs to be added to
         * @param itemId the item id
         * @param serialNum the unique serialNum for the specific supplier
         * @return string indicator
         */
        public String addItemToSupplier(int supplierId, int itemId, int serialNum, double price, int quantity)
        {
            try{  return supplierController.addItemToSupplier(supplierId, itemId, serialNum, price, quantity);}
            catch (Exception e){return e.getMessage();}
        }


        /**
         *this method is used when adding a new contact to supplier in the system/
         * @param  supplierId the supplier's unique (ח.פ) identifier
         * @param contactId  the contact's id
         * @param name the contact's name
         * @param phoneNumber the contacts phone number
         * @param email the contact's email
         * @ return temporary return a string specifying the contact's id, later returns a json
         */
        public String addSupplierContact(int supplierId, int contactId, String name, String phoneNumber, String email)
        {
            try {
                return supplierController.addSupplierContact(supplierId,contactId,name,phoneNumber,email);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        /**
         *this method adds a condition to the supplier's agreement
         *  @param  supplierId the supplier's unique (ח.פ) identifier
         * @param itemIDs  the item's id that the condition apply on
         * @param isForQuantity determines if the condition is for quantity
         * @param isForPrice determines if the condition is for Price
         * @param discountPercentage the amount of discount that apply per item/bill
         * @ return temporary return a string specifying if the operation succeeded
         */
        public String addCondition(int supplierId, List<Integer> itemIDs, boolean isForQuantity, boolean isForPrice, double discountPercentage, int threshold)
        {
            try {
                return supplierController.addCondition(supplierId,itemIDs,isForQuantity,isForPrice,discountPercentage, threshold);
            }

            catch (Exception e)
            { return e.getMessage(); }
        }

        public String addItemToSupplierAgreement(int supplierId, int itemId, Double price, int quantity)
        {
            try  {return supplierController.addItemToSupplierAgreement(supplierId,itemId,price,quantity);}
            catch (Exception e){return e.getMessage();}
        }



//===============================================================================================================
//                    Set Functions
//===============================================================================================================

        /**
         * changing the mobility field in supplier card
         * @param supplierId the suppliers id
         * @param isMobile indicates if an order should be delivered by the supplier
         * @return string indicating if successful
         */
        public String setSupplierIsMobile(int supplierId,boolean isMobile)
        {
            try {
                return supplierController.setSupplierIsMobile(supplierId,isMobile);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        /**
         * setting the supplier bank account to a new number
         * @param supplierId the suppliers id
         * @param bankAccount the supplier's new bank account number
         * @return string indicator
         */
        public String setSupplierBankAccount(int supplierId,int bankAccount)
        {
            try {
                return supplierController.setSupplierBankAccount(supplierId,bankAccount);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        /**
         *this method changes a contacts email for a specific supplier
         * @param  supplierId the supplier's unique (ח.פ) identifier
         * @param contactId  the contact's id
         * @param email the contact's email
         * @ return temporary return a string specifying if the operation succeeded
         */
        public String setContactEmail(int supplierId,int contactId, String email)
        {
            try {
                return supplierController.setContactEmail(supplierId,contactId,email);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        /**
         *this method changes a contacts phone number for a specific supplier
         * @param  supplierId the supplier's unique (ח.פ) identifier
         * @param contactId  the contact's id
         * @param phoneNum the contact's Phone number
         * @ return temporary return a string specifying if the operation succeeded
         */
        public String setContactPhone(int supplierId,int contactId, String phoneNum)
        {
            try {
                return supplierController.setContactPhone(supplierId,contactId,phoneNum);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        /**
         *this method changes a contacts name for a specific supplier
         * @param  supplierId the supplier's unique (ח.פ) identifier
         * @param contactId  the contact's id
         * @param contactName the contact's name
         * @ return temporary return a string specifying if the operation succeeded
         */
        public String setContactName(int supplierId,int contactId, String contactName)
        {
            try {
                return supplierController.setContactName(supplierId,contactId,contactName);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }


        /**
         *this method changes a supplier payment condition
         * @param  supplierId the supplier's unique (ח.פ) identifier
         * @param cond  the contact's id
         * @ return temporary return a string specifying if the operation succeeded
         */
        public String setSupplierPaymentCondition(int supplierId,PaymentConditions cond)
        {
            try {
                return supplierController.setSupplierPaymentCondition(supplierId,cond);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        /**
         *this method changes the specified item of a supplier guaranteed quantity to supply
         * @param  supplierId the supplier's unique (ח.פ) identifier
         * @param itemId  the item's id
         * @param quantity the new quantity
         * @ return temporary return a string specifying if the operation succeeded
         */
        public String setItemQuantity(int supplierId, int itemId,int quantity)
        {
            try {
                return supplierController.setItemQuantity(supplierId,itemId,quantity);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        /**
         *this method changes the specified item of a supplier guaranteed price to supply
         * @param  supplierId the supplier's unique (ח.פ) identifier
         * @param itemId  the item's id
         * @param price the new price
         * @ return temporary return a string specifying if the operation succeeded
         */
        public String setItemPrice(int supplierId, int itemId,Double price)
        {
            try {
                return supplierController.setItemPrice(supplierId,itemId,price);
            }
            catch (Exception e)
            { return e.getMessage(); }
        }

        public String SetItem(int itemId, String name, String manufacturer){
            try{
                return supplierController.setItem(itemId, name, manufacturer);
            }
            catch (Exception e){
                return e.getMessage();
            }
        }


//===============================================================================================================
//                    Get Functions
//===============================================================================================================

        /**
         *
         * @param supplierId the supplier id
         * @return the supplier card in string
         */
        public String  getSupplierByID(int supplierId)
        {
            try {
                return supplierController.getSupplierByID(supplierId).toString();
            }
            catch (Exception e){return e.getMessage();}
        }
        public SupplierCard  getSupplierByIdObj(int supplierId) {return supplierController.getSupplierByIDObj(supplierId);}

        /**
         *
         * @return all the suppliers in string list
         */
        public String getSuppliers()  {
            try {return supplierController.getSuppliers().toString();}
            catch (Exception e){return  e.getMessage();}
        }

        /**
         *
         * @param supplierId the suppliers id
         * @return All the supplier's contacts in string
         */
        public String getSupplierContacts(int supplierId)
        {
            try {return supplierController.getSupplierContacts(supplierId).toString();}
            catch (Exception e){return e.getMessage();}
        }

        /**
         *
         * @param supplierId the suppliers id
         * @return the supplier map of serial num to item and the item specified
         */
        public String getSupplierItemToSerialNum(int supplierId)
        {
            try {return supplierController.getSupplierSerialNumToItem(supplierId).toString();}
            catch (Exception e){return e.getMessage();}
        }


        /**
         *
         * @param supplierId the supplier's id
         * @return All  the conditions in the agreement mapped out by id as string
         */
        public String getSupplierConditionsMap(int supplierId)
        {try {return supplierController.getSupplierConditionsMap(supplierId).toString();}
        catch (Exception e){return e.getMessage();}

        }


        public SupplierAgreement getFullAgreement(int supplierId) {return supplierController.getSupplierAgreement(supplierId);}
        public List<DayOfWeek> getSupplierAllDays(int supplierId) {return supplierController.getSupplierAllDays(supplierId);}
        public SupplierController getSupplierController(){return supplierController;}

        public Item getItemObj(int itemId){
            return supplierController.getItemObj(itemId);
        }





    }




