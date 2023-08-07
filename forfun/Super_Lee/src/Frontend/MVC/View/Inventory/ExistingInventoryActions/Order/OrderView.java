package Frontend.MVC.View.Inventory.ExistingInventoryActions.Order;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Order.OrderController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderView extends JFrame implements ActionListener {
    private OrderController controller;
    public JButton makeMissingOrder;
    public JButton makePeriodOrder;
    public JButton receiveTodayOrders;
    public JButton getFutureOrders;
    public JButton backButton;

    public OrderView(FactoryModel factoryModel, int invID, String worker) {
        controller = new OrderController(factoryModel,this, invID, worker);

        makeMissingOrder = new JButton();
        makeMissingOrder.setBounds(100,50,250,50);
        makeMissingOrder.addActionListener(controller);
        makeMissingOrder.setText("Make missing order");

        makePeriodOrder = new JButton();
        makePeriodOrder.setBounds(100,110,250,50);
        makePeriodOrder.addActionListener(controller);
        makePeriodOrder.setText("Make period order");

        receiveTodayOrders = new JButton();
        receiveTodayOrders.setBounds(100,170,250,50);
        receiveTodayOrders.addActionListener(controller);
        receiveTodayOrders.setText("Receive today's orders");

        getFutureOrders = new JButton();
        getFutureOrders.setBounds(100,230,250,50);
        getFutureOrders.addActionListener(controller);
        getFutureOrders.setText("Get future orders");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Order actions");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(makeMissingOrder);
        this.add(makePeriodOrder);
        this.add(receiveTodayOrders);
        this.add(getFutureOrders);
        this.add(backButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
