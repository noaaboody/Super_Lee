package Frontend.MVC.Controller.Supplier;

import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Model.Supplier.SupItemModel;
import Frontend.MVC.View.Supplier.SupItemView;
import Frontend.MVC.View.Supplier.SupMainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupItemController {
	private SupItemView view;
	private SupItemModel model;
	private SupplierService service;
	String worker;

	public SupItemController(SupItemView v, SupplierService s,String worker){
		this.worker=worker;
		this.view = v;
		service = s;
		this.model = new SupItemModel(s);
		view.addAddItemBListener(new AddItemBListener());
		view.addSetItemBListener(new SetItemBListener());
		view.addGetItemBListener(new GetItemBListener());
		view.addBackBListener(new BackBListener());
	}

	private class BackBListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e){
			new SupMainController(new SupMainView(),service,worker);
			view.dispose();
		}
	}

	private class GetItemBListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			int itemId;
			try{
				itemId = Integer.parseInt(view.getIdText());
				String res = model.GetItem(itemId);
				if(res.equals(""))
					view.setTextFields(model.ID, model.name, model.manufacturer);
				 else
					JOptionPane.showMessageDialog(view, "Item id does not exist in the system");
			}
			catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(view, "Item id should contain numbers only", "error",2 );
			}
		}
	}
	private class SetItemBListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			int itemId;
			try{
				itemId = Integer.parseInt(view.getIdText());
				String res = model.SetItem(itemId, view.getNameText(),view.getManufacturerText());
				JOptionPane.showMessageDialog(view, res);
			}
			catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(view, "Item id should contain numbers only", "error",2 );
			}
		}
	}
	private class AddItemBListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			int itemId;
			try {
				itemId = Integer.parseInt(view.getIdText());
				if (model.GetItem(itemId).equals("")) {
					JOptionPane.showMessageDialog(view, "Item already exist in the system");
				} else {
					JOptionPane.showMessageDialog(view, model.AddItem(itemId, view.getNameText(), view.getManufacturerText()));
				}
			}
			catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(view, "Item id should contain numbers only", "error",2 );
			}
		}
	}
}
