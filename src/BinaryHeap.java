/*
* Implements a binary heap where the heap rule is the value in the parent
* node is less than or equal to the values in the child nodes. The
* implementation uses parallel arrays to store the priorities and the
* trees.
*/
public class BinaryHeap {
    int[] priority;
    HuffmanTree[] trees;
    int size;

    /**
     * creates arrays of appropriate sizes
     * @param s the size of the heap
     */
    public BinaryHeap(int s) {
        priority = new int[s + 1];
        trees = new HuffmanTree[s + 1];
        size = 0;
    }

    /**
     * removes the priority and changes the root of the heap
     * PRE: size != 0
     */
    public void removeMin() {
        //place the last node at the index 0
        priority[0] = priority[size];
        trees[0] = trees[size];

        //make last node null and decrement size
        priority[size] = 0;
        trees[size] = null;
        size--;
        int leftChild, rightChild, least;

        //the position of the node that needs changing
        int targetNode = 1;
        while(targetNode * 2 < size && targetNode * 2 + 1 <= size) {
            //while there is a child node
            leftChild = getLeftChild(targetNode);
            rightChild = getRightChild(targetNode);

            //find the lesser priority node and change node at target position
            int lesser = leftChild <= rightChild ? 2 * targetNode : 2 * targetNode + 1;
            if(priority[lesser] <= priority[0]) {
                priority[targetNode] = priority[lesser];
                trees[targetNode] = trees[lesser];
                targetNode = lesser;
            } else {
                priority[targetNode] = priority[0];
                trees[targetNode] = trees[0];
                break;
            }
        }

        //special case where there is only one child node
        if (size == 2) {
            int greater;
            if(priority[2] < priority[0]) {
                least = 2;
                greater = 0;
            } else {
                least = 0;
                greater = 2;
            }

            //swap nodes
            priority[1] = priority[least];
            trees[1] = trees[least];
            priority[2] = priority[greater];
            trees[2] = trees[greater];
            priority[0] = 0;
            trees[0] = null;
            return;
        }

        //set the node at the target index 0 to appropriate node
        //and reset the values at the index 0 to null
        priority[targetNode] = priority[0];
        trees[targetNode] = trees[0];
        priority[0] = 0;
        trees[0] = null;
    }

    /**
     * A method to get the left child of a parent "i" in a binary heap
     * @param i the parent of the left child
     * @return index of left child
     */
    private int getLeftChild(int i) {
        if(i * 2 > size)
            return 0;
        return priority[2 * i];
    }

    /**
     * A method to get the right child of a parent "i" in a binary heap
     * @param i the parent of the right child
     * @return index of right child
     */
    private int getRightChild(int i) {
        if(i * 2 + 1 > size)
            return 0;
        return priority[2 * i + 1];
    }

    /**
     * get the priority int of the heap
     * @return the priority in the root of the heap
     * PRE: size != 0
     */
    public int getMinPriority() {
        return priority[1];
    }

    /**
     * get the priority tree of the heap
     * @return the tree in the root of the heap
     * PRE: size != 0
     */
    public HuffmanTree getMinTree() {
        return trees[1];
    }

    /**
     * checks if the binary heap is full
     * @return true if heap is full
     */
    public boolean full() {
        for(int i = 1; i < trees.length; i++) {
            if (trees[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inserts a node into the parallel arrays, using a binary heap-style implementation
     * @param p the priority value
     * @param t the tree
     */
    public void insert(int p, HuffmanTree t) {
        //increment size of heap and insert values into the last nodes.
        size++;
        priority[size] = p;
        trees[size] = t;

        //Starting with the last node, compare priorities and switch.
        for(int target = size; priority[target] < priority[target/2]; target /= 2) {
            int tempPri = priority[target/2];
            HuffmanTree tempTree = trees[target/2];
            priority[target/2] = priority[target];
            trees[target/2] = trees[target];
            priority[target] = tempPri;
            trees[target] = tempTree;
        }
    }

    /**
     * return the number of values pairs in the heap
     * @return the size of the heap
     */
    public int getSize() {
        return size;
    }

    /**
     * overrides the toString() method
     * @return a String representation of the priority array
     */
    public String toString() {
        String result = "";
        for(int i = 0; i < priority.length; i++) {
            result += " " + priority[i];
        }
        return result;
    }
}
