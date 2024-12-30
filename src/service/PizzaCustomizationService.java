package service;

import model.Pizza;
import model.enums.PizzaSize;
import model.enums.PizzaTopping;

public interface PizzaCustomizationService {
    Pizza customizePizza(PizzaSize size, String crust, String sauce, PizzaTopping[] toppings, String cheese);}
