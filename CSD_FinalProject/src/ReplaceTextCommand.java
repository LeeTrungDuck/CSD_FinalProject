/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
public class ReplaceTextCommand implements Command{
    private int position;
    private int end;
    private String oldText;
    private String newText;
    private Editor editor;

    public ReplaceTextCommand(int position, int end, String newText, Editor editor) {
        this.position = position;
        this.end = end;
        this.newText = newText;
        this.editor = editor;
    }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public int getEnd() { return end; }
    public void setEnd(int end) { this.end = end; }
    public String getOldText() { return oldText; }
    public void setOldText(String oldText) { this.oldText = oldText; }
    public String getNewText() { return newText; }
    public void setNewText(String newText) { this.newText = newText; }
    public Editor getEditor() { return editor; }
    public void setEditor(Editor editor) { this.editor = editor; }

    @Override
    public void execute() throws UndoRedoException {
        if (editor == null) {
            throw new UndoRedoException("Editor is null!");
        }
        if (position < 0 || end > editor.getContent().length() || position >= end) {
            throw new UndoRedoException("Position out of bounds!");
        }
        oldText = editor.getContent().substring(position, end);
        editor.replace(position, end, newText);
    }

    @Override
    public void undo() throws UndoRedoException {
        if (editor == null || oldText == null || newText == null) {
            throw new UndoRedoException("Editor or text is null!");
        }
        if (oldText != null) {
            editor.replace(position, position + newText.length(), oldText);
        }
    }
}
