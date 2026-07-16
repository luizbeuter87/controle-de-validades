const API_URL = "http://localhost:8081/api/produtos";
let indexEdicao = -1;
let idEdicao = null;

function autenticar() {
    const usuario = document.getElementById("usuario").value;
    const senha = document.getElementById("senha").value;
    const telaLogin = document.getElementById("telaLogin");
    const sistema = document.getElementById("sistemaPrincipal");

    if (usuario === "tche" && senha === "3430") {
        telaLogin.style.display = "none";
        sistema.style.display = "block";
        renderizar();
    } else {
        alert("Usuário ou senha incorretos!");
    }
}

async function renderizar() {
    const lista = document.getElementById("listaValidades");
    const busca = document.getElementById("busca").value.toLowerCase();
    lista.innerHTML = "<li>Carregando estoque...</li>";

    try {
        const response = await fetch(API_URL);
        const produtos = await response.json();

        lista.innerHTML = "";
        const hoje = new Date();
        hoje.setHours(0,0,0,0);

        produtos.filter(p => p.nome.toLowerCase().includes(busca)).forEach((item) => {
            const dataVenc = new Date(item.dataVencimento);
            dataVenc.setHours(24,0,0,0);
            const diffDias = Math.ceil((dataVenc - hoje) / (1000 * 60 * 60 * 24));

            let classeStatus = "status-ok";
            if (diffDias <= 5) classeStatus = "status-critico";
            else if (diffDias <= 15) classeStatus = "status-alerta";

            const li = document.createElement("li");
            li.className = classeStatus;
            li.innerHTML = `
                <div>
                    <strong>${item.nome}</strong> <span class="qtd-badge">${item.quantidade} un</span><br>
                    <small>${diffDias < 0 ? 'VENCIDO' : 'Vence em ' + diffDias + ' dias'}</small>
                </div>
                <div class="acoes">
                    <button style="background:#d1ecf1" onclick="prepararEdicao(${JSON.stringify(item).replace(/"/g, '&quot;')})">📝</button>
                    <button style="background:#f8d7da" onclick="excluir(${item.id})">🗑️</button>
                </div>
            `;
            lista.appendChild(li);
        });
    } catch (error) {
        lista.innerHTML = "<li style='color:red'>Erro ao conectar com o servidor.</li>";
        console.error("Erro na API:", error);
    }
}

async function adicionarOuSalvar() {
    const nome = document.getElementById("produto").value;
    const dataVencimento = document.getElementById("dataVencimento").value;
    const quantidade = document.getElementById("quantidade").value;

    if (!nome || !dataVencimento || !quantidade) {
        alert("Preencha todos os campos!");
        return;
    }

    const produtoObj = {
        nome,
        dataVencimento,
        quantidade: parseInt(quantidade)
    };

    if (idEdicao) produtoObj.id = idEdicao;

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(produtoObj)
        });

        if (response.ok) {
            idEdicao = null;
            limparCampos();
            renderizar();
        }
    } catch (error) {
        alert("Erro ao salvar produto.");
    }
}

async function excluir(id) {
    if (!confirm("Remover este item permanentemente do banco de dados?")) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        if (response.ok) renderizar();
    } catch (error) {
        alert("Erro ao excluir.");
    }
}

function prepararEdicao(item) {
    document.getElementById("produto").value = item.nome;
    document.getElementById("dataVencimento").value = item.dataVencimento;
    document.getElementById("quantidade").value = item.quantidade;
    idEdicao = item.id;
    window.scrollTo(0,0);
}

function limparCampos() {
    document.getElementById("produto").value = "";
    document.getElementById("dataVencimento").value = "";
    document.getElementById("quantidade").value = "";
}

function logout() {
    location.reload();
}
