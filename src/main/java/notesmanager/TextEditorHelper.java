package notesmanager;

import java.io.IOException;

public class TextEditorHelper {

    // Opens a file in nano (can change editor later)
    public static void openEditor(String filePath) throws IOException, InterruptedException {
        new ProcessBuilder("nano", filePath)
                .inheritIO()
                .start()
                .waitFor();
    }
}

