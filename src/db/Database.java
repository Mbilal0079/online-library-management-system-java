package db;

import model.Book;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static final String URL = "jdbc:sqlite:library.db";

    // Initialize database and table
    static {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT NOT NULL, " +
                    "author TEXT NOT NULL, " +
                    "available INTEGER NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all books
    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("available") == 1
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Add book
    public static void addBook(String title, String author) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO books (title, author, available) VALUES (?, ?, 1)");
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update book availability
    public static void updateAvailability(int id, boolean available) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement("UPDATE books SET available = ? WHERE id = ?");
            ps.setInt(1, available ? 1 : 0);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete book
    public static void deleteBook(int id) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
