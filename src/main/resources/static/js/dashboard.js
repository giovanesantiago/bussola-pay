// Elementos do DOM
const btnNotificacoes = document.getElementById('btnNotificacoes');
const btnMenu = document.getElementById('btnMenu');
const btnAddDivida = document.getElementById('btnAddDivida');
const dayCards = document.querySelectorAll('.day-card:not(.filter-card)');
const filterCard = document.getElementById('filterCard');
const dividasSection = document.getElementById('dividasSection');
const dividasTableBody = document.getElementById('dividasTableBody');
const dividasSubtitle = document.getElementById('dividasSubtitle');

// Elementos do Modal de Filtro
const modalFilter = document.getElementById('modalFilter');
const modalFilterOverlay = document.getElementById('modalFilterOverlay');
const btnCloseFilter = document.getElementById('btnCloseFilter');
const btnCancelarFilter = document.getElementById('btnCancelarFilter');
const btnAplicarFilter = document.getElementById('btnAplicarFilter');
const formFilter = document.getElementById('formFilter');

// Dados das dívidas serão injetados pelo Thymeleaf no HTML
// A variável resumoDiarioData será definida em um script inline antes deste arquivo

// Event Listeners para o Header
btnNotificacoes.addEventListener('click', function() {
  // TODO: Implementar abertura de painel de notificações
  window.location.href = '/notificacoes';
});

btnMenu.addEventListener('click', function() {
  // TODO: Implementar abertura do menu lateral ou dropdown
  window.location.href = '/menu';
});

// Event Listener para o Botão Flutuante (FAB)
btnAddDivida.addEventListener('click', function() {
  window.location.href = '/adicionar/divida';
});

// Event Listeners para os Cards de Dia
dayCards.forEach((card) => {
  card.addEventListener('click', function() {
    const index = parseInt(this.getAttribute('data-index'));
    const dayDate = this.querySelector('.day-date').textContent;
    const dayLabel = this.querySelector('.day-label').textContent;
    
    // Remove classe active de todos os cards
    dayCards.forEach(c => c.classList.remove('active'));
    
    // Adiciona classe active no card clicado
    this.classList.add('active');
    
    // Exibe a seção de dívidas
    mostrarDividas(index, dayLabel, dayDate);
  });
});

// Função para mostrar dívidas na tabela
function mostrarDividas(diaIndex, dayLabel, dayDate) {
  // Busca as dívidas do dia selecionado (assumindo que resumoDiarioData está disponível)
  const dia = resumoDiarioData && resumoDiarioData[diaIndex];
  const dividas = dia ? dia.dividas : [];
  
  // Atualiza o subtítulo
  dividasSubtitle.textContent = `Dívidas de ${dayLabel} - ${dayDate}`;
  
  // Exibe a seção
  dividasSection.style.display = 'block';
  
  // Limpa a tabela
  dividasTableBody.innerHTML = '';
  
  // Se não houver dívidas, mostra estado vazio
  if (!dividas || dividas.length === 0) {
    dividasTableBody.innerHTML = `
      <tr class="empty-state-row">
        <td colspan="3">
          <div class="empty-state-table">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10"></circle>
              <line x1="12" y1="8" x2="12" y2="12"></line>
              <line x1="12" y1="16" x2="12.01" y2="16"></line>
            </svg>
            <p>Nenhuma dívida encontrada</p>
            <span>Não há dívidas para este dia</span>
          </div>
        </td>
      </tr>
    `;
    return;
  }
  
  // Renderiza as dívidas
  dividas.forEach(divida => {
    const row = document.createElement('tr');
    row.setAttribute('data-id', divida.id);
    
    // Determina o badge de status
    let statusBadge = ``;
    const statusLower = divida.status.toLowerCase();
    
    if (statusLower === 'paga') {
      statusBadge = `
        <span class="status-badge paga">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="20 6 9 17 4 12"></polyline>
          </svg>
          Paga
        </span>
      `;
    } else if (statusLower === 'atrasada') {
      statusBadge = `
        <span class="status-badge atrasada">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"></circle>
            <line x1="12" y1="8" x2="12" y2="12"></line>
            <line x1="12" y1="16" x2="12.01" y2="16"></line>
          </svg>
          Atrasada
        </span>
      `;
    } else {
      statusBadge = `
        <span class="status-badge pendente">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"></circle>
            <polyline points="12 6 12 12 16 14"></polyline>
          </svg>
          Pendente
        </span>
      `;
    }
    
    row.innerHTML = `
      <td>${divida.descricao}</td>
      <td>R$ ${divida.valor}</td>
      <td>${statusBadge}</td>
    `;
    
    dividasTableBody.appendChild(row);
  });
  
  // Scroll suave até a tabela
  setTimeout(() => {
    dividasSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
  }, 100);
}

// Modal de Filtro Personalizado
function abrirModalFiltro() {
  modalFilter.classList.add('active');
  document.body.style.overflow = 'hidden';
  
  // Define data de hoje como mínimo
  const hoje = new Date().toISOString().split('T')[0];
  document.getElementById('dataInicio').setAttribute('min', hoje);
  document.getElementById('dataFinal').setAttribute('min', hoje);
}

function fecharModalFiltro() {
  modalFilter.classList.remove('active');
  document.body.style.overflow = 'auto';
}

// Event Listeners do Modal de Filtro
filterCard?.addEventListener('click', abrirModalFiltro);
btnCloseFilter?.addEventListener('click', fecharModalFiltro);
btnCancelarFilter?.addEventListener('click', fecharModalFiltro);
modalFilterOverlay?.addEventListener('click', fecharModalFiltro);

// Sincronizar checkboxes com inputs hidden
document.getElementById('filterPendente')?.addEventListener('change', function() {
  document.getElementById('hiddenPendente').value = this.checked ? 'true' : 'false';
});

document.getElementById('filterVencidas')?.addEventListener('change', function() {
  document.getElementById('hiddenVencida').value = this.checked ? 'true' : 'false';
});

document.getElementById('filterPaga')?.addEventListener('change', function() {
  document.getElementById('hiddenPaga').value = this.checked ? 'true' : 'false';
});

// Inicializar valores dos hidden inputs
document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('hiddenPendente').value = document.getElementById('filterPendente').checked ? 'true' : 'false';
  document.getElementById('hiddenVencida').value = document.getElementById('filterVencidas').checked ? 'true' : 'false';
  document.getElementById('hiddenPaga').value = document.getElementById('filterPaga').checked ? 'true' : 'false';
});

// Validação do formulário antes de submeter
formFilter?.addEventListener('submit', function(e) {
  const dataInicio = document.getElementById('dataInicio').value;
  const dataFinal = document.getElementById('dataFinal').value;
  const pendente = document.getElementById('filterPendente').checked;
  const vencidas = document.getElementById('filterVencidas').checked;
  const paga = document.getElementById('filterPaga').checked;
  
  if (!dataInicio || !dataFinal) {
    e.preventDefault();
    alert('Preencha as datas de início e fim');
    return false;
  }
  
  if (new Date(dataInicio) > new Date(dataFinal)) {
    e.preventDefault();
    alert('A data inicial não pode ser maior que a data final');
    return false;
  }
  
  if (!pendente && !vencidas && !paga) {
    e.preventDefault();
    alert('Selecione pelo menos um status');
    return false;
  }
  
  return true;
});

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
  // Página carregada
});
