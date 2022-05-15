package ru.company.restaurantmenu.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {
    private final List<Advertisement> videos = new ArrayList<>();

    private AdvertisementStorage() {
        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        videos.add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        videos.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min

        videos.add(new Advertisement(someContent, "Четвертое", 40, 2, 1 * 60)); //10 min
        videos.add(new Advertisement(someContent, "пятое", 4, 1, 100 * 60)); //10 min
        videos.add(new Advertisement(someContent, "десятое", 50, 3, 20 * 60)); //10 min
    }

    private static class SingletonHelper {
        private static final AdvertisementStorage STORAGE = new AdvertisementStorage();
    }

    public static AdvertisementStorage getInstance() {
        return SingletonHelper.STORAGE;
    }

    public List<Advertisement> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }
}
