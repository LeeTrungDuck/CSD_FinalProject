import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class FileHandle {

    private String filePath;

    public FileHandle(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

   
    public String readFile() throws UndoRedoException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new UndoRedoException("File not found: " + filePath);
        }

        char[] buffer = new char[(int) file.length()];

        try (FileReader reader = new FileReader(file)) {
            int totalRead = 0;
            int charsRead;
            // Đọc cho tới khi đầy buffer hoặc hết file
            while (totalRead < buffer.length
                    && (charsRead = reader.read(buffer, totalRead, buffer.length - totalRead)) != -1) {
                totalRead += charsRead;
            }
            return new String(buffer, 0, totalRead);
        } catch (IOException e) {
            throw new UndoRedoException("Could not read file: " + filePath);
        }
    }

   
    public void writeFile(String content) throws UndoRedoException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content == null ? "" : content);
        } catch (IOException e) {
            throw new UndoRedoException("Could not write file: " + filePath);
        }
    }
}