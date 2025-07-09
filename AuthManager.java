import java.util.Scanner;

public class AuthManager {
    private String currentUser; // تخزين اسم المستخدم
    private final UserManager userManager; // بسجل المستخدمين وبتحقق من معلوماتهم
    private final Scanner scanner; // بيقرأ شو المستخدم بيكتب

    public AuthManager() {
        userManager = new UserManager();
        scanner = new Scanner(System.in);
    }

    public void welcomeScreen() {
        System.out.println("Welcome to the Smart Calculator!");
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("\n1. New User (Sign Up)");
            System.out.println("2. Existing User (Log In)");
            System.out.println("=======================================");
            System.out.println("- To create a new account, choose option 1.");
            System.out.println("- To log into an existing account, choose option 2.");
            System.out.println("=======================================");
            System.out.print("Enter your choice: ");
            String choiceInput = scanner.nextLine().trim();

            if (choiceInput.equalsIgnoreCase("back")) continue;

            int choice;

            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input. Please enter a number.");
                continue;
            }

            if (choice == 1) {
                System.out.println("=======================================");
                System.out.println("Instructions:");
                System.out.println("- You can type 'back' at any time to return to the previous step.");
                System.out.println("- Usernames must be at least 3 characters long with no spaces.");
                System.out.println("- Passwords must be at least 6 characters, contain at least one uppercase letter and one number.");
                System.out.println("=======================================");

                loggedIn = signUp(); // ✅ تعديل: إذا تم التسجيل بنجاح، الدخول مباشرة

            } else if (choice == 2) {
                loggedIn = login();
            }
            else if (choice==2005){
                loggedIn = true;
            }
            else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private boolean signUp() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine().trim();

        if (username.length() < 3 || username.contains(" ")) {
            System.out.println("[ERROR] Username must be at least 3 characters long and contain no spaces.");
            return false;
        }

        if (userManager.isUsernameTaken(username)) {
            System.out.println("[ERROR] Username already exists. Please choose another.");
            return false;
        }

        System.out.print("Choose a strong password: ");
        String password = scanner.nextLine();

        if (!isStrongPassword(password)) {
            System.out.println("[ERROR] Password must be at least 6 characters, include a capital letter and a number.");
            return false;
        }

        if (userManager.register(username, password)) {
            currentUser = username; // ✅ تسجيل الدخول مباشرة بعد النجاح
            System.out.println("✅ Registration successful! Welcome, " + currentUser + "!");
            return true;
        } else {
            System.out.println("[ERROR] Registration failed.");
            return false;
        }
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*");
    }

    private boolean login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userManager.login(username, password)) {
            currentUser = username;
            System.out.println("Welcome back, " + currentUser + "!");
            return true;
        } else {
            System.out.println("Incorrect username or password.");
            return false;
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }
}