// svoe.js

// Выбираем все панели, которые можно растягивать
const resizablePanels = document.querySelectorAll('.resizable');

resizablePanels.forEach((panel) => {
    let isResizing = false;
    let startX;
    let startWidth;

    panel.addEventListener('mousedown', (e) => {
        // Проверяем, попали ли в область хвата справа
        if (e.offsetX > panel.offsetWidth - 10) {
            isResizing = true;
            startX = e.clientX;
            startWidth = panel.offsetWidth;

            // Отключаем выделение текста
            document.body.classList.add('no-select');
            document.body.style.cursor = 'ew-resize'; // Меняем курсор
        }
    });

    document.addEventListener('mousemove', (e) => {
        if (!isResizing) return;

        const newWidth = startWidth + (e.clientX - startX);
        if (newWidth >= 200 && newWidth <= 600) { // Учитываем минимальную и максимальную ширину
            panel.style.width = `${newWidth}px`;

            // Убедимся, что панель сохраняет внутренний скроллинг
            panel.style.overflow = 'auto';
        }
    });

    document.addEventListener('mouseup', () => {
        if (isResizing) {
            isResizing = false;

            // Возвращаем возможность выделения текста
            document.body.classList.remove('no-select');
            document.body.style.cursor = 'default'; // Сбрасываем курсор
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const appLogo = document.getElementById('app-logo');
    const appMenu = document.getElementById('app-menu');

    // Функция для переключения видимости меню
    function toggleMenu() {
        appMenu.classList.toggle('show');
    }

    // Открыть меню при клике на иконку
    appLogo.addEventListener('click', function(event) {
        event.stopPropagation(); // Предотвращаем всплытие события
        toggleMenu();
    });

    // Закрыть меню, если клик вне его
    document.addEventListener('click', function(event) {
        if (!appMenu.contains(event.target) && event.target !== appLogo) {
            appMenu.classList.remove('show');
        }
    });

    // Закрыть меню при уходе курсора за пределы меню
    appMenu.addEventListener('mouseleave', function() {
        appMenu.classList.remove('show');
    });
});

// Пример функций для кнопок меню
function showSettings() {
    // Реализуйте логику отображения настроек
    console.log('Settings clicked');
}

function showProfile() {
    // Реализуйте логику отображения профиля
    console.log('Profile clicked');
}

/***************************************************
 *  Модальные окна для плейлистов и треков
 ***************************************************/

// Модальное окно для плейлистов
function openPlaylistModal() {
    const modal = document.getElementById("createPlaylistModal");
    modal.style.display = "block";
}

function closePlaylistModal() {
    const modal = document.getElementById("createPlaylistModal");
    modal.style.display = "none";
}

// Модальное окно для треков (если необходимо)
function openTrackModal() {
    const modal = document.getElementById("trackModal");
    modal.style.display = "block";
}

function closeTrackModal() {
    const modal = document.getElementById("trackModal");
    modal.style.display = "none";
}
