package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.PopUpController;

import javax.swing.*;

public class PopUpView extends JFrame {
    private JTextArea outputTextArea;
    //private JLabel outputLabel;
    public JButton continueButton;
    private PopUpController controller;

    public PopUpView(String output, String worker){
        controller = new PopUpController(this, worker);

        outputTextArea = new JTextArea(output);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setBounds(30, 100, 400, 200);

//        outputLabel = new JLabel();
//        outputLabel.setBounds(30,100,400,50);
//        outputLabel.setText("<html><div style='text-align: center;'>" + output + "</div></html>");

        continueButton = new JButton();
        continueButton.setBounds(30,300,100,20);
        continueButton.addActionListener(controller);
        continueButton.setText("Continue");

        this.setTitle("Pop-up");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(scrollPane);

        //this.add(outputLabel);
        this.add(continueButton);
    }
}
