
import java.util.Scanner;

public class UI {

    private static final String DEFAULT_PATH = "content.txt";

    private final Scanner sc = new Scanner(System.in);
    private final Editor editor = new Editor();
    private final UndoHistoryManager undo = new UndoHistoryManager();
    private final RedoHistoryManager redo = new RedoHistoryManager();
    private final HistoryLimitManager limit = new HistoryLimitManager();
    private final FileHandle fileHandle = new FileHandle(DEFAULT_PATH);
    private final SystemEngine engine
            = new SystemEngine(editor, undo, redo, limit, fileHandle);

    // ---------- Chạy chương trình ----------
    public void start() {
        while (true) {
            show(editor.getContent());
            int len = editor.getContent().length();
            Command c = null;
            switch (menu()) {
                case 1:
                    int pos = inputPosition(len);
                    c = new InsertTextCommand(pos, inputText("Text: "), editor);
                    break;
                case 2:
                    if (len == 0) {
                        error("Content is empty!");
                        break;
                    }
                    int[] r = inputRange(len);
                    c = new DeleteTextCommand(r[0], r[1], editor);
                    break;
                case 3:
                    if (len == 0) {
                        error("Content is empty!");
                        break;
                    }
                    int[] r2 = inputRange(len);
                    c = new ReplaceTextCommand(r2[0], r2[1], inputText("New text: "), editor);
                    break;
                case 4:
                    if (undo.isEmpty()) {
                        error("Nothing to undo!");
                        break;
                    }
                    try {
                        engine.undo();
                    } catch (UndoRedoException e) {
                        error("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    if (redo.isEmpty()) {
                        error("Nothing to redo!");
                        break;
                    }
                    try {
                        engine.redo();
                    } catch (UndoRedoException e) {
                        error("Error: " + e.getMessage());
                    }
                    break;
                case 6:
                    saveFile();
                    break;
                case 7:
                    loadFile();
                    break;
                case 8:
                    changeLimit();
                    break;
                case 9:
                    if (confirm("Are you sure to exit?")) {
                        info("Bye!");
                        return;
                    }
                    break;
            }
            if (c != null) {
                try {
                    engine.executeCommand(c);
                } catch (UndoRedoException e) {
                    error("Error: " + e.getMessage());
                }
            }
        }
    }
    // ---------- Save / Load (FileHandle) ----------

    private void saveFile() {
        String oldPath = fileHandle.getFilePath();
        fileHandle.setFilePath(inputPath());
        try {
            engine.saveFile();
            info("Saved to: " + fileHandle.getFilePath());
        } catch (UndoRedoException e) {
            fileHandle.setFilePath(oldPath); // khôi phục đường dẫn cũ
            error("Save failed: " + e.getMessage());
        }
    }

    private void loadFile() {
        String oldPath = fileHandle.getFilePath();
        fileHandle.setFilePath(inputPath());
        try {
            engine.loadFile();
            info("Loaded from: " + fileHandle.getFilePath()
                    + " (undo/redo history cleared)");
        } catch (UndoRedoException e) {
            fileHandle.setFilePath(oldPath);
            error("Load failed: " + e.getMessage());

        }
    }

    private String inputPath() {
        System.out.print("File path [" + fileHandle.getFilePath() + "]: ");
        String s = sc.nextLine().trim();
        return s.isEmpty() ? fileHandle.getFilePath() : s;
    }

    // ---------- Giới hạn lịch sử (HistoryLimitManager) ----------
    private void changeLimit() {
        info("Current limit: " + limit.getLimit()
                + " (default " + HistoryLimitManager.MAX_SIZE + ")");
        int newLimit = inputInt("New limit (1-1000): ", 1, 1000);
        limit.setLimit(newLimit);
        // Cắt ngay phần vượt quá giới hạn mới
        limit.enforceLimit(undo.getUndoStack());
        limit.enforceLimit(redo.getRedoStack());
        info("History limit set to " + limit.getLimit());
    }

    // ---------- Menu ----------
    private int menu() {
        System.out.println();
        System.out.println("+==================================+");
        System.out.println("|        TEXT EDITOR - UNDO/REDO   |");
        System.out.println("+==================================+");
        System.out.println("|  1. Insert text                  |");
        System.out.println("|  2. Delete text                  |");
        System.out.println("|  3. Replace text                 |");
        System.out.println("|----------------------------------|");
        System.out.println("|  4. Undo                         |");
        System.out.println("|  5. Redo                         |");
        System.out.println("|----------------------------------|");
        System.out.println("|  6. Save file                    |");
        System.out.println("|  7. Load file                    |");
        System.out.println("|  8. Set history limit            |");
        System.out.println("|----------------------------------|");
        System.out.println("|  9. Exit                         |");
        System.out.println("+==================================+");
        return inputInt(">> Your choice: ", 1, 9);
    }

    // ---------- Nhập liệu ----------
    private int inputPosition(int len) {
        if (len == 0) {
            return 0;
        }
        return inputInt("Position (0-" + len + "): ", 0, len);
    }

    private int[] inputRange(int len) {
        int start = inputInt("Start (0-" + (len - 1) + "): ", 0, len - 1);
        int end = inputInt("End (" + (start + 1) + "-" + len + "): ", start + 1, len);
        return new int[]{start, end};
    }

    private String inputText(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            if (!s.isEmpty()) {
                return s;
            }
            System.err.println("Input must not be empty");
        }
    }

    private int inputInt(String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) {
                    return n;
                }
                System.err.println("Please input number in range [" + min + ", " + max + "]");
            } catch (NumberFormatException e) {
                System.err.println("Please input a valid integer");
            }
        }
    }

    // ---------- Hiển thị ----------
    private void show(String content) {
        System.out.println();
        System.out.println("+----------------- CONTENT -----------------+");
        System.out.println("  " + (content.isEmpty() ? "(empty)" : content));
        System.out.println("+-------------------------------------------+");
        System.out.println("  Length: " + content.length());
        System.out.println("  Undo: " + undo.getUndoStack().getSize() + "/" + limit.getLimit()
                + " | Redo: " + redo.getRedoStack().getSize() + "/" + limit.getLimit());
    }

    // YES / NO
    private boolean confirm(String msg) {
        while (true) {
            System.out.print(msg + " (y/n): ");
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) {
                return true;
            }
            if (s.equals("n") || s.equals("no")) {
                return false;
            }
            System.err.println("Please enter y or n");
        }
    }

    private void info(String msg) {
        System.out.println(msg);
    }

    private void error(String msg) {
        System.err.println(msg);
    }
}
