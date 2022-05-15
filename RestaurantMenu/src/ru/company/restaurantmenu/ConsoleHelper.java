package ru.company.restaurantmenu;

import ru.company.restaurantmenu.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        String text = bis.readLine();
        return text;
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        Dish[] massDish = Dish.values();
        List<Dish> listForOrder = new ArrayList<>();
        int count = 0;
        writeMessage("Список блюд: ");
        writeMessage(Dish.allDishesToString());
        writeMessage("Введите название блюда: ");
        String rez = readString();
        while (!rez.equals("exit")) {
            for (int i = 0; i < massDish.length; i++) {
                if (rez.equals(String.valueOf(massDish[i]))) {
                    listForOrder.add(massDish[i]);
                }
            }
            if (listForOrder.size() == count) {
                writeMessage("Такого блюда нет в меню. Попробуйте снова.");
            } else count = listForOrder.size();

            rez = readString();
        }
        return listForOrder;
    }
}
