import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel parcelPanel;
    private JPanel customerPanel;
    private JPanel currentParcelPanel;

    public MainFrame() {
        // Setting up the main frame
        setTitle("Parcel Processing System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creating a container panel for the top layout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3, 10, 0)); // Horizontal layout with spacing
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Initializing panels
        parcelPanel = new JPanel();
        customerPanel = new JPanel();
        currentParcelPanel = new JPanel();

        // Setting panel layouts and colors
        parcelPanel.setLayout(new BorderLayout());
        parcelPanel.setBackground(Color.LIGHT_GRAY);

        customerPanel.setLayout(new BorderLayout());
        customerPanel.setBackground(Color.CYAN);

        currentParcelPanel.setLayout(new BorderLayout());
        currentParcelPanel.setBackground(Color.PINK);

        // Adding borders and titles to panels
        parcelPanel.setBorder(new TitledBorder("Parcels"));
        customerPanel.setBorder(new TitledBorder("Customers"));
        currentParcelPanel.setBorder(new TitledBorder("Current Parcel"));

        // Adding sample content to the panels
        parcelPanel.add(new JLabel("List of parcels will be displayed here", JLabel.CENTER), BorderLayout.CENTER);
        customerPanel.add(new JLabel("Queue of customers will be displayed here", JLabel.CENTER), BorderLayout.CENTER);
        currentParcelPanel.add(new JLabel("Details of the current parcel will be displayed here", JLabel.CENTER), BorderLayout.CENTER);

        // Adding panels to the top container
        topPanel.add(parcelPanel);
        topPanel.add(customerPanel);
        topPanel.add(currentParcelPanel);

        // Adding the top container to the frame
        add(topPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        // Creating and displaying the main frame
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}