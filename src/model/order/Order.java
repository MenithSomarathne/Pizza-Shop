package model.order;

import model.Pizza;
import model.enums.OrderStatus;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private OrderState state;
    private Pizza pizza;
    private String orderStatus;
    private int quantity;
    private String deliveryAddress;
    private String deliveryMethod;
    private double totalAmount;
    private LocalDateTime orderDate;

    public Order() {
        this.state = new OrderedState();
        this.orderStatus = "Ordered";
    }
    public void handleOrder() {

        this.state.handleOrder(this);
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setState(OrderState state) {

        this.state = state;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


    public OrderState getState() {

        return state;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

