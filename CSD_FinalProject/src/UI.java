
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
public class UI extends JFrame{
    private final Editor editor = new Editor();
    private final UndoHistoryManager undoManager = new UndoHistoryManager();
    private final RedoHistoryManager redoManager = new RedoHistoryManager();

    private final JTextArea area = new JTextArea(15, 50);
    private final JLabel status = new JLabel(" ");

    public UI() {
        setTitle("Undo/Redo Engine for Text Editors");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        area.setEditable(false);          // mọi thay đổi phải đi qua Command
        area.setLineWrap(true);
        area.getCaret().setVisible(true);

        JButton btnInsert = new JButton("Insert");
        JButton btnDelete = new JButton("Delete");
        JButton btnReplace = new JButton("Replace");
        JButton btnUndo = new JButton("Undo");
        JButton btnRedo = new JButton("Redo");

        btnInsert.addActionListener(e -> doInsert());
        btnDelete.addActionListener(e -> doDelete());
        btnReplace.addActionListener(e -> doReplace());
        btnUndo.addActionListener(e -> doUndo());
        btnRedo.addActionListener(e -> doRedo());

        JPanel bar = new JPanel(new FlowLayout());
        bar.add(btnInsert);
        bar.add(btnDelete);
        bar.add(btnReplace);
        bar.add(btnUndo);
        bar.add(btnRedo);

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(bar, BorderLayout.NORTH);
        add(status, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    // ---------- Insert: hỏi text, chèn tại vị trí con trỏ ----------
    private void doInsert() {
        String s = JOptionPane.showInputDialog(this, "Nhập chuỗi cần chèn:");
        if (s == null || s.isEmpty()) return;
        run(new InsertTextCommand(area.getCaretPosition(), s, editor));
    }

    // ---------- Delete: xóa đoạn đang chọn ----------
    private void doDelete() {
        int s = area.getSelectionStart(), t = area.getSelectionEnd();
        if (s == t) { status.setText("Hãy bôi đen đoạn cần xóa"); return; }
        run(new DeleteTextCommand(s, t, editor));
    }

    // ---------- Replace: thay đoạn đang chọn ----------
    private void doReplace() {
        int s = area.getSelectionStart(), t = area.getSelectionEnd();
        if (s == t) { status.setText("Hãy bôi đen đoạn cần thay"); return; }
        String n = JOptionPane.showInputDialog(this, "Thay bằng:");
        if (n == null) return;
        run(new ReplaceTextCommand(s, t, n, editor));
    }

    // ---------- Undo / Redo ----------
    private void doUndo() {
        Command c = undoManager.pop();
        if (c == null) { status.setText("Không còn gì để Undo"); return; }
        c.undo();
        redoManager.push(c);
        refresh("Undo");
    }

    private void doRedo() {
        Command c = redoManager.pop();
        if (c == null) { status.setText("Không còn gì để Redo"); return; }
        c.execute();
        undoManager.push(c);
        refresh("Redo");
    }

    // TODO: thay bằng systemEngine.executeCommand(c) khi SystemEngine xong
    private void run(Command c) {
        try {
            c.execute();
            undoManager.push(c);
            redoManager.clear();           // có thao tác mới thì xóa redo
            refresh("OK");
        } catch (IllegalArgumentException ex) {
            status.setText("Lỗi: " + ex.getMessage());
        }
    }

    private void refresh(String msg) {
        area.setText(editor.getContent());
        status.setText(msg);
    }
}
