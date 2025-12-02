// Elementos do DOM
const btnVoltar = document.getElementById('btnVoltar');
const btnCancelar = document.getElementById('btnCancelar');
const formAdicionarDivida = document.getElementById('formAdicionarDivida');
const toggleBtns = document.querySelectorAll('.toggle-btn');
const togglePaymentBtns = document.querySelectorAll('.toggle-btn-payment');
const toggleItemPaymentBtns = document.querySelectorAll('.toggle-btn-item-payment');
const tipoDividaInput = document.getElementById('tipoDivida');
const formaPagamentoInput = document.getElementById('formaPagamento');
const itemsSection = document.getElementById('itemsSection');
const labelValorTotal = document.getElementById('labelValorTotal');
const labelDataVencimento = document.getElementById('labelDataVencimento');
const infoValorTotal = document.getElementById('infoValorTotal');
const valorTotalInput = document.getElementById('valorTotal');
const parcelasGroup = document.getElementById('parcelasGroup');
const numeroParcelasInput = document.getElementById('numeroParcelas');
const infoParcelamento = document.getElementById('infoParcelamento');
const valorParcelaSpan = document.getElementById('valorParcela');
const itemParcelasGroup = document.getElementById('itemParcelasGroup');
const itemNumeroParcelasInput = document.getElementById('itemNumeroParcelas');
const infoItemParcelamento = document.getElementById('infoItemParcelamento');
const itemValorParcelaSpan = document.getElementById('itemValorParcela');
const formaPagamentoGroup = document.getElementById('formaPagamentoGroup');
const checkboxRecorrente = document.getElementById('checkboxRecorrente');
const dividaRecorrenteInput = document.getElementById('dividaRecorrente');
const checkboxItemRecorrente = document.getElementById('checkboxItemRecorrente');
const itemRecorrenteInput = document.getElementById('itemRecorrente');

// Modal e Itens
const modalAddItem = document.getElementById('modalAddItem');
const modalOverlay = document.getElementById('modalOverlay');
const btnAddItem = document.getElementById('btnAddItem');
const btnCloseModal = document.getElementById('btnCloseModal');
const btnCancelarItem = document.getElementById('btnCancelarItem');
const btnSalvarItem = document.getElementById('btnSalvarItem');
const itemsList = document.getElementById('itemsList');
const itemsSummary = document.getElementById('itemsSummary');
const totalItensSpan = document.getElementById('totalItens');
const valorTotalItensSpan = document.getElementById('valorTotalItens');

// Array para armazenar itens
let items = [];
let formaPagamentoItem = 'vista';

// Controle do checkbox de dívida recorrente
dividaRecorrenteInput?.addEventListener('change', function() {
  if (this.checked) {
    numeroParcelasInput.value = '';
    numeroParcelasInput.disabled = true;
    numeroParcelasInput.placeholder = 'Não aplicável para dívida recorrente';
    infoParcelamento.style.display = 'none';
  } else {
    numeroParcelasInput.disabled = false;
    numeroParcelasInput.placeholder = 'Ex: 12';
    calcularValorParcela();
  }
});

// Controle do checkbox de item recorrente
itemRecorrenteInput?.addEventListener('change', function() {
  if (this.checked) {
    itemNumeroParcelasInput.value = '';
    itemNumeroParcelasInput.disabled = true;
    itemNumeroParcelasInput.placeholder = 'Não aplicável para sub-dívida recorrente';
    infoItemParcelamento.style.display = 'none';
  } else {
    itemNumeroParcelasInput.disabled = false;
    itemNumeroParcelasInput.placeholder = 'Ex: 12';
    calcularValorParcelaItem();
  }
});

// Navegação
btnVoltar?.addEventListener('click', () => {
  window.history.back();
});

btnCancelar?.addEventListener('click', () => {
  window.history.back();
});

// Toggle entre Simples e Composta
toggleBtns.forEach(btn => {
  btn.addEventListener('click', function() {
    // Remove active de todos
    toggleBtns.forEach(b => b.classList.remove('active'));
    // Adiciona active no clicado
    this.classList.add('active');
    
    const tipo = this.getAttribute('data-type');
    tipoDividaInput.value = tipo;
    
    if (tipo === 'composta') {
      // Mostra seção de itens
      itemsSection.style.display = 'block';
      labelValorTotal.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <line x1="12" y1="1" x2="12" y2="23"></line>
          <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
        </svg>
        Dívida Principal (Valor Total)
      `;
      infoValorTotal.style.display = 'block';
      valorTotalInput.readOnly = true;
      valorTotalInput.style.cursor = 'not-allowed';
      valorTotalInput.style.opacity = '0.7';
      // Esconde forma de pagamento (apenas sub-dívidas terão parcelamento)
      formaPagamentoGroup.style.display = 'none';
      parcelasGroup.style.display = 'none';
    } else {
      // Esconde seção de itens
      itemsSection.style.display = 'none';
      labelValorTotal.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <line x1="12" y1="1" x2="12" y2="23"></line>
          <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
        </svg>
        Valor Total
      `;
      infoValorTotal.style.display = 'none';
      valorTotalInput.readOnly = false;
      valorTotalInput.style.cursor = 'text';
      valorTotalInput.style.opacity = '1';
      // Mostra forma de pagamento para dívida simples
      formaPagamentoGroup.style.display = 'block';
      items = []; // Limpa itens ao voltar para simples
      atualizarListaItens();
    }
  });
});

