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

    public void insert(int position, String text) {

        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null!");
        } else if (position < 0 || position > content.length()) {
            throw new IllegalArgumentException("Invalid Position!");
        }
        content = content.substring(0, position) + text + content.substring(position);
    }

    public void delete(int start, int end) {
        if (start < 0 || end < 0 || start > content.length() || end > content.length()) {
            throw new IllegalArgumentException("Position out of bounds!");
        }

        if (start >= end) {
            throw new IllegalArgumentException("Start must be less than end!");
        }
        content = content.substring(0, start) + content.substring(end);
    }

    public void replace(int start, int end, String newText) {
        if (newText == null) {
            throw new IllegalArgumentException("Text cannot be nulL!");
        }
        if (start < 0 || end < 0 || start > content.length() || end > content.length()) {
            throw new IllegalArgumentException("Position out of bounds!");
        }

        if (start >= end) {
            throw new IllegalArgumentException("Start must be less than end!");
        }

        content = content.substring(0, start) + newText + content.substring(end);
    }
}
