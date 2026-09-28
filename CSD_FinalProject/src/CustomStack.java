/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ltrun
 */
public class CustomStack {
    private Node top;
    private int size;
    public CustomStack() {
        this.size = 0;
    }

    public CustomStack(Node top, int size) {
        this.size = 0;
        this.top = top;
        this.size = size;
    }

    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
        Node curr = top;
        int count =0;
        while(curr != null){
            count++;
            curr = curr.getNext();
        }
        size = count;
    }
    public boolean isEmpty(){
        return top == null;
    }
    
    public void push(Command command){
        
        if (command == null) return;
        Node node = new Node(command, top);
        top = node;
        size++;
    }
    
    public Node pop(){
        if (top == null) return null;
        Node tmp = top;
        top = top.getNext();
        tmp.setNext(null);
        size--;
        return tmp;
    }
    
    public Node peek(){
        return top;
    }
    
    public int getSize(){
        return size;
    }
}
