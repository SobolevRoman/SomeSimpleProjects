package ru.company.restaurantmenu.kitchen;

import ru.company.restaurantmenu.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        int randomValuesDishesInTestOrder = (int) (Math.random() * 9 + 1);
        int valueVariantsOfDishes = Dish.values().length;
        ArrayList<Dish> arrayDishes = new ArrayList<>(Arrays.asList(Dish.values()));

        dishes = new ArrayList<>();
        for (int i = 0; i < randomValuesDishesInTestOrder; i++) {
            Dish dd = arrayDishes.get((int) (Math.random() * (valueVariantsOfDishes - 1)));
            dishes.add(dd);
        }
    }
}