// Toggle entre À Vista e Parcelada (Dívida Principal)
togglePaymentBtns.forEach(btn => {
  btn.addEventListener('click', function() {
    togglePaymentBtns.forEach(b => b.classList.remove('active'));
    this.classList.add('active');
    
    const payment = this.getAttribute('data-payment');
    formaPagamentoInput.value = payment;
    
    if (payment === 'parcelada') {
      parcelasGroup.style.display = 'block';
      labelDataVencimento.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
          <line x1="16" y1="2" x2="16" y2="6"></line>
          <line x1="8" y1="2" x2="8" y2="6"></line>
          <line x1="3" y1="10" x2="21" y2="10"></line>
        </svg>
        Vencimento da Primeira Parcela
      `;
      checkboxRecorrente.style.display = 'block';
      calcularValorParcela();
    } else {
      parcelasGroup.style.display = 'none';
      checkboxRecorrente.style.display = 'none';
      dividaRecorrenteInput.checked = false;
      labelDataVencimento.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
          <line x1="16" y1="2" x2="16" y2="6"></line>
          <line x1="8" y1="2" x2="8" y2="6"></line>
          <line x1="3" y1="10" x2="21" y2="10"></line>
        </svg>
        Data de Vencimento
      `;
      infoParcelamento.style.display = 'none';
    }
  });
});

// Toggle entre À Vista e Parcelada (Item da Sub-Dívida)
toggleItemPaymentBtns.forEach(btn => {
  btn.addEventListener('click', function() {
    toggleItemPaymentBtns.forEach(b => b.classList.remove('active'));
    this.classList.add('active');
    
    const payment = this.getAttribute('data-item-payment');
    formaPagamentoItem = payment;
    
    if (payment === 'parcelada') {
      itemParcelasGroup.style.display = 'block';
      checkboxItemRecorrente.style.display = 'block';
      calcularValorParcelaItem();
    } else {
      itemParcelasGroup.style.display = 'none';
      checkboxItemRecorrente.style.display = 'none';
      itemRecorrenteInput.checked = false;
      infoItemParcelamento.style.display = 'none';
    }
  });
});

// Modal
function abrirModal() {
  modalAddItem.classList.add('active');
  document.body.style.overflow = 'hidden';
  document.getElementById('itemDescricao').value = '';
  document.getElementById('itemValor').value = '';
  itemNumeroParcelasInput.value = '';
  
  // Reset forma de pagamento do item
  toggleItemPaymentBtns.forEach(b => b.classList.remove('active'));
  toggleItemPaymentBtns[0].classList.add('active');
  formaPagamentoItem = 'vista';
  itemParcelasGroup.style.display = 'none';
  infoItemParcelamento.style.display = 'none';
  checkboxItemRecorrente.style.display = 'none';
  itemRecorrenteInput.checked = false;
  
  document.getElementById('itemDescricao').focus();
}

function fecharModal() {
  modalAddItem.classList.remove('active');
  document.body.style.overflow = 'auto';
}

btnAddItem?.addEventListener('click', abrirModal);
btnCloseModal?.addEventListener('click', fecharModal);
btnCancelarItem?.addEventListener('click', fecharModal);
modalOverlay?.addEventListener('click', fecharModal);

// Adicionar Item
btnSalvarItem?.addEventListener('click', () => {
  const descricao = document.getElementById('itemDescricao').value.trim();
  const valorStr = document.getElementById('itemValor').value.trim();
  
  if (!descricao || !valorStr) {
    mostrarErroModal('Preencha todos os campos do item');
    return;
  }
  
  const valor = parseFloat(valorStr.replace(/\./g, '').replace(',', '.'));
  
  if (isNaN(valor) || valor <= 0) {
    mostrarErroModal('Digite um valor válido');
    return;
  }
  
  const item = {
    id: Date.now(),
    descricao,
    valor,
    formaPagamento: formaPagamentoItem
  };
  
  if (formaPagamentoItem === 'parcelada') {
    const numeroParcelas = parseInt(itemNumeroParcelasInput.value);
    if (!numeroParcelas || numeroParcelas < 2) {
      mostrarErroModal('Digite o número de parcelas (mínimo 2)');
      return;
    }
    item.numeroParcelas = numeroParcelas;
    item.recorrente = itemRecorrenteInput.checked;
  }
  
  items.push(item);
  atualizarListaItens();
  fecharModal();
});

