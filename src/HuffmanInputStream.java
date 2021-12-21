/*
* A stream used for decoding a HuffmanTree.
 */
import java.io.*;

public class HuffmanInputStream {
    private String tree;
    private int totalChars;
    private DataInputStream d;
    private int[] bits;
    private int count;

    /**
     * The constructor method of the HuffmanInputStream that reads from
     * the file "fileName." Gets the String representation of the
     * HuffmanTree and the total number of characters from file.
     * @param fileName the input binary file
     */
    public HuffmanInputStream(String fileName) {
        try {
            d = new DataInputStream(new FileInputStream(fileName));
            tree = d.readUTF();
            totalChars = d.readInt();
            count = 8;
            bits = new int[8];
        } catch (IOException e) {
            System.out.println("Input error");
        }
    }

    /**
     * Gets and returns the next bit in the file.
     * @return either 0 or 1
     */
    public int readBit() {
        int b;
        int bit = 0;
        try {
            //unpacks the byte
            if(count == 8) {
                b = d.readUnsignedByte();
                for(int i = 7; i >= 0; i--) {
                    bits[i] = b % 2;
                    b = b / 2;
                }
                count = 0;
            }
            bit = bits[count];
            count++;
        } catch(IOException e) {
            System.out.println("reading bit error");
        }
        return bit;
    }

    /**
     * Gets the String representation of the tree.
     * @return the String representation of the HuffmanTree
     */
    public String getTree() {
        return tree;
    }

    /**
     * gets the total character count
     * @return the total character count in the file
     */
    public int getTotalChars() {
        return totalChars;
    }

    /**
     * closes the stream
     */
    public void close() {
        //close the DataInputStream
        try {
            d.close();
        } catch(IOException e) {
            System.out.println("Input close error");
        }
    }
}
