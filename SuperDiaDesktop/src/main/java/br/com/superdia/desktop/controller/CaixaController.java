package br.com.superdia.desktop.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.superdia.desktop.application.LoginApplication;
import br.com.superdia.desktop.auth.AuthenticationManager;
import br.com.superdia.desktop.model.CartItem;
import br.com.superdia.desktop.model.Produto;
import br.com.superdia.desktop.service.CaixaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField numeroCartaoField;

    @FXML
    private Button removeButton; // Botão para remover itens

    @FXML
    private CaixaService caixaService;

    private final ObservableList<CartItem> cartItems = FXCollections.observableArrayList();

    private List<Produto> listaProdutos = new ArrayList<>();

    private double total = 0.0;

    @FXML
    public void initialize() {
        caixaService = new CaixaService();
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        subtotalColumn.setCellValueFactory(cellData -> cellData.getValue().subtotalProperty().asObject());
        cartTable.setItems(cartItems);

        // Configuração do botão de remoção
        removeButton.setOnAction(event -> removeProductFromCart());
    }

    @FXML
    private void addProductToCart() {
        String productCode = productCodeField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        Produto produto = caixaService.obterProduto(productCode, quantity);
        if (produto != null) {
            CartItem item = new CartItem(produto.getNome(), produto.getQuantidadeEstoque(), produto.getPreco());
            cartItems.add(item);
            total += item.getSubtotal();
            updateTotalLabel();
            listaProdutos.add(produto);
        }
        productCodeField.clear();
        quantityField.clear();
    }

    @FXML
    private void finalizePurchase() {
        String customerCPF = customerCPFField.getText();
        String numeroCartao = numeroCartaoField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (caixaService.finalizarCompra(total, customerCPF, numeroCartao, listaProdutos)) {
            alert.setTitle("Compra Finalizada");
            alert.setHeaderText(null);
            alert.setContentText("""
                    Compra finalizada!
                    Total: R$ %.2f
                    CPF do Cliente: %s
                """.formatted(total, customerCPF));
            alert.showAndWait();
        } else {
            alert.setTitle("Compra não realizada");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        listaProdutos = new ArrayList<>();
        cartItems.clear();
        total = 0.0;
        updateTotalLabel();
        customerCPFField.clear();
        numeroCartaoField.clear();
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

    // Método para remover um item do carrinho
    private void removeProductFromCart() {
        CartItem selectedItem = cartTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Remove o item da lista observável
            cartItems.remove(selectedItem);

            // Atualiza o total subtraindo o subtotal do item removido
            total -= selectedItem.getSubtotal();
            updateTotalLabel();

            // Remove o produto correspondente da lista de produtos
            listaProdutos.removeIf(produto -> produto.getNome().equals(selectedItem.getProductName()));
        } else {
            // Exibe uma mensagem caso nenhum item esteja selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Item Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um item para remover.");
            alert.showAndWait();
        }
    }
}