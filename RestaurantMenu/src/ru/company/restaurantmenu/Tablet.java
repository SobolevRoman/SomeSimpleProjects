package ru.company.restaurantmenu;

import ru.company.restaurantmenu.ad.AdvertisementManager;
import ru.company.restaurantmenu.ad.NoVideoAvailableException;
import ru.company.restaurantmenu.kitchen.Order;
import ru.company.restaurantmenu.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private LinkedBlockingQueue<Order> queue;
    final int number;
    static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);

            if (order.isEmpty()) return null;

            afterCreateOrder(order);
            return order;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return order;
        }
    }


    public void createTestOrder() {
        try {
            TestOrder testOrder = new TestOrder(this);

            if (!testOrder.isEmpty()) {

                afterCreateOrder(testOrder);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    private void afterCreateOrder(Order order) {
        ConsoleHelper.writeMessage(order.toString());
        try {
            queue.put(order);
            new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
