package ru.company.restaurantmenu.kitchen;

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int i) {
        duration = i;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        Dish[] mass = values();
        StringBuilder stringBuilder = new StringBuilder();
        for (Dish dish : mass) {
            stringBuilder.append(dish).append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.toString().length() - 2);
    }
}