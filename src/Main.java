import model.Customer;
import model.order.CompletedState;
import model.order.DeliveryState;
import model.order.Order;
import model.Pizza;
import model.enums.OrderStatus;
import model.enums.PizzaSize;
import model.enums.PizzaTopping;
import model.order.PreparationState;
import model.payment.CardPayment;
import model.payment.CashPayment;
import model.payment.PayPalPayment;
import model.payment.PaymentStrategy;
import model.promotion.Promotion;
import service.CustomerService;
import service.OrderService;
import service.PizzaCustomizationService;
import service.impl.CustomerServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.PizzaCustomizationServiceImpl;

import java.time.LocalDateTime;
import java.util.*;
public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final PizzaCustomizationService pizzaCustomizationService = new PizzaCustomizationServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final CustomerService customerService = new CustomerServiceImpl();
    private static List<Order> orders = new ArrayList<>();
    private static Map<Integer, Double> customerPoints = new HashMap<>();

    private static final PizzaTopping[] AVAILABLE_TOPPINGS = PizzaTopping.values();

    public static void main(String[] args) {
        Main main = new Main();
        main.run();


    }

    public void run() {
        while (true) {
            clearScreen(); // Clear the screen for a clean look
            printHeader("🍕 Welcome to Pizza Mania Ordering System! 🍕");
            System.out.println("\033[1;34m1. Place Order\033[0m");
            System.out.println("\033[1;34m2. View Orders\033[0m");
            System.out.println("\033[1;34m3. User Profile\033[0m");
            System.out.println("\033[1;34m4. View Order Status\033[0m");
            System.out.println("\033[1;34m5. Seasonal Offer\033[0m");
            System.out.println("\033[1;34m6. Give Feedback\033[0m");
            System.out.println("\033[1;34m7. Exit\033[0m");

            System.out.print("\n\033[1;33mPlease select an option: \033[0m");
            int choice = getIntInput(1, 7, "\033[1;31mInvalid choice. Please try again.\033[0m");

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    manageUserProfile();
                    break;
                case 4:
                    viewOrderStatus();
                    break;
                case 5:
                    displaySeasonalOffer();
                    break;
                case 6:
                    handleFeedbackAndRatings(scanner);
                    break;
                case 7:
                    printFooter("Thank you for using the Pizza Mania Ordering System!");
                    return;
            }
        }
    }

    private void printHeader(String title) {
        String border = "═".repeat(title.length());
        System.out.println("\033[1;32m╔" + border + "╗\033[0m");
        System.out.println("\033[1;32m║ " + title + " ║\033[0m");
        System.out.println("\033[1;32m╚" + border + "╝\033[0m\n");
    }

    private void printFooter(String message) {
        System.out.println("\n\033[1;36m" + message + "\033[0m");
        System.out.println("\033[1;36mGoodbye! Have a great day! 🍕\033[0m");
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void placeOrder() {
       // Step 1: Customize Pizza
       Pizza pizza = customizePizza();

       // Step 2: Enter Quantity
       System.out.println("Enter quantity:");
       int quantity = getIntInput(1, Integer.MAX_VALUE, "Invalid quantity. Defaulting to 1.");

       // Step 3: Select Delivery Method
       String deliveryMethod = selectDeliveryMethod();

       // Step 4: Get Address if Delivery
       String address = null;
       if ("Delivery".equals(deliveryMethod)) {
           address = getDeliveryAddress();
       } else {
           System.out.println("Please visit our store to collect your order.");
       }

       // Step 5: Get or Create Customer
       System.out.println("Enter your contact number:");
       String phoneNumber = scanner.nextLine();
       Customer customer = getOrCreateCustomer(phoneNumber);

       // Step 6: Calculate Order Amount and Apply Loyalty Discount
       double orderAmount = pizza.getPrice() * quantity;
       double discount = applyLoyaltyPoints(customer, orderAmount);
       double totalAmount = orderAmount - discount;

       System.out.println("Order Amount: " + orderAmount);
       System.out.println("Discount Applied: " + discount);
       System.out.println("Total Amount after Discount: " + totalAmount);

       // Step 7: Select Payment Method
       PaymentStrategy paymentStrategy = getPaymentStrategy();
       customer.setPaymentStrategy(paymentStrategy);
       customer.makePayment(totalAmount);

       // Step 8: Create and Track the Order
       Order order = createOrder(pizza, deliveryMethod, quantity, address, totalAmount);
       orders.add(order);

       System.out.println("Your order has been placed with ID: " + order.getId());

        trackOrderStatus(order, deliveryMethod);


         }

    private Pizza customizePizza() {
        System.out.println("Select pizza size:");
        System.out.println("1. Mini");
        System.out.println("2. Regular");
        System.out.println("3. Family");
        PizzaSize size = switch (getIntInput(1, 3, "Invalid choice. Defaulting to Mini.")) {
            case 1 -> PizzaSize.SMALL;
            case 2 -> PizzaSize.REGULAR;
            case 3 -> PizzaSize.LARGE;
            default -> PizzaSize.SMALL;
        };

        String crust = selectOption("Select crust style:", new String[]{"No Crust", "Crispy Crust", "Fluffy Crust", "Cheese Burst"});
        String sauce = selectOption("Select base sauce:", new String[]{"No Sauce", "Marinara Sauce", "Garlic Sauce", "Hot Sauce"});
        String cheese = selectOption("Select cheese variety:", new String[]{"No Cheese", "Swiss", "Feta", "Provolone"});
        List<PizzaTopping> toppings = selectToppings();

        return new Pizza.PizzaBuilder()
                .setSize(size)
                .setCrust(crust)
                .setSauce(sauce)
                .setCheese(cheese)
                .addToppings(toppings)
                .build();
    }


    private String selectOption(String prompt, String[] options) {
        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        int choice = getIntInput(1, options.length, "Invalid choice. Defaulting to None.");
        return choice == 1 ? null : options[choice - 1];
    }

    private List<PizzaTopping> selectToppings() {
        System.out.println("Available toppings:");
        for (PizzaTopping topping : PizzaTopping.values()) {
            System.out.println((topping.ordinal() + 1) + ". " + topping);
        }
        System.out.println("Select toppings (comma separated, e.g., 1,3,5), or press Enter for None:");
        String toppingInput = scanner.nextLine();

        List<PizzaTopping> selectedToppings = new ArrayList<>();
        if (!toppingInput.isEmpty()) {
            String[] toppingChoices = toppingInput.split(",");
            for (String choice : toppingChoices) {
                try {
                    int index = Integer.parseInt(choice.trim()) - 1;
                    if (index >= 0 && index < PizzaTopping.values().length) {
                        selectedToppings.add(PizzaTopping.values()[index]);
                    } else {
                        System.out.println("Invalid topping choice: " + (index + 1));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for toppings: " + choice);
                }
            }
        }
        return selectedToppings;
    }
    private void displaySeasonalOffer() {
        System.out.println("----- Seasonal Offer -----");
        Promotion seasonalPromotion = new Promotion("Winter Special", 150.0); // Example seasonal promotion
        System.out.println("Promotion Name: " + seasonalPromotion.getName());
        System.out.println("Discount: LKR " + seasonalPromotion.getDiscount());
        System.out.println("--------------------------");

        System.out.println("Would you like to apply this offer to your next order? (Yes/No)");
        String response = scanner.next();

        if (response.equalsIgnoreCase("Yes")) {
            System.out.println("The " + seasonalPromotion.getName() + " promotion will be applied to your next order.");
        } else {
            System.out.println("You can apply this offer at any time during your next order!");
        }
    }

    private String selectDeliveryMethod() {
        System.out.println("Select delivery method:");
        System.out.println("1. Pickup");
        System.out.println("2. Delivery");

        int choice = getIntInput(1, 2, "Invalid choice. Defaulting to Pickup.");
        if (choice == 2) {
            return "Delivery";
        }
        return "Pickup";
    }

    private String getDeliveryAddress() {
        System.out.println("Please enter your delivery address:");
        return scanner.nextLine();
    }

    private Customer getOrCreateCustomer(String phoneNumber) {
        Customer customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("New customer detected. Please provide your name:");
            String name = scanner.nextLine();
            System.out.println("Please provide your email:");
            String email = scanner.nextLine();
            customer = customerService.createCustomer(name, phoneNumber, email);
            System.out.println("Welcome, " + name + "! Your loyalty points have been initialized to 0.");
        } else {
            System.out.println("Welcome back, " + customer.getName() + "! You currently have " + customer.getLoyaltyPoints() + " loyalty points.");
        }
        return customer;
    }

    private double applyLoyaltyPoints(Customer customer, double orderAmount) {
        double discount = 0;
        if (customer.getLoyaltyPoints() >= 1000) {
            discount = 100;
            System.out.println("You are eligible for a LKR 100 discount!");
            customerService.deductLoyaltyPoints(customer.getPhoneNumber(), 1000);
        }
        customerService.updateLoyaltyPoints(customer.getPhoneNumber(), (int) (orderAmount / 10));
        return discount;
    }

    private PaymentStrategy getPaymentStrategy() {
        System.out.println("Select payment method:");
        System.out.println("1. Card");
        System.out.println("2. Cash");
        System.out.println("3. PayPal");
        int paymentMethodChoice = getIntInput(1, 3, "Invalid choice. Defaulting to Cash.");

        return switch (paymentMethodChoice) {
            case 1 -> {
                String cardNumber = null;
                boolean validCard = false;
                while (!validCard) {
                    System.out.println("Enter your card number (16 digits):");
                    cardNumber = scanner.nextLine();
                    if (cardNumber.matches("\\d{16}")) {
                        validCard = true;
                    } else {
                        System.out.println("Invalid card number. Please enter a valid 16-digit card number.");
                    }
                }

                String cardHolderName = null;
                boolean validName = false;
                while (!validName) {
                    System.out.println("Enter your name on the card:");
                    cardHolderName = scanner.nextLine();
                    if (!cardHolderName.trim().isEmpty()) {
                        validName = true;
                    } else {
                        System.out.println("Name cannot be empty. Please enter a valid name.");
                    }
                }

                yield new CardPayment(cardNumber, cardHolderName);
            }
            case 2 -> new CashPayment();
            case 3 -> {
                String paypalEmail = null;
                boolean validEmail = false;
                while (!validEmail) {
                    System.out.println("Enter your PayPal email:");
                    paypalEmail = scanner.nextLine();
                    if (paypalEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                        validEmail = true;
                    } else {
                        System.out.println("Invalid email address. Please enter a valid PayPal email.");
                    }
                }

                yield new PayPalPayment(paypalEmail);
            }
            default -> new CashPayment();
        };
    }

    private Order createOrder(Pizza pizza, String deliveryMethod, int quantity, String address, double totalAmount) {
        Order order = new Order();
        order.setId(orders.size() + 1); // Auto-generate unique order ID
        order.setPizza(pizza); // Set pizza details
        order.setQuantity(quantity); // Set order quantity
        order.setDeliveryMethod(deliveryMethod); // Set delivery method

        // Conditionally set delivery address for delivery orders
        if ("Delivery".equalsIgnoreCase(deliveryMethod)) {
            order.setDeliveryAddress(address != null ? address : "No address provided");
        }

        order.setTotalAmount(totalAmount); // Set the total amount for the order
        order.setOrderStatus(String.valueOf(OrderStatus.ORDERED)); // Set initial order status to "Ordered"
        order.setOrderDate(LocalDateTime.now()); // Record the order creation date and time

        return order;
    }


    // Helper method for input validation
    private int getIntInput(int min, int max, String errorMessage) {
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (Exception e) {
                scanner.nextLine(); // Clear invalid input
            }
            System.out.println(errorMessage);
        }
    }

    private String getDeliveryEstimate(int quantity, boolean isDelivery) {
        int time = quantity * 15;
        if (isDelivery) {
            time += 15;
        }
        return time + " minutes";
    }

    private void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders have been placed yet.");
            return;
        }

        System.out.println("Viewing all orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Order State: " + order.getState().getClass().getSimpleName());
            System.out.println("----------------------------");
        }
    }

    private void manageUserProfile() {
        System.out.println("Enter your contact number to access your profile:");
        String phoneNumber = scanner.nextLine();
        Customer customer = getOrCreateCustomer(phoneNumber);

        if (customer == null) {
            System.out.println("Customer not found. Please place an order to create your profile.");
            return;
        }

        System.out.println("Welcome, " + customer.getName() + "!");
        System.out.println("1. View Profile");
        System.out.println("2. View Favorite Pizzas");
        System.out.println("3. Add Favorite Pizza");
        System.out.println("4. Reorder Favorite Pizza");
        System.out.println("5. Back to Main Menu");

        int choice = getIntInput(1, 5, "Invalid choice. Please try again.");

        switch (choice) {
            case 1:
                System.out.println(customer);
                break;
            case 2:
                customer.displayFavorites();
                break;
            case 3:
                addFavoritePizza(customer);
                break;
            case 4:
                reorderFavoritePizza(customer);
                break;
            case 5:
                return; // Return to the main menu
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addFavoritePizza(Customer customer) {
        System.out.println("Customize a pizza to add as a favorite:");
        Pizza pizza = customizePizza(); // Calls the existing customizePizza() method
        customer.addFavoritePizza(pizza); // Adds the customized pizza to the user's favorites
        System.out.println("Pizza added to your favorites!");
    }
    private void reorderFavoritePizza(Customer customer) {
        List<Pizza> favorites = customer.getFavoritePizzas();
        if (favorites.isEmpty()) {
            System.out.println("No favorite pizzas available to reorder.");
            return;
        }

        System.out.println("Select a favorite pizza to reorder:");
        for (int i = 0; i < favorites.size(); i++) {
            System.out.println((i + 1) + ". " + favorites.get(i));
        }

        int choice = getIntInput(1, favorites.size(), "Invalid choice. Please try again.");
        Pizza selectedPizza = favorites.get(choice - 1);

        System.out.println("Enter quantity:");
        int quantity = getIntInput(1, Integer.MAX_VALUE, "Invalid quantity. Defaulting to 1.");

        System.out.println("Select delivery method:");
        String deliveryMethod = selectDeliveryMethod();

        String address = null;
        if ("Delivery".equals(deliveryMethod)) {
            address = getDeliveryAddress();
        }

        double orderAmount = selectedPizza.getPrice() * quantity;

        // Select payment method
        PaymentStrategy paymentStrategy = getPaymentStrategy();
        customer.setPaymentStrategy(paymentStrategy);
        customer.makePayment(orderAmount);

        // Create and add the order
        Order order = createOrder(selectedPizza, deliveryMethod, quantity, address, orderAmount);
        orders.add(order);

        System.out.println("Favorite pizza reordered! Order ID: " + order.getId());
    }
    private static void handleFeedbackAndRatings(Scanner scanner) {
        System.out.println("\nFeedback and Ratings:");
        System.out.println("Please rate your experience (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please provide a rating between 1 and 5.");
        } else {
            System.out.println("Thank you for your feedback! You rated us: " + rating + " stars.");
            System.out.println("Would you like to leave additional comments? (yes/no): ");
            String additionalComments = scanner.nextLine();
            if (additionalComments.equalsIgnoreCase("yes")) {
                System.out.println("Please enter your comments: ");
                String comments = scanner.nextLine();
                System.out.println("Thank you for your comments: " + comments);
            }

        }
    }

    private void viewOrderStatus() {
        System.out.println("Enter order ID to check status:");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Find the order by ID in the static orders list
        Order order = findOrderById(orderId);

        if (order != null) {
            // Print order details
            System.out.println("Order ID: " + order.getId());
            System.out.println("Order Status: " + order.getOrderStatus());
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    private Order findOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    private void trackOrderStatus(Order order, String deliveryMethod) {
        Thread orderTrackingThread = new Thread(() -> {
            try {
                // Order placed, transitioning to preparation
                Thread.sleep(10000); // Simulate time taken for each step
                order.setState(new PreparationState());
                order.handleOrder(); // Handle the state change
//                System.out.println("Transitioned to: PreparationState");

                if (deliveryMethod.equals("Delivery")) {
                    // Simulate delivery process
                    Thread.sleep(10000); // Simulate time for delivery
                    order.setState(new DeliveryState());
                    order.handleOrder(); // Handle the state change
//                    System.out.println("Transitioned to: DeliveryState");

                    // Final transition to completed
                    Thread.sleep(10000); // Simulate time for delivery completion
                    order.setState(new CompletedState());
                    order.handleOrder(); // Handle the state change
//                    System.out.println("Transitioned to: CompletedState");

                } else {
                    // Simulate pickup process
                    Thread.sleep(10000); // Simulate time for pickup
                    order.setState(new CompletedState());
                    order.handleOrder(); // Handle the state change
//                    System.out.println("Transitioned to: CompletedState");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        orderTrackingThread.start(); // Start the state change thread
    }

}
