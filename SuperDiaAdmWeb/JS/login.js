const form = document.getElementById("loginForm");
const message = document.getElementById("message");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const login = document.getElementById("login").value;
  const senha = document.getElementById("senha").value;

  try {
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

    if (response.ok && data.data.perfil === "ADMINISTRADOR") {
      window.location.href = "../html/dashboard.html";
    } else if (response.ok) {
      message.textContent =
        "Acesso negado: Apenas administradores podem acessar esta página.";
    } else {
      message.textContent = data.message || "Erro ao realizar login.";
    }
  } catch (error) {
    console.error("Erro ao realizar login:", error);
    message.textContent =
      "Não foi possível realizar o login. Tente novamente mais tarde.";
  }
});
