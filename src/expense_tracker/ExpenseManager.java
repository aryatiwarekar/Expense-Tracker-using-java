package expense_tracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager implements Serializable {
    private Map<String, List<Expense>> expenses;

    public ExpenseManager() {
        this.expenses = new HashMap<>();
    }

    public List<Expense> getExpenses(String username) {
        return expenses.getOrDefault(username, List.of());
    }

    public Map<String, List<Expense>> getAllExpenses() {
        return expenses;
    }

    public void setExpenses(Map<String, List<Expense>> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(String username, Expense expense) {
        List<Expense> userExpenses = expenses.computeIfAbsent(username, k -> new ArrayList<>());
        userExpenses.add(expense);
        expenses.put(username, userExpenses);
    }

}
