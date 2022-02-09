package work.app.view;

import work.app.contract.ContractDto;
import work.app.contract.ContractService;
import work.app.delivery_statement.DeliveryStatementDto;
import work.app.delivery_statement.DeliveryStatementService;
import work.app.notification.NotificationDto;
import work.app.notification.NotificationService;
import work.app.product.ProductDto;
import work.app.product.ProductService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
    private JPanel productPanel;
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
    private ContractService contractService;
    private ProductService productService;
    private DeliveryStatementService deliveryStatementService;





    public MainFrame(NotificationService notificationService, ContractService contractService,
                     ProductService productService, DeliveryStatementService deliveryStatementService) {
        this.contractService = contractService;
        this.productService = productService;
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
        productBackButton.addActionListener(BACK_TO_MAIN_MENU);
        notificationButton.addActionListener(repaintToPanel(notificationPanel));
        contractButton.addActionListener(repaintToPanel(contractPanel));
        dsButton.addActionListener(repaintToPanel(deliveryShipmentPanel));
        productButton.addActionListener(repaintToPanel(contractPanel));

        saveContractButton.addActionListener(e -> {
            ContractDto contract = new ContractDto();
            contract.setNumber(contractNumber.getText().trim());
            contract.setDateOfConclusion(LocalDate.parse(contractDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE));
            contract.setParentContract(parentContract.getText().trim());
            contract.setPrice(new BigInteger(contractPrice.getText().trim()));
            contract.setNote(contractNote.getText().trim());
            contract.setCompleted(false);
            contractService.saveContract(contract);
        });

        saveProductButton.addActionListener(e -> {
            ProductDto product = new ProductDto();
            product.setName(productName.getText().trim());
            product.setDescription(productDescription.getText().trim());
            productService.saveProduct(product);
        });

        saveNotificationButton.addActionListener(e -> {
            NotificationDto notification = new NotificationDto();
            notification.setContractNumber(notificationContractNumber.getText().trim());
            notification.setDate(LocalDate.parse(notificationDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE));
            notification.setNumber(Integer.valueOf(notificationNumber.getText().trim()));
            notification.setProductName(notificationProductName.getText().trim());
            notification.setProductQuantity(Integer.valueOf(notificationProductQuantity.getText().trim()));
            notificationService.saveNotification(notification);
            JOptionPane.showMessageDialog(panel, "Извещение добавлено!");
        });

        saveDsButton.addActionListener(e -> {
            DeliveryStatementDto deliveryStatement = new DeliveryStatementDto();
            deliveryStatement.setNumber(Integer.valueOf(dsNumber.getText().trim()));
            deliveryStatement.setContractNumber(dsContractNumber.getText().trim());
            deliveryStatement.setPeriod(Short.valueOf(dsPeriod.getText().trim()));
            deliveryStatement.setProductNumber(dsProductNumber.getText().trim());
            deliveryStatement.setScheduledProductQuantity(Integer.valueOf(dsScheduledProductQuantity.getText().trim()));
            deliveryStatement.setNote(dsNote.getText().trim());
            deliveryStatement.setPriceForOneProduct(new BigInteger(dsProductPrice.getText().trim()));
            Map<Month, Integer> scheduledShipment = new HashMap<>();
            scheduledShipment.put(Month.JANUARY, Integer.valueOf(dsJan.getText().trim()));
            scheduledShipment.put(Month.FEBRUARY, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.MARCH, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.APRIL, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.MAY, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.JUNE, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.JULY, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.AUGUST, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.SEPTEMBER, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.OCTOBER, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.NOVEMBER, Integer.valueOf(dsFeb.getText().trim()));
            scheduledShipment.put(Month.DECEMBER, Integer.valueOf(dsFeb.getText().trim()));
            deliveryStatement.setScheduledShipment(scheduledShipment);
            deliveryStatementService.saveDeliveryStatement(deliveryStatement);
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
