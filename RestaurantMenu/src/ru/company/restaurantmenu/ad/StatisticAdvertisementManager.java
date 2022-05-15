package ru.company.restaurantmenu.ad;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {

    }

    private static class SingletonHelper {
        private static final StatisticAdvertisementManager STATISTIC_ADVERTISEMENT_MANAGER = new StatisticAdvertisementManager();
    }

    public static StatisticAdvertisementManager getInstance() {
        return SingletonHelper.STATISTIC_ADVERTISEMENT_MANAGER;
    }

    public List<Advertisement> getActiveVideoSet() {

        return advertisementStorage.list()
                .stream()
                .filter(advertisement -> advertisement.getHits() >= 1)
                .sorted(new Comparator<Advertisement>() {
                    @Override
                    public int compare(Advertisement o1, Advertisement o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Advertisement> getArchivedVideoSet() {
        return advertisementStorage.list()
                .stream()
                .filter(advertisement -> advertisement.getHits() == 0)
                .sorted(new Comparator<Advertisement>() {
                    @Override
                    public int compare(Advertisement o1, Advertisement o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                })
                .collect(Collectors.toList());
    }
}
