package service.impl;

import model.Pizza;
import model.enums.PizzaSize;
import model.enums.PizzaTopping;
import service.PizzaCustomizationService;

public class PizzaCustomizationServiceImpl implements PizzaCustomizationService {

    @Override
    public Pizza customizePizza(PizzaSize size, String crust, String sauce, PizzaTopping[] toppings, String cheese) {
        Pizza.PizzaBuilder builder = new Pizza.PizzaBuilder().setSize(size).setCrust(crust).setSauce(sauce).setCheese(cheese);
        for (PizzaTopping topping : toppings) {
            builder.addTopping(topping);
        }
        return builder.build();
    }
}
