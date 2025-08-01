package notesmanager;

import java.time.LocalDateTime;
import java.util.List;

public class Note {
    private String id;
    private String title;
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<String> tags;
    private String content;

    public Note(String title, String content, List<String> tags, LocalDateTime created, LocalDateTime modified,
            String id) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        if (created == null && modified == null && id == null) {
            this.id = java.util.UUID.randomUUID().toString(); // Unique note ID
            this.created = LocalDateTime.now();
            this.modified = LocalDateTime.now();
        }else{
            this.id = id;
            this.created = created;
            this.modified = modified;
        }
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }

    // --- Setters ---
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCreated() {
        this.created = LocalDateTime.now();
    }

    public void setModified() {
        this.modified = LocalDateTime.now();
    }

    // --- Update helpers ---
    public void updateContent(String newContent) {
        this.content = newContent;
        this.modified = LocalDateTime.now();
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
        this.modified = LocalDateTime.now();
    }

    public void updateTags(List<String> newTags) {
        this.tags = newTags;
        this.modified = LocalDateTime.now();
    }

    // --- Display note in console ---
    public void display() {
        System.out.println("Title: " + title);
        System.out.println("Created: " + created);
        System.out.println("Modified: " + modified);
        System.out.println("Tags: " + tags);
        System.out.println("\n" + content);
    }

    // --- Convert note to YAML + content for saving ---
    public String toFileFormat() {
        String yamlHeader = "---\n"
                + "title: " + title + "\n"
                + "created: " + created + "\n"
                + "modified: " + modified + "\n"
                + "tags: " + tags + "\n"
                + "---\n\n";
        return yamlHeader + content;
    }

    // --- Search helpers ---
    public boolean matchesQuery(String query) {
        final String lowerQuery = query.toLowerCase();
        return title.toLowerCase().contains(lowerQuery)
                || content.toLowerCase().contains(lowerQuery)
                || tags.stream().anyMatch(t -> t.toLowerCase().contains(lowerQuery));
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public void printTags() {
        System.out.print(getTitle() + getTags());
    }
}
