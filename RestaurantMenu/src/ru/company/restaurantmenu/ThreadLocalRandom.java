package ru.company.restaurantmenu;

import java.util.List;

public class ThreadLocalRandom implements Runnable {
    private List<Tablet> tabletList;
    private int interval;

    public ThreadLocalRandom(List<Tablet> tabletList, int interval) {
        this.tabletList = tabletList;
        this.interval = interval;
    }

    @Override
    public void run() {
        if (tabletList.size() > 0) {
            while (!Thread.currentThread().isInterrupted()) {
                Tablet tablet = tabletList.get((int) Math.random() * tabletList.size());
                tablet.createTestOrder();
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