// Mostrar erro no modal
function mostrarErroModal(mensagem) {
  const itemDescricao = document.getElementById('itemDescricao');
  itemDescricao.style.borderColor = '#ff6b6b';
  itemDescricao.focus();
  
  setTimeout(() => {
    itemDescricao.style.borderColor = '';
  }, 2000);
}

// Atualizar lista de itens
function atualizarListaItens() {
  if (items.length === 0) {
    itemsList.innerHTML = `
      <div class="empty-state">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="12" y1="8" x2="12" y2="12"></line>
          <line x1="12" y1="16" x2="12.01" y2="16"></line>
        </svg>
        <p>Nenhum item adicionado ainda</p>
        <span>Clique em "Adicionar Item" para começar</span>
      </div>
    `;
    itemsSummary.style.display = 'none';
    valorTotalInput.value = '';
    return;
  }
  
  // Renderiza itens
  itemsList.innerHTML = items.map(item => {
    let infoPagamento = '';
    if (item.formaPagamento === 'parcelada' && item.numeroParcelas) {
      const valorParcela = item.valor / item.numeroParcelas;
      const textoRecorrente = item.recorrente ? ' (Recorrente)' : '';
      infoPagamento = `<div class="item-payment-info">Parcelada ${item.numeroParcelas}x de R$ ${formatarMoeda(valorParcela)}${textoRecorrente}</div>`;
    } else {
      infoPagamento = `<div class="item-payment-info">À Vista</div>`;
    }
    
    return `
      <div class="item-card" data-id="${item.id}">
        <div class="item-info">
          <div class="item-description">${item.descricao}</div>
          <div class="item-value">R$ ${formatarMoeda(item.valor)}</div>
          ${infoPagamento}
        </div>
        <button type="button" class="btn-remove-item" onclick="removerItem(${item.id})">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="3 6 5 6 21 6"></polyline>
            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
            <line x1="10" y1="11" x2="10" y2="17"></line>
            <line x1="14" y1="11" x2="14" y2="17"></line>
          </svg>
        </button>
      </div>
    `;
  }).join('');
  
  // Atualiza resumo
  const total = items.reduce((sum, item) => sum + item.valor, 0);
  totalItensSpan.textContent = items.length;
  valorTotalItensSpan.textContent = `R$ ${formatarMoeda(total)}`;
  itemsSummary.style.display = 'block';
  
  // Atualiza valor total principal
  valorTotalInput.value = formatarMoeda(total);
}

// Remover Item
function removerItem(id) {
  items = items.filter(item => item.id !== id);
  atualizarListaItens();
}

