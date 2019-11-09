/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Employee;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tables.ProductManager;
import epicpos.Main;
import java.sql.SQLException;
import java.text.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import posexample.AutoCompleteComboBoxListener;
import beans.Product;
import beans.ProductsInTransaction;
import beans.Receipts;
import beans.SaleItems;
import javafx.scene.control.cell.PropertyValueFactory;
import com.sun.prism.impl.Disposer.Record;
import database.ConnectionManager;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.ProgressDialog;
import tables.ReceiptsManager;
import tables.SaleItemsManager;

//
///**
// *
// * @author KwabenaEpic
// */
public class PosClientController implements Initializable {
//

    private Connection conn = null;

    private StringBuilder strBuild;

    private Button comma;
    private TextField calcTxtField;
    private Integer employeeId;

    private ComboBox<String> searchField;

    private AutoCompleteComboBoxListener aux;

    private ObservableList<String> comboBoxData = FXCollections.observableArrayList();
//    ObservableList<String> entries = FXCollections.observableArrayList();
    @FXML
    private TableView<Product> table;
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Integer> itemIdColumn;
    private TableColumn<Product, Boolean> actionCol;

    private ObservableList<Product> data, tableItems;

    private ObservableList<String> entries;
//    final ObservableList<Product> tableContent = FXCollections.observableArrayList();
    private String quantity;
    private int currentTicket;
    private Double qty = 0.0;
    private Label subTotal;
    @FXML
    private Label dateLocal;
    @FXML
    private Label subTotalLbl;
    @FXML
    private Label taxLbl;
    private Label totalLbl;
    @FXML
    private Label ticketNumberLbl;

    private Main application;
    private static ObservableList<Employee> staff;
    @FXML
    private Label staffNameLbl;
    @FXML
    private TableColumn<Record, Boolean> actionColumn;
    private TextField tfCashPaid;
    @FXML
    private Label lblBalance;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDiscount;
    @FXML
    private Button btnHoldSale;
    @FXML
    private Label lblDiscount;
    @FXML
    private Label lblTotal;
    @FXML
    private CustomTextField tfFieldSearch;

    HashMap<Integer, Double> saleItems = new HashMap();
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private Label lblHour;
    @FXML
    private Label dateLocal1;
    @FXML
    private Label lblMinutes;
    @FXML
    private Label dateLocal11;
    @FXML
    private Label lblSeconds;
    @FXML
    private ImageView ivUserImage;
    @FXML
    private Button btnCheckout;

    ProductsInTransaction receiptsBean = new ProductsInTransaction();
    private InetAddress ip;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnTenderCash;
    private String tenderType;
    @FXML
    private Label lblAmountPaid;
    @FXML
    private Button btnTenderCard;

    public void setStaff(ObservableList<Employee> staff) {
        this.staff = staff;
    }

    public ObservableList<Employee> getStaff() {
        return staff;
    }
    SimpleDoubleProperty getBalance = new SimpleDoubleProperty();

    private Stage lostConnecttion = null;
    private Alert alert = null;
    Task copyWorker;
    ProgressDialog dialog = null;
    private BigDecimal amountPaid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        Boolean status = ConnectionManager.getInstance().getConnectionStatus();
                        if (status == false) {
//                           application.gotoProgressForm();
                            isValidCondition();
                            System.out.println("Connection Lost");
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        //don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();

        try {
            this.ip = InetAddress.getLocalHost();
//            System.out.println(ip.getHostName());
        } catch (UnknownHostException ex) {
            Logger.getLogger(PosClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentTicket = ticketGenerator();
        ticketNumberLbl.setText(currentTicket + "");
        btnTenderCash.setDisable(true);
        btnTenderCard.setDisable(false);
        btnCheckout.setDisable(true);
        disPlayDateTime();
        displayDate();
        importItemsToSearch();
        configureTable();

        AutoCompletionBinding<String> acb = TextFields.bindAutoCompletion(tfFieldSearch, entries);
        acb.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<String>>() {

            @Override
            public void handle(AutoCompletionEvent<String> event) {
                String valueFromAutoCompletion = event.getCompletion();
                String[] splitData = valueFromAutoCompletion.split("\\s+");
                int productId = Integer.parseInt(splitData[splitData.length - 1]);

                for (Product model : data) {
                    if (model.getProductId() == productId) {
                        model.setQuantity(Integer.parseInt(showDialog()));
                        tableItems.add(model);
                        table.setItems(tableItems);
                        tfFieldSearch.setText("");
                        calcSubTotal();
                    }
                }
                btnTenderCash.setDisable(false);
            }
        });

//        tfCashPaid.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            NumberFormat nf = NumberFormat.getCurrencyInstance();
//
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
//                if (newPropertyValue) {
//                    System.out.println("Textfield on focus");
//                } else if (tfCashPaid.getText().trim().isEmpty()) {
////                    if (tfCashPaid.getText().trim().isEmpty()) {
//                    lblBalance.setText("");
////                    } else {
////                        lblBalance.setText((Double.parseDouble(tfCashPaid.getText()) - Double.parseDouble(lblTotal.getText())) + "");
////                    }
//                } else if (Double.parseDouble(tfCashPaid.getText()) < Double.parseDouble(lblTotal.getText())) {
//                    btnCheckout.setDisable(true);
//                    lblBalance.setText((Double.parseDouble(tfCashPaid.getText()) - Double.parseDouble(lblTotal.getText())) + "");
//                    tfCashPaid.requestFocus();
//                } else {
//
//                    lblBalance.setText((Double.parseDouble(tfCashPaid.getText()) - Double.parseDouble(lblTotal.getText())) + "");
//                    btnCheckout.setDisable(false);
//                }
//            }//
//        });
//        TextFormatter<Number> formatter = new TextFormatter<>(new FormatStringConverter<>(DecimalFormat.getNumberInstance()));
//
//        tfCashPaid.setTextFormatter(formatter);
//
//        lblBalance.textProperty().bind(Bindings.concat(tfCashPaid.getTextFormatter().valueProperty().asString())
//                .concat(" ").concat(Currency.getInstance(Locale.getDefault()).getCurrencyCode()));
//        NumberBinding balance = getBalance.subtract(0)Double.parseDouble(tfCashPaid.getText().s(Double.parseDouble(tfCashPaid.getText()) - Double.parseDouble(lblTotal.getText()));
//        lblBalance.textProperty().bindBidirectional((Double.parseDouble(tfCashPaid.textProperty()) - Double.parseDouble(lblTotal.getText())));
    }

    // uses round half up, or bankers' rounding
    public static double roundToTwoPlaces(double d) {
        return Math.round(d * 100) / 100.0;
    }

    private void disPlayDateTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = Calendar.getInstance();
            int second = cal.get(Calendar.SECOND);
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR);
//            System.out.println(hour + ":" + (minute) + ":" + second);
////            time.setText(hour + ":" + (minute) + ":" + second);
            lblHour.setText(hour + "");
            lblMinutes.setText(minute + "");
            lblSeconds.setText(second + "");
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void importItemsToSearch() {
        data = FXCollections.observableArrayList();
        tableItems = FXCollections.observableArrayList();
        entries = FXCollections.observableArrayList();
        try {
            ProductManager.getProductsList();
            data = ProductManager.getProductsList();
            for (Product next : data) {
                entries.add(next.getName().concat(" ").concat(next.getUnitPrice().toString()).concat(" ").concat((next.getProductId().toString())));
//                entries.add(next.getDescription().concat(" ").concat(next.getUnitPrice().toString()).concat(" ").concat(Integer.parseInt(next.getProductId())));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PosClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        searchField.setItems(entries);
//        aux = new AutoCompleteComboBoxListener(searchField);
    }

    public void displayDate() {
        String s;
        Format formatter;
        Date date = new Date();
        // 29-Jan-02
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        s = formatter.format(date);
        dateLocal.setText(s);
    }

    public void calcSubTotal() {
        int rowIndex = table.getItems().size() - 1;
        Product rowList = table.getItems().get(rowIndex);
        double price = rowList.getUnitPrice();
        double quant = rowList.getQuantity();
        double result = price * quant;
        this.qty += result;
//        subTotalLbl.setText(qty + "");
//        lblTotal.setText(qty + "");
        saleItems.put(rowList.getProductId(), result);
        reSetItems();
    }

    private void reSetItems() {
        Double doubleSum = 0.0;
        for (Double d : saleItems.values()) {
            doubleSum += d;
        }
        subTotalLbl.setText(doubleSum + "");
        lblTotal.setText(doubleSum + "");
//        lblAmountPaid.setText(doubleSum + "");
    }

    private void onEditSaleItemsQty() {
        Double doubleSum = 0.0;
        for (Double d : saleItems.values()) {
            doubleSum += d;
        }
        subTotalLbl.setText(doubleSum + "");
        lblTotal.setText(doubleSum + "");
    }

    private String showDialog() {
        String qty = null;
        TextInputDialog dialog = new TextInputDialog("Add Quantity");
        dialog.setTitle("Add Quantity");
//        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Enter Quantity: ");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            qty = result.get();
        }
        return qty;
    }

    private String showDialogCash() {
        String qty = null;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Cash Paid");
        dialog.setHeaderText(null);
        dialog.setContentText("Cash Paid: ");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            qty = result.get();
        }
        amountPaid = new BigDecimal(qty);
//        lblAmountPaid.setText(Double.parseDouble(qty) + "");
//        btnCheckout.setDisable(false);
        return qty;
    }

