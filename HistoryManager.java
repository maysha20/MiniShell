import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private final String historyFile;

    public HistoryManager(String username) {
        this.historyFile = username + "_history.txt";
    }

    public void addToHistory(String operation, double result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile, true))) {
            writer.write(operation + " = " + result);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to history file: " + e.getMessage());
        }
    }

    public void showHistory() {
        System.out.println("\n--- Operation History ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String line;
            int count = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(count++ + ". " + line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No history found for this user.");
        } catch (IOException e) {
            System.out.println("Error reading history file: " + e.getMessage());
        }
    }
}