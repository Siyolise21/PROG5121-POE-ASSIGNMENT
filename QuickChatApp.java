
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

public class QuickChatApp {

    private static List<Chat> messages = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Login Section
        System.out.println("Enter username to login: ");
        String username = scanner.nextLine();

        if (!username.isEmpty()) {
            boolean isLoggedIn = true;
            System.out.println("\nWelcome to QuickChat.");
        } else {
            System.out.println("Login failed. Exiting...");
            return;
        }

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("How many messages do you want to send? ");
                    int num = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 0; i < num; i++) {
                        System.out.print("Enter recipient number containing +:");
                        String cell = scanner.nextLine();

                        if (!Chat.checkrecipientCell(cell)) {
                            System.out.println("Invalid recipient cell.");
                            continue;
                        }

                        System.out.print("Enter message: ");
                        String body = scanner.nextLine();

                        if (!Chat.checkMessageLength(body)) {
                            System.out.println("Please enter a message of less than 250 characters.");
                            continue;
                        }

                        Chat chat = new Chat(cell, username, body);
                        chat.displayMessageSummary();

                        String action = chat.sendMessageOption();
                        if (action.equals("send")) {
                            messages.add(chat);
                            System.out.println("Message sent.");
                        } else if (action.equals("store")) {
                            chat.storeMessage();
                        } else {
                            System.out.println("Message disregarded.");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println("Total messages sent: " + Chat.returnTotalMessages(messages.size()));
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}

class Chat {
    private static int messageCounter = 1;

    private String messageID;
    private String recipientCell;
    private String sender;
    private String message;
    private LocalDateTime timestamp;
    private String messageHash;

    public Chat(String recipientCell, String sender, String message) {
        this.messageID = generateMessageID();
        this.recipientCell = recipientCell;
        this.sender = sender;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.messageHash = createMessageHash();
    }

    public static String generateMessageID() {
        Random rand = new Random();
        int uniquePart = 100000000 + rand.nextInt(900000000);
        return String.valueOf(messageCounter++) + uniquePart;
    }

    public static boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    public static boolean checkrecipientCell(String cell) {
        return cell.startsWith("+") && cell.length() >= 11 && cell.length() <= 15;
    }
    
    public static boolean checkMessageLength(String message) {
        return message.length() <= 250;
    }

    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String firstTwoDigits = messageID.substring(0, 2);
        String colonCount = String.valueOf(message.chars().filter(ch -> ch == ':').count());

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        return (firstTwoDigits + ":" + colonCount + ": " + firstWord + lastWord).toUpperCase();
    }

    public String sendMessageOption() {
        Object[] options = {"Send", "Store", "Disregard"};
        int choice = JOptionPane.showOptionDialog(null, "What would you like to do with the message?",
                "Message Action", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.YES_OPTION) return "send";
        else if (choice == JOptionPane.NO_OPTION) return "store";
        else return "disregard";
    }

    public void storeMessage() {
        JSONObject obj = new JSONObject();
        obj.put("Message ID", messageID);
        obj.put("Recipient", recipientCell);
        obj.put("Sender", sender);
        obj.put("Message", message);
        obj.put("Time", timestamp.toString());
        obj.put("Hash", messageHash);

        System.out.println("Stored JSON:\n" + obj.toString());
    }

    public void displayMessageSummary() {
        System.out.println("Message ID: " + messageID);
        System.out.println("Hash: " + messageHash);
        System.out.println("Recipient: " + recipientCell);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
    }

    public static int returnTotalMessages(int total) {
        return total;
    }

    private class JSONObject {
        public void put(String messageId, String messageID) {
        }
    }
}