    public static int ticketGenerator() {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((10000 - 1) + 1) + 1;
        return randomNum;
    }

    private void enterReceipts() throws Exception {
        Receipts bean = new Receipts();
        bean.setEmployeeId(employeeId);
        bean.setTicketNumber(currentTicket + "");
        bean.setCustomerId(currentTicket + "");
        bean.setSalesOutletId(ip.getHostName());
        bean.setModeOfPayment(tenderType);
        bean.setAmountPaid(Double.parseDouble(lblAmountPaid.getText()));
        bean.setBalance(roundToTwoPlaces(Double.parseDouble(lblBalance.getText())));
        boolean result = ReceiptsManager.insert(bean);
        if (result) {
        }

    }

    private void updateInventoryQty() throws Exception {
        Product bean = new Product();
        for (Product model : tableItems) {
            bean.setQuantity(model.getQuantity());
            bean.setProductId(model.getProductId());
            boolean result = ProductManager.updateItemQuntityOnSale(bean);
            if (result) {
//                System.out.println("Product " + bean.getProductId() + " qty was reduced by !");
            }
        }
    }

    private void enterSaleItems() throws Exception {
        SaleItems bean = new SaleItems();
        for (Product model : tableItems) {
            bean.setProductId(model.getProductId());
            bean.setQuantity(model.getQuantity());
            bean.setTicketNumber(currentTicket + "");

            boolean result = SaleItemsManager.insert(bean);
            if (result) {
//                System.out.println("New row with primary key " + bean.getSaleId() + " was inserted!");
            }
        }
    }

    private void configureTable() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        actionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        //Adding the Button to the cell
        actionColumn.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new ButtonCell();
            }
        });
        nameColumn.setPrefWidth(200);
        nameColumn.setMinWidth(200);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(null);
//        actionCol.setSortable(false);

    }
