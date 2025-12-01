// Elementos do DOM
const btnNotificacoes = document.getElementById('btnNotificacoes');
const btnMenu = document.getElementById('btnMenu');
const btnAddDivida = document.getElementById('btnAddDivida');
const dayCards = document.querySelectorAll('.day-card');

// Event Listeners para o Header
btnNotificacoes.addEventListener('click', function() {
  console.log('Abrir notificações');
  // TODO: Implementar abertura de painel de notificações
  alert('Funcionalidade de notificações em desenvolvimento');
});

btnMenu.addEventListener('click', function() {
  console.log('Abrir menu');
  // TODO: Implementar abertura do menu lateral ou dropdown
  alert('Funcionalidade de menu em desenvolvimento');
});

// Event Listener para o Botão Flutuante (FAB)
btnAddDivida.addEventListener('click', function() {
  console.log('Adicionar nova dívida');
  // TODO: Redirecionar para Tela 10 (Adicionar Dívida)
  window.location.href = '/mv/adicionar/divida';
});

// Event Listeners para os Cards de Dia
dayCards.forEach((card, index) => {
  card.addEventListener('click', function() {
    console.log(`Card do dia ${index + 1} clicado`);
    // TODO: Implementar navegação para detalhes do dia
    // Pode redirecionar para uma tela com todas as dívidas daquele dia
    const dayDate = this.querySelector('.day-date').textContent;
    console.log(`Visualizar detalhes do dia ${dayDate}`);
    // window.location.href = `/mv/dia/${dayDate}`;
  });
});

// Função para formatar valores monetários
function formatarValor(valor) {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  }).format(valor);
}

// Função para atualizar um card de dia (útil para futuras integrações com backend)
function atualizarCard(cardIndex, dados) {
  const card = dayCards[cardIndex];
  if (!card) return;

  const dayLabel = card.querySelector('.day-label');
  const dayDate = card.querySelector('.day-date');
  const valueElement = card.querySelector('.day-amount .value');
  const currencyElement = card.querySelector('.day-amount .currency');
  const amountLabel = card.querySelector('.amount-label');
  const debtIndicator = card.querySelector('.debt-indicator');

  // Atualizar data
  if (dados.label) dayLabel.textContent = dados.label;
  if (dados.date) dayDate.textContent = dados.date;

  // Atualizar valor e quantidade de contas
  if (dados.totalAmount > 0) {
    if (currencyElement) currencyElement.textContent = 'R$';
    valueElement.textContent = dados.totalAmount.toLocaleString('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });
    if (amountLabel) amountLabel.textContent = 'a pagar';

    // Atualizar indicador de dívidas
    const debtCount = dados.debtCount || 0;
    const debtText = debtCount === 1 ? '1 conta vence' : `${debtCount} contas vencem`;
    if (dados.label && dados.label.includes('HOJE')) {
      debtIndicator.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
          <line x1="16" y1="2" x2="16" y2="6"></line>
          <line x1="8" y1="2" x2="8" y2="6"></line>
          <line x1="3" y1="10" x2="21" y2="10"></line>
        </svg>
        <span>${debtCount} contas vencem hoje</span>
      `;
    } else {
      debtIndicator.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
          <line x1="16" y1="2" x2="16" y2="6"></line>
          <line x1="8" y1="2" x2="8" y2="6"></line>
          <line x1="3" y1="10" x2="21" y2="10"></line>
        </svg>
        <span>${debtText}</span>
      `;
    }
    debtIndicator.classList.remove('success');
  } else {
    // Sem contas neste dia
    const dayBody = card.querySelector('.day-body');
    dayBody.innerHTML = `
      <div class="day-amount no-debts">
        <span class="value">Sem contas</span>
      </div>
    `;
    debtIndicator.classList.add('success');
    debtIndicator.innerHTML = `
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <polyline points="20 6 9 17 4 12"></polyline>
      </svg>
      <span>Tudo certo!</span>
    `;
  }
}

// Função para carregar dados do backend (exemplo)
async function carregarDadosDashboard() {
  try {
    // TODO: Substituir por chamada real ao backend
    // const response = await fetch('/api/dashboard/proximos-dias');
    // const dados = await response.json();
    
    // Exemplo de dados mockados para desenvolvimento:
    const dadosMockados = [
      { label: 'HOJE', date: '04/11', totalAmount: 1500.00, debtCount: 3 },
      { label: 'AMANHÃ', date: '05/11', totalAmount: 850.00, debtCount: 2 },
      { label: 'QUA', date: '06/11', totalAmount: 320.00, debtCount: 1 },
      { label: 'QUI', date: '07/11', totalAmount: 0, debtCount: 0 },
      { label: 'SEX', date: '08/11', totalAmount: 2100.00, debtCount: 4 }
    ];

    // Atualizar cards com os dados
    // dadosMockados.forEach((dados, index) => {
    //   atualizarCard(index, dados);
    // });
    
    console.log('Dados do dashboard carregados:', dadosMockados);
  } catch (error) {
    console.error('Erro ao carregar dados do dashboard:', error);
  }
}

// Adicionar efeito de ripple nos botões
function criarRipple(event) {
  const button = event.currentTarget;
  const ripple = document.createElement('span');
  const rect = button.getBoundingClientRect();
  const size = Math.max(rect.width, rect.height);
  const x = event.clientX - rect.left - size / 2;
  const y = event.clientY - rect.top - size / 2;

  ripple.style.width = ripple.style.height = size + 'px';
  ripple.style.left = x + 'px';
  ripple.style.top = y + 'px';
  ripple.classList.add('ripple');

  button.appendChild(ripple);

  setTimeout(() => {
    ripple.remove();
  }, 600);
}

// Aplicar efeito ripple nos botões
const buttons = document.querySelectorAll('.icon-button, .fab');
buttons.forEach(button => {
  button.addEventListener('click', criarRipple);
});

// Inicialização
document.addEventListener('DOMContentLoaded', function() {
  console.log('Dashboard carregado');
  // carregarDadosDashboard();
});
