package Game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cgpimenta on 09/04/17.
 */
public class Player {

    private int id;
    private double balance;
    private Collection<Property> propertiesList;
    private PlayerStats stats;
    private boolean active;
    private int currentPosition;
    int numRounds;

    public Player(int id, double balance) {
        this.id = id;
        this.balance = balance;
        this.propertiesList = new ArrayList<Property>();    // Empty properties list
        this.stats = new PlayerStats();
        this.active = true;
        this.currentPosition = 0;
    }

    public int getId() {
        return this.id;
    }

    public double getBalance() {
        return this.balance;
    }

    public void buyProperty(Property property) {
        // Deduce property price from balance:
        this.balance -= property.getPurchaseValue();
        // Add property to properties list:
        this.propertiesList.add(property);
        // Set property's owner id attribute:
        property.setOwnerId(this.id);
        // Add statistics:
        this.stats.addPropertyExpense(property.getPurchaseValue());
    }

    public void payRent(Player owner, double rent) {
        // Deduce rent from balance:
        this.balance -= rent;
        // Call owner's receiveRent() method:
        owner.receiveRent(rent);
        // Add statistics:
        this.stats.addRentExpense(rent);
    }

    public void receiveRent(double rent) {
        // Add rent to balance:
        this.balance += rent;
        // Add statistics:
        this.stats.addRentIncome(rent);
    }

    public void goBankrupt(double debt) {
        this.active = false;
        this.balance -= debt;

        // Return properties to bank (Bank Id = 0):
        for (Property property : this.propertiesList) {
            property.setOwnerId(0);
        }
    }

    public PlayerStats getStats() {
        return this.stats;
    }

    public boolean isActive() {
        return this.active;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void receiveBonus(Double bonus) {
        this.balance += bonus;
    }
}
