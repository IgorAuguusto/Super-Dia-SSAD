const apiUrl = "http://localhost:8080/SuperDiaWeb/api/vendas/todos"; // URL da API
const clientName = document.getElementById("clientName");
const clientEmail = document.getElementById("clientEmail");
const productTable = document.getElementById("productTable");
const totalPrice = document.getElementById("totalPrice");

// Função para obter o parâmetro da URL
function getQueryParam(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(param);
}

// Função para buscar e renderizar os detalhes da venda
async function fetchSaleDetails(saleId) {
  try {
    const response = await fetch(apiUrl);
    if (!response.ok) throw new Error("Erro ao buscar as vendas.");

    const { data } = await response.json();

    // Encontra a venda com o ID fornecido
    const sale = data.find((item) => item.id === parseInt(saleId));

    if (!sale) {
      alert("Venda não encontrada!");
      window.location.href = "../html/dashboard.html"; // Volta ao dashboard se a venda não existir
      return;
    }

    // Preenche as informações do cliente
    clientName.textContent = sale.usuario.pessoa.nome;
    clientEmail.textContent = sale.usuario.pessoa.email;

    // Preenche a tabela com os produtos
    let total = 0;
    sale.produtos.forEach((produto) => {
      const row = document.createElement("tr");
      const nameCell = document.createElement("td");
      const priceCell = document.createElement("td");

      nameCell.textContent = produto.nome;
      priceCell.textContent = produto.preco.toFixed(2);

      row.appendChild(nameCell);
      row.appendChild(priceCell);
      productTable.appendChild(row);

      total += produto.preco;
    });

    // Exibe o total
    totalPrice.textContent = total.toFixed(2);
  } catch (error) {
    console.error(error);
    alert("Erro ao carregar os detalhes da venda.");
  }
}

// Função para apagar a venda (exemplo de lógica)
function deleteSale(saleId) {
  if (confirm("Tem certeza que deseja apagar esta venda?")) {
    // Implementar lógica de deleção
    alert(`Venda ${saleId} apagada!`);
    window.location.href = "../html/dashboard.html";
  }
}

// Função para editar a venda (exemplo de lógica)
function editSale(saleId) {
  // Implementar lógica de edição
  alert(`Redirecionando para edição da venda ${saleId}.`);
}

// Botão "Voltar"
document.getElementById("back").addEventListener("click", () => {
  window.location.href = "../html/dashboard.html";
});

// Botão "Apagar"
document.getElementById("deleteSale").addEventListener("click", () => {
  const saleId = getQueryParam("saleId");
  deleteSale(saleId);
});

// Botão "Editar"
document.getElementById("editSale").addEventListener("click", () => {
  const saleId = getQueryParam("saleId");
  editSale(saleId);
});

// Obtém o ID da venda da URL e carrega os detalhes
const saleId = getQueryParam("saleId");
if (saleId) {
  fetchSaleDetails(saleId);
} else {
  alert("ID da venda não fornecido.");
  window.location.href = "../html/dashboard.html";
}
