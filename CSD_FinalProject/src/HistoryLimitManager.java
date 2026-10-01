


public class HistoryLimitManager {

    /**
     * Giới hạn mặc định theo thiết kế (Class Diagram): MAX_SIZE = 30.
     */
    public static final int MAX_SIZE = 30;

    /**
     * Giới hạn hiện tại đang áp dụng.
     */
    private int limit;

    /**
     * Constructor mặc định: dùng giới hạn chuẩn MAX_SIZE = 30.
     */
    public HistoryLimitManager() {
        this.limit = MAX_SIZE;
    }

    /**
     * Constructor với giới hạn tuỳ chỉnh.
     *
     * @param limit số lượng Command tối đa được phép lưu
     */
    public HistoryLimitManager(int limit) {
        this.limit = (limit > 0) ? limit : MAX_SIZE;
    }

    public void setLimit(int limit) {
        this.limit = (limit > 0) ? limit : MAX_SIZE;
    }

    public int getLimit() {
        return limit;
    }

    
    public void enforceLimit(CustomStack stack) {
        if (stack == null) {
            return;
        }
        while (stack.getSize() > limit) {
            removeOldest(stack);
        }
    }

  
    private void removeOldest(CustomStack stack) {
        Node top = stack.getTop();
        if (top == null) {
            return;
        }

        // Chỉ có 1 phần tử: xoá luôn top
        if (top.getNext() == null) {
            stack.setTop(null);
            return;
        }

        // Duyệt tới node ngay trước node cuối cùng (đáy)
        Node curr = top;
        while (curr.getNext().getNext() != null) {
            curr = curr.getNext();
        }

        // curr.getNext() chính là node đáy (cũ nhất) -> cắt bỏ
        curr.setNext(null);

        // setTop() để CustomStack tự tính lại size cho đúng
        stack.setTop(top);
    }
}
