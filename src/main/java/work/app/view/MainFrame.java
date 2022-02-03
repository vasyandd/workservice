package work.app.view;

import work.app.notification.NotificationService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    private final static String TITLE = "Окно военпреда";

    private JPanel panel;
    private JButton notificationButton;
    private JButton dsButton;
    private JPanel notificationPanel;
    private JTextField notificationNumber;
    private JTextField notificationQuantity;
    private JTextField notificationDate;
    private JTextField notificationProduct;
    private JPanel deliveryShipmentPanel;
    private JTextField notificationContract;
    private JButton saveNotificationButton;
    private JPanel cards;
    private JButton productButton;
    private JButton contractButton;
    private JButton tablesButton;
    private JPanel buttons;
    private JButton notificationBackButton;
    private JPanel contractPanel;
    private JTextField contractNumber;
    private JTextField contractDate;
    private JTextField parentContract;
    private JButton saveContractButton;
    private JButton contractBackButton;
    private JTextField contractPrice;
    private JTextArea contractNote;
    private JPanel productPanel;
    private JTextField dsNumber;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea textArea1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JButton saveDsButton;
    private JButton dsBackButton;
    private final ActionListener BACK_TO_MAIN_MENU = e -> {
        cards.removeAll();
        cards.add(buttons);
        cards.repaint();
        cards.revalidate();
    };

    private NotificationService notificationService;




    public MainFrame(NotificationService notificationService) {
        this.notificationService = notificationService;
        JFrame jFrame = new JFrame(TITLE);
        jFrame.setContentPane(panel);
        jFrame.setResizable(false);
        jFrame.setLocation(156, 156);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        addButtonListeners();
    }

    private void addButtonListeners() {
        contractBackButton.addActionListener(BACK_TO_MAIN_MENU);
        notificationBackButton.addActionListener(BACK_TO_MAIN_MENU);
        dsBackButton.addActionListener(BACK_TO_MAIN_MENU);
        notificationButton.addActionListener(repaintToPanel(notificationPanel));
        contractButton.addActionListener(repaintToPanel(contractPanel));
        dsButton.addActionListener(repaintToPanel(deliveryShipmentPanel));
        productButton.addActionListener(repaintToPanel(contractPanel));
        saveNotificationButton.addActionListener(e -> {
//                Notification notification = new Notification();
//                notification.setContract(new Contract(notificationContract.getText(), LocalDate.now(), null));
//                notification.setProduct(new Product(notificationProduct.getText()));
//                notification.setNumber(Integer.parseInt(notificationNumber.getText()));
//                notification.setProductQuantity(Integer.parseInt(notificationQuantity.getText()));
//                notification.setDate(LocalDate.parse(notificationDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE));
//                notificationService.saveNotificationButton(notification);
            JOptionPane.showMessageDialog(panel, "Извещение добавлено!");
        });
    }

    private ActionListener repaintToPanel(JPanel panel) {
        return e -> {
            cards.removeAll();
            cards.add(panel);
            cards.repaint();
            cards.revalidate();
        };
    }

}
