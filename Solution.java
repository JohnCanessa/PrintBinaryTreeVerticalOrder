import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;


 /**
  * Definition for a binary tree node.
  */
class TreeNode {
    
    // **** class members ****
    int         val;
    TreeNode    left;
    TreeNode    right;
    
    // **** constructors ****
    TreeNode() {}
    
    TreeNode(int val) { this.val = val; }
    
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
  
    // **** ****
    @Override
    public String toString() {
        return "" + this.val;
    }
}


/**
 * Print a Binary Tree in Vertical Order
 * https://www.geeksforgeeks.org/print-binary-tree-vertical-order/
 */
public class Solution {


    // **** ****
    static HashMap<Integer, Integer> dists = new HashMap<Integer, Integer>();


    /**
     * Compute the horizontal distance from the root for each node.
     * Recursive call.
     * BFS approach.
     */
    static void computeDistance(TreeNode root, int dist) {

        // **** base case ****
        if (root == null)
            return;

        // **** store the distance for this node ****
        dists.put(root.val, dist);

        // **** visit left node ****
        computeDistance(root.left, dist - 1);

        // **** visit right node ****
        computeDistance(root.right, dist + 1);
    }


    /**
     * Print Binary Tree in Vertical Order
     */
    static void printBTVerticalOrder (TreeNode root) {

        // **** populate the node distances from the root ****
        computeDistance(root, 0);

        // **** hash map of distance, node values ****
        TreeMap<Integer, ArrayList<Integer>> distNode = new TreeMap<>();
        
        // **** traverse the dists hash map ****
        for (Entry<Integer, Integer> dist : dists.entrySet()) {

            // **** for ease of use ****
            int d = dist.getValue();
            int v = dist.getKey();

            // **** update dist-node hash map ****
            ArrayList<Integer> al = distNode.get(d);
            if (al == null) {
                al = new ArrayList<Integer>();
                al.add(v);
                distNode.put(d, al);
            } else {
                al.add(v);
            }
        }

        // **** print binary tree in vertical order ****
        for (Entry<Integer, ArrayList<Integer>> dn : distNode.entrySet())
            System.out.println(dn.getValue().toString());
    }


    /**
     * Enumerate which child in the node at the head of the queue 
     * (see insertValue function).
     */
    enum Child {
        LEFT,
        RIGHT
    }


    // **** child turn to insert on node at head of queue ****
    static Child  insertChild = null;


    /**
     * This function inserts the next value into the specified BST.
     * This function is called repeatedly from the populateTree method.
     * This function supports 'null' value.
     */
    static TreeNode insertValue(TreeNode root, String strVal, Queue<TreeNode> q) {
    
        // **** node to add to the BST in this pass ****
        TreeNode node = null;
    
        // **** create a node (if needed) ****
        if (!strVal.equals("null"))
            node = new TreeNode(Integer.parseInt(strVal));
    
        // **** check is the BST is empty (this becomes the root node) ****
        if (root == null)
            root = node;
    
        // **** add node to left child (if possible) ****
        else if (insertChild == Child.LEFT) {
        
            // **** add this node as the left child ****
            if (node != null)
                q.peek().left = node; 
            
            // **** for next pass ****
            insertChild = Child.RIGHT;
        }
    
        // **** add node to right child (if possible) ****
        else if (insertChild == Child.RIGHT) {
        
            // **** add this node as a right child ****
            if (node != null)
                q.peek().right = node;
    
            // **** remove node from queue ****
            q.remove();
    
            // **** for next pass ****
            insertChild = Child.LEFT;
        }
    
        // **** add this node to the queue (if NOT null) ****
        if (node != null)
            q.add(node);
        
        // **** return the root of the BST ****
        return root;
    }


    /**
     * This function populates a binary tree in level order as 
     * specified by the data array.
     * This function supports 'null' values.
     */
    static TreeNode populateTree(String[] data) {
    
        // **** root for the BT ****
        TreeNode root = null;
    
        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();

        // **** start with the left child ****
        insertChild = Child.LEFT;

        // **** traverse the array of values inserting nodes 
        //      one into the binary tree one at a time ****
        for (String strVal : data)
            root = insertValue(root, strVal, q);
    
        // **** return the root of the populated binary tree ****
        return root;
    }
    

    /**
     * Traverse the specified binary tree displaying the values in depth first
     * search order.
     * This method is used to verify that the binary tree was properly generated.
     */
    static void inOrder(TreeNode root) {
    
        // **** end condition ****
        if (root == null)
            return;
    
        // **** visit the left sub tree ****
        inOrder(root.left);
    
        // **** display the value of this node ****
        System.out.print(root.val + " ");
    
        // **** visit the right sub tree ****
        inOrder(root.right);
    }


    /**
     * Traverse the specified binary tree displaying the values in breadth-first 
     * order.
     * This method is used to verify that the binary tree was properly generated.
     */
    static void bfs(TreeNode root) {

        // **** initialize queues ****
        Queue<TreeNode> q       = new LinkedList<TreeNode>();
        Queue<TreeNode> nextQ   = new LinkedList<TreeNode>();

        // **** prime the queue ****
        q.add(root);

        // **** loop displaying and inserting nodes into the queue ****
        while (!q.isEmpty()) {

            // **** get the next node from the queue ****
            TreeNode node = q.remove();

            // **** display the node value ****
            System.out.print(node.val + " ");

            // **** push into the next queue the left child (if needed) ****
            if (node.left != null)
                nextQ.add(node.left);

            // **** push into the next queue the right child (if needed) ****
            if (node.right != null)
                nextQ.add(node.right);
        
            // **** switch queues ****
            if (q.isEmpty()) {

                // **** mark end of level ****
                System.out.println();

                // **** point to the next queue ****
                q = nextQ;

                // **** clear the next queue ****
                nextQ  = new LinkedList<TreeNode>();
            }
        }
    }


    /**
     * Test scaffolding
     */
    public static void main(String[] args) {
        
        // **** open scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read and split the data for the binary tree ****
        String[] data = sc.nextLine().trim().split(",");

        // **** close scanner ****
        sc.close();

        // **** binary tree ****
        TreeNode bt = null;

        // **** populate the binary tree ****
        bt = populateTree(data);

        // ???? ????
        System.out.print("main <<< inOrder: ");
        inOrder(bt);
        System.out.println();

        // ???? ????
        System.out.println("main <<< bfs: ");
        bfs(bt);

        // **** print binary tree in vertical order ****
        System.out.println("main <<< binary tree in vertical order:");
        printBTVerticalOrder(bt);
    }
}