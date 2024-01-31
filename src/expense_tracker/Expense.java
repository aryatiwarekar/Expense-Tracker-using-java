package expense_tracker;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Serializable {
    private String username;
    private String category;
    private double amount;
    private LocalDate date;

    public Expense(String username, String category, double amount, LocalDate date) {
        this.username = username;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "username='" + username + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
