package expense_tracker;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileIOManager {
    private static final String USER_FILE_PATH = "users.txt";
    private static final String EXPENSE_FILE_PATH = "expenses.txt";

    public void saveDataToFile(Map<String, User> users, ExpenseManager expenseManager) {
        try (ObjectOutputStream userOutputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE_PATH));
             ObjectOutputStream expenseOutputStream = new ObjectOutputStream(new FileOutputStream(EXPENSE_FILE_PATH))) {

            userOutputStream.writeObject(users);
            expenseOutputStream.writeObject(expenseManager.getAllExpenses());

            System.out.println("Data saved to files.");

        } catch (IOException e) {
            System.err.println("Error saving data to files: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile(UserManager userManager, ExpenseManager expenseManager) {
        try (ObjectInputStream userInputStream = new ObjectInputStream(new FileInputStream(USER_FILE_PATH));
             ObjectInputStream expenseInputStream = new ObjectInputStream(new FileInputStream(EXPENSE_FILE_PATH))) {

            Map<String, User> loadedUsers = (Map<String, User>) userInputStream.readObject();
            Map<String, List<Expense>> loadedExpenses = (Map<String, List<Expense>>) expenseInputStream.readObject();

            userManager.getUsers().clear();
            userManager.getUsers().putAll(loadedUsers);

            expenseManager.setExpenses(loadedExpenses);

            System.out.println("Data loaded from files.");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from files: " + e.getMessage());
        }
    }
}
