package model.customize;

import model.Pizza;

public class OrderCustomizationRequest {
    private Pizza pizza;

    public OrderCustomizationRequest(Pizza pizza) {
        this.pizza = pizza;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}

