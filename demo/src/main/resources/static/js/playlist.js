// playlist.js

// Вызывается при загрузке index.html (в самом конце .html или через DOMContentLoaded)
document.addEventListener('DOMContentLoaded', () => {
  loadUserPlaylists(); // загружаем плейлисты
});

/**
 * 1) Загрузить все плейлисты
 */
function loadUserPlaylists() {
  fetch('/api/playlist', { method: 'GET' })
    .then(resp => {
      if (!resp.ok) throw new Error('Failed to load playlists');
      return resp.json();
    })
    .then(list => {
      displayPlaylists(list);
    })
    .catch(err => {
      console.error(err);
      alert("Ошибка при загрузке плейлистов");
    });
}

/**
 * 2) Отобразить плейлисты в левом блоке (id="playlists-container")
 */
function displayPlaylists(playlists) {
  const container = document.getElementById('playlists-container');
  container.innerHTML = '';

  playlists.forEach(pl => {
    const card = document.createElement('div');
    card.classList.add('playlist');

    // Обложка
    const img = document.createElement('img');
    img.src = pl.coverImage || '/resources/img.png';
    img.alt = pl.name;
    img.classList.add('playlist-cover');

    // Добавляем обработчик ошибки загрузки изображения
    img.onerror = function() {
      this.onerror = null; // Предотвращаем бесконечный цикл в случае ошибки загрузки изображения по умолчанию
      this.src = '/resources/img.png'; // Путь к изображению по умолчанию
    };

    // Детали
    const detailsDiv = document.createElement('div');
    detailsDiv.classList.add('details');

    const titleSpan = document.createElement('span');
    titleSpan.classList.add('title');
    titleSpan.textContent = pl.name;

    const infoSpan = document.createElement('span');
    infoSpan.classList.add('info');
    infoSpan.textContent = `${pl.songsCount} songs`;

    detailsDiv.appendChild(titleSpan);
    detailsDiv.appendChild(infoSpan);

    card.appendChild(img);
    card.appendChild(detailsDiv);

    // При клике — показать детали плейлиста
    card.addEventListener('click', () => {
      getPlaylistDetails(pl.id);
    });

    container.appendChild(card);
  });
}

/**
 * 3) При клике на плейлист -> грузим детали /api/playlist/{id}
 */
function getPlaylistDetails(playlistId) {
  fetch(`/api/playlist/${playlistId}`, { method: 'GET' })
    .then(resp => {
      if (!resp.ok) throw new Error('Failed to get playlist details');
      return resp.json();
    })
    .then(playlist => {
      // отрисовываем таблицу треков + обложку
      showPlaylistDetails(playlist);
    })
    .catch(err => {
      console.error(err);
      alert("Ошибка при загрузке плейлиста");
    });
}

/**
 * 4) Показать детали плейлиста в правой/центральной области
 *    — используем тот же подход, что и в вашем сниппете
 */
function showPlaylistDetails(playlist) {
  const dynamicContent = document.getElementById("dynamic-content");

  // Обложка
  let coverImg = playlist.coverImage || "/resources/img.png";

  // Треки (если у вас playlist.tracks - массив объектов Track)
  const songs = playlist.tracks || [];

  // Формируем HTML для шапки
  let html = `
    <div class="playlist-ui">
      <div class="home-main">
        <div class="playlist-header">
          <img class="playlist-cover" src="${coverImg}" alt="Playlist cover" onerror=" this.src='/resources/img.png'"/>
          <div class="playlist-info-block">
            <h1 class="playlist-title">${playlist.name}</h1>
            <p class="playlist-subinfo">${playlist.songsCount} songs</p>
          </div>
        </div>

        <div class="playlist-table">
          <table>
            <thead>
              <tr>
                <th class="th-number">#</th>
                <th>Cover</th>
                <th>Title</th>
                <th>Artist</th>
                <th>Date</th>
                <th>Duration</th>
              </tr>
            </thead>
            <tbody>
  `;

  // Для каждой песни формируем строку
  songs.forEach((track, index) => {
    const cover = track.imagePath || "/resources/img.png";
    const title = track.title || "No title";
    const artist = track.artist ? track.artist.name : "Unknown";
    const dateAdded = track.dateAdded ? new Date(track.dateAdded).toLocaleDateString() : "";
    const duration = track.duration || "0:00";

    html += `
      <tr class="playlist-row" onclick="playTrackFromPlaylist(${index})">
        <td class="td-number">${index + 1}</td>
        <td><img class="track-cover" src="${cover}" alt="Track cover" onerror="this.onerror=null; this.src='/resources/song/img.png'"/></td>
        <td>${title}</td>
        <td>${artist}</td>
        <td>${dateAdded}</td>
        <td>${duration}</td>
      </tr>
    `;
  });

  html += `
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `;

  dynamicContent.innerHTML = html;
}

