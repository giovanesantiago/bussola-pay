// Elementos do DOM
const btnNotificacoes = document.getElementById('btnNotificacoes');
const btnMenu = document.getElementById('btnMenu');
const btnAddDivida = document.getElementById('btnAddDivida');
const dayCards = document.querySelectorAll('.day-card');

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
dayCards.forEach((card, index) => {
  card.addEventListener('click', function() {
    const dayDate = this.querySelector('.day-date').textContent;
    // TODO: Implementar navegação para detalhes do dia
    // window.location.href = `/mv/dia/${dayDate}`;
  });
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
