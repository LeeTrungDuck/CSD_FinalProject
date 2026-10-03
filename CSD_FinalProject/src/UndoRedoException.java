/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */

/**
 *
 * @author ltrun
 */
public class UndoRedoException extends Exception{

    /**
     * Creates a new instance of <code>UndoRedoException</code> without detail
     * message.
     */
    public UndoRedoException() {
    }

    /**
     * Constructs an instance of <code>UndoRedoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UndoRedoException(String msg) {
        super(msg);
    }
}
