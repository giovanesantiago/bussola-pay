// Função para exibir erro
function mostrarErro(campoId, mensagem) {
  const errorSpan = document.getElementById('error-' + campoId);
  const input = document.getElementById(campoId);
  errorSpan.textContent = mensagem;
  errorSpan.style.display = 'block';
  input.classList.add('input-error');
}

// Função para limpar erro
function limparErro(campoId) {
  const errorSpan = document.getElementById('error-' + campoId);
  const input = document.getElementById(campoId);
  errorSpan.textContent = '';
  errorSpan.style.display = 'none';
  input.classList.remove('input-error');
}

// Função para limpar todos os erros
function limparTodosErros() {
  const campos = ['nomeCompleto', 'dataNascimento', 'email', 'cpf', 'senha', 'confirmarSenha', 'termos'];
  campos.forEach(campo => limparErro(campo));
}

// Validação de CPF
function validarCPF(cpf) {
  cpf = cpf.replace(/\D/g, '');
  if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;
  
  let soma = 0, resto;
  
  for (let i = 1; i <= 9; i++) {
    soma += parseInt(cpf.substring(i - 1, i)) * (11 - i);
  }
  resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;
  if (resto !== parseInt(cpf.substring(9, 10))) return false;
  
  soma = 0;
  for (let i = 1; i <= 10; i++) {
    soma += parseInt(cpf.substring(i - 1, i)) * (12 - i);
  }
  resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;
  if (resto !== parseInt(cpf.substring(10, 11))) return false;
  
  return true;
}

// Máscara de CPF
document.getElementById('cpf').addEventListener('input', function (e) {
  let value = e.target.value.replace(/\D/g, '');
  if (value.length <= 11) {
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    e.target.value = value;
  }
  limparErro('cpf');
});

// Validações em tempo real
document.getElementById('nomeCompleto').addEventListener('blur', function() {
  if (this.value.trim() === '') {
    mostrarErro('nomeCompleto', 'O nome completo é obrigatório.');
  } else if (this.value.trim().length < 3) {
    mostrarErro('nomeCompleto', 'O nome deve ter pelo menos 3 caracteres.');
  } else {
    limparErro('nomeCompleto');
  }
});

document.getElementById('dataNascimento').addEventListener('blur', function() {
  if (this.value === '') {
    mostrarErro('dataNascimento', 'A data de nascimento é obrigatória.');
  } else {
    const hoje = new Date();
    const nascimento = new Date(this.value);
    const idade = hoje.getFullYear() - nascimento.getFullYear();
    if (idade < 16) {
      mostrarErro('dataNascimento', 'Você deve ter pelo menos 16 anos.');
    } else {
      limparErro('dataNascimento');
    }
  }
});

document.getElementById('email').addEventListener('blur', function() {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (this.value.trim() === '') {
    mostrarErro('email', 'O e-mail é obrigatório.');
  } else if (!emailRegex.test(this.value)) {
    mostrarErro('email', 'Digite um e-mail válido.');
  } else {
    limparErro('email');
  }
});

document.getElementById('cpf').addEventListener('blur', function() {
  if (this.value.trim() === '') {
    mostrarErro('cpf', 'O CPF é obrigatório.');
  } else if (!validarCPF(this.value)) {
    mostrarErro('cpf', 'CPF inválido.');
  } else {
    limparErro('cpf');
  }
});

document.getElementById('senha').addEventListener('blur', function() {
  if (this.value === '') {
    mostrarErro('senha', 'A senha é obrigatória.');
  } else if (this.value.length < 6) {
    mostrarErro('senha', 'A senha deve ter pelo menos 6 caracteres.');
  } else {
    limparErro('senha');
  }
});

document.getElementById('confirmarSenha').addEventListener('blur', function() {
  const senha = document.getElementById('senha').value;
  if (this.value === '') {
    mostrarErro('confirmarSenha', 'Confirme sua senha.');
  } else if (this.value !== senha) {
    mostrarErro('confirmarSenha', 'As senhas não coincidem.');
  } else {
    limparErro('confirmarSenha');
  }
});

// Limpar erros ao digitar
const inputs = document.querySelectorAll('.form-input');
inputs.forEach(input => {
  input.addEventListener('input', function() {
    if (this.id !== 'cpf') {
      limparErro(this.id);
    }
  });
});

// Validação no submit
document.querySelector('form').addEventListener('submit', function (e) {
  limparTodosErros();
  let temErro = false;

  const nomeCompleto = document.getElementById('nomeCompleto').value.trim();
  const dataNascimento = document.getElementById('dataNascimento').value;
  const email = document.getElementById('email').value.trim();
  const cpf = document.getElementById('cpf').value;
  const senha = document.getElementById('senha').value;
  const confirmarSenha = document.getElementById('confirmarSenha').value;
  const termos = document.getElementById('termos').checked;

  if (nomeCompleto === '' || nomeCompleto.length < 3) {
    mostrarErro('nomeCompleto', nomeCompleto === '' ? 'O nome completo é obrigatório.' : 'O nome deve ter pelo menos 3 caracteres.');
    temErro = true;
  }

  if (dataNascimento === '') {
    mostrarErro('dataNascimento', 'A data de nascimento é obrigatória.');
    temErro = true;
  } else {
    const hoje = new Date();
    const nascimento = new Date(dataNascimento);
    const idade = hoje.getFullYear() - nascimento.getFullYear();
    if (idade < 18) {
      mostrarErro('dataNascimento', 'Você deve ter pelo menos 18 anos.');
      temErro = true;
    }
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (email === '') {
    mostrarErro('email', 'O e-mail é obrigatório.');
    temErro = true;
  } else if (!emailRegex.test(email)) {
    mostrarErro('email', 'Digite um e-mail válido.');
    temErro = true;
  }

  if (cpf === '' || !validarCPF(cpf)) {
    mostrarErro('cpf', cpf === '' ? 'O CPF é obrigatório.' : 'CPF inválido.');
    temErro = true;
  }

  if (senha === '' || senha.length < 6) {
    mostrarErro('senha', senha === '' ? 'A senha é obrigatória.' : 'A senha deve ter pelo menos 6 caracteres.');
    temErro = true;
  }

  if (confirmarSenha === '' || senha !== confirmarSenha) {
    mostrarErro('confirmarSenha', confirmarSenha === '' ? 'Confirme sua senha.' : 'As senhas não coincidem.');
    temErro = true;
  }

  if (!termos) {
    mostrarErro('termos', 'Você deve aceitar os termos de uso.');
    temErro = true;
  }

  if (temErro) {
    e.preventDefault();
    return false;
  }
});
