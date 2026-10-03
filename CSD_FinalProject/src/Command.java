/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author ltrun
 */
public interface Command {
    public void undo() throws UndoRedoException;
    public void execute()throws UndoRedoException;
}
