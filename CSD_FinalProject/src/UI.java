import java.util.Scanner;

public class UI {

    private final Scanner sc = new Scanner(System.in);
    private final Editor editor = new Editor();
    private final UndoHistoryManager undo = new UndoHistoryManager();
    private final RedoHistoryManager redo = new RedoHistoryManager();

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
                    if (len == 0) { error("Content is empty!"); break; }
                    int[] r = inputRange(len);
                    c = new DeleteTextCommand(r[0], r[1], editor);
                    break;
                case 3:
                    if (len == 0) { error("Content is empty!"); break; }
                    int[] r2 = inputRange(len);
                    c = new ReplaceTextCommand(r2[0], r2[1], inputText("New text: "), editor);
                    break;
                case 4:
                    Command u = undo.pop();
                    if (u == null) { error("Nothing to undo!"); break; }
                    u.undo();
                    redo.push(u);
                    break;
                case 5:
                    Command d = redo.pop();
                    if (d == null) { error("Nothing to redo!"); break; }
                    d.execute();
                    undo.push(d);
                    break;
                case 6:
                    info("Bye!");
                    return;
            }

            // TODO: thay bằng systemEngine.executeCommand(c) khi SystemEngine xong
            if (c != null) {
                try {
                    c.execute();
                    undo.push(c);
                    redo.clear();
                } catch (IllegalArgumentException e) {
                    error("Error: " + e.getMessage());
                }
            }
        }
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
    System.out.println("|  6. Exit                         |");
    System.out.println("+==================================+");
    return inputInt(">> Your choice: ", 1, 6);
    }

    // ---------- Nhập liệu ----------
    private int inputPosition(int len) {
        if (len == 0) return 0;
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
            if (!s.isEmpty()) return s;
            System.err.println("Input must not be empty");
        }
    }

    private int inputInt(String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) return n;
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
    }

    private void info(String msg) {
        System.out.println(msg);
    }

    private void error(String msg) {
        System.err.println(msg);
    }
}