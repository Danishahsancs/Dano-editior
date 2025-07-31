package notesmanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import notesmanager.TextEditorHelper;

public class NoteManager {
    private static final String noteDirectory = "/Users/danish/Desktop/Dano_Notes";

    public static String getNoteDirectory() {
        return noteDirectory;
    }

    public void createNote(String title, String content, List<String> tags) {
        Note note = new Note(title, content, tags);

        
        String safeTitle = title.replaceAll("\\s+", "_");
        File file = new File(noteDirectory, safeTitle + "_" + note.getId() + ".note");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(note.toFileFormat());
            System.out.println("Note created: " + file.getName());

            
            TextEditorHelper.openEditor(file.getAbsolutePath());
            System.out.println("Finished editing: " + file.getName());
            note.setModified();
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to create or edit note: " + e.getMessage());
        }
    }

    public void readNote() {

    }
}
