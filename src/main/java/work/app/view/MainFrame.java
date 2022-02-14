package work.app.view;

import work.app.delivery_statement.DeliveryStatementService;
import work.app.notification.Notification;
import work.app.notification.NotificationService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainFrame {
    private final static String TITLE = "Окно военпреда";

    private JPanel panel;
    private JButton notificationButton;
    private JButton dsButton;
    private JPanel notificationPanel;
    private JTextField notificationNumber;
    private JTextField notificationProductQuantity;
    private JTextField notificationDate;
    private JTextField notificationProductName;
    private JPanel deliveryShipmentPanel;
    private JTextField notificationContractNumber;
    private JButton saveNotificationButton;
    private JPanel cards;
    private JButton productButton;
    private JButton contractButton;
    private JButton tablesButton;
    private JPanel mainButtons;
    private JButton notificationBackButton;
    private JPanel contractPanel;
    private JTextField contractNumber;
    private JTextField contractDate;
    private JTextField parentContract;
    private JButton saveContractButton;
    private JButton contractBackButton;
    private JTextField contractPrice;
    private JTextArea contractNote;
    private JTextField dsNumber;
    private JTextField dsProductNumber;
    private JTextField dsProductPrice;
    private JTextField dsContractNumber;
    private JTextField dsScheduledProductQuantity;
    private JTextArea dsNote;
    private JTextField dsJan;
    private JTextField dsApr;
    private JTextField dsFeb;
    private JTextField dsMar;
    private JTextField dsMay;
    private JTextField dsJun;
    private JTextField dsJul;
    private JTextField dsAug;
    private JTextField dsSep;
    private JTextField dsOct;
    private JTextField dsNov;
    private JTextField dsDec;
    private JTextField dsPeriod;
    private JButton saveDsButton;
    private JButton dsBackButton;
    private JTextField dsContractDate;
    private JTextField productName;
    private JTextArea productDescription;
    private JButton saveProductButton;
    private JButton productBackButton;
    private final ActionListener BACK_TO_MAIN_MENU = e -> {
        cards.removeAll();
        cards.add(mainButtons);
        cards.repaint();
        cards.revalidate();
    };

    private NotificationService notificationService;
    private DeliveryStatementService deliveryStatementService;





    public MainFrame(NotificationService notificationService, DeliveryStatementService deliveryStatementService) {
        this.notificationService = notificationService;
        this.deliveryStatementService = deliveryStatementService;
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
        productBackButton.addActionListener(BACK_TO_MAIN_MENU);
        notificationButton.addActionListener(repaintToPanel(notificationPanel));
        contractButton.addActionListener(repaintToPanel(contractPanel));
        dsButton.addActionListener(repaintToPanel(deliveryShipmentPanel));
        productButton.addActionListener(repaintToPanel(contractPanel));


        saveNotificationButton.addActionListener(e -> {
            Notification notification = new Notification();
            notification.setContractNumber(notificationContractNumber.getText().trim());
            notification.setDate(LocalDate.parse(notificationDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE));
            notification.setNumber(Integer.valueOf(notificationNumber.getText().trim()));
            notification.setProductName(notificationProductName.getText().trim());
            notification.setProductQuantity(Integer.valueOf(notificationProductQuantity.getText().trim()));
            notificationService.saveNotification(notification);
            JOptionPane.showMessageDialog(panel, "Извещение добавлено!");
        });

        saveDsButton.addActionListener(e -> {
//            DeliveryStatement deliveryStatement = new DeliveryStatement();
//            deliveryStatement.setNumber(Integer.valueOf(dsNumber.getText().trim()));
//            deliveryStatement.setContractNumber(dsContractNumber.getText().trim());
//            deliveryStatement.setPeriod(Integer.parseInt(dsPeriod.getText().trim()));
//            deliveryStatement.setProductName(dsProductNumber.getText().trim());
//            deliveryStatement.setContractDate(LocalDate.parse(dsContractDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE));
//            deliveryStatement.setScheduledProductQuantity(Integer.valueOf(dsScheduledProductQuantity.getText().trim()));
//            deliveryStatement.setNote(dsNote.getText().trim());
//            deliveryStatement.setPriceForOneProduct(new BigInteger(dsProductPrice.getText().trim()));
//            Map<Month, Integer> scheduledShipment = new HashMap<>();
//            scheduledShipment.put(Month.JANUARY, Integer.valueOf(dsJan.getText().trim()));
//            scheduledShipment.put(Month.FEBRUARY, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.MARCH, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.APRIL, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.MAY, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.JUNE, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.JULY, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.AUGUST, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.SEPTEMBER, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.OCTOBER, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.NOVEMBER, Integer.valueOf(dsFeb.getText().trim()));
//            scheduledShipment.put(Month.DECEMBER, Integer.valueOf(dsFeb.getText().trim()));
//            deliveryStatement.setScheduledShipment(scheduledShipment);
//            deliveryStatementService.saveDeliveryStatement(deliveryStatement);
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
