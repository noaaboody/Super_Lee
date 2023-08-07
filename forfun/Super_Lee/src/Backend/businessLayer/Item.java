package Backend.businessLayer;

public class Item {
    private  int id;
    private String name;
    private String manufacturer;

    public Item(int id, String name, String manufacturer){
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }



    public int GetId(){
        return this.id;
    }

    public String GetName(){
        return this.name;
    }
    public String GetManufacturer(){return this.manufacturer;}

    public String toString()
    {
        return "\nid: "+this.id+"\n"+
                "name: "+this.name+
                "manufacturer: "+manufacturer;
    }
}

