const apiUrl = "http://localhost:8080/SuperDiaWeb/api/vendas/todos";
const apiUrlProducts = "http://localhost:8080/SuperDiaWeb/api/produtos/todos";
const apiUrlUpdateSale = "http://localhost:8080/SuperDiaWeb/api/vendas/alterar";
const clientName = document.getElementById("clientName");
const clientEmail = document.getElementById("clientEmail");
const productTable = document.getElementById("productTable");
const totalPrice = document.getElementById("totalPrice");
const editSaleBtn = document.getElementById("editSale");
const saveSaleBtn = document.getElementById("saveSale");
const cancelEditBtn = document.getElementById("cancelEdit");
const actionsHeader = document.getElementById("actionsHeader");
const addProductRow = document.getElementById("addProductRow");
const productDropdown = document.getElementById("productDropdown");
const addProductButton = document.getElementById("addProductButton");

let currentSale = null;
let allProducts = [];
let isEditing = false;

function getQueryParam(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(param);
}

// detalhes da venda
async function fetchSaleDetails(saleId) {
  try {
    const response = await fetch(apiUrl);
    if (!response.ok) throw new Error("Erro ao buscar as vendas.");

    const { data } = await response.json();

    currentSale = data.find((item) => item.id === Number.parseInt(saleId));

    if (!currentSale) {
      alert("Venda não encontrada!");
      window.location.href = "../html/dashboard.html";
      return;
    }

    clientName.textContent = currentSale.usuario.pessoa.nome;
    clientEmail.textContent = currentSale.usuario.pessoa.email;

    renderProductTable();
  } catch (error) {
    console.error(error);
    alert("Erro ao carregar os detalhes da venda.");
  }
}

// Tabela de produtos
function renderProductTable() {
  productTable.innerHTML = "";
  let total = 0;

  currentSale.produtos.forEach((produto, index) => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${produto.nome}</td>
      <td>${produto.preco.toFixed(2)}</td>
      ${
        isEditing
          ? `<td><button class="delete-product" data-index="${index}">Remover</button></td>`
          : ""
      }
    `;
    productTable.appendChild(row);
    total += produto.preco;
  });

  totalPrice.textContent = total.toFixed(2);
  actionsHeader.style.display = isEditing ? "table-cell" : "none";
  addProductRow.style.display = isEditing ? "table-row" : "none";
}

// Funcao apagar a venda
async function deleteSale(saleId) {
  if (confirm("Tem certeza que deseja apagar esta venda?")) {
    const apiUrl = `http://localhost:8080/SuperDiaWeb/api/vendas/deletar/${saleId}`;

    try {
      const response = await fetch(apiUrl, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Erro ao apagar a venda.");
      }

      alert("Venda apagada com sucesso!");
      window.location.href = "../html/dashboard.html";
    } catch (error) {
      console.error(error);
      alert("Ocorreu um erro ao apagar a venda. Tente novamente.");
    }
  }
}

// Busca produtos
async function fetchAllProducts() {
  try {
    const response = await fetch(apiUrlProducts);
    if (!response.ok) throw new Error("Erro ao buscar os produtos.");

    const { data } = await response.json();
    allProducts = data;
    populateProductDropdown();
  } catch (error) {
    console.error(error);
    alert("Erro ao carregar a lista de produtos.");
  }
}

// Dropdown produtos
function populateProductDropdown() {
  productDropdown.innerHTML = '<option value="">Selecione um produto</option>';
  allProducts.forEach((product) => {
    const option = document.createElement("option");
    option.value = product.id;
    option.textContent = `${product.nome} - R$ ${product.preco.toFixed(2)}`;
    productDropdown.appendChild(option);
  });
}

// Add produto venda
function addProductToSale() {
  const selectedProductId = Number.parseInt(productDropdown.value);
  if (!selectedProductId) {
    alert("Por favor, selecione um produto.");
    return;
  }

  const selectedProduct = allProducts.find((p) => p.id === selectedProductId);
  currentSale.produtos.push(selectedProduct);
  renderProductTable();
}

// Remove produto da venda
function removeProductFromSale(index) {
  currentSale.produtos.splice(index, 1);
  renderProductTable();
}

// Funcao para salvar edicao venda
async function saveSaleChanges() {
  try {
    const response = await fetch(apiUrlUpdateSale, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(currentSale),
    });

    if (!response.ok) {
      throw new Error("Erro ao atualizar a venda.");
    }

    alert("Venda atualizada com sucesso!");
    toggleEditMode();
  } catch (error) {
    console.error(error);
    alert("Ocorreu um erro ao atualizar a venda. Tente novamente.");
  }
}

// Modo de edicao
function toggleEditMode() {
  isEditing = !isEditing;
  editSaleBtn.style.display = isEditing ? "none" : "inline-block";
  saveSaleBtn.style.display = isEditing ? "inline-block" : "none";
  cancelEditBtn.style.display = isEditing ? "inline-block" : "none";
  renderProductTable();
}

// Event Listeners
document.getElementById("deleteSale").addEventListener("click", () => {
  const saleId = getQueryParam("saleId");
  if (saleId) {
    deleteSale(saleId);
  } else {
    alert("ID da venda não encontrado!");
  }
});

editSaleBtn.addEventListener("click", () => {
  toggleEditMode();
  fetchAllProducts();
});

saveSaleBtn.addEventListener("click", saveSaleChanges);

cancelEditBtn.addEventListener("click", () => {
  if (confirm("Tem certeza que deseja cancelar as alterações?")) {
    toggleEditMode();
    fetchSaleDetails(currentSale.id);
  }
});

document.getElementById("back").addEventListener("click", () => {
  window.location.href = "../html/dashboard.html";
});

addProductButton.addEventListener("click", addProductToSale);

productTable.addEventListener("click", (e) => {
  if (e.target.classList.contains("delete-product")) {
    const index = Number.parseInt(e.target.getAttribute("data-index"));
    removeProductFromSale(index);
  }
});

// Pega o ID venda
const saleId = getQueryParam("saleId");
if (saleId) {
  fetchSaleDetails(saleId);
} else {
  alert("ID da venda não fornecido.");
  window.location.href = "../html/dashboard.html";
}
