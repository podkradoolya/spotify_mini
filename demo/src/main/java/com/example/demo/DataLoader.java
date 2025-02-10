// DataLoader.java

package com.example.demo;

import com.example.demo.model.Artist;
import com.example.demo.model.Track;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, есть ли уже треки в базе данных
        if (trackRepository.count() == 0) {
            loadTracks();
        }
    }

    private void loadTracks() {
        // Создаём исполнителей
        Artist Armich = createArtist("Armich");
        Artist Kanye = createArtist("Kanye West");
        Artist Kendrick = createArtist("Kendrick Lamar");
        Artist Irina = createArtist("Irina Kairatovna");
        Artist kislo = createArtist("Kislo-sladki");
        Artist Maslo = createArtist("Maslo chernogo tmina");

        // Добавляем треки

        // 1. Armich
        trackRepository.save(new Track(
                new Date(),
                "Песня с ретро-вибрацией 80-х, рассказывающая о тоске по любви.",
                "3:20",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                "/images/viyuga_menia_zamela.png",
                "Slow mo",
                Armich
        ));

        // 2. Kanye West
        trackRepository.save(new Track(
                new Date(),
                "Лиричная композиция о сожалении и утраченных отношениях.",
                "3:45",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
                "/images/apparat_presidenta.jpg",
                "I Wonder",
                Kanye
        ));

        // 3. Kendrick Lamar
        trackRepository.save(new Track(
                new Date(),
                "Зажигательный поп-хит о безудержной любви и лёгкости чувств.",
                "3:23",
                "/songs/Kendrick_Lamar_DNA.mp3",
                "/images/armich-feat-skriptonit-slow-mo-acoustic-live.jpeg",
                "DNA",
                Kendrick
        ));

        // 4. Kendrick Lamar
        trackRepository.save(new Track(
                new Date(),
                "Гимн самодостаточности после разрыва.",
                "3:03",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
                "/images/bs_korob.jpg",
                "Not Like Us",
                Kendrick
        ));

        // 5. Kanye West
        trackRepository.save(new Track(
                new Date(),
                "Провокационная песня с ироничными текстами о самоуверенности.",
                "3:14",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
                "/images/ia_tancuyu_vas.png",
                "Runaway",
                Kanye
        ));

        // 6. Maslo chernogo tmina
        trackRepository.save(new Track(
                new Date(),
                "Грустная баллада о разочаровании в отношениях.",
                "4:08",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
                "/images/kok_tu.jpg",
                "ya_tancuyu",
                Maslo
        ));

        // 7. Irina Kairatovna
        trackRepository.save(new Track(
                new Date(),
                "Весёлая и романтичная песня о девушке в красном платье, которая запала в душу.",
                "3:50",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3",
                "/images/loshadka.jpg",
                "KOK TU",
                Irina
        ));

        // 8. Irina Kairatovna
        trackRepository.save(new Track(
                new Date(),
                "Ожидание любви и надежда на встречу с тем самым человеком.",
                "3:35",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3",
                "/images/ne_angime.jpg",
                "Otyzdan asyp baramyn",
                Irina
        ));

        // 9. Irina Kairatovna
        trackRepository.save(new Track(
                new Date(),
                "Признание в любви с глубокими эмоциями.",
                "3:10",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3", // Убедитесь, что ссылка правильная
                "/images/not_like_us.jpg",
                "NE ÁŃGIME",
                Irina
        ));

        // 10. Kislo-sladki
        trackRepository.save(new Track(
                new Date(),
                "Ожидание любви и желание найти своего человека.",
                "3:45",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3", // Убедитесь, что ссылка правильная
                "/images/OK.jpg",
                "OK",
                kislo
        ));

        // 11. Kislo-sladki
        trackRepository.save(new Track(
                new Date(),
                "Лирическая композиция, восхваляющая нежность и чистоту возлюбленной.",
                "3:25",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-11.mp3", // Убедитесь, что ссылка правильная
                "/images/on_sight.jpg",
                "BS Korob",
                kislo
        ));

        // 12. Kislo-sladki
        trackRepository.save(new Track(
                new Date(),
                "Песня о девушке из рода Қоңырат, в которой выражается уважение к её культуре и красоте.",
                "3:15",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3", // Убедитесь, что ссылка правильная
                "/images/otyzdan_asyp_baramyn.jpg",
                "Bugin-Erten",
                kislo
        ));

        // 13. Kislo-sladki
        trackRepository.save(new Track(
                new Date(),
                "Шуточная песня о свадебных традициях и приключениях жениха.",
                "2:50",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-13.mp3", // Исправлена ссылка
                "/images/run_away.jpg",
                "Ход Joq",
                kislo
        ));

        // 14. Maslo chernogo tmina
        trackRepository.save(new Track(
                new Date(),
                "О сильных чувствах, которые невозможно скрыть, словно взлетающий космический корабль.",
                "3:40",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-14.mp3", // Убедитесь, что ссылка правильная
                "/images/apparat_presidenta.jpg",
                "аппарат президента",
                Maslo
        ));

        // 15. Maslo chernogo tmina
        trackRepository.save(new Track(
                new Date(),
                "Танцевальная композиция о мечте жениться на богатой девушке.",
                "3:05",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3", // Убедитесь, что ссылка правильная
                "/images/apparat_presidenta.jpg",
                "вьюга меня замела",
                Maslo
        ));

        System.out.println("Треки успешно загружены в базу данных.");
    }

    private Artist createArtist(String name) {
        return artistRepository.findByName(name).orElseGet(() -> {
            Artist artist = new Artist();
            artist.setName(name);
            return artistRepository.save(artist);
        });
    }
}
