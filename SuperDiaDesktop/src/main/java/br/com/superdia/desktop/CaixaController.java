package br.com.superdia.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.util.Locale;

public class CaixaController {

    @FXML private TextField productCodeField;
    @FXML private TextField quantityField;
    @FXML private TableView<CartItem> cartTable;
    @FXML private TableColumn<CartItem, String> productNameColumn;
    @FXML private TableColumn<CartItem, Integer> quantityColumn;
    @FXML private TableColumn<CartItem, Double> priceColumn;
    @FXML private TableColumn<CartItem, Double> subtotalColumn;
    @FXML private Label totalLabel;
    @FXML private TextField customerCPFField;

    private final ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
    private double total = 0.0;

    @FXML
    public void initialize() {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        cartTable.setItems(cartItems);
    }

    @FXML
    private void addProductToCart() {
        String productCode = productCodeField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        // TODO: Fetch product details from EJB backend
        // For now, we'll use dummy data
        String productName = "Produto " + productCode;
        double price = 10.0; // dummy price

        CartItem item = new CartItem(productName, quantity, price);
        cartItems.add(item);

        total += item.subtotal();
        updateTotalLabel();

        productCodeField.clear();
        quantityField.clear();
    }

    @FXML
    private void finalizePurchase() {
        String customerCPF = customerCPFField.getText();

        // TODO: Send purchase data to EJB backend
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Compra Finalizada");
        alert.setHeaderText(null);
        alert.setContentText("""
            Compra finalizada!
            Total: R$ %.2f
            CPF do Cliente: %s
            """.formatted(total, customerCPF));
        alert.showAndWait();

        // Clear cart
        cartItems.clear();
        total = 0.0;
        updateTotalLabel();
        customerCPFField.clear();
    }

    private void updateTotalLabel() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        totalLabel.setText("Total: " + currencyFormat.format(total));
    }

    public record CartItem(String productName, int quantity, double price) {
        public double subtotal() {
            return quantity * price;
        }
    }
}

