package ru.company.restaurantmenu.kitchen;

import ru.company.restaurantmenu.ConsoleHelper;
import ru.company.restaurantmenu.statistic.StatisticManager;
import ru.company.restaurantmenu.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    private LinkedBlockingQueue<Order> queue;
    private String name;
    private boolean busy;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(),
                this.name, order.getTotalCookingTime() * 60, order.getDishes()));

        ConsoleHelper.writeMessage("Start cooking - " + order);

        try {
            Thread.sleep(order.getTotalCookingTime() * 10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(order);
        busy = false;
    }

    @Override
    public void run() {
        while (true) {
            if (!this.isBusy() && !queue.isEmpty()) {
                this.startCookingOrder(queue.poll());
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}