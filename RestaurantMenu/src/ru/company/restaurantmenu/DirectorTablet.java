package ru.company.restaurantmenu;

import ru.company.restaurantmenu.ad.Advertisement;
import ru.company.restaurantmenu.ad.StatisticAdvertisementManager;
import ru.company.restaurantmenu.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DirectorTablet {
    private StatisticManager statisticManager = StatisticManager.getInstance();
    private StatisticAdvertisementManager statisticAdvertisementManager = StatisticAdvertisementManager.getInstance();

    public void printAdvertisementProfit() {

        Map<Date, Double> advProfit = StatisticManager.getInstance().calculateProfit();

        double sum = 0.00;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Map.Entry<Date, Double> d : advProfit.entrySet()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(simpleDateFormat.format(d.getKey())).append(" - ").
                    append(String.format(Locale.ENGLISH, "%.2f", d.getValue()));
            sum += d.getValue();
            ConsoleHelper.writeMessage(stringBuilder.toString());

        }
        ConsoleHelper.writeMessage("Total - " + String.format(Locale.ENGLISH, "%.2f", sum));

    }

    public void printCookWorkloading() {
        Map<Date, TreeMap<String, Integer>> timeOfWorks = StatisticManager.getInstance().calculateTimeOfWork();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        boolean firstLine = true;

        for (Map.Entry<Date, TreeMap<String, Integer>> e : timeOfWorks.entrySet()) {
            ConsoleHelper.writeMessage((firstLine ? "" : "\n") + simpleDateFormat.format(e.getKey()));

            for (Map.Entry<String, Integer> m : e.getValue().entrySet()) {
                ConsoleHelper.writeMessage(m.getKey() + " - " + (int) Math.ceil(m.getValue() / 60.0d) + " min");
            }
            firstLine = false;
        }
    }

    public void printActiveVideoSet() {
        for (Advertisement ad : statisticAdvertisementManager.getActiveVideoSet()) {
            ConsoleHelper.writeMessage(String.format("%s - %d", ad.getName(), ad.getHits()));
        }
    }

    public void printArchivedVideoSet() {
        for (Advertisement ad : statisticAdvertisementManager.getArchivedVideoSet()) {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }
}
