import java.util.Scanner;

public class NextFitMemoryManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of blocks and processes
        System.out.print("Enter the number of blocks: ");
        int blockCount = sc.nextInt();
        System.out.print("Enter the number of processes: ");
        int processCount = sc.nextInt();

        // Input block sizes
        int[] blockSizes = new int[blockCount];
        System.out.println("\nEnter the size of the blocks:-");
        for (int i = 0; i < blockCount; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blockSizes[i] = sc.nextInt();
        }

        // Input process sizes
        int[] processSizes = new int[processCount];
        System.out.println("\nEnter the size of the Processes:-");
        for (int i = 0; i < processCount; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            processSizes[i] = sc.nextInt();
        }

        // Allocate memory using Next Fit
        int[] allocation = new int[processCount];
        int lastAllocatedIndex = 0; // Start from the beginning of the block list

        for (int i = 0; i < processCount; i++) {
            allocation[i] = -1; // Initialize to indicate no allocation
            int count = 0; // To ensure we do not loop infinitely
            while (count < blockCount) {
                int currentIndex = (lastAllocatedIndex + count) % blockCount;
                if (blockSizes[currentIndex] >= processSizes[i]) {
                    allocation[i] = currentIndex; // Allocate block
                    blockSizes[currentIndex] -= processSizes[i];
                    lastAllocatedIndex = currentIndex; // Update last allocated block
                    break;
                }
                count++;
            }
        }

        // Calculate Internal and External Fragmentation
        int internalFragmentation = 0;
        for (int i = 0; i < blockCount; i++) {
            if (blockSizes[i] > 0) {
                internalFragmentation += blockSizes[i];
            }
        }

        int externalFragmentation = 0;
        for (int i = 0; i < blockCount; i++) {
            if (blockSizes[i] > 0 && !isBlockUsable(blockSizes[i], processSizes)) {
                externalFragmentation += blockSizes[i];
            }
        }

        // Display results
        System.out.println("\nProcess_No\tProcess_Size\tBlock_No\tBlock_Size\tFragment");
        for (int i = 0; i < processCount; i++) {
            if (allocation[i] != -1) {
                int blockNo = allocation[i] + 1;
                System.out.println((i + 1) + "\t\t" + processSizes[i] + "\t\t" + blockNo
                        + "\t\t" + (blockSizes[allocation[i]] + processSizes[i]) + "\t\t" + blockSizes[allocation[i]]);
            } else {
                System.out.println((i + 1) + "\t\t" + processSizes[i] + "\t\tNot Allocated");
            }
        }

        System.out.println("\nInternal Fragmentation = " + internalFragmentation);
        System.out.println("External Fragmentation = " + externalFragmentation);

        sc.close();
    }

    // Helper function to check if a block is usable for any process
    private static boolean isBlockUsable(int blockSize, int[] processSizes) {
        for (int size : processSizes) {
            if (blockSize >= size) {
                return true;
            }
        }
        return false;
    }
}
