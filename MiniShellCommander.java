import java.io.File;
import java.io.IOException;

class ShellCommandHandler {
    private File currentDirectory;

    public ShellCommandHandler() {
        // TODO: Initialize currentDirectory to the user's current working directory
        // Hint: Use System.getProperty("user.dir")
        currentDirectory = new File(System.getProperty("user.dir"));
    }

    public void printWorkingDirectory() {
        // TODO: Print the absolute path of the current directory
        System.out.println(System.getProperty("user.dir"));
    }

    public void listDirectory() {
        // TODO: List all files and directories in the current directory
        // For directories, prefix with "[DIR] "
        // For files, prefix with "[FILE]"
        File[] files = currentDirectory.listFiles(); // Get all items in current directory

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("[DIR]  " + file.getName());
                } else {
                    System.out.println("[FILE] " + file.getName());
                }
            }
        } else {
            System.out.println("Unable to access the directory.");
        }
    }

    public void changeDirectory(String name) {
        // TODO: Implement the change directory command
        // If name is null, show usage message: "Usage: cd [directory_name]"
        // If name is "..", move to parent directory if possible
        // Otherwise, try to move to the specified directory
        // If directory doesn't exist, print error message: "Directory not found: [name]"
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Usage: cd [directory_name]");
            return;
        }

        if (name.equals("..")) {
            File parent = currentDirectory.getParentFile();
            if (parent != null && parent.exists()) {
                currentDirectory = parent;
            } else {
                System.out.println("Already at the root directory.");
            }
        } else {
            File newDir = new File(currentDirectory, name);
            if (newDir.exists() && newDir.isDirectory()) {
                currentDirectory = newDir;
            } else {
                System.out.println("Directory not found: " + name);
            }
        }
    }

    public void makeDirectory(String name) {
        // TODO: Implement the make directory command
        // If name is null, show usage message: "Usage: mkdir [directory_name]"
        // If directory already exists, print: "Directory already exists."
        // If directory creation is successful, print: "Directory created: [name]"
        // If directory creation fails, print: "Failed to create directory."
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Usage: mkdir [directory_name]");
            return;
        }

        File newDir = new File(currentDirectory, name);

        if (newDir.exists()) {
            System.out.println("Directory already exists.");
        } else {
            boolean created = newDir.mkdir();
            if (created) {
                System.out.println("Directory created: " + name);
            }
            else {
                System.out.println("Failed to create directory.");
            }
        }
    }

    public void createFile(String name) {
        // TODO: Implement the create file command
        // If name is null, show usage message: "Usage: touch [file_name]"
        // If file already exists, print: "File already exists."
        // If file creation is successful, print: "File created: [name]"
        // If file creation fails, print error message with exception details
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Usage: touch [file_name]");
            return;
        }

        File newFile = new File(currentDirectory, name);

        if (newFile.exists()) {
            System.out.println("File already exists.");
        } else {
            try {
                boolean created = newFile.createNewFile();
                if (created) {
                    System.out.println("File created: " + name);
                } else {
                    System.out.println("Failed to create file.");
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }

    public void printHelp() {
        // TODO: Implement help command to print information about all available commands
        // List all commands with their descriptions
        System.out.println("type pwd for working directory \ntype ls for List of files in current directory \n" +
                "write cd for change directory \nwrite mkdir to create new directory \nwrite touch to create new file \n" +
                "write exit to exit the program");
    }

    public File getCurrentDirectory() {
        // TODO: Return the current directory
        return currentDirectory; // Replace this with your implementation
    }
}