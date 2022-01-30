package work.service.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    private JButton button1;
    private JTextField textField1;
    private JPanel panel;
    private JTextField textField2;
    private JList list1;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel Hello;

    public MainFrame() {
        JFrame jFrame = new JFrame("Окно военпреда");
        jFrame.setContentPane(panel);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setSize(640, 480);
        jFrame.setLocation(456, 456);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
