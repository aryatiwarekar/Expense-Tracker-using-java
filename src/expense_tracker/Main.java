package expense_tracker;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        ExpenseManager expenseManager = new ExpenseManager();
        FileIOManager fileIOManager = new FileIOManager();

        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to the Expense Tracker!");

        boolean exit = false;
        User loggedInUser = null;

        while (!exit) {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Enter Expense");
            System.out.println("4. View Expenses");
            System.out.println("5. Exit");

            // Get user choice
            int choice = getUserChoice(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Register user
                    registerUser(userManager, fileIOManager, scanner);
                    break;
                case 2:
                    // Login
                    loggedInUser = loginUser(userManager, fileIOManager, scanner);
                    break;
                case 3:
                    // Enter expense
                    if (loggedInUser != null) {
                        addExpense(expenseManager, loggedInUser.getUsername(), scanner);
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;
                case 4:
                    // View expenses
                    if (loggedInUser != null) {
                        viewExpenses(expenseManager, loggedInUser.getUsername());
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;
                case 5:
                    // Exit
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Save and load data using fileIOManager
        fileIOManager.saveDataToFile(userManager.getUsers(), expenseManager);
        fileIOManager.loadDataFromFile(userManager, expenseManager);

        // Close the scanner
        scanner.close();
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print("Enter your choice: ");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }

    private static void registerUser(UserManager userManager, FileIOManager fileIOManager, Scanner scanner) {
        // User registration with input validation
        String username;
        String password;

        do {
            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
            } else if (userManager.userExists(username)) {
                System.out.println("Username already exists. Please choose a different one.");
            }
        } while (username.isEmpty() || userManager.userExists(username));

        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            }
        } while (password.isEmpty());

        User newUser = new User(username, password);
        userManager.registerUser(newUser);

        // Save the updated user data to the file
        fileIOManager.saveDataToFile(userManager.getUsers(), new ExpenseManager());

        System.out.println("User registered successfully.");
    }


    private static User loginUser(UserManager userManager, FileIOManager fileIOManager, Scanner scanner) {
        // User login with input validation
        String username;
        String password;
        User loggedInUser;

        do {
            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Invalid username. Please try again.");
            } else {
                // Attempt to load user data from file
                fileIOManager.loadDataFromFile(userManager, new ExpenseManager());

                if (!userManager.userExists(username)) {
                    System.out.println("User does not exist. Please register or enter a valid username.");
                }
            }
        } while (username.isEmpty() || !userManager.userExists(username));

        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();

            loggedInUser = userManager.login(username, password);

            if (loggedInUser == null) {
                System.out.println("Invalid password. Please try again.");
            }
        } while (loggedInUser == null);

        System.out.println("Login successful. Welcome, " + loggedInUser.getUsername() + "!");
        return loggedInUser;
    }



    private static void addExpense(ExpenseManager expenseManager, String username, Scanner scanner) {
        // Add expense with input validation
        String category;
        double amount;

        System.out.print("Enter expense category: ");
        category = scanner.nextLine().trim();

        while (category.isEmpty()) {
            System.out.println("Category cannot be empty. Please try again.");
            System.out.print("Enter expense category: ");
            category = scanner.nextLine().trim();
        }

        System.out.print("Enter expense amount: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            System.out.print("Enter expense amount: ");
            scanner.next(); // Consume the invalid input
        }
        amount = scanner.nextDouble();

        Expense newExpense = new Expense(username, category, amount, LocalDate.now());
        expenseManager.addExpense(username, newExpense);

        System.out.println("Expense added successfully.");
    }

    private static void viewExpenses(ExpenseManager expenseManager, String username) {
        // View expenses
        List<Expense> userExpenses = expenseManager.getExpenses(username);

        if (userExpenses.isEmpty()) {
            System.out.println("No expenses found for user: " + username);
        } else {
            System.out.println("Expenses for user " + username + ":");
            for (Expense expense : userExpenses) {
                System.out.println(expense);
            }
        }
    }
}
