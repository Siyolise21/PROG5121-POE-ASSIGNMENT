import static org.junit.jupiter.api.Assertions.*;

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