//

    public void showReport() throws JRException {
        String sourceFileName = ("src\\reports\\ReceiptsPOS.jasper");
//      String sourceFileName = ("C:/Users/hp/Documents/NetBeansProjects/EpicPOS/src/reports/ReceiptsPOS.jasper");
        String printFileName = null;
        ObservableList<ProductsInTransaction> beanSaleItemList = FXCollections.observableArrayList();

        for (Product model : tableItems) {
            ProductsInTransaction beanSaleItem = new ProductsInTransaction();
            beanSaleItem.setProductName(model.getName() + " @ " + (model.getUnitPrice()));
            beanSaleItem.setQuantityBought(model.getQuantity());
            beanSaleItem.setUnitPrice(model.getUnitPrice() * model.getQuantity());
//            beanSaleItem.setUnitPrice(Double.parseDouble(subTotalLbl.getText()));
            beanSaleItem.setTicketNumber(currentTicket + "");
            beanSaleItem.setReceiptDate(dateLocal.getText());
            beanSaleItem.setAmountPaid(Double.parseDouble(lblAmountPaid.getText()));
            beanSaleItem.setChange(Double.parseDouble(lblBalance.getText()));
            beanSaleItemList.add(beanSaleItem);

        }
        for (ProductsInTransaction beanSaleItemList1 : beanSaleItemList) {
            System.out.println(beanSaleItemList1.getChange());
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanSaleItemList);
        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("receiptDate", dateLocal.getText());
        try {
            printFileName = JasperFillManager.fillReportToFile(
                    sourceFileName, parameters, beanColDataSource);
            if (printFileName != null) {

                JasperPrintManager.printReport(printFileName, true);
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void showReport2(int ticketNumber) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            JasperDesign jd = null;
            String sql = "SELECT * FROM productsbyticketnumber "
                    + "JOIN salereports on productsbyticketnumber.ticketNumber = salereports.ticketNumber "
                    + "WHERE productsbyticketnumber.ticketNumber = " + ticketNumber;

            jd = JRXmlLoader.load("C:/Users/KwabenaEpic/Documents/NetBeansProjects/EpicPOS/src/reports/Receipts.jrxml");
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);

            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp, false);
            JasperPrintManager.printReport(jp, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void checkout(ActionEvent event) throws Exception {
        enterReceipts();
        enterSaleItems();
        updateInventoryQty();
        showReport();
        prepAnotherSale();

    }

    private void resetItems() {
        subTotalLbl.setText("0.0");
        taxLbl.setText("0.0");
        lblBalance.setText("0.0");
        lblAmountPaid.setText("");
        lblTotal.setText("0.0");
        lblDiscount.setText("0.0");
        lblAmountPaid.setText("0.0");
        btnCheckout.setDisable(true);
        btnTenderCash.setDisable(true);

    }

    private void prepAnotherSale() {
        subTotalLbl.setText("");
        taxLbl.setText("");
        lblBalance.setText("");
        lblAmountPaid.setText("");
        lblTotal.setText("");
        lblDiscount.setText("");
        currentTicket = ticketGenerator();
        ticketNumberLbl.setText(currentTicket + "");
        saleItems.clear();
        table.getItems().clear();
        btnCheckout.setDisable(true);
        btnTenderCash.setDisable(true);

    }

    @FXML
    private void btnClearOnAction(ActionEvent event) {
        Product product = new Product();

        btnCheckout.setDisable(true);
        btnTenderCash.setDisable(true);
        table.getItems().clear();
        saleItems.clear();
        lblTotal.setText("0.0");
        subTotalLbl.setText("0.0");
        taxLbl.setText("0.0");
        lblBalance.setText("0.0");
        lblDiscount.setText("0.0");
        lblAmountPaid.setText("0.0");

//        tableItems.removeAll(product);
    }

    @FXML
    private void btnDiscountOnAction(ActionEvent event) {
//        showDialogCash();
    }

    @FXML
    private void btnHoldSaleOnAction(ActionEvent event) {
    }

    @FXML
    private void fieldSearchOnAction(ActionEvent event) {

    }

    @FXML
    private void btnLogoutOnAction(ActionEvent event) {
        application.userLogout();
    }

    @FXML
    private void btnTenderCashOnAction(ActionEvent event) {
        tenderType = "Cash";
        while (Double.parseDouble(showDialogCash()) < Double.parseDouble(lblTotal.getText())) {
            showDialogCash();
        }

        lblBalance.setText((this.amountPaid.subtract(new BigDecimal(lblTotal.getText()))).toString());
        lblAmountPaid.setText(this.amountPaid.toString());
        btnCheckout.setDisable(false);

    }

    @FXML
    private void btnTenderCardOnAction(ActionEvent event) {

    }

    //Define the button cell
    private class ButtonCell extends TableCell<Record, Boolean> {

        final Button editButton = new Button("");
        final Button deleteButton = new Button("");

        ButtonCell() {
            editButton.setId("btnEdit");
            editButton.setPrefWidth(31);
            editButton.setPrefHeight(31);
            deleteButton.setId("btnDelete");
            deleteButton.setPrefWidth(31);
            deleteButton.setPrefHeight(31);
            //Action when the button is pressed

            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    Product currentProduct = (Product) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    currentProduct.setQuantity(Integer.parseInt(showDialog()));
                    tableItems.remove(currentProduct);
//                    saleItems.remove(currentProduct.getProductId());
                    tableItems.add(currentProduct);
                    calcSubTotal();
                }
            });

            //Action when the button is pressed
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    Product currentProduct = (Product) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    saleItems.remove(currentProduct.getProductId());
                    tableItems.remove(currentProduct);
                    reSetItems();
                    if (saleItems.isEmpty()) {
                        resetItems();
                    }
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                HBox pane = new HBox(editButton, deleteButton);
                pane.setSpacing(10);
                pane.setAlignment(Pos.CENTER);
                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
    }

    public void setApp(Main application) {
        this.application = application;
        Employee loggedUser = application.getLoggedUser();
        employeeId = loggedUser.getEmployeeId();
        staffNameLbl.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
        if (loggedUser.getImage() != null) {
            ByteArrayInputStream byteArrayInputStream = null;
            try {
                byteArrayInputStream = new ByteArrayInputStream(loggedUser.getImage().getBytes(1, (int) loggedUser.getImage().length()));

            } catch (SQLException ex) {
                Logger.getLogger(PosClientController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            ivUserImage.setImage(new Image(byteArrayInputStream));
        } else {
            ivUserImage.setImage(null);
        }

    }

//    private void launchLostConnection() {
//        if (lostConnecttion == null) {
//            FXMLLoader fXMLLoader = new FXMLLoader();
//            fXMLLoader.setLocation(getClass().getResource("/view/lostConnection.fxml"));
//            // initializing the controller
////        DailyStockReceiptsController dailystockReceiptsController = new DailyStockReceiptsController();
////        fXMLLoader.setController(dailystockReceiptsController);
//            try {
//                fXMLLoader.load();
//                Parent parent = fXMLLoader.getRoot();
//                Scene scene = new Scene(parent);
//                scene.setFill(new Color(0, 0, 0, 0));
//                LostConnectionController lostConnectionController = fXMLLoader.getController();
//                lostConnecttion = new Stage();
//                lostConnecttion.setScene(scene);
//                lostConnecttion.initModality(Modality.APPLICATION_MODAL);
//                lostConnecttion.initStyle(StageStyle.UTILITY);
//                lostConnecttion.show();
//
////                if (lostConnecttion.isShowing()) {
////                    lostConnecttion.toFront();
////                } else {
////                    lostConnecttion.show();
////                }
//            } catch (IOException ex) {
//                Logger.getLogger(LostConnectionController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//    }
    private void isValidCondition() {
//        boolean validCondition = true;
        if (dialog == null) {
//            if (result == false) {
            copyWorker = createWorker();
            dialog = new ProgressDialog(copyWorker);
            dialog.setGraphic(null);
            dialog.setContentText("Connecting to server...");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setHeaderText("");

//            dialog.initStyle(StageStyle.UTILITY);
            new Thread(copyWorker).start();
            dialog.showAndWait();

//            } else {
//                validCondition = true;
//            }
        }
//        return validCondition;
    }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                final int max = 10000000;
                updateProgress(0, max);
                for (int i = 1; i <= max; i++) {
//                    Thread.sleep(5000);
                    updateMessage("Connecting to Server...");
                    updateProgress(i, max);
                }
                return true;
            }
        };
    }

//    @FXML
//    private void checkout(ActionEvent event) {
//    }
}
