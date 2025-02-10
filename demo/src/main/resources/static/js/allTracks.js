/* allTracks.js */

// Глобальная переменная для хранения всех треков
let allTracksData = [];

// Функция для открытия модального окна с песнями
function openAllTracksModal() {
    const modal = document.getElementById("allTracksModal");
    modal.style.display = "block";
    loadAllTracks();
}

// Функция для закрытия модального окна
function closeAllTracksModal() {
    const modal = document.getElementById("allTracksModal");
    modal.style.display = "none";
}

// Функция для загрузки всех песен из бэкенда
function loadAllTracks() {
    fetch('/api/tracks', { method: 'GET' })
        .then(response => {
            if (!response.ok) throw new Error('Не удалось загрузить песни');
            return response.json();
        })
        .then(tracks => {
            allTracksData = tracks; // Сохраняем данные для фильтрации и плейлиста
            displayAllTracks(tracks);
        })
        .catch(error => {
            console.error(error);
            alert("Ошибка при загрузке песен");
        });
}

// Функция для отображения песен в модальном окне
// Функция для отображения песен в модальном окне
// Функция для отображения песен в модальном окне
// Функция для отображения песен в модальном окне
function displayAllTracks(tracks) {
    const container = document.getElementById('all-tracks-container');
    container.innerHTML = '';

    if (tracks.length === 0) {
        container.innerHTML = '<p>Нет доступных песен.</p>';
        return;
    }

    tracks.forEach(track => {
        const trackDiv = document.createElement('div');
        trackDiv.classList.add('track-item');

        // Изображение трека
        const img = document.createElement('img');
        img.src = track.image;
        img.alt = `${track.title} cover`;
        img.onerror = function() {
            this.onerror = null;
            this.src = '/images/img.png'; // Резервное изображение
        };
        img.classList.add('small-image'); // Добавлен класс

        // Название трека
        const titleDiv = document.createElement('div');
        titleDiv.classList.add('track-title');
        titleDiv.textContent = track.title;
        titleDiv.style.cursor = 'pointer';
        titleDiv.onclick = () => playTrack(track); // Используем функцию из main.js

        // Имя артиста
        const artistDiv = document.createElement('div');
        artistDiv.classList.add('track-artist');
        artistDiv.textContent = track.artistName;

        // Продолжительность
        const durationDiv = document.createElement('div');
        durationDiv.classList.add('track-duration');
        durationDiv.textContent = track.duration;

        // Кнопка "Лайк"
        const likeButton = document.createElement('button');
        likeButton.classList.add('like-button');
        likeButton.innerHTML = track.isLiked ? '❤️' : '🤍';
        likeButton.style.color = track.isLiked ? 'blue' : 'grey'; // Установка цвета
        likeButton.onclick = (e) => {
            e.stopPropagation(); // Предотвращаем срабатывание клика по треку
            toggleLike(track.id, likeButton);
        };

        // Добавление элементов в трек
        trackDiv.appendChild(img); // Добавляем изображение
        trackDiv.appendChild(titleDiv);
        trackDiv.appendChild(artistDiv);
        trackDiv.appendChild(durationDiv);
        trackDiv.appendChild(likeButton);

        container.appendChild(trackDiv);
    });
}




// Функция для фильтрации треков
function filterTracks() {
    const query = document.getElementById('all-tracks-search-input').value.toLowerCase();
    if (query === "") {
        displayAllTracks(allTracksData);
        return;
    }
    const filteredTracks = allTracksData.filter(track =>
        track.title.toLowerCase().includes(query) ||
        track.artistName.toLowerCase().includes(query)
    );
    displayAllTracks(filteredTracks);
}

// Функция для воспроизведения трека
function playTrack(track) {
    const index = allTracksData.findIndex(t => t.id === track.id);
    if (index === -1) return;

    currentPlaylist = allTracksData;
    currentSongIndex = index;
    loadSong(currentSongIndex);
    audioPlayer.play();
}

// Закрытие модального окна при клике вне его области
window.addEventListener('click', function(event) {
    const allTracksModal = document.getElementById("allTracksModal");
    if (event.target === allTracksModal) {
        closeAllTracksModal();
    }
});
