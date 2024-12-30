package model.customize;

import model.Pizza;

import java.util.ArrayList;

public class PizzaToppingsHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleCustomization(OrderCustomizationRequest request) {
        Pizza pizza = request.getPizza();
        if (pizza.getToppings() == null || pizza.getToppings().isEmpty()) {
            System.out.println("Applying default topping: None");
            pizza.setToppings(new ArrayList<>());  // Set default toppings if not specified
        }
        if (nextHandler != null) {
            nextHandler.handleCustomization(request);
        }
    }
}

