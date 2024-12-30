package model.order;

public class DeliveryState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Order is out for delivery.");

        // Simulate delivery time
        try {
            Thread.sleep(3000); // Simulate 3 seconds for delivery
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Transition to CompletedState when delivery is finished
        System.out.println("Order has been delivered.");
        order.setState(new CompletedState()); // Order completed after delivery
    }
}