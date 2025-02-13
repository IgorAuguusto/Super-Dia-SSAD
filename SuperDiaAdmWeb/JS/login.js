// Referência ao formulário e à mensagem de erro
const form = document.getElementById("loginForm");
const message = document.getElementById("message");

// Função para fazer login
form.addEventListener("submit", async (event) => {
  event.preventDefault(); // Evita o reload da página ao enviar o formulário

  // Obtém os valores de login e senha
  const login = document.getElementById("login").value;
  const senha = document.getElementById("senha").value;

  try {
    // Faz a requisição POST para a API
    const response = await fetch(
      "http://localhost:8080/SuperDiaWeb/api/usuarios/login",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ login, senha }),
      }
    );

    const data = await response.json();

    // Verifica o status da resposta
    if (response.ok && data.data.perfil === "ADMINISTRADOR") {
      // Redireciona para a página de administração
      window.location.href = "../html/dashboard.html";
    } else if (response.ok) {
      // Exibe mensagem de erro se não for administrador
      message.textContent =
        "Acesso negado: Apenas administradores podem acessar esta página.";
    } else {
      // Exibe mensagem de erro genérico
      message.textContent = data.message || "Erro ao realizar login.";
    }
  } catch (error) {
    console.error("Erro ao realizar login:", error);
    message.textContent =
      "Não foi possível realizar o login. Tente novamente mais tarde.";
  }
});
