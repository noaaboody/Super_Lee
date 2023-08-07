package Frontend.presentationLayer;

import Backend.businessLayer.Suppliers.PaymentConditions;
import Backend.serviceLayer.SupplierService;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * this class is for temp ui interface, please notice each function in the service layer has its own function that iterates  the inputs with cin.
 */
public class SupplierPre {
    SupplierService service;
    Scanner scanner;
    boolean shouldTerminate;
    public SupplierPre(SupplierService service)  {this.service=service; scanner=new Scanner(System.in);shouldTerminate=false;}

    public void run()
    {
        shouldTerminate=false;
        while(!shouldTerminate)
        {
            try {mainMenu();}
            catch (Exception e){
                System.out.println("an exception was made please be aware of you're inputs\n");
                System.out.println(e.getMessage()+"\n");
            }
        }
    }

    public void mainMenu()
    {
        System.out.println("""
                press
                1 to add info
                2 to get Info
                3 to set info
                press -1 to quit""");
        int action =scanner.nextInt();
        switch (action) {
            case (-1) -> this.shouldTerminate = true;
            case (1) -> addFunctionsMenu();
            case (2) -> getFunctionsMenu();
            case (3) -> setFunctionsMenu();
            default -> throw new RuntimeException("not a valid input, please try again");
        }
    }
    //===============================================================================================================
//                    second layer Functions
//===============================================================================================================
    public void addFunctionsMenu()
    {
        System.out.println("""
                press:
                1 to add a Supplier Card
                2 to add a Item To the Systems
                3 to add a Item To a Supplier
                4 to add contact to a supplier
                5 to add a Condition to an agreement
                press -1 to return to main menu
                """);
        int action =scanner.nextInt();
        switch (action){
            case (-1):
                break;
            case (1):
                addSupplierCard();
                break;
            case (2):
                addItemToSystems();
                break;
            case (3):
                addItemToSupplier();
                break;
            case (4):
                addSupplierContact();
                break;
            case (5):
                addCondition();
                break;
            default:
                throw new RuntimeException("not a valid input, please try again");
        }


    }
    public void getFunctionsMenu()
    {
        System.out.println ("""
                press
                1 for getSuppliers
                2 to get a Supplier card
                3 to get the Supplier's Contacts
                4 to get Supplier Serial Num To Item documentation
                5 to getSupplier's Conditions in agreement
                -1 to return to main menu
                """);
        int action =scanner.nextInt();
        switch (action){
            case (-1):
                break;
            case (1):
                System.out.println(service.getSuppliers());
                break;
            case (2):
                getSupplierByID();
                break;
            case (3):
                getSupplierContacts();
                break;
            case (4):
                getSupplierItemToSerialNum();
                break;
            case (5):
                getSupplierConditionsMap();
                break;
            default:
                throw new RuntimeException("not a valid input, please try again");
        }

    }
    public void setFunctionsMenu()
    {
        System.out.println ("""
                press 1 for setSupplierIsMobile
                2 to set a Supplier Bank Account
                3 to set a Contact new Email
                4 to set a Contact new Phone num
                5 to set a Contact new name
                6 to set a Supplier Payment Condition
                7 to set an Item Quantity
                8 to set an Item Price
                press -1 to return to main menu
                """);
        int action =scanner.nextInt();
        switch (action){
            case (-1):
                break;
            case (1):
                setSupplierIsMobile();
                break;
            case (2):
                setSupplierBankAccount();
                break;
            case (3):
                setContactEmail();
                break;
            case (4):
                setContactPhone();
                break;
            case (5):
                setContactName();
                break;
            case (6):
                setSupplierPaymentCondition();
                break;
            case (7):
                setItemQuantity();
                break;
            case (8):
                setItemPrice();
                break;
            default:
                throw new RuntimeException("not a valid input, please try again");
        }
    }



//===============================================================================================================
//                   thirdLayer menu layer Functions
//===============================================================================================================


//________________________________________________________________________________________________________________
//                   add Functions
//________________________________________________________________________________________________________________

