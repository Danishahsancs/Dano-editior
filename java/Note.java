package java;

import java.time.LocalDateTime;
import java.util.List;

public class Note {
    private String id;
    private String title;
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<String> tags;
    private String content;

    public Note(String title, String content, List<String> tags) {
        this.id = java.util.UUID.randomUUID().toString(); // Unique note ID
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }

    // Getters
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

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreated() {
        this.created = LocalDateTime.now();
    }

    public void setModified() {
        this.modified = LocalDateTime.now();
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public void display() {
        System.out.println("Title: " + title);
        System.out.println("Created: " + created);
        System.out.println("Modified: " + modified);
        System.out.println("Tags: " + tags);
        System.out.println("\n" + content);
    }

    public String toFileFormat() {
        String yamlHeader = "---\n"
                + "title: " + title + "\n"
                + "created: " + created + "\n"
                + "modified: " + modified + "\n"
                + "tags: " + tags + "\n"
                + "---\n\n";
        return yamlHeader + content;
    }

    public boolean matchesQuery(String query) {
        final String lowerQuery = query.toLowerCase();
        return title.toLowerCase().contains(lowerQuery)
                || content.toLowerCase().contains(lowerQuery)
                || tags.stream().anyMatch(t -> t.toLowerCase().contains(lowerQuery));
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

}
