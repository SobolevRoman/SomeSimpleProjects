package ru.company.restaurantmenu.kitchen;

import ru.company.restaurantmenu.ConsoleHelper;
import ru.company.restaurantmenu.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime() {
        int count = 0;
        for (Dish dish : dishes) {
            count += dish.getDuration();
        }
        return count;
    }

    public boolean isEmpty() {
        if (dishes.isEmpty())
            return true;
        return false;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    @Override
    public String toString() {
        return "Your order: " +
                dishes +
                " of " + tablet +
                ", cooking time " + getTotalCookingTime() + "min";
    }
}