    public void addSupplierCard()
    {
        int supplierId;
        String name;
        boolean isMobile;
        boolean isConstant;
        int bankAccount;
        int payment;
        PaymentConditions cond;
        List<DayOfWeek> daysOfSupply= new LinkedList<>();

        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter name");
        name = scanner.next();
        System.out.println("enter if supplier is Mobile true/false");
        isMobile = scanner.nextBoolean();
        System.out.println("enter supplier's Bank account");
        bankAccount = scanner.nextInt();
        System.out.println("enter payment conditions: 0 for flow,  30 for flow 30,  and 60 for flow 60");
        payment = scanner.nextInt();
        cond = switch (payment) {
            case (30) -> PaymentConditions.FLOW30;
            case (60) -> PaymentConditions.FLOW60;
            case (0) -> PaymentConditions.FLOW;
            default -> throw new RuntimeException("please enter a valid payment condition");
        };
        System.out.println("enter if supplier is constant true/false");
        isConstant = scanner.nextBoolean();
        if (isConstant)
        {
            System.out.println("enter days of Supply");
            String day;
            boolean stop=false;
            while (!stop)
            {
                System.out.println("enter days of Supply, -1 to stop");
                day=scanner.next();
                day=day.toLowerCase();
                switch (day) {
                    case ("sunday") -> daysOfSupply.add(DayOfWeek.SUNDAY);
                    case ("monday") -> daysOfSupply.add(DayOfWeek.MONDAY);
                    case ("tuesday") -> daysOfSupply.add(DayOfWeek.TUESDAY);
                    case ("wednesday") -> daysOfSupply.add(DayOfWeek.WEDNESDAY);
                    case ("thursday") -> daysOfSupply.add(DayOfWeek.THURSDAY);
                    case ("friday") -> daysOfSupply.add(DayOfWeek.FRIDAY);
                    case ("saturday") -> daysOfSupply.add(DayOfWeek.SATURDAY);
                    case ("-1") -> stop = true;
                }
            }
        }

        System.out.println(service.addSupplierCard(supplierId,name,isMobile,bankAccount,cond,isConstant,daysOfSupply));

    }
    public void addItemToSystems()
    {
        int itemId;
        String itemName;
        String manufacturer;
        System.out.println("enter ItemId");
        itemId = scanner.nextInt();
        System.out.println("enter item name");
        itemName=scanner.next();
        System.out.println("enter item manufacturer");
        manufacturer= scanner.next();
        System.out.println(service.addItemToSystem(itemId,itemName,manufacturer));
    }
    public void addItemToSupplier()
    {
        int supplierId;
        int itemId;
        int serialNum;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter ItemId");
        itemId = scanner.nextInt();
        System.out.println("enter Serial number");
        serialNum = scanner.nextInt();
        System.out.println("enter price");
        double price = scanner.nextDouble();
        System.out.println("enter quantity");
        int quantity = scanner.nextInt();
        System.out.println(service.addItemToSupplier(supplierId, itemId, serialNum, price, quantity));
    }
    public void addSupplierContact()
    {
        int supplierId;
        int contactId;
        String name;
        String phoneNumber;
        String email;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter contactId");
        contactId = scanner.nextInt();
        System.out.println("enter name");
        name = scanner.next();
        System.out.println("enter phoneNumber");
        phoneNumber= scanner.next();
        System.out.println("enter email");
        email = scanner.next();
        System.out.println(service.addSupplierContact(supplierId,contactId,name,phoneNumber,email));
    }
    public void addCondition()
    {
        int supplierId;
        boolean isForQuantity;
        boolean isForPrice;
        float discountPercentage;
        int threshold;
        List<Integer> itemIDs= new LinkedList<>();
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter if condition is for quantity if products true/false");
        isForQuantity = scanner.nextBoolean();
        System.out.println("enter if the condition is for price of order true/false");
        isForPrice = scanner.nextBoolean();
        System.out.println("enter Discount percentage");
        discountPercentage= scanner.nextFloat();
        System.out.println("enter Discount threshold");
        threshold = scanner.nextInt();
        System.out.println("enter Item ids , press -1 to finish");
        while(true)
        {
            int id= scanner.nextInt();
            if(id==-1) break;
            else itemIDs.add(id);
        }
        System.out.println(service.addCondition(supplierId,itemIDs,isForQuantity,isForPrice,discountPercentage,threshold));
    }

    public void addItemToSupplierAgreement()
    {
        int supplierId;
        int itemId;
        double price;
        int quantity;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter item id");
        itemId = scanner.nextInt();
        System.out.println("enter Price");
        price = scanner.nextDouble();
        System.out.println("enter quantity");
        quantity= scanner.nextInt();
        System.out.println(service.addItemToSupplierAgreement(supplierId,itemId,price,quantity));
    }


    //________________________________________________________________________________________________________________
//                   get Functions
//________________________________________________________________________________________________________________
    public  void getSupplierByID()
    {
        int supplierId;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println(service.getSupplierByID(supplierId));
    }

    public void getSupplierContacts()
    {
        int supplierId;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println(service.getSupplierContacts(supplierId));
    }

    public void getSupplierItemToSerialNum()
    {
        int supplierId;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println(service.getSupplierItemToSerialNum(supplierId));
    }


    public  void getSupplierConditionsMap()
    {
        int supplierId;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();

        System.out.println(service.getSupplierConditionsMap(supplierId));
    }







//________________________________________________________________________________________________________________
//                   set Functions
//________________________________________________________________________________________________________________


