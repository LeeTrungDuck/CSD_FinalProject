/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Lớp RedoHistoryManager quản lý lịch sử các thao tác làm lại (Redo)
 * bằng cấu trúc dữ liệu CustomStack tự xây dựng.
 *
 * Manages the redo history stack using the project's CustomStack.
 *
 * @author ltrun
 */
public class RedoHistoryManager {

    /**
     * Stack lưu trữ danh sách các Command có thể làm lại. Internal stack
     * storing commands for redo operations.
     */
    private CustomStack redoStack;

    /**
     * Constructor mặc định: khởi tạo redoStack mới và rỗng. Default constructor
     * initializing an empty redoStack.
     */
    public RedoHistoryManager() {
        this.redoStack = new CustomStack();
    }

    /**
     * Constructor với tham số: khởi tạo với một CustomStack đã có.
     * Parameterized constructor initializing with an existing CustomStack.
     *
     * @param redoStack stack chứa lịch sử làm lại
     */
    public RedoHistoryManager(CustomStack redoStack) {
        this.redoStack = (redoStack != null) ? redoStack : new CustomStack();
    }

    /**
     * Lấy đối tượng CustomStack của redo history. Gets the redo stack.
     *
     * @return CustomStack đối tượng stack quản lý redo
     */
    public CustomStack getRedoStack() {
        return redoStack;
    }

    /**
     * Thiết lập đối tượng CustomStack cho redo history. Sets the redo stack.
     *
     * @param redoStack stack cần thiết lập
     */
    public void setRedoStack(CustomStack redoStack) {
        this.redoStack = redoStack;
    }

    /**
     * Đẩy một command vào redoStack. Pushes a command onto the redoStack.
     *
     * @param cmd command cần lưu trữ vào lịch sử làm lại
     * @throws IllegalArgumentException nếu cmd là null
     */
    public void push(Command cmd) {
        if (cmd == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        if (redoStack == null) {
            redoStack = new CustomStack();
        }
        redoStack.push(cmd);
    }

    /**
     * Lấy và xóa command trên cùng ra khỏi redoStack. Pops and returns the top
     * command from the redoStack.
     *
     * @return Command trên cùng của stack, hoặc null nếu stack rỗng
     */
    public Command pop() {
        if (isEmpty()) {
            return null;
        }
        Node node = redoStack.pop();
        return (node != null) ? node.getData() : null;
    }

    /**
     * Kiểm tra redoStack có đang rỗng hay không. Checks if the redoStack is
     * empty.
     *
     * @return true nếu redoStack rỗng hoặc chưa được khởi tạo, ngược lại false
     */
    public boolean isEmpty() {
        return redoStack == null || redoStack.isEmpty();
    }

    /**
     * Làm rỗng toàn bộ redoStack. Clears all commands from the redoStack.
     */
    public void clear() {
        if (redoStack != null) {
            while (!redoStack.isEmpty()) {
                redoStack.pop();
            }
        }
    }
}
