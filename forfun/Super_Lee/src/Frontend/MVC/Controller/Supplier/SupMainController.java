package Frontend.MVC.Controller.Supplier;

import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Controller.WelcomeController;
import Frontend.MVC.View.Supplier.ContactsView;
import Frontend.MVC.View.Supplier.SupItemView;
import Frontend.MVC.View.Supplier.SupMainView;
import Frontend.MVC.View.Supplier.SupplierView;
import Frontend.MVC.View.WelcomePageView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupMainController {
	private SupMainView view;
	private SupplierService service;
	String worker;
	//private model;

	public SupMainController(SupMainView v, SupplierService s,String worker){
		this.view = v;
		this.service = s;
		this.worker=worker;

		view.addContactBListener(new ContactBListener());
		view.addSupplierBListener(new SupplierBListener());
		view.addItemBListener(new ItemBListener());
		view.addBackBListener(new BackBListener());
		if (worker.equals("SupplierWorker"))
			view.enableBack();
	}
	private class ContactBListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			new ContactsController(new ContactsView(),service,worker);
			view.dispose();
		}
	}
	private class ItemBListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			new SupItemController(new SupItemView(), service,worker);
			view.dispose();
		}
	}
	private class SupplierBListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			new SupplierControllerFront(new SupplierView(),service,worker);
			view.dispose();
		}
	}
	private class BackBListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			new WelcomeController(new WelcomePageView(), service, worker);
			view.dispose();
		}
	}
}

