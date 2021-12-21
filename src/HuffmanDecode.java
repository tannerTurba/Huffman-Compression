/*
 * A decoder for a HuffmanTree
 */
import java.io.*;

public class HuffmanDecode {
    private HuffmanInputStream input;
    private String huffmanString;
    private int totalChars;

    /**
     * implements the Huffman Decoding algorithm
     * @param in the file that contains the tree and encodings
     * @param out the file that contains the decoded text
     */
    public HuffmanDecode(String in, String out) {
        try{
        input = new HuffmanInputStream(in);
        huffmanString = input.getTree();
        totalChars = input.getTotalChars();

        //recreate the HuffmanTree from the information stored in the binary file
        HuffmanTree tree = new HuffmanTree(huffmanString, (char) 128);

        //Use a PrintWriter to write to the new text file.
        PrintWriter writer = new PrintWriter(out);
        tree.moveToRoot();

        //for each character in the file, move left or right and write
        //the information stored in the node to the file if at a leaf
        for(int i = 0; i < totalChars; i++){
            while(!tree.atLeaf()){
                int bit = input.readBit();
                if(bit == 0)
                    tree.moveToLeft();
                if(bit == 1){
                    tree.moveToRight();
                }
                if(tree.atLeaf()){
                    char c = tree.current();
                    writer.print(c);
                }
            }
            tree.moveToRoot();
        }
        input.close();
        writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * creates the decoder and gets the names of the files
     * @param args
     *   args[0] is the name of an input file (a file created by Huffman Encode)
     *   args[1] is the name of the output file for the uncompressed file
     */
    public static void main(String[] args) {
        new HuffmanDecode(args[0], args[1]);
    }
}
