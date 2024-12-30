package model.order;

public interface OrderState {
    void handleOrder(Order order);
}