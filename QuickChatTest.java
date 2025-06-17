import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

// Assuming your Message class has fields: recipient, message, status, ID, and hash
public class QuickChatTest {

    private List<Message> sentMessages;
    private List<Message> storedMessages;
    private List<Message> disregardedMessages;
    private Map<String, String> messageHashes;
    private Map<String, String> messageIDs;

    @BeforeEach
    public void setUp() {
        sentMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
        messageHashes = new HashMap<>();
        messageIDs = new HashMap<>();

        // Test Message 1 (Sent)
        Message m1 = new Message("+27834557896", "Did you get the cake", "Sent");
        sentMessages.add(m1);
        messageHashes.put(m1.getHash(), m1.getMessage());
        messageIDs.put(m1.getId(), m1.getMessage());

        // Test Message 2 (Stored)
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        storedMessages.add(m2);
        messageHashes.put(m2.getHash(), m2.getMessage());
        messageIDs.put(m2.getId(), m2.getMessage());

        // Test Message 3 (Disregarded)
        Message m3 = new Message("+27834484567", "yohoooo, I am at your gate.", "Disregard");
        disregardedMessages.add(m3);
        messageHashes.put(m3.getHash(), m3.getMessage());
        messageIDs.put(m3.getId(), m3.getMessage());
    }

    @Test
    public void testSentMessagesCorrectlyPopulated() {
        List<String> expected = Arrays.asList("Did you get the cake");
        List<String> actual = new ArrayList<>();
        for (Message m : sentMessages) {
            actual.add(m.getMessage());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testLongestSentMessage() {
        sentMessages.add(new Message("+27839999999", "It is dinner time!", "Sent"));
        Message longest = Collections.max(sentMessages, Comparator.comparingInt(m -> m.getMessage().length()));
        assertEquals("It is dinner time!", longest.getMessage());
    }

    @Test
    public void testSearchMessageByID() {
        String expectedMessage = "Where are you? You are late! I have asked you to be on time.";
        boolean found = false;

        for (Map.Entry<String, String> entry : messageIDs.entrySet()) {
            if (entry.getValue().equals(expectedMessage)) {
                assertEquals(expectedMessage, entry.getValue());
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testSearchMessagesByRecipient() {
        List<String> expected = Arrays.asList(
                "Where are you? You are late! I have asked you to be on time."
        );
        List<String> foundMessages = new ArrayList<>();

        for (Message m : sentMessages) {
            if (m.getRecipient().equals("+27838884567")) {
                foundMessages.add(m.getMessage());
            }
        }

        for (Message m : storedMessages) {
            if (m.getRecipient().equals("+27838884567")) {
                foundMessages.add(m.getMessage());
            }
        }

        assertTrue(foundMessages.containsAll(expected));
    }

    @Test
    public void testDeleteMessageByHash() {
        Message toDelete = storedMessages.get(0);
        String hash = toDelete.getHash();

        storedMessages.remove(toDelete);
        messageHashes.remove(hash);

        assertFalse(messageHashes.containsKey(hash));
    }

    @Test
    public void testDisplayReport() {
        assertFalse(sentMessages.isEmpty());
        for (Message m : sentMessages) {
            System.out.println("Hash: " + m.getHash());
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessage());
            System.out.println();
        }
    }
}
