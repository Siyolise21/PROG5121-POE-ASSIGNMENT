import java.util.Scanner;

public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean isLoggedIn = false;

    public boolean checkUserName(String username) {
        if (username.contains("_") && username.length() <= 5) {
            System.out.println("The system returns: True");
            return true;
        } else {
            System.out.println("The system returns: False");
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            return false;
        }
    }

    public boolean checkPasswordComplexity(String password) {
        boolean hasUppercase = (password.matches(".*[A-Z].*"));
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecialChar = password.matches(".*[!,@,&,*]*");
        boolean hasMinLength = password.length() <= 13;

        if (hasUppercase && hasDigit && hasSpecialChar && hasMinLength) {
            System.out.println("The system returns: True");
            System.out.println("Password successfully captured.");
            return true;
        } else {
            System.out.println("The system returns: False");
            System.out.println("Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        }
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+27") && phoneNumber.length() == 12) {
            System.out.println("The system returns: True");
            System.out.println("Cell number successfully captured.");
            return true;
        } else {
            System.out.println("The system returns: False");
            System.out.println("Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
            return false;
        }
    }

    public String registerUser(String firstName, String lastName, String username, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;

        // Validate inputs before registering
        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validPhone = checkCellPhoneNumber(phoneNumber);

        if (!validUsername) {
            return "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!validPassword) {
            return "Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }

        if (!validPhone) {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }

        this.registeredUsername = username;
        this.registeredPassword = password;
        this.phoneNumber = phoneNumber;

        return "User has been registered successfully.";
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        isLoggedIn = inputUsername.equals(registeredUsername) && inputPassword.equals(registeredPassword);
        if (isLoggedIn) {
            System.out.println("The system returns: True");
        } else {
            System.out.println("The system returns: False");
        }
        return isLoggedIn;
    }

    public String returnLoginStatus() {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        // Loop for username validation
        String username = "";
        boolean usernameValid = false;
        while (!usernameValid) {
            System.out.println("Enter username (must contain '_' and max 5 characters): ");
            username = scanner.nextLine();
            usernameValid = login.checkUserName(username);
        }

        // Loop for password validation
        String password = "";
        boolean passwordValid = false;
        while (!passwordValid) {
            System.out.println("Enter password (min 8 chars, 1 uppercase, 1 number, 1 special char): ");
            password = scanner.nextLine();
            passwordValid = login.checkPasswordComplexity(password);  // Make sure this returns true if password is valid
        }

        // Loop for phone number validation
        String phone = "";
        boolean phoneValid = false;
        while (!phoneValid) {
            System.out.println("Enter South African phone number (start with +27 and 12 characters): ");
            phone = scanner.nextLine();
            phoneValid = login.checkCellPhoneNumber(phone);
        }

        // Register the user
        String registrationResult = login.registerUser(firstName, lastName, username, password, phone);
        System.out.println(registrationResult);

        if (registrationResult.equals("User has been registered successfully.")) {
            // Login after successful registration
            System.out.println("Login to your account:");
            System.out.print("Username: ");
            String inputUsername = scanner.nextLine();

            System.out.print("Password: ");
            String inputPassword = scanner.nextLine();

            login.loginUser(inputUsername, inputPassword);
            System.out.println(login.returnLoginStatus());
        }
        class LoginTest {
            public static void main(String[] args) {
                Login login = new Login();

                System.out.println("=== Username Tests ===");
                System.out.println("Test 1: Valid username (_ and ≤ 5 chars)");
                assert login.checkUserName("kyl_1") == true;

                System.out.println("Test 2: Invalid username (too long)");
                assert login.checkUserName("kyl_1") == false;

                System.out.println("Test 3: Invalid username (no underscore)");
                assert login.checkUserName("kyle!!!!!!!") == false;

                System.out.println("\n=== Password Tests ===");
                System.out.println("Test 1: Valid password (has uppercase, digit, special char, <= 9 chars)");
                assert login.checkPasswordComplexity("CH&&sec@ke99!") == true;

                System.out.println("Test 2: Invalid password (no uppercase)");
                assert login.checkPasswordComplexity("password") == false;

                System.out.println("Test 3: Invalid password (too long)");
                assert login.checkPasswordComplexity("password") == false;

                System.out.println("\n=== Phone Number Tests ===");
                System.out.println("Test 1: Valid phone number (+27 and 12 chars)");
                assert login.checkCellPhoneNumber("+27838968976") == true;

                System.out.println("Test 2: Invalid phone number (wrong length)");
                assert login.checkCellPhoneNumber("08966553") == false;

                System.out.println("Test 3: Invalid phone number (missing +27)");
                assert login.checkCellPhoneNumber("08966553") == false;

                System.out.println("\n=== Registration and Login Tests ===");
                String regStatus = login.registerUser("John", "Doe", "j_doe", "P@ssw0rd", "+27831234567");
                System.out.println("Register user: " + regStatus);
                assert regStatus.equals("User has been registered successfully.");

                System.out.println("Test login with correct credentials");
                assert login.loginUser("j_doe", "P@ssw0rd") == true;

                System.out.println("Login Status: " + login.returnLoginStatus());

                System.out.println("Test login with incorrect password");
                assert login.loginUser("j_doe", "wrongPass") == false;
            }
        }
        scanner.close();
    }
}