package model.customize;

import model.Pizza;

public class PizzaSauceHandler implements CustomizationHandler {
    private CustomizationHandler nextHandler;

    @Override
    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleCustomization(OrderCustomizationRequest request) {
        Pizza pizza = request.getPizza();
        if (pizza.getSauce() == null) {
            System.out.println("Applying default sauce: Tomato Sauce");
            pizza.setSauce("Tomato Sauce");  // Set default sauce if not specified
        }
        if (nextHandler != null) {
            nextHandler.handleCustomization(request);
        }
    }
}
