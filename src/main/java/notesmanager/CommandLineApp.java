package notesmanager;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandLineApp {
    private static final NoteManager noteManager = new NoteManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Dano notes editor!");
        System.out.println("Type 'help' for available commands or 'exit' to quit.\n");

        while (true) {
            System.out.print("Dano editor> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty())
                continue;

            String[] parts = input.split("\\s+");
            String command = parts[0].toLowerCase();

            switch (command) {
                case "help":
                case "--help":
                    displayHelp();
                    break;
                case "create":
                    handleCreateCommand();
                    break;
                case "list":
                    handleListCommand(parts);
                    break;
                case "read":
                    handleReadCommand(parts);
                    break;
                case "edit":
                    handleEditCommand(parts);
                    break;
                case "delete":
                    handleDeleteCommand(parts);
                    break;
                case "search":
                    handleSearchCommand(parts);
                    break;
                case "stats":
                    noteManager.stats();
                    break;
                case "exit":
                case "quit":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                case "clear":
                    clearScreen();
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    System.out.println("Type 'help' for available commands.");
            }
            System.out.println(); // Add spacing between commands
        }
    }

    private static void displayHelp() {
        System.out.println("Dano Notes Editor - Available Commands:");
        System.out.println("===================================");
        System.out.println("help, --help                 Display this help information");
        System.out.println("create                       Create a new note (opens in editor)");
        System.out.println("list                         List all notes");
        System.out.println("list --tag <tag>             List notes with specific tag");
        System.out.println("read <note-title>               Display a specific note");
        System.out.println("edit <note-title>               Edit a specific note");
        System.out.println("delete <note-title>             Delete a specific note");
        System.out.println("search <query>               Search notes for text");
        System.out.println("stats                        Display statistics about notes");
        System.out.println("clear                        Clear the screen");
        System.out.println("exit, quit                   Exit the program");
    }

    private static void handleCreateCommand() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        if (noteManager.findNoteByTitle(title) != null) {
            System.out.println("Already a file by the name of '" + title + "'");
            return;
        }

        System.out.print("Enter tags (comma-separated, or press Enter for none): ");
        String tagsInput = scanner.nextLine().trim();

        List<String> tags;
        if (tagsInput.isEmpty()) {
            tags = Arrays.asList();
        } else {
            tags = Arrays.asList(tagsInput.split("\\s*,\\s*"));
        }

        noteManager.createNote(title, tags);
    }

    private static void handleListCommand(String[] parts) {
        if (parts.length >= 3 && "--tag".equals(parts[1])) {
            String tag = parts[2];
            noteManager.listNotesByTag(tag);
        } else {
            noteManager.listNotes();
        }
    }

    private static void handleReadCommand(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: read <note-id>");
            return;
        }

        String noteId = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
        noteManager.readNote(noteId);
    }

    private static void handleEditCommand(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: edit <note-id>");
            return;
        }

        String noteId = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
        noteManager.editNote(noteId);
    }

    private static void handleDeleteCommand(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: delete <note-id>");
            return;
        }

        String noteId = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));

        System.out.print("Are you sure you want to delete this note? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation.toLowerCase()) || "yes".equals(confirmation.toLowerCase())) {
            noteManager.deleteNote(noteId);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void handleSearchCommand(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: search <query>");
            return;
        }

        // Join all arguments after "search" to handle multi-word queries
        String query = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
        noteManager.searchNotes(query);
    }

    private static void clearScreen() {
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }
}
