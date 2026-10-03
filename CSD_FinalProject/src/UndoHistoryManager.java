/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Lớp UndoHistoryManager quản lý lịch sử các thao tác hoàn tác (Undo)
 * bằng cấu trúc dữ liệu CustomStack tự xây dựng.
 *
 * Manages the undo history stack using the project's CustomStack.
 *
 * @author ltrun
 */
public class UndoHistoryManager {

    /**
     * Stack lưu trữ danh sách các Command có thể hoàn tác. Internal stack
     * storing commands for undo operations.
     */
    private CustomStack undoStack;

    /**
     * Constructor mặc định: khởi tạo undoStack mới và rỗng. Default constructor
     * initializing an empty undoStack.
     */
    public UndoHistoryManager() {
        this.undoStack = new CustomStack();
    }

    /**
     * Constructor với tham số: khởi tạo với một CustomStack đã có.
     * Parameterized constructor initializing with an existing CustomStack.
     *
     * @param undoStack stack chứa lịch sử hoàn tác
     */
    public UndoHistoryManager(CustomStack undoStack) {
        this.undoStack = (undoStack != null) ? undoStack : new CustomStack();
    }

    /**
     * Lấy đối tượng CustomStack của undo history. Gets the undo stack.
     *
     * @return CustomStack đối tượng stack quản lý undo
     */
    public CustomStack getUndoStack() {
        return undoStack;
    }

    /**
     * Thiết lập đối tượng CustomStack cho undo history. Sets the undo stack.
     *
     * @param undoStack stack cần thiết lập
     */
    public void setUndoStack(CustomStack undoStack) {
        this.undoStack = undoStack;
    }

    /**
     * Đẩy một command vào undoStack. Pushes a command onto the undoStack.
     *
     * @param cmd command cần lưu trữ vào lịch sử hoàn tác
     * @throws IllegalArgumentException nếu cmd là null
     */
    public void push(Command cmd) throws UndoRedoException {
        if (cmd == null) {
            throw new UndoRedoException("Command cannot be null");
        }
        if (undoStack == null) {
            undoStack = new CustomStack();
        }
        undoStack.push(cmd);
    }

    /**
     * Lấy và xóa command trên cùng ra khỏi undoStack. Pops and returns the top
     * command from the undoStack.
     *
     * @return Command trên cùng của stack, hoặc null nếu stack rỗng
     */
    public Command pop() {
        if (isEmpty()) {
            return null;
        }
        Node node = undoStack.pop();
        return (node != null) ? node.getData() : null;
    }

    /**
     * Kiểm tra undoStack có đang rỗng hay không. Checks if the undoStack is
     * empty.
     *
     * @return true nếu undoStack rỗng hoặc chưa được khởi tạo, ngược lại false
     */
    public boolean isEmpty() {
        return undoStack == null || undoStack.isEmpty();
    }

    /**
     * Xóa sạch toàn bộ command trong undoStack. Clears all commands from the
     * undoStack.
     */
    public void clear() {
        if (undoStack != null) {
            while (!undoStack.isEmpty()) {
                undoStack.pop();
            }
        }
    }
}
