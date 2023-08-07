package Backend.businessLayer.Suppliers;

public class Contact {
    private final int id;
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(int id, String name, String phoneNumber, String email){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String GetName(){return this.name;}
    public int GetId(){return this.id;}
    public String GetPhoneNumber(){return this.phoneNumber;}
    public String GetEmail(){return this.email;}
    public void setName(String name) {
        this.name = name;
    }
    public void SetPhoneNumber(String phoneNum){this.phoneNumber = phoneNum;}

    public void SetEmail(String email){this.email = email;}

    public String toString()
    {
        return "\n name: "+name+
                "\n contact id: "+this.id+
                "\n phone: "+phoneNumber+
                "\n email: "+email+".";
    }
}
