package ru.company.restaurantmenu.statistic;

import ru.company.restaurantmenu.statistic.event.CookedOrderEventDataRow;
import ru.company.restaurantmenu.statistic.event.EventDataRow;
import ru.company.restaurantmenu.statistic.event.EventType;
import ru.company.restaurantmenu.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {
    }

    private static class SingletonHelper {
        private static final StatisticManager STATISTIC_MANAGER = new StatisticManager();
    }

    public static StatisticManager getInstance() {
        return SingletonHelper.STATISTIC_MANAGER;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<Date, Double> calculateProfit() {
        Map<Date, Double> calc = new TreeMap<>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o2.compareTo(o1);
            }
        });
        Map<EventType, List<EventDataRow>> strg = statisticStorage.getStorage();
        List<EventDataRow> dataRowList = strg.get(EventType.SELECTED_VIDEOS);
        for (EventDataRow e : dataRowList) {

            Date currentDate = e.getDate();
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            double currentAmount = ((VideoSelectedEventDataRow) e).getAmount() / 100.00;
            if (calc.containsKey(calendar.getTime())) {
                currentAmount += calc.get(calendar.getTime());
            }
            calc.put(calendar.getTime(), currentAmount);
        }
        return calc;

    }

    public Map<Date, TreeMap<String, Integer>> calculateTimeOfWork() {
        Map<Date, TreeMap<String, Integer>> calc = new TreeMap<>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o2.compareTo(o1);
            }
        });
        Map<EventType, List<EventDataRow>> strg = statisticStorage.getStorage();
        List<EventDataRow> dataRowList = strg.get(EventType.COOKED_ORDER);
        for (EventDataRow e : dataRowList) {

            Date currentDate = e.getDate();
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            CookedOrderEventDataRow cookedOrderEventDataRow = (CookedOrderEventDataRow) e;
            String cookName = cookedOrderEventDataRow.getCookName();


            TreeMap<String, Integer> cookedForThisDay = calc.get(calendar.getTime());
            if (cookedForThisDay == null) {
                cookedForThisDay = new TreeMap<>();
                calc.put(calendar.getTime(), cookedForThisDay);
            }
            Integer timeCooked = cookedForThisDay.get(cookName);
            if (timeCooked == null) {
                timeCooked = e.getTime();
            } else {
                timeCooked += e.getTime();
            }
            cookedForThisDay.put(cookName, timeCooked);

        }
        return calc;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private StatisticStorage() {
            for (EventType event : EventType.values()) {
                storage.put(event, new ArrayList<>());
            }
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }
}
