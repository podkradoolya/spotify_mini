/* home.js */

function showHome() {
  const dynamicContent = document.getElementById('dynamic-content');

  // 1. Получаем плейлисты пользователя с бэка
  fetch('/api/playlist', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Ошибка при получении плейлистов');
      }
      return response.json();
    })
    .then(userPlaylists => {
      // 2. Берём первые 6
      const firstSix = userPlaylists.slice(0, 6);

      // 3. Формируем HTML для карточек
      let playlistCardsHTML = '';
      firstSix.forEach(playlist => {
        const coverImg = playlist.coverImage || "/images/img.png";
        playlistCardsHTML += `
          <div class="home-playlist-card" onclick="getPlaylistDetails(${playlist.id})">
            <img src="${coverImg}" alt="${playlist.name} cover"
                 onerror="this.onerror=null;this.src='/images/img.png';"
                 class="small-image"/> <!-- Добавлен класс -->
            <span>${playlist.name}</span>
          </div>
        `;
      });

      // 4. Секция Also Listen (пример, у вас была)
      const alsoListenHTML = `
        <div class="also-listen">
          <h3>Also Listen</h3>
          <div class="album-list">
            <div class="album-item">
              <img src="/images/img.png" alt="Sample cover" class="small-image"/> <!-- Добавлен класс -->
              <h4>Playlist Name</h4>
              <h5>Artists in playlist</h5>
            </div>
            <div class="album-item">
              <img src="/images/img.png" alt="Sample cover" class="small-image"/> <!-- Добавлен класс -->
              <h4>Самая Самая</h4>
              <h5>Artists in playlist</h5>
            </div>
          </div>
        </div>
      `;

      // 5. Итоговый HTML для Home
      dynamicContent.innerHTML = `
        <div class="home-container">
          <div class="home-main">
            <!-- Верхняя зона: до 6 плейлистов (3 в первой строке, 3 во второй) -->
            <div class="home-playlists-wrapper">
              ${playlistCardsHTML}
            </div>

            <!-- Creators (горизонтальный скролл) -->
            <div class="creators-block">
              <h3>Creators</h3>
              <div class="creator-list">
                <div class="creator-item">
                  <img src="/images/img.png" alt="Artist Name" class="small-image"/> <!-- Добавлен класс -->
                  <h4>Artist Name</h4>
                  <h5>Artist</h5>
                </div>
                <div class="creator-item">
                  <img src="/images/img.png" alt="TV GIRL" class="small-image"/> <!-- Добавлен класс -->
                  <h4>Artist Name</h4>
                  <h5>Artists in playlist</h5>
                </div>
              </div>
            </div>

            <!-- Also Listen -->
            ${alsoListenHTML}

            <!-- Нижняя секция -->
            <div class="home-footer-section">
              <div class="group-info">
                <p><strong>Join Us</strong></p>
                <p>Group P022046</p>
                <p>Sharipov Aidar<br>Shegenov Ailan<br>Tubokpai Aslan</p>
                <p>Feel free to reach out to us for collaborations and projects!</p>
              </div>
              <div class="social-links">
                <a href="#" aria-label="Facebook">Facebook</a>
                <a href="#" aria-label="Twitter">Twitter</a>
                <a href="#" aria-label="Instagram">Instagram</a>
                <a href="#" aria-label="YouTube">YouTube</a>
              </div>
            </div>
          </div>
        </div>
      `;
    })
    .catch(error => {
      console.error('Ошибка в showHome():', error);
      alert('Не удалось загрузить плейлисты для главной страницы');
    });
}

/**
 * Пример logout()
 */
function logout() {
  window.location.href = '/logout';
}
