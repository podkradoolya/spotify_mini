/* main.js */

const playlists = {
    likedSongs: [],
    kendrickLamar: [
        {
            "id": 1,
            "title": "HUMBLE.",
            "artistName": "Kendrick Lamar",
            "duration": "3:05",
            "audioSrc": "/songs/Kendrick_Lamar_DNA.mp3",
            "image": "/images/viyuga_menia_zamela.png",
            "isLiked": false,
            "info": "Humble description."
        },
        // ... остальные треки
    ]

    // ... остальные плейлисты
};

let currentPlaylist = [];
let currentSongIndex = 0;

const audioPlayer = document.getElementById('audio-player');
const progressBar = document.getElementById('progress-bar');
const currentTimeElem = document.getElementById('current-time');
const totalTimeElem = document.getElementById('total-time');

const footerSongTitle = document.getElementById('footer-song-title');
const footerSongArtist = document.getElementById('footer-song-artist');
const footerImage = document.getElementById('footer-image');

const playPauseBtn = document.getElementById('play-pause-btn');
let isPlaying = false;

// Объект для отслеживания попыток воспроизведения каждой песни
const songPlayAttempts = {};

/***************************************************
 *  При загрузке — показываем Home и настраиваем плеер
 ***************************************************/
window.onload = () => {
    showHome();
    setupAudioEvents();
};

/***************************************************
 *  Загрузка плейлиста
 ***************************************************/
function loadPlaylist(playlistName) {
    currentPlaylist = playlists[playlistName] || [];
    currentSongIndex = 0;
    if (currentPlaylist.length > 0) {
        loadSong(currentSongIndex);
    }
    showPlaylist(playlistName);
}

/***************************************************
 *  Загрузка конкретной песни
 ***************************************************/
function loadSong(index) {
    const songData = currentPlaylist[index];
    if (!songData) return;

    audioPlayer.src = songData.audioSrc;
    audioPlayer.load(); // Обязательно загрузите новый источник
    footerSongTitle.textContent = songData.title;
    footerSongArtist.textContent = songData.artistName;
    footerImage.src = songData.image;

    // Для центральной панели:
    document.getElementById('song-cover').src = songData.image;
    document.getElementById('song-title').textContent = songData.title;
    document.getElementById('song-artist').textContent = songData.artistName;
    document.getElementById('song-info').textContent = songData.info || "No additional info";

    // Сбрасываем прогресс
    audioPlayer.currentTime = 0;

    // Сбрасываем количество попыток для этой песни
    if (songPlayAttempts[songData.id]) {
        songPlayAttempts[songData.id] = 0;
    }
}

/***************************************************
 *  Управление плеером
 ***************************************************/
function togglePlayPause() {
    if (isPlaying) {
        audioPlayer.pause();
    } else {
        audioPlayer.play().catch(error => {
            console.error("Ошибка при воспроизведении:", error);
        });
    }
}

function previousSong() {
    if (currentPlaylist.length === 0) return;
    currentSongIndex = (currentSongIndex - 1 + currentPlaylist.length) % currentPlaylist.length;
    loadSong(currentSongIndex);
    audioPlayer.play();
}

function nextSong() {
    if (currentPlaylist.length === 0) return;
    currentSongIndex = (currentSongIndex + 1) % currentPlaylist.length;
    loadSong(currentSongIndex);
    audioPlayer.play();
}

/***************************************************
 *  Обработчики событий для аудио
 ***************************************************/
function setupAudioEvents() {
    // Обновление прогресс-бара при изменении времени трека
    audioPlayer.addEventListener('timeupdate', () => {
        if (!audioPlayer.duration) return;
        const progressPercent = (audioPlayer.currentTime / audioPlayer.duration) * 100;
        progressBar.value = progressPercent;
        currentTimeElem.textContent = formatTime(audioPlayer.currentTime);
        totalTimeElem.textContent = formatTime(audioPlayer.duration);
    });

    // Перемотка трека при изменении прогресс-бара
    progressBar.addEventListener('input', () => {
        if (!audioPlayer.duration) return;
        const seekTime = (progressBar.value / 100) * audioPlayer.duration;
        audioPlayer.currentTime = seekTime;
    });

    // Обработчики play и pause для обновления кнопки
    audioPlayer.addEventListener('play', () => {
        isPlaying = true;
        playPauseBtn.innerHTML = "&#10073;&#10073;"; // Pause icon
    });

    audioPlayer.addEventListener('pause', () => {
        isPlaying = false;
        playPauseBtn.innerHTML = "&#9658;"; // Play icon
    });

    // Автоматический переход к следующей песне по завершении текущей
    audioPlayer.addEventListener('ended', () => {
        nextSong();
    });

    // Обработчик ошибок при загрузке или воспроизведении песни
    audioPlayer.addEventListener('error', handleAudioError);
}

/***************************************************
 *  Обработчик ошибок аудио
 ***************************************************/
function handleAudioError() {
    const currentSong = currentPlaylist[currentSongIndex];
    console.error(`Ошибка при воспроизведении песни: ${currentSong.title}`);

    // Увеличиваем количество попыток воспроизведения для этой песни
    if (songPlayAttempts[currentSong.id]) {
        songPlayAttempts[currentSong.id]++;
    } else {
        songPlayAttempts[currentSong.id] = 1;
    }

    const maxAttempts = 2;

    if (songPlayAttempts[currentSong.id] <= maxAttempts) {
        // Повторная попытка воспроизведения той же песни
        console.log(`Повторная попытка воспроизведения песни: ${currentSong.title}`);
        audioPlayer.play().catch(error => console.error("Ошибка при повторном воспроизведении:", error));
    } else {
        // Переходим к следующей песне после превышения попыток
        alert(`Не удалось воспроизвести песню "${currentSong.title}". Переход к следующей песне.`);
        nextSong();

        // Проверим, не зациклились ли мы (все песни не воспроизводятся)
        const allAttemptsExceeded = currentPlaylist.every(song => songPlayAttempts[song.id] >= maxAttempts);
        if (allAttemptsExceeded) {
            alert("Не удалось воспроизвести ни одну из песен в текущем плейлисте.");
            audioPlayer.pause();
        }
    }
}

/***************************************************
 *  ГРОМКОСТЬ
 ***************************************************/
function changeVolume(value) {
    audioPlayer.volume = value / 100;
}

/***************************************************
 *  Форматирование времени (минуты:секунды)
 ***************************************************/
function formatTime(time) {
    const minutes = Math.floor(time / 60) || 0;
    const seconds = Math.floor(time % 60) || 0;
    return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
}

/***************************************************
 *  Функция для воспроизведения конкретного трека
 ***************************************************/
function playTrack(track) {
    console.log(`Выбран трек: ${track.title} от ${track.artistName}`);

    // Если текущий плейлист состоит из одного трека и он тот же, просто переключите воспроизведение
    if (currentPlaylist.length === 1 && currentPlaylist[0].id === track.id) {
        console.log("Тот же трек выбран, переключаем воспроизведение");
        togglePlayPause();
        return;
    }

    // Установить текущий плейлист как массив с одним треком
    currentPlaylist = [track];
    currentSongIndex = 0;
    loadSong(currentSongIndex);
    console.log(`Загружен трек: ${track.title}`);
    audioPlayer.play()
        .then(() => console.log(`Воспроизведение трека: ${track.title}`))
        .catch(error => console.error("Ошибка при воспроизведении:", error));
}
