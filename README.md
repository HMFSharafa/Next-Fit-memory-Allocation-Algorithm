# Next Fit Memory Management

This project demonstrates a simple simulation of the **Next Fit Memory Allocation** algorithm using a graphical user interface (GUI) implemented in Java. The program allows users to allocate processes to memory blocks based on the Next Fit strategy and provides insights into internal and external fragmentation.

## System Design 
![image](https://github.com/user-attachments/assets/d4f39a23-e4f2-479e-b376-702c7caff9f7)

## Features

- **Graphical User Interface (GUI):** User-friendly interface created using Java Swing.
- **Custom Inputs:** Users can input the number of blocks, block sizes, number of processes, and process sizes.
- **Process Allocation Table:** Displays allocation details for each process.
- **Block Summary Table:** Shows the initial size, remaining size, and status of each memory block.
- **Fragmentation Analysis:** Calculates and displays internal and external fragmentation.
- **Clear Functionality:** Option to reset all inputs and tables.

## How to Run

1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Clone or download this repository to your local machine.
3. Navigate to the directory containing the source code.
4. Compile the program using the command:
   ```
   javac NextFitMemoryAllocation.java
   ```
5. Run the program using the command:
   ```
   java NextFitMemoryAllocation
   ```

## Input Instructions

- **Number of Blocks:** Enter the total number of memory blocks.
- **Block Sizes:** Provide the sizes of memory blocks, separated by commas (e.g., `100, 200, 300`).
- **Number of Processes:** Enter the total number of processes.
- **Process Sizes:** Provide the sizes of processes, separated by commas (e.g., `120, 150, 80`).

## Output

- **Process Allocation Table:** Displays the following for each process:
  - Process number
  - Process size
  - Allocated block number (if applicable)
  - Block size before allocation
  - Remaining block size (fragment)
  
- **Block Summary Table:** Displays:
  - Block number
  - Initial size
  - Remaining size
  - Status (Free/Allocated)

- **Fragmentation Details:**
  - Internal Fragmentation: Sum of leftover space within allocated blocks.
  - External Fragmentation: Sum of free spaces in blocks that cannot accommodate any process.

## Example

**Input:**
- Number of Blocks: `3`
- Number of Processes: `4`
- Block Sizes: `100, 200, 300`
- Process Sizes: `120, 150, 80, 150`

**Output:**
- Process Allocation Table:
  | Process No | Process Size | Block No | Block Size | Fragment |
  |------------|--------------|----------|------------|----------|
  | 1          | 120          | 2        | 200        | 80       |
  | 2          | 150          | 3        | 300        | 150      |
  | 3          | 80           | 3        | 150        | 70       |
  | 4          | 150           | Not Allocated | -      | -        |

  - Block Summary Table:
  | Block No   | Initial Size | Remining Size| Status       | 
  |------------|--------------|--------------|--------------|
  | 1          | 100          | 100          | Free         |
  | 2          | 200          | 80           | Allocated    |
  | 3          | 300          | 70           | Allocated    |



- Internal Fragmentation: `130`
- External Fragmentation: `100`

## Technologies Used

- **Java** for programming
- **Swing** for GUI development
- **Git** for Virsion Control
- 
## Future Enhancements

- Support for dynamic memory allocation.
- Visualization of memory allocation in real-time.
- Enhanced error handling and validation.

## Contact

For questions or suggestions, feel free to contact the project maintainer.


