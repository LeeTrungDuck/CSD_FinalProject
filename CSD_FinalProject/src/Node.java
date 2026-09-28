/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ltrun
 */
public class Node {
    private Command data;
    private Node next;

    public Node() {
    }

    public Node(Command data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Command getData() {
        return data;
    }

    public void setData(Command data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    
    
}