    public  void setSupplierIsMobile()
    {
        int supplierId;
        boolean isMobile;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter If supplier is mobile true/false");
        isMobile = scanner.nextBoolean();
        System.out.println(service.setSupplierIsMobile(supplierId,isMobile));
    }

    public  void setSupplierBankAccount()
    {
        int supplierId;
        int bankAccount;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter new bank account");
        bankAccount = scanner.nextInt();
        System.out.println(service.setSupplierBankAccount(supplierId,bankAccount));
    }

    public  void setContactEmail()
    {
        int supplierId;
        int contactId;
        String email;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter contact id");
        contactId = scanner.nextInt();
        System.out.println("enter new contact email");
        email = scanner.next();
        System.out.println(service.setContactEmail(supplierId,contactId,email));

    }

    public  void setContactPhone()
    {
        int supplierId;
        int contactId;
        String phone;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter contact id");
        contactId = scanner.nextInt();
        System.out.println("enter new contact phone num");
        phone = scanner.next();
        System.out.println(service.setContactPhone(supplierId,contactId,phone));
    }

    public  void setContactName()
    {
        int supplierId;
        int contactId;
        String name;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter contact id");
        contactId = scanner.nextInt();
        System.out.println("enter new contact name");
        name = scanner.next();
        System.out.println(service.setContactName(supplierId,contactId,name));
    }

    public  void setSupplierPaymentCondition() {
        int supplierId;
        int payment;
        PaymentConditions cond;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter new payment condition: 0 for flow,  30 for flow 30,  and 60 for flow 60");
        payment = scanner.nextInt();
        cond = switch (payment) {
            case (30) -> PaymentConditions.FLOW30;
            case (60) -> PaymentConditions.FLOW60;
            case (0) -> PaymentConditions.FLOW;
            default -> throw new RuntimeException("please enter a valid payment condition");
        };
        System.out.println(service.setSupplierPaymentCondition(supplierId, cond));

    }

    public  void setItemQuantity()
    {
        int supplierId;
        int itemId;
        int quantity;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter itemId");
        itemId = scanner.nextInt();
        System.out.println("enter quantity");
        quantity = scanner.nextInt();
        System.out.println(service.setItemQuantity(supplierId,itemId,quantity));
    }


    public  void setItemPrice()
    {
        int supplierId;
        int itemId;
        double price;
        System.out.println("enter supplier id");
        supplierId = scanner.nextInt();
        System.out.println("enter itemId");
        itemId = scanner.nextInt();
        System.out.println("enter price");
        price = scanner.nextDouble();
        System.out.println(service.setItemPrice(supplierId,itemId,price));
    }

    public void loadDemoData()
    {
        service.addSupplierCard(1,"eldor",false,1020304050, PaymentConditions.FLOW30,false,new LinkedList<>());
        service.addSupplierCard(2,"omer", true, 99, PaymentConditions.FLOW60,false,new LinkedList<>());

        service.addSupplierContact(1,666666666,"eldor","0526942069","eldor@gmail.com");
        service.addSupplierContact(2,206510828,"omer","0525832377","omer@gmail.com");


        service.addItemToSystem(7824,"chocolate","Nutella");
        service.addItemToSystem(10023,"bread","Berman");
        service.addItemToSystem(1234,"water","Neviot");
        service.addItemToSystem(767576,"toilet paper","Glili");

        service.addItemToSupplier(1,7824,1, 20,200);
        service.addItemToSupplier(1,10023,2, 3, 1000);
        service.addItemToSupplier(1,1234,3, 2.5, 1000);

        service.addItemToSupplier(2,7824,1, 18, 150);
        service.addItemToSupplier(2,10023,2, 3, 1000);
        service.addItemToSupplier(2,767576,3, 2.7, 900);


//        service.addItemToSupplierAgreement(1,7824,27.5,3000);
//        service.addItemToSupplierAgreement(1,10023,30.99,1500);
//        service.addItemToSupplierAgreement(1,1234,12.5,10000);
//
//        service.addItemToSupplierAgreement(2,7824,30.0,4500);
//        service.addItemToSupplierAgreement(2,10023,14.99,1000);
//        service.addItemToSupplierAgreement(2,767576,13.5,250);

        List<Integer> itemList = new LinkedList<>();
        itemList.add(7824);
        itemList.add(10023);

        service.addCondition(1,itemList,true,false,5,40);
        service.addCondition(1,itemList,false,true,7.5f,5000);

        itemList = new LinkedList<>();
        itemList.add(7824);
        itemList.add(767576);

        service.addCondition(2,itemList,true,false,6,50);
    }
}