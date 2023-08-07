package Frontend.MVC.Model.Supplier;

import Backend.businessLayer.Item;
import Backend.serviceLayer.SupplierService;

public class SupItemModel {
	public int ID;
	public String name;
	public String manufacturer;

	private SupplierService service;
	public SupItemModel(SupplierService s){
		this.service = s;
	}

	public String GetItem(int itemId){
		Item i = service.getItemObj(itemId);
		if(i == null) return "Item does not exist";
		this.ID = i.GetId();
		this.name = i.GetName();
		this.manufacturer = i.GetManufacturer();
		return "";
	}

	public String SetItem(int itemId, String name, String manufacturer){
		return service.SetItem(itemId,name,manufacturer);
	}

	public String AddItem(int itemId, String name, String manufacturer){
		return service.addItemToSystem(itemId,name, manufacturer);
	}
}