/**
 * 5) Создание нового плейлиста (через модалку)
 */
function createPlaylist() {
  const name = document.getElementById('playlist-name').value.trim();
  const description = document.getElementById('playlist-desc').value.trim();

  if (!name) {
    alert("Введите название плейлиста!");
    return;
  }

  fetch('/api/playlist', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, description })
  })
  .then(resp => {
    if (!resp.ok) {
      return resp.text().then(txt => { throw new Error(txt); });
    }
    return resp.json();
  })
  .then(newPl => {
    // Добавим в левую колонку
    addPlaylistToLeftPanel(newPl);

    // Очистим поля ввода
    document.getElementById('playlist-name').value = '';
    document.getElementById('playlist-desc').value = '';

    // Закроем модалку без сообщения
    closePlaylistModal();
  })
  .catch(err => {
    console.error(err);
    alert("Ошибка при создании плейлиста");
  });
}

function addPlaylistToLeftPanel(playlist) {
  const container = document.getElementById('playlists-container');

  const card = document.createElement('div');
  card.classList.add('playlist');

  const img = document.createElement('img');
  img.src = playlist.coverImage || '/resources/default_playlist.png';
  img.alt = playlist.name;
  img.classList.add('playlist-cover');

  // Добавляем обработчик ошибки загрузки изображения
  img.onerror = function() {
    this.onerror = null; // Предотвращаем бесконечный цикл
    this.src = '/resources/img.png'; // Путь к изображению по умолчанию
  };

  const detailsDiv = document.createElement('div');
  detailsDiv.classList.add('details');

  const titleSpan = document.createElement('span');
  titleSpan.classList.add('title');
  titleSpan.textContent = playlist.name;

  const infoSpan = document.createElement('span');
  infoSpan.classList.add('info');
  infoSpan.textContent = `${playlist.songsCount} songs`;

  detailsDiv.appendChild(titleSpan);
  detailsDiv.appendChild(infoSpan);

  card.appendChild(img);
  card.appendChild(detailsDiv);

  // При клике — показать детали плейлиста
  card.addEventListener('click', () => {
    getPlaylistDetails(playlist.id);
  });

  container.appendChild(card);
}

/**
 * Пример "playTrackFromPlaylist" — если хотим при клике на трек проигрывать
 * (нужно, чтобы playlist уже был загружен).
 */
function playTrackFromPlaylist(trackIndex) {
  if (currentPlaylist.length === 0) return;
  currentSongIndex = trackIndex;
  loadSong(currentSongIndex);
  audioPlayer.play();
}

/**
 * Дополнительные функции для управления профилем пользователя
 */
function loadUserProfile() {
    fetch('/api/users/me', { method: 'GET' })
        .then(resp => {
            if (!resp.ok) throw new Error('Ошибка при загрузке профиля пользователя');
            return resp.json();
        })
        .then(user => {
            displayUserProfile(user); // Отображаем данные профиля
        })
        .catch(err => {
            console.error(err);
            alert("Ошибка при загрузке профиля пользователя");
        });
}

/**
 * Отобразить данные профиля пользователя в модальном окне
 */
function displayUserProfile(user) {
    // Заполняем данные в модальном окне
    document.getElementById('profile-email').textContent = user.email;
    document.getElementById('profile-nickname').textContent = user.nickname;
    document.getElementById('profile-registration-date').textContent = new Date(user.registrationDate).toLocaleDateString();
    document.getElementById('profile-image').src = user.profileImage || '/resources/img.png';

    // Добавляем обработчик ошибки для изображения профиля
    const profileImage = document.getElementById('profile-image');
    profileImage.onerror = function() {
      this.onerror = null; // Предотвращаем бесконечный цикл
      this.src = '/resources/img.png'; // Путь к изображению по умолчанию
    };

    // Показываем модальное окно (предполагается, что функция showProfile уже реализована)
    showProfile();
}
