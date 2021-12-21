/*
* Defines the functionality of the Huffman Tree that is used for
* encoding and decoding text files.
 */
import java.util.*;

public class HuffmanTree {
    private Node root;
    private Node current;   //the value is changed by the move methods

    /**
     * A basic node for a binary tree
     */
    private class Node {
        private Node left;
        private char data;
        private Node right;

        /**
         * A basic constructor to set values of a Node
         * @param L reference to the left child
         * @param d the data it contains
         * @param R reference to the right child
         */
        private Node(Node L, char d, Node R) {
            left = L;
            data = d;
            right = R;
        }
    }

    /**
     * An empty tree constructor
     */
    public HuffmanTree() {
        root = null;
        current = null;
    }

    /**
     * A constructor for a tree with a single node
     * @param d the character in the single node
     */
    public HuffmanTree(char d) {
        root = new Node(null, d, null);
        current = root;
    }

    /**
     * Assumes t represents a post order representation of the tree as discussed
     * in class. NonLeaf is the char value of the data in the non-leaf nodes
     * in the following I will use (char) 128 for the non-leaf value
     * @param t a post-order representation of a HuffmanTree
     * @param nonLeaf the char for the non-leaf representation
     */
    public HuffmanTree(String t, char nonLeaf) {
        char currentChar;
        HuffmanTree left, right;
        Stack<HuffmanTree> treeStack = new Stack<>();

        //parse through string, character by character
        for(int i = 0; i < t.length(); i++) {
            currentChar = t.charAt(i);
            if(currentChar == nonLeaf) {
                //get left and right nodes when a connecting value(nonLeaf) is found, push on stack
                right = treeStack.pop();
                left = treeStack.pop();
                treeStack.push(new HuffmanTree(left, currentChar, right));
            } else {
                //push any other character onto tree with null left/right references.
                treeStack.push(new HuffmanTree(new HuffmanTree(), currentChar, new HuffmanTree()));
            }
        }
        root = treeStack.pop().root;
        current = root;
    }

    /**
     * Makes a new Huffman Tree by combining two subtrees at a new Node
     * @param b1 the left subtree
     * @param d the data in the root
     * @param b2 the right subtree
     */
    public HuffmanTree(HuffmanTree b1, char d, HuffmanTree b2) {
        root = new Node(b1.root, d, b2.root);
    }

    /*
    The move methods that change the value of current
    to traverse the Huffman Tree. These methods are
    used these in the decoding process
    */

    /**
     * Change current to reference the root of the tree
     */
    public void moveToRoot() {
        current = root;
    }

    /**
     * Change current to reference the left child of the current node
     * PRE: the current node is not a leaf
     */
    public void moveToLeft() {
        current = current.left;
    }

    /**
     * Change current to reference the right child of the current node
     * PRE: the current node is not a leaf
     */
    public void moveToRight() {
        current = current.right;
    }

    /**
     * Determines if "current" is at the root.
     * @return true if the current node is the root
     */
    public boolean atRoot() {
        return current.equals(root);
    }

    /**
     * Determines if "current" is at a leaf.
     * @return true if current references a leaf
     */
    public boolean atLeaf() {
        return current.left == null && current.right == null;
    }

    /**
     * Gets the data in the "current" node.
     * @return the data in current
     */
    public char current() {
        return current.data;
    }

    /**
     * Makes an array of Strings that represents the path to each character
     * in the Huffman Tree. 0 represents left and 1 represents right.
     * @return an array of 128 strings(some of which could be null) with all paths from the
     *         root to the leaves.
     */
    public String[] pathsToLeaves() {
        String[] allPaths = new String[128];
        pathReader(root, allPaths, "");
        return allPaths;
    }

    /**
     * A recursive method that loads the allP String array with the
     * path to each leaf.
     * @param node the next node in the Tree
     * @param allP the paths to each leaf
     * @param path the String representation of the path
     */
    private void pathReader(Node node, String[] allP, String path) {
        //store path representation in the array if at a leaf
        if(node.left == null && node.right == null) {
            allP[node.data] = path;
            return;
        }
        //go to child nodes
        pathReader(node.left, allP,path + "0");
        pathReader(node.right, allP,path + "1");
    }

    /**
     * @return a string representation of the tree using the postorder format.
     */
    public String toString() {
        return treeReader(root);
    }

    /**
     * A recursive method to read and return a string based on postorder traversal
     * @param node the next node to read
     * @return a post-order representation of a Huffman Tree
     */
    private String treeReader(Node node) {
        if(node == null) {
            return "";
        }
        return treeReader(node.left) + treeReader(node.right) + node.data;
    }
}
