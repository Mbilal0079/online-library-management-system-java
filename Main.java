import javax.swing.*;

/**
 * Main class - Entry point for the Library Management System
 * 
 * This application provides a comprehensive library management solution with features including:
 * - User authentication
 * - Book inventory management (Add, Update, Delete, Search)
 * - Book issue and return tracking
 * - Student record management
 * - Real-time availability tracking
 * 
 * @author Your Name
 * @version 1.0
 * @since 2024
 */
public class Main {
    
    /**
     * Main method - Application entry point
     * Initializes the Look and Feel and launches the login window
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set the system look and feel for better UI appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            // If system look and feel is not available, continue with default
            System.err.println("Warning: Could not set system look and feel.");
            e.printStackTrace();
        }
        
        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the login frame
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                
                // Optional: Display welcome message in console
                System.out.println("========================================");
                System.out.println("Library Management System Started");
                System.out.println("========================================");
                System.out.println("Default Login Credentials:");
                System.out.println("Username: admin");
                System.out.println("Password: admin123");
                System.out.println("========================================");
            }
        });
    }
}