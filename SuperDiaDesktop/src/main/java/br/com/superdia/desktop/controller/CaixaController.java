package br.com.superdia.desktop.controller;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.superdia.desktop.application.LoginApplication;
import br.com.superdia.desktop.auth.AuthenticationManager;
import br.com.superdia.desktop.model.Produto;
import br.com.superdia.desktop.service.CaixaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CaixaController {
	@FXML
	private TextField productCodeField;
	@FXML
	private TextField quantityField;
	@FXML
	private TableView<CartItem> cartTable;
	@FXML
	private TableColumn<CartItem, String> productNameColumn;
	@FXML
	private TableColumn<CartItem, Integer> quantityColumn;
	@FXML
	private TableColumn<CartItem, Double> priceColumn;
	@FXML
	private TableColumn<CartItem, Double> subtotalColumn;
	@FXML
	private Label totalLabel;
	@FXML
	private TextField customerCPFField;
	private CaixaService caixaService;
	private final ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
	private double total = 0.0;

	@FXML
	public void initialize() {
		caixaService = new CaixaService();
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
		Produto produto = caixaService.obterProduto(productCode, quantity);

		if (produto != null) {
			CartItem item = new CartItem(produto.getNome(), produto.getQuantidade(), produto.getPreco());
			cartItems.add(item);
			total += item.subtotal();
			updateTotalLabel();
		}

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

	@FXML
	private void handleLogout() {
		AuthenticationManager.getInstance().logout();
		Window window = cartTable.getScene().getWindow();
		if (window instanceof Stage) {
			((Stage) window).close();
		}

		try {
			new LoginApplication().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
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