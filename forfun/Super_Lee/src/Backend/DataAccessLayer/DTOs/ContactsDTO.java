package Backend.DataAccessLayer.DTOs;

public class ContactsDTO extends DTO{

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private int supplierId;

    public ContactsDTO(int id,String name, String phoneNumber, String email, int supplierId) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.supplierId = supplierId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
