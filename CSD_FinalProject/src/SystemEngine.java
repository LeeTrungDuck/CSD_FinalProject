/**
 * Lớp SystemEngine là bộ điều phối trung tâm của Undo/Redo Engine,
 * kết nối Editor, UndoHistoryManager, RedoHistoryManager,
 * HistoryLimitManager và FileHandle.
 *
 * Main controller coordinating the Editor, undo/redo history managers,
 * history limit enforcement, and file persistence.
 *
 * @author ltrun
 */
public class SystemEngine {

    private Editor editor;
    private UndoHistoryManager undoManager;
    private RedoHistoryManager redoManager;
    private HistoryLimitManager limitManager;
    private FileHandle fileHandle;

    public SystemEngine(Editor editor, UndoHistoryManager undoManager,
                         RedoHistoryManager redoManager, HistoryLimitManager limitManager,
                         FileHandle fileHandle) {
        this.editor = editor;
        this.undoManager = undoManager;
        this.redoManager = redoManager;
        this.limitManager = (limitManager != null) ? limitManager : new HistoryLimitManager();
        this.fileHandle = fileHandle;
    }

    // ---------- Getter / Setter ----------
    public Editor getEditor() { return editor; }
    public void setEditor(Editor editor) { this.editor = editor; }

    public UndoHistoryManager getUndoManager() { return undoManager; }
    public void setUndoManager(UndoHistoryManager undoManager) { this.undoManager = undoManager; }

    public RedoHistoryManager getRedoManager() { return redoManager; }
    public void setRedoManager(RedoHistoryManager redoManager) { this.redoManager = redoManager; }

    public HistoryLimitManager getLimitManager() { return limitManager; }
    public void setLimitManager(HistoryLimitManager limitManager) { this.limitManager = limitManager; }

    public FileHandle getFileHandle() { return fileHandle; }
    public void setFileHandle(FileHandle fileHandle) { this.fileHandle = fileHandle; }

    // ---------- Core Operations ----------

 
    public void executeCommand(Command command) {
        if (command == null) {
            return;
        }
        command.execute();
        undoManager.push(command);
        limitManager.enforceLimit(undoManager.getUndoStack());
        redoManager.clear();
    }

    /**
     * Hoàn tác hành động gần nhất: pop khỏi Undo History, gọi undo(),
     * rồi chuyển Command sang Redo History.
     *
     * Reverses the last action and moves it to the redo history.
     */
    public void undo() {
        Command command = undoManager.pop();
        if (command != null) {
            command.undo();
            redoManager.push(command);
            limitManager.enforceLimit(redoManager.getRedoStack());
        }
    }

    /**
     * Làm lại hành động vừa hoàn tác: pop khỏi Redo History, gọi execute(),
     * rồi chuyển Command trở lại Undo History.
     *
     * Re-applies the last undone action and moves it back to undo history.
     */
    public void redo() {
        Command command = redoManager.pop();
        if (command != null) {
            command.execute();
            undoManager.push(command);
            limitManager.enforceLimit(undoManager.getUndoStack());
        }
    }

    /**
     * Lưu nội dung hiện tại của Editor ra file thông qua FileHandle.
     *
     * Saves the current editor content to disk via FileHandle.
     */
    public void saveFile() {
        if (fileHandle == null) {
            throw new IllegalStateException("FileHandle is not set!");
        }
        fileHandle.writeFile(editor.getContent());
    }

   
    public void loadFile() {
        if (fileHandle == null) {
            throw new IllegalStateException("FileHandle is not set!");
        }
        String content = fileHandle.readFile();

        int currentLength = editor.getContent().length();
        if (currentLength > 0) {
            editor.delete(0, currentLength);
        }
        if (content != null && !content.isEmpty()) {
            editor.insert(0, content);
        }

        undoManager.clear();
        redoManager.clear();
    }
}