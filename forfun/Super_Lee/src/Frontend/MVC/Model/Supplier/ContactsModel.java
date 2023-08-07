package Frontend.MVC.Model.Supplier;


import Backend.businessLayer.Suppliers.Contact;
import Backend.serviceLayer.SupplierService;

import java.util.LinkedList;
import java.util.List;

public class ContactsModel {
    private int id=-1;
    private List<Contact> contacts;
    private SupplierService service;

    public ContactsModel(SupplierService service)
    {
        this.service=service;
    }

    public void setId(int id)
    {
        if(service.isValidIdForEdit(id)) {
            this.id = id;
            this.contacts= service.getSupplierController().getSupplierContacts(id);
        }

        else {
            id=-1;
            contacts= new LinkedList<>();
            }
    }

    public String setContact(int cId, String name, String mail, String phone)
    {
        String res="";
            if(service.isValidIdForEdit(id)) {
               res+= service.setContactName(id,cId,name)+"\n";
               res+= service.setContactEmail(id,cId,mail)+"\n";
               res+= service.setContactPhone(id,cId,phone)+"\n";
            }

        return res;
    }

    public List<Contact> getContacts()
    {return this.contacts;}

    public void addContact(int cId, String name, String mail, String phone)
    {
        String out= service.getSupplierController().addSupplierContact(this.id,cId,name,phone,mail);
        contacts.add(new Contact(cId,name,phone,mail));
    }
}
