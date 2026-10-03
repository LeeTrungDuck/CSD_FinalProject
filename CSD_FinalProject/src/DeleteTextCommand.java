/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
public class DeleteTextCommand implements Command{
    private int position;
    private int end;
    private String text;
    private Editor editor;

    public DeleteTextCommand(int position, int end, Editor editor) {
        this.position = position;
        this.end = end;
        this.editor = editor;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void undo() throws UndoRedoException {
        if (editor == null) {
            throw new UndoRedoException("Editor is null!");
        }
        if (text == null) {
            throw new UndoRedoException("Nothing to undo, command has not been executed!");
        }
        editor.insert(position, text);
    }

    @Override
    public void execute() throws UndoRedoException {
        if (editor == null) {
            throw new UndoRedoException("Editor is null!");
        }
        int length = editor.getContent().length();
        if (position < 0 || end < 0 || position > length || end > length) {
            throw new UndoRedoException("Position out of bounds!");
        }
        if (position >= end) {
            throw new UndoRedoException("Start must be less than end!");
        }

        text = editor.getContent().substring(position, end);
        editor.delete(position, end);
    }
}
