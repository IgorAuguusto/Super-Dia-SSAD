const apiUrl = "http://localhost:8080/SuperDiaWeb/api/vendas/todos";
const apiUrlProducts = "http://localhost:8080/SuperDiaWeb/api/produtos/todos";
const salesTable = document.getElementById("salesTable");
const productsTable = document.getElementById("productsTable");
const message = document.getElementById("message");
const messageProducts = document.getElementById("messageProducts");

const itemsPerPage = 6;
let currentPage = 1;
let currentPageProducts = 1;
let allSales = [];
let allProducts = [];

// Função para buscar vendas da API
async function fetchSales() {
  try {
    const response = await fetch(apiUrl);

    if (!response.ok) {
      throw new Error("Erro ao buscar as vendas.");
    }

    const { data } = await response.json();
    allSales = data;
    renderSales();
  } catch (error) {
    console.error(error);
    message.textContent =
      "Erro ao carregar as vendas. Tente novamente mais tarde.";
    message.style.display = "block";
  }
}

// Função para buscar produtos da API
async function fetchProducts() {
  try {
    const response = await fetch(apiUrlProducts);

    if (!response.ok) {
      throw new Error("Erro ao buscar os produtos.");
    }

    const { data } = await response.json();
    allProducts = data;
    renderProducts();
  } catch (error) {
    console.error(error);
    messageProducts.textContent =
      "Erro ao carregar os produtos. Tente novamente mais tarde.";
    messageProducts.style.display = "block";
  }
}

// Função para renderizar as vendas na tabela
function renderSales() {
  const start = (currentPage - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  const pageSales = allSales.slice(start, end);

  if (pageSales.length === 0) {
    message.textContent = "Nenhuma venda encontrada.";
    message.style.display = "block";
    salesTable.innerHTML = "";
    return;
  }

  message.style.display = "none";
  salesTable.innerHTML = "";

  pageSales.forEach((sale) => {
    const totalVenda = sale.produtos
      .reduce((total, produto) => total + produto.preco, 0)
      .toFixed(2);

    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${sale.usuario.pessoa.nome}</td>
      <td>${sale.produtos[0]?.vendidoPor || "N/A"}</td>
      <td>${totalVenda}</td>
      <td><button onclick="showSaleDetails(${sale.id})">Ver Mais</button></td>
    `;
    salesTable.appendChild(row);
  });

  updatePagination(
    allSales.length,
    currentPage,
    "currentPage",
    "prevPage",
    "nextPage"
  );
}

// Função para renderizar os produtos na tabela
function renderProducts() {
  const start = (currentPageProducts - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  const pageProducts = allProducts.slice(start, end);

  if (pageProducts.length === 0) {
    messageProducts.textContent = "Nenhum produto encontrado.";
    messageProducts.style.display = "block";
    productsTable.innerHTML = "";
    return;
  }

  messageProducts.style.display = "none";
  productsTable.innerHTML = "";

  pageProducts.forEach((product) => {
    const row = document.createElement("tr");
    const isLowStock = product.quantidadeEstoque <= product.estoqueMinimo;
    row.innerHTML = `
      <td>${product.nome}</td>
      <td>R$ ${product.preco.toFixed(2)}</td>
      <td class="${isLowStock ? "low-stock" : ""}">${product.quantidadeEstoque}
     </td>
      <td>${product.estoqueMinimo}</td>
    `;
    /**
     * 
      <td><button onclick="editProduct(${product.id})">Editar</button></td>
     */
    productsTable.appendChild(row);
  });

  updatePagination(
    allProducts.length,
    currentPageProducts,
    "currentPageProducts",
    "prevPageProducts",
    "nextPageProducts"
  );
}

// Função para atualizar a paginação
function updatePagination(
  totalItems,
  currentPageNum,
  currentPageId,
  prevPageId,
  nextPageId
) {
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  document.getElementById(currentPageId).textContent = currentPageNum;
  document.getElementById(prevPageId).disabled = currentPageNum === 1;
  document.getElementById(nextPageId).disabled = currentPageNum === totalPages;
}

// Função para exibir os detalhes de uma venda
function showSaleDetails(saleId) {
  window.location.href = `../html/detalhes.html?saleId=${saleId}`;
}

// Função para editar um produto (a ser implementada)
function editProduct(productId) {
  console.log(`Editar produto com ID: ${productId}`);
  // Implementar a lógica de edição do produto
}

// Event listeners para paginação
document.getElementById("prevPage").addEventListener("click", () => {
  if (currentPage > 1) {
    currentPage--;
    renderSales();
  }
});

document.getElementById("nextPage").addEventListener("click", () => {
  if (currentPage < Math.ceil(allSales.length / itemsPerPage)) {
    currentPage++;
    renderSales();
  }
});

document.getElementById("prevPageProducts").addEventListener("click", () => {
  if (currentPageProducts > 1) {
    currentPageProducts--;
    renderProducts();
  }
});

document.getElementById("nextPageProducts").addEventListener("click", () => {
  if (currentPageProducts < Math.ceil(allProducts.length / itemsPerPage)) {
    currentPageProducts++;
    renderProducts();
  }
});

// Event listener para trocar entre abas
document.querySelectorAll(".sidebar a").forEach((tab) => {
  tab.addEventListener("click", (e) => {
    e.preventDefault();
    document.querySelectorAll(".tab-content").forEach((content) => {
      content.classList.remove("active");
    });
    document.querySelectorAll(".sidebar a").forEach((link) => {
      link.classList.remove("active");
    });
    const tabId = e.target.getAttribute("data-tab");
    document.getElementById(tabId).classList.add("active");
    e.target.classList.add("active");
  });
});

// Event listener para logout
document.getElementById("logout").addEventListener("click", () => {
  alert("Logout realizado com sucesso!");
  window.location.href = "../html/login.html";
});

// Inicialização
fetchSales();
fetchProducts();
