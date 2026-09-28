
import java.util.Stack;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author ltrun
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          Editor editor = new Editor();

    editor.insert(0, "Hello");
    System.out.println(editor.getContent());
    // Hello
    Command newCommand = new InsertTextCommand(10, "1 Word", editor);
    newCommand.execute();
    System.out.println(editor.getContent());
    
    }
}
