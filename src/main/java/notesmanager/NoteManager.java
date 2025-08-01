package notesmanager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import notesmanager.TextEditorHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class NoteManager {
    private static final String NOTE_FILE = "/Users/danish/Desktop/Dano_Notes/notes.yaml";
    private static final String TEMP_FILE = "/Users/danish/Desktop/Dano_Notes/temp_edit.note";

    private List<Note> notes;

    public NoteManager() {
        notes = new ArrayList<>();
        loadNotes();
    }

    public void createNote(String title, List<String> tags) {
        if (findNoteByTitle(title) != null) {
            System.out.println("Note with name'" + title + "' already exsits");
            return;
        }

        try {
            TextEditorHelper.openEditor(TEMP_FILE);

            String content = Files.readString(Paths.get(TEMP_FILE));
            Note newNote = new Note(title, content, tags, null, null, null);
            notes.add(newNote);
            saveNotes();

            Files.deleteIfExists(Path.of(TEMP_FILE));
            System.out.println("Note created and saved");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readNote(String title) {
        Note note = findNoteByTitle(title);
        if (note != null) {
            note.display();
        } else {
            System.out.println("Note not found. Try again!");
        }
    }

    public void editNote(String title) {
        Note note = findNoteByTitle(title);

        if (note == null) {
            System.out.println("Note with name'" + title + "' does not exsits");
            return;
        }

        try {
            Files.write(Paths.get(TEMP_FILE), note.getContent().getBytes());
            TextEditorHelper.openEditor(TEMP_FILE);

            String updatedContent = Files.readString(Paths.get(TEMP_FILE));
            note.updateContent(updatedContent);

            saveNotes();
            Files.deleteIfExists(Paths.get(TEMP_FILE));
            System.out.println("Note is updated");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteNote(String title) {
        if (notes.removeIf(note -> note.getTitle().toLowerCase().equals(title.toLowerCase()))) {
            System.out.println("Succesfully removed: " + title);
        } else {
            System.out.println("Note with name'" + title + "' does not exsits");
        }
    }

    public void searchNotes(String query) {
        List<Note> results = notes.stream()
                .filter(note -> note.matchesQuery(query))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No Matching notes found.");
        } else {
            for (Note note : results) {
                note.display();
            }
        }
    }

    public void stats() {
        System.out.println("Total Notes: " + notes.size());
    }

    public void listNotes() {
        for (Note note : notes) {
            System.out.println("~" + note.getTitle());
        }
    }

    public void listNotesByTag(String tag) {
        for (Note note : notes) {
            if (note.hasTag(tag)) {
                note.printTags();
            }
        }
    }

    // helper funtctions
    public Note findNoteByTitle(String title) {
        if (notes == null)
            return null;
        return notes.stream()
                .filter(note -> note.getTitle().toLowerCase().equals(title.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    private void loadNotes() {
        File notesFile = new File(NOTE_FILE);

        try (FileReader reader = new FileReader(notesFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(reader);

            if (data != null && data.containsKey("notes")) {
                List<Map<String, Object>> noteData = (List<Map<String, Object>>) data.get("notes");

                notes = noteData.stream()
                        .map(this::mapToNote)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.out.println("Error loading files: " + e.getMessage());
        }
    }

    private void saveNotes() {
        File dir = new File(NOTE_FILE);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("notes", notes.stream()
                .map(this::noteToMap)
                .collect(Collectors.toList()));

        try (FileWriter writer = new FileWriter(NOTE_FILE)) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            Yaml yaml = new Yaml(options);
            yaml.dump(data, writer);
        } catch (Exception e) {
            System.out.println("Error saving Data: " + e.getMessage());
        }
    }

    private Note mapToNote(Map<String, Object> map) {
        Note note = new Note(map.get("title").toString(), map.get("content").toString(), (List<String>) map.get("tags"),
                LocalDateTime.parse(map.get("created").toString()), LocalDateTime.parse(map.get("modified").toString()),
                map.get("id").toString());
        return note;
    }

    private Map<String, Object> noteToMap(Note note) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", note.getId());
        map.put("title", note.getTitle());
        map.put("created", note.getCreated().toString());
        map.put("modified", note.getModified().toString());
        map.put("tags", note.getTags());
        map.put("content", note.getContent());
        return map;
    }
}
