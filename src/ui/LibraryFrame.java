package ui;
import db.Database;
import model.Book;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LibraryFrame extends JFrame {

    private ArrayList<Book> books;
    private DefaultTableModel tableModel;
    private JTable bookTable;

    public LibraryFrame() {
        try {
            setTitle("Library Management System");
            setSize(700, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Table setup
            tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Available"}, 0);
            bookTable = new JTable(tableModel);
            add(new JScrollPane(bookTable), BorderLayout.CENTER);

            // Bottom panel for buttons
            JPanel bottomPanel = new JPanel();
            JButton addButton = new JButton("Add");
            JButton borrowButton = new JButton("Borrow");
            JButton returnButton = new JButton("Return");
            JButton deleteButton = new JButton("Delete");

            bottomPanel.add(addButton);
            bottomPanel.add(borrowButton);
            bottomPanel.add(returnButton);
            bottomPanel.add(deleteButton);
            add(bottomPanel, BorderLayout.SOUTH);

            // Top panel for search
            JPanel topPanel = new JPanel();
            JTextField searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            topPanel.add(new JLabel("Search:"));
            topPanel.add(searchField);
            topPanel.add(searchButton);
            add(topPanel, BorderLayout.NORTH);

            // Load books from DB
            loadBooks();

            // Add book
            addButton.addActionListener(e -> {
                String title = JOptionPane.showInputDialog(this, "Enter book title:");
                String author = JOptionPane.showInputDialog(this, "Enter author:");
                if (title != null && author != null && !title.isEmpty() && !author.isEmpty()) {
                    Database.addBook(title, author);
                    loadBooks();
                }
            });

            // Borrow book
            borrowButton.addActionListener(e -> {
                int row = bookTable.getSelectedRow();
                if (row != -1) {
                    Book b = books.get(row);
                    if (b.isAvailable()) {
                        Database.updateAvailability(b.getId(), false);
                        loadBooks();
                    } else {
                        JOptionPane.showMessageDialog(this, "Book is already borrowed!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Select a book to borrow!");
                }
            });

            // Return book
            returnButton.addActionListener(e -> {
                int row = bookTable.getSelectedRow();
                if (row != -1) {
                    Book b = books.get(row);
                    if (!b.isAvailable()) {
                        Database.updateAvailability(b.getId(), true);
                        loadBooks();
                    } else {
                        JOptionPane.showMessageDialog(this, "Book is already available!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Select a book to return!");
                }
            });

            // Delete book
            deleteButton.addActionListener(e -> {
                int row = bookTable.getSelectedRow();
                if (row != -1) {
                    Book b = books.get(row);
                    int confirm = JOptionPane.showConfirmDialog(this, "Delete book: " + b.getTitle() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Database.deleteBook(b.getId());
                        loadBooks();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Select a book to delete!");
                }
            });

            // Search books
            searchButton.addActionListener(e -> {
                String query = searchField.getText().toLowerCase();
                tableModel.setRowCount(0);
                for (Book b : books) {
                    if (b.getTitle().toLowerCase().contains(query) || b.getAuthor().toLowerCase().contains(query)) {
                        tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.isAvailable() ? "Yes" : "No"});
                    }
                }
            });

            setVisible(true); // must be last
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to initialize LibraryFrame:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBooks() {
        books = Database.getAllBooks();
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.isAvailable() ? "Yes" : "No"});
        }
    }
}
