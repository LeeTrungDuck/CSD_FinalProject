/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ltrun
 */
public class InsertTextCommand implements Command {

    private int position;
    private String text;
    private Editor editor;

    public InsertTextCommand(int position, String text, Editor editor) {
        this.position = position;
        this.text = text;
        this.editor = editor;
    }

    @Override
    public void undo() throws UndoRedoException { // doan nay co nem loi, nho bat loi, dung de hoan tac 1 thao tac them
        if (editor == null || text == null) {
            throw new UndoRedoException("Editor Is Null!");
        } else {
            editor.delete(position, position + text.length());
        }

    }

    @Override
    public void execute() throws UndoRedoException { //dung khi them 1 chuoi - co exeption nho bat
        if (editor == null) {
            throw new UndoRedoException("Editor Is Null!");
        } else {
            editor.insert(position, text);
        }

    }

}
