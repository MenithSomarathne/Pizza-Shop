package model.customize;

public interface CustomizationHandler {
    void setNextHandler(CustomizationHandler nextHandler);
    void handleCustomization(OrderCustomizationRequest request);
}
