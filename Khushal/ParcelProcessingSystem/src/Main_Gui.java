import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main_Gui extends JFrame {
    private JPanel parcelPanel;
    private JPanel customerPanel;
    private JPanel currentParcelPanel;
    private JTable parcelTable;
    private JTextArea customerArea;
    private JTextArea currentParcelArea;
    private JTextArea logArea;
    private QueueManager queueOfCustomers;
    private ParcelManager parcelMap;
    private DepotWorker worker;

    public Main_Gui(ParcelManager parcelMap, QueueManager queueOfCustomers) {
        this.parcelMap = parcelMap;
        this.queueOfCustomers = queueOfCustomers;
        this.worker = new DepotWorker(parcelMap);

        // Set up the main frame
        setTitle("Parcel Processing System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Add some spacing between components

        // Initialize panels
        parcelPanel = new JPanel();
        customerPanel = new JPanel();
        currentParcelPanel = new JPanel();

        // Set panel layouts
        parcelPanel.setLayout(new BorderLayout());
        customerPanel.setLayout(new BorderLayout());
        currentParcelPanel.setLayout(new BorderLayout());

        // Add borders and titles to panels
        parcelPanel.setBorder(new TitledBorder("Parcels"));
        customerPanel.setBorder(new TitledBorder("Customers"));
        currentParcelPanel.setBorder(new TitledBorder("Current Parcel"));

        // Add some padding to the panels
        parcelPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        customerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        currentParcelPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Set background color to greyish
        parcelPanel.setBackground(Color.GRAY);
        customerPanel.setBackground(Color.GRAY);
        currentParcelPanel.setBackground(Color.GRAY);

        // Create the parcel table model and table
        List<Parcel> parcels = new ArrayList<>(parcelMap.getAllParcels());
        ParcelTableModel parcelTableModel = new ParcelTableModel(parcels);
        parcelTable = new JTable(parcelTableModel);

        // Add the parcel table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(parcelTable);
        parcelPanel.add(scrollPane, BorderLayout.CENTER);

        // Create the customer area
        customerArea = new JTextArea();
        customerArea.setEditable(false);
        JScrollPane customerScrollPane = new JScrollPane(customerArea);
        customerPanel.add(customerScrollPane, BorderLayout.CENTER);

        // Create the current parcel area
        currentParcelArea = new JTextArea();
        currentParcelArea.setEditable(false);
        JScrollPane currentParcelScrollPane = new JScrollPane(currentParcelArea);
        currentParcelPanel.add(currentParcelScrollPane, BorderLayout.CENTER);

        // Create the log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(new TitledBorder("Log"));
        logScrollPane.setPreferredSize(new Dimension(1200, 200)); // Make the log area larger
        add(logScrollPane, BorderLayout.SOUTH);

        // Add panels to the frame
        add(parcelPanel, BorderLayout.WEST);
        add(customerPanel, BorderLayout.EAST);
        add(currentParcelPanel, BorderLayout.CENTER);

        // Add a button to process the next customer
        JButton processButton = new JButton("Process Next Customer");
        processButton.addActionListener(e -> processNextCustomer());
        add(processButton, BorderLayout.NORTH);

        // Update the customer area with the initial queue
        updateCustomerArea();
    }

    private void processNextCustomer() {
        if (!queueOfCustomers.isEmpty()) {
            Customer customer = queueOfCustomers.getNextCustomer();
            worker.processCustomer(customer);
            Parcel parcel = parcelMap.findParcelById(customer.parcelId);
            if (parcel != null) {
                parcel.setCollected(true); // Mark the parcel as collected
            }
            updateCustomerArea();
            updateCurrentParcelArea(customer);
            updateLogArea();
        } else {
            JOptionPane.showMessageDialog(this, "No more customers in the queue.");
        }
    }

    private void updateCustomerArea() {
        StringBuilder customerText = new StringBuilder("Customer Is :\n");
        for (Customer customer : queueOfCustomers.getCustomerQueue()) {
            customerText.append(customer.getName()).append(" - Parcel ID: ").append(customer.parcelId).append("\n");
        }
        customerArea.setText(customerText.toString());
    }

    private void updateCurrentParcelArea(Customer customer) {
        Parcel parcel = parcelMap.findParcelById(customer.parcelId);
        if (parcel != null) {
            currentParcelArea.setText("Current Parcel Is :\n" +
                    "ID: " + parcel.id + "\n" +
                    "Weight: " + parcel.weight + "kg\n" +
                    "Days in Depot: " + parcel.daysInDepot + "\n" +
                    "Collected: " + parcel.isCollected);
        } else {
            currentParcelArea.setText("No parcel found for customer: " + customer.getName());
        }
    }

    private void updateLogArea() {
        logArea.setText(Report.getInstance().getLog());
    }

    public void readCustomersFromFile(String fileName) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String name = parts[0] + " " + parts[1];
                    String parcelId = parts[2];
                    Customer customer = new Customer(queueOfCustomers.size() + 1, name, parcelId);
                    queueOfCustomers.addCustomer(customer);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateCustomerArea();
    }

    public static void main(String[] args) {
        // Create a ParcelMap and add the sample parcels
        ParcelManager parcelMap = new ParcelManager();
        QueueManager queueOfCustomers = new QueueManager();
        String[] sampleData = {
                "X009 9 1 9 9 7",
                "X020 1 1 6 4 14",
                "X025 7 1 4 9 9",
                "X036 8 4 6 9 12",
                "X064 8 4 1 8 15",
                "X086 7 4 1 7 13",
                "X121 3 7 2 3 6",
                "X198 9 4 8 0 10",
                "X213 4 8 5 2 15",
                "X214 1 8 1 1 15",
                "X278 5 3 1 0 11",
                "X285 1 4 3 1 10",
                "X309 1 2 8 5 11",
                "X316 9 5 4 0 11",
                "X386 9 1 6 5 9",
                "X475 4 3 8 1 11",
                "X507 5 3 9 8 13",
                "X521 6 4 4 4 8",
                "X540 9 2 5 4 5",
                "X552 4 5 7 8 12",
                "X606 8 8 4 2 13",
                "X682 3 6 4 4 12",
                "X720 4 2 1 3 8",
                "X733 6 6 5 7 11",
                "X746 4 4 9 5 7",
                "X780 4 1 2 5 12",
                "X782 5 3 27 12",
                "X857 2 6 6 3 9",
                "X904 4 1 4 9 15",
                "X919 5 8 7 4 10"
        };

        for (String data : sampleData) {
            String[] parts = data.split(" ");
            String id = parts[0];
            int weight = Integer.parseInt(parts[1]);
            int daysInDepot = Integer.parseInt(parts[2]);
            Parcel parcel = new Parcel(id, weight, daysInDepot);
            parcelMap.addParcel(parcel);
        }

        // Create and display the main frame
        SwingUtilities.invokeLater(() -> {
            Main_Gui notificationSystem = new Main_Gui(parcelMap, queueOfCustomers);
            notificationSystem.setVisible(true);
            notificationSystem.readCustomersFromFile("D:\\Khushal\\ParcelProcessingSystem\\src\\Customer.txt");
        });
    }
}