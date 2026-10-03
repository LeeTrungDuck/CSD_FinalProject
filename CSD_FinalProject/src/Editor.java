/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ltrun
 */
public class Editor {

    private String content;

    public Editor() {
        content = new String();
    }

    public String getContent() {
        return content;
    }

    public void insert(int position, String text) throws UndoRedoException {

        if (text == null) {
            throw new UndoRedoException("Text cannot be null!");
        } else if (position < 0 || position > content.length()) {
            throw new UndoRedoException("Invalid Position!");
        }
        content = content.substring(0, position) + text + content.substring(position);
    }

    public void delete(int start, int end) throws UndoRedoException {
        if (start < 0 || end < 0 || start > content.length() || end > content.length()) {
            throw new UndoRedoException("Position out of bounds!");
        }

        if (start >= end) {
            throw new UndoRedoException("Start must be less than end!");
        }
        content = content.substring(0, start) + content.substring(end);
    }

    public void replace(int start, int end, String newText) throws UndoRedoException {
        if (newText == null) {
            throw new UndoRedoException("Text cannot be nulL!");
        }
        if (start < 0 || end < 0 || start > content.length() || end > content.length()) {
            throw new UndoRedoException("Position out of bounds!");
        }

        if (start >= end) {
            throw new UndoRedoException("Start must be less than end!");
        }

        content = content.substring(0, start) + newText + content.substring(end);
    }
}
