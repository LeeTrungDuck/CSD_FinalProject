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
    public void insert(int position, String text){
        
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null!");
        }else if (position < 0 || position > text.length()) {
             throw new IllegalArgumentException("Invalid Position!");
        }
        content = content.substring(0,position)+ text + content.substring(position);
    }
    public void delete(int start, int end){
        content = content.substring(0, start)+content.substring(end);
    }
}
