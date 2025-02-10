// registration.js

function displayLoginMessages() {
  const params = new URLSearchParams(window.location.search);
  const error = params.get('error');
  const logout = params.get('logout');

  let messageHTML = '';

  if (logout) {
    messageHTML += '<p class="success-message">Вы успешно вышли из системы.</p>';
  }
  if (error) {
    if (error === 'bad_credentials') {
      messageHTML += '<p class="error-message">Неверные учетные данные</p>';
      document.getElementById('email-group').classList.add('input-error');
      document.getElementById('password-group').classList.add('input-error');
    } else {
      const decodedError = decodeURIComponent(error);
      messageHTML += `<p class="error-message">${decodedError}</p>`;
      highlightErrorFields(decodedError);
    }
  }

  document.getElementById('message').innerHTML = messageHTML;
}

function displayRegisterMessages() {
  const params = new URLSearchParams(window.location.search);
  const error = params.get('error');

  if (!error) return;
  const decodedError = decodeURIComponent(error);
  let messageHTML = `<p class="error-message">${decodedError}</p>`;

  if (decodedError.includes('Email уже зарегистрирован')) {
    document.getElementById('email-group').classList.add('input-error');
  }
  if (decodedError.includes('Nickname уже используется')) {
    document.getElementById('username-group').classList.add('input-error');
  }

  document.getElementById('message').innerHTML = messageHTML;
}

function highlightErrorFields(errorMessage) {
  // Сброс предыдущих ошибок
  document.getElementById('email-group').classList.remove('input-error');
  document.getElementById('username-group').classList.remove('input-error');

  // Пример проверки текста ошибки
  if (errorMessage.includes("Email уже зарегистрирован")) {
    document.getElementById('email-group').classList.add('input-error');
  }
  if (errorMessage.includes("Nickname уже используется")) {
    document.getElementById('username-group').classList.add('input-error');
  }
}
