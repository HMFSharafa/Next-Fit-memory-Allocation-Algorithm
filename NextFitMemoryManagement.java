import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextFitMemoryManagement {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NextFitMemoryManagement::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Next Fit Memory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Input panel for blocks and processes
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));

        JLabel blockCountLabel = new JLabel("Number of Blocks:");
        JTextField blockCountField = new JTextField();
        inputPanel.add(blockCountLabel);
        inputPanel.add(blockCountField);

        JLabel processCountLabel = new JLabel("Number of Processes:");
        JTextField processCountField = new JTextField();
        inputPanel.add(processCountLabel);
        inputPanel.add(processCountField);

        JLabel blockSizesLabel = new JLabel("Block Sizes (comma-separated):");
        JTextField blockSizesField = new JTextField();
        inputPanel.add(blockSizesLabel);
        inputPanel.add(blockSizesField);

        JLabel processSizesLabel = new JLabel("Process Sizes (comma-separated):");
        JTextField processSizesField = new JTextField();
        inputPanel.add(processSizesLabel);
        inputPanel.add(processSizesField);

        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear All");
        inputPanel.add(calculateButton);
        inputPanel.add(clearButton);

        // Result table
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable resultTable = new JTable(tableModel);
        tableModel.addColumn("Process No");
        tableModel.addColumn("Process Size");
        tableModel.addColumn("Block No");
        tableModel.addColumn("Block Size");
        tableModel.addColumn("Fragment");
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        JLabel internalFragLabel = new JLabel("Internal Fragmentation: 0");
        JLabel externalFragLabel = new JLabel("External Fragmentation: 0");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int blockCount = Integer.parseInt(blockCountField.getText().trim());
                    int processCount = Integer.parseInt(processCountField.getText().trim());

                    int[] blockSizes = parseInput(blockSizesField.getText().trim(), blockCount);
                    int[] processSizes = parseInput(processSizesField.getText().trim(), processCount);

                    // Perform Next Fit allocation
                    int[] allocation = new int[processCount];
                    int lastAllocatedIndex = 0;

                    for (int i = 0; i < processCount; i++) {
                        allocation[i] = -1;
                        int count = 0;
                        while (count < blockCount) {
                            int currentIndex = (lastAllocatedIndex + count) % blockCount;
                            if (blockSizes[currentIndex] >= processSizes[i]) {
                                allocation[i] = currentIndex;
                                blockSizes[currentIndex] -= processSizes[i];
                                lastAllocatedIndex = currentIndex;
                                break;
                            }
                            count++;
                        }
                    }

                    // Calculate internal and external fragmentation
                    int internalFragmentation = 0;
                    for (int size : blockSizes) {
                        if (size > 0) {
                            internalFragmentation += size;
                        }
                    }

                    int externalFragmentation = 0;
                    for (int size : blockSizes) {
                        if (size > 0 && !isBlockUsable(size, processSizes)) {
                            externalFragmentation += size;
                        }
                    }

                    // Update results table
                    tableModel.setRowCount(0);
                    for (int i = 0; i < processCount; i++) {
                        if (allocation[i] != -1) {
                            int blockNo = allocation[i] + 1;
                            tableModel.addRow(new Object[]{
                                    i + 1,
                                    processSizes[i],
                                    blockNo,
                                    blockSizes[allocation[i]] + processSizes[i],
                                    blockSizes[allocation[i]]
                            });
                        } else {
                            tableModel.addRow(new Object[]{
                                    i + 1,
                                    processSizes[i],
                                    "Not Allocated",
                                    "-",
                                    "-"
                            });
                        }
                    }

                    internalFragLabel.setText("Internal Fragmentation: " + internalFragmentation);
                    externalFragLabel.setText("External Fragmentation: " + externalFragmentation);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please check your values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockCountField.setText("");
                processCountField.setText("");
                blockSizesField.setText("");
                processSizesField.setText("");
                tableModel.setRowCount(0);
                internalFragLabel.setText("Internal Fragmentation: 0");
                externalFragLabel.setText("External Fragmentation: 0");
            }
        });

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        JPanel fragmentationPanel = new JPanel(new GridLayout(0, 1));
        fragmentationPanel.add(internalFragLabel);
        fragmentationPanel.add(externalFragLabel);
        frame.add(fragmentationPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static int[] parseInput(String input, int count) throws Exception {
        String[] parts = input.split(",");
        if (parts.length != count) {
            throw new Exception("Invalid input size.");
        }
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
        }
        return result;
    }

    private static boolean isBlockUsable(int blockSize, int[] processSizes) {
        for (int size : processSizes) {
            if (blockSize >= size) {
                return true;
            }
        }
        return false;
    }
}