// Formatação de moeda
function formatarMoeda(valor) {
  return valor.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

function parseMoeda(valorStr) {
  return parseFloat(valorStr.replace(/\./g, '').replace(',', '.')) || 0;
}

// Máscaras de input
valorTotalInput?.addEventListener('input', function(e) {
  if (tipoDividaInput.value === 'composta') return; // Não formata se for composta
  
  let valor = e.target.value.replace(/\D/g, '');
  valor = (parseInt(valor) / 100).toFixed(2);
  e.target.value = formatarMoeda(parseFloat(valor));
});

document.getElementById('itemValor')?.addEventListener('input', function(e) {
  let valor = e.target.value.replace(/\D/g, '');
  valor = (parseInt(valor) / 100).toFixed(2);
  e.target.value = formatarMoeda(parseFloat(valor));
});

// Validações
function mostrarErro(campoId, mensagem) {
  const errorSpan = document.getElementById('error-' + campoId);
  const input = document.getElementById(campoId);
  if (errorSpan && input) {
    errorSpan.textContent = mensagem;
    errorSpan.style.display = 'block';
    input.classList.add('input-error');
  }
}

function limparErro(campoId) {
  const errorSpan = document.getElementById('error-' + campoId);
  const input = document.getElementById(campoId);
  if (errorSpan && input) {
    errorSpan.textContent = '';
    errorSpan.style.display = 'none';
    input.classList.remove('input-error');
  }
}

function limparTodosErros() {
  const campos = ['descricao', 'valorTotal', 'dataVencimento'];
  campos.forEach(campo => limparErro(campo));
}

// Validações em tempo real
document.getElementById('descricao')?.addEventListener('blur', function() {
  if (this.value.trim() === '') {
    mostrarErro('descricao', 'A descrição é obrigatória.');
  } else if (this.value.trim().length < 3) {
    mostrarErro('descricao', 'A descrição deve ter pelo menos 3 caracteres.');
  } else {
    limparErro('descricao');
  }
});

document.getElementById('descricao')?.addEventListener('input', function() {
  limparErro('descricao');
});

valorTotalInput?.addEventListener('blur', function() {
  if (tipoDividaInput.value === 'composta') return; // Não valida se for composta
  
  if (this.value.trim() === '') {
    mostrarErro('valorTotal', 'O valor total é obrigatório.');
  } else {
    const valor = parseMoeda(this.value);
    if (valor <= 0) {
      mostrarErro('valorTotal', 'Digite um valor maior que zero.');
    } else {
      limparErro('valorTotal');
    }
  }
});

valorTotalInput?.addEventListener('input', function() {
  limparErro('valorTotal');
});

document.getElementById('dataVencimento')?.addEventListener('blur', function() {
  if (this.value === '') {
    mostrarErro('dataVencimento', 'A data de vencimento é obrigatória.');
  } else {
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);
    const dataVencimento = new Date(this.value + 'T00:00:00');
    
    if (dataVencimento < hoje) {
      mostrarErro('dataVencimento', 'A data de vencimento não pode ser no passado.');
    } else {
      limparErro('dataVencimento');
    }
  }
});

document.getElementById('dataVencimento')?.addEventListener('input', function() {
  limparErro('dataVencimento');
});

// Validação no submit
formAdicionarDivida?.addEventListener('submit', function(e) {
  e.preventDefault();
  limparTodosErros();
  let temErro = false;
  
  const descricao = document.getElementById('descricao').value.trim();
  const valorTotal = valorTotalInput.value.trim();
  const dataVencimento = document.getElementById('dataVencimento').value;
  const tipoDivida = tipoDividaInput.value;
  
  // Validar descrição
  if (descricao === '' || descricao.length < 3) {
    mostrarErro('descricao', descricao === '' ? 'A descrição é obrigatória.' : 'A descrição deve ter pelo menos 3 caracteres.');
    temErro = true;
  }
  
  // Validar valor total
  if (tipoDivida === 'simples') {
    if (valorTotal === '') {
      mostrarErro('valorTotal', 'O valor total é obrigatório.');
      temErro = true;
    } else {
      const valor = parseMoeda(valorTotal);
      if (valor <= 0) {
        mostrarErro('valorTotal', 'Digite um valor maior que zero.');
        temErro = true;
      }
    }
  } else {
    // Dívida composta
    if (items.length === 0) {
      mostrarErro('valorTotal', 'Adicione pelo menos um item à dívida composta.');
      temErro = true;
    }
  }
  
  // Validar data
  if (dataVencimento === '') {
    mostrarErro('dataVencimento', 'A data de vencimento é obrigatória.');
    temErro = true;
  } else {
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);
    const dataVenc = new Date(dataVencimento + 'T00:00:00');
    
    if (dataVenc < hoje) {
      mostrarErro('dataVencimento', 'A data de vencimento não pode ser no passado.');
      temErro = true;
    }
  }
  
  if (temErro) {
    return false;
  }
  
  // Se for dívida composta, adiciona os itens ao formulário
  if (tipoDivida === 'composta') {
    items.forEach((item, index) => {
      const inputDescricao = document.createElement('input');
      inputDescricao.type = 'hidden';
      inputDescricao.name = `itens[${index}].descricao`;
      inputDescricao.value = item.descricao;
      this.appendChild(inputDescricao);
      
      const inputValor = document.createElement('input');
      inputValor.type = 'hidden';
      inputValor.name = `itens[${index}].valor`;
      inputValor.value = item.valor;
      this.appendChild(inputValor);
    });
  }
  
  // Converte valor formatado para número antes de enviar
  const valorNumerico = parseMoeda(valorTotal);
  const inputValorNumerico = document.createElement('input');
  inputValorNumerico.type = 'hidden';
  inputValorNumerico.name = 'valorNumerico';
  inputValorNumerico.value = valorNumerico;
  this.appendChild(inputValorNumerico);
  
  // Submit do formulário
  this.submit();
});

// Inicialização
document.addEventListener('DOMContentLoaded', function() {
  // Define data mínima como hoje
  const hoje = new Date().toISOString().split('T')[0];
  document.getElementById('dataVencimento').setAttribute('min', hoje);
});
