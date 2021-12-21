/*
* Used to encode the text file into binary based on the Huffman Tree.
 */
import java.io.*;
import java.util.*;

public class HuffmanEncode {

    /**
     * Implements the main flow of Huffman Encoding.
     * @param in the name of the input file
     * @param out the name of the output file
     */
    public HuffmanEncode(String in, String out) {
        //find the frequencies of the characters in the file, store in an int[]
        int[] test = findFrequency(in);

        //create a BinaryHeap to sort the priorities
        BinaryHeap priorities = createHeap(test);

        //create a HuffmanTree using the BinaryHeap
        HuffmanTree tree = createHuffmanTree(priorities);

        //find the encodings for each character in the HuffmanTree, store in a String[]
        String[] encodings = tree.pathsToLeaves();

        //get the total number of chars and create a .bin file using a HuffmanOutputStream
        int totalChars = priorities.priority[1];
        HuffmanOutputStream writer = new HuffmanOutputStream(out, tree.toString(), totalChars);
        encodeText(encodings, in, writer);
        writer.close();
    }

    /**
     * Used to encode the text file and write the encoding in the output file
     * @param encodingList the encodings for each char
     * @param fileName the name of the input file
     * @param writer the output stream that writes to a binary file
     */
    private void encodeText(String[] encodingList, String fileName, HuffmanOutputStream writer) {
        try {
            //use a BufferedReader to get each character for encoding.
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while(reader.ready()) {
                //get the read character and write its encoding to the binary file
                int readChar = reader.read();
                String encoding = encodingList[readChar];
                for(int i = 0; i < encoding.length(); i++) {
                    writer.writeBit(encoding.charAt(i));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("file not found");
        }
    }

    /**
     * Creates a HuffmanTree
     * @param heap the BinaryHeap the acts as a priority queue
     * @return the resulting HuffmanTree
     */
    private HuffmanTree createHuffmanTree(BinaryHeap heap) {
        HuffmanTree left, right, combined;
        int leftPri, rightPri;
        int length = heap.getSize();

        for(int i = 0; i < length - 1; i++) {
            //get, store, and remove the highest priority items.
            left = heap.getMinTree();
            leftPri = heap.getMinPriority();
            heap.removeMin();
            right = heap.getMinTree();
            rightPri = heap.getMinPriority();
            heap.removeMin();

            //combine them into one tree.
            int combinedPri = leftPri + rightPri;
            //set right to an empty HuffmanTree if null
            if(right == null)
                right = new HuffmanTree();
            combined = new HuffmanTree(left, (char)128, right);

            //insert new tree into the BinaryHeap to sort and keep track of priority.
            heap.insert(combinedPri, combined);
        }
        //return the last HuffmanTree in the heap, which is the completed one.
        return heap.getMinTree();
    }

    /**
     * Creates a BinaryHeap to sort and keep track of the highest priority characters.
     * Implements a LinkedList to keep track of character found in a file to
     * prevent unnecessary parsing.
     * @param freq the frequencies for each char in the file
     * @return the new BinaryHeap
     */
    private BinaryHeap createHeap(int[] freq) {
        LinkedList<Integer> characters = new LinkedList<>();
        int count = 0;
        for(int i = 0; i < freq.length; i++) {
            if(freq[i] > 0) {
                count++;
                characters.add(i);
            }
        }
        //creates a BinaryHeap and inserts the characters in the LinkedList
        BinaryHeap priorities = new BinaryHeap(count);
        for(int i = 0; i < count; i++) {
            int nextInsert = characters.getFirst();
            HuffmanTree newTree = new HuffmanTree((char) nextInsert);
            priorities.insert(freq[nextInsert], newTree);
            characters.remove();
        }
        return priorities;
    }

    /**
     * Reads through a file with a BufferedReader and increments the value in an
     * array of ints when a character is found.
     * @param fileName the input file name
     * @return the array of frequencies
     */
    private int[] findFrequency(String fileName) {
        int[] frequencies = new int[128];
        try {
            //uses a BufferedReader to get each character at a time.
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while(reader.ready()) {
                //increment the frequency of the read character
                frequencies[reader.read()] ++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("file not found");
        }
        return frequencies;
    }

    /**
     * Begins the encoding process
     * @param args
     *     args[0] is the name of the source file
     *     args[1] is the name of the output file
     */
    public static void main(String args[]) {
        new HuffmanEncode(args[0], args[1]);
    }
}