package ru.company.restaurantmenu.ad;

public class Advertisement {
    private Object content;       //video
    private String name;          //name
    private long initialAmount;   //стоимость рекламы в копейках
    private int hits;             //количество оплаченных показов
    private int duration;         //продолжительность в секундах
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        if (hits > 0) {
            amountPerOneDisplaying = initialAmount / hits;
        }
    }

    public int getHits() {
        return hits;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() {
        if (hits <= 0) {
            throw new NoVideoAvailableException();
        }
        hits--;
    }
}
