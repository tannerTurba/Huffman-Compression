/*
* An output stream that writes the encoded HuffmanTree to a binary file.
 */
import java.io.*;

public class HuffmanOutputStream {
    private DataOutputStream d;
    private int b;
    private int count;

    /**
     * The Constructor for the HuffmanOutputStream to write to the binary file.
     * @param fileName the file name
     * @param tree the String representation of the HuffmanTree
     * @param totalChars total number of chars
     */
    public HuffmanOutputStream(String fileName, String tree, int totalChars) {
        try {
            //Create a DataOutputSteam and write the tree's String representation and
            //total number of character to the file "fileName"
            d = new DataOutputStream(new FileOutputStream(fileName));
            d.writeUTF(tree);
            d.writeInt(totalChars);
            b = 0;
            count = 0;
        } catch (IOException e) {
            System.out.println("Output error");
        }
    }

    /**
     * Writes the bit to the binary file
     * @param bit the character being converted to binary
     * PRE: bit == '0' || bit == '1'
     */
    public void writeBit(char bit) {
        try {
            b = b * 2 + (bit - '0');
            count++;
            if(count == 8) {
                d.writeByte(b);
                b = 0;
                count = 0;
            }
        } catch(IOException e) {
        }
    }

    /**
     * Writes the final byte if needed and closes the OutputStream
     */
    public void close() {
        try {
            if(count != 0) {
                b = b << (8 - count);
                d.writeByte(b);
            }
            d.close();
        } catch (IOException e) {
            System.out.println("Output close error");
        }
    }
}
