package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private List<Pizza> favoritePizzas;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.favoritePizzas = new ArrayList<>();
    }

    public void addFavoritePizza(Pizza pizza) {
        favoritePizzas.add(pizza);
    }

    public List<Pizza> getFavoritePizzas() {
        return favoritePizzas;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", email=" + email + ", favoritePizzas=" + favoritePizzas + "]";
    }
}
