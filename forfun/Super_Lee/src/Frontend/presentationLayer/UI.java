package Frontend.presentationLayer;

import java.util.Scanner;

public class UI {

    Scanner scanner=new Scanner(System.in);
    InventoryPre invP;
    SupplierPre supP;
    private String worker;

    boolean shouldTerminate;
    public UI(InventoryPre invP, SupplierPre supP, String worker)  {
        shouldTerminate=false;
        this.invP=invP;
        this.supP=supP;
        this.worker = worker;
    }

    public void run()
    {
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
        if(worker.equals("StoreManager")){
            System.out.println("""
                press
                1 to Inventory actions
                2 to Suppliers action
                press -1 to quite""");
            int action =scanner.nextInt();
            switch (action) {
                case (-1) -> this.shouldTerminate = true;
                case (1) -> invP.start();
                case (2) -> supP.run();
                default -> throw new RuntimeException("not a valid input, please try again");
            }
        }
        else if(worker.equals("InventoryWorker")){
            invP.start();
        }
        else if(worker.equals("SupplierWorker")){
            supP.run();
        }
    }
}