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
    public void undo() {
        if (editor == null) {
            throw new IllegalArgumentException("Editor is null!");
        }
        if (text == null) {
            throw new IllegalArgumentException("Nothing to undo, command has not been executed!");
        }
        editor.insert(position, text);
    }

    @Override
    public void execute() {
        if (editor == null) {
            throw new IllegalArgumentException("Editor is null!");
        }
        int length = editor.getContent().length();
        if (position < 0 || end < 0 || position > length || end > length) {
            throw new IllegalArgumentException("Position out of bounds!");
        }
        if (position >= end) {
            throw new IllegalArgumentException("Start must be less than end!");
        }

        text = editor.getContent().substring(position, end);
        editor.delete(position, end);
    }
}
