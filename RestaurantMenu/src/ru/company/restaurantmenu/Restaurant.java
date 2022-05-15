package ru.company.restaurantmenu;

import ru.company.restaurantmenu.kitchen.Cook;
import ru.company.restaurantmenu.kitchen.Order;
import ru.company.restaurantmenu.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Cook cook1 = new Cook("CookMaster");
        cook1.setQueue(orderQueue);
        Cook cook2 = new Cook("MasterShef");
        cook2.setQueue(orderQueue);

        Waiter waiter = new Waiter();
        cook1.addObserver(waiter);
        cook2.addObserver(waiter);

        Thread threadCook1 = new Thread(cook1);
        Thread threadCook2 = new Thread(cook2);
        threadCook1.start();
        threadCook2.start();

        List<Tablet> tabletList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tabletList.add(new Tablet(i));
        }

        for (Tablet tabletTemp : tabletList) {
            tabletTemp.setQueue(orderQueue);
        }

        ThreadLocalRandom threadLocalRandom = new ThreadLocalRandom(tabletList, ORDER_CREATING_INTERVAL);
        Thread thread = new Thread(threadLocalRandom);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printActiveVideoSet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printArchivedVideoSet();
    }
}
