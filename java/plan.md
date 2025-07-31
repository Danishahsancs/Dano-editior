# Personal Notes Manager – Phase 1 Plan

## 🎯 Goal
Build a **CLI-based note manager** that stores notes as UTF-8 text files with **YAML headers** for metadata.

---

## ✅ Features

1. **Create notes**
   - Opens default editor (`nano`)
   - Auto-add YAML metadata (title, created, modified)
2. **Read notes**
   - Display note in terminal
3. **Edit notes**
   - Open existing note in `nano`
4. **Delete notes**
   - Remove note file
5. **List notes**
   - Show all notes, optionally filter by tag
6. **Search notes**
   - Find by title, content, or tags
7. **Stats**
   - Count notes, tags, etc.

---


---

## 🧩 Core Classes 

- `Note` – Represents a single note
- `NoteManager` – Handles CRUD, search, and stats
- `CommandLineApp` – CLI entry point
- `TextEditorHelper` – Opens notes in `nano`
- `YamlParserHelper` – Reads/writes YAML metadata
- `FileUtils` – Handles filesystem I/O

---

## 🛠️ Technical Requirements

- Language: Java 11+ or Python 3.11+
- YAML parser (`SnakeYAML` or `PyYAML`)
- Default editor: `nano` (configurable)
- UTF-8 `.note` files

---

## 🚀 Next Steps

1. Set up folder structure & constants (notes directory, editor)
2. Implement `Note` class and file I/O
3. Add `createNote` and `listNotes` commands
4. Integrate `nano` via `TextEditorHelper`
5. Expand to edit, delete, and search

---

## 🔮 Future-Proofing Notes

- Keep editor configurable (swap `nano` → `vim` or `code`)
- Use modular classes (CLI, manager, I/O separated)
- Avoid hardcoded paths and limits
- Write clear docstrings and comments
- Keep YAML file format for easy migration to databases later

