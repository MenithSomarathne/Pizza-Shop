package model.order;

public class PreparationState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Order is being prepared (cooking the pizza)...");

        // Simulate preparation time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Order is complete.");
        order.setState(new DeliveryState());
    }
}
