import java.util.UUID;

public class Message {
    private String recipient;
    private String message;
    private String flag;

    private String status;
    private String id;
    private String hash;

    public Message(String recipient, String message, String status) {
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;
        this.status = status;
        this.id = generateID();
        this.hash = generateHash();
    }

    private String generateID() {
        // Generates a short unique ID
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String generateHash() {
        String[] words = message.trim().split("\\s+");
        String firstTwoDigits = id.length() >= 2 ? id.substring(0, 2) : "00";
        long colonCount = message.chars().filter(ch -> ch == ':').count();
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        return (firstTwoDigits + ":" + colonCount + ":" + firstWord + lastWord).toUpperCase();
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }
}
