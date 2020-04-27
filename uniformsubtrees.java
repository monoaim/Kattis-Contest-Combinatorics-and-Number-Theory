/**
 * Uniform Subtrees
 * https://open.kattis.com/problems/uniformsubtrees
 */


import java.io.*;
import java.util.*;


public class uniformsubtrees_vanb {

    public BufferedReader br;
    public PrintStream ps;

    /**
     * This is a node in the Answer Tree
     *
     * @author vanb
     */
    public class AnswerNode {

        /**
         * This count is the number of siblings that this node appears in
         */
        public int count;

        /**
         * Children of this Answer Node, with the integer link. children[i] is
         * the child of this node from the link labeled 'i'.
         */
        public AnswerNode children[];

        /**
         * Create an empty Answer Node
         */
        public AnswerNode() {
            count = 0;
            children = new AnswerNode[1];
        }

        /**
         * Show the answers from this node down.
         *
         * @param prefix Part of the answer generated by ancestors
         */
        public void showAnswers(String prefix) {
            ps.println(prefix + "0");
            for (int i = 1; i < children.length; i++) {
                if (children[i] != null) {
                    children[i].showAnswers(prefix + i + " ");
                }
            }
        }

        /**
         * Make a copy of this node, but ONLY IF it appears at this level.
         *
         * @param level Level
         * @return A copy of this node, or null if level is too big.
         */
        public AnswerNode copy(int level) {
            AnswerNode result = null;
            if (level <= count) {
                result = new AnswerNode();
                result.children = new AnswerNode[children.length];
                for (int i = 1; i < children.length; i++) {
                    result.children[i] = children[i] == null ? null : children[i].copy(level);
                }
            }

            return result;
        }
    }

    /**
     * Merge an answer tree into another answer tree.
     *
     * @param base Base node
     * @param newnode Node to merge
     * @return Base node (create it if base is null)
     */
    public AnswerNode merge(AnswerNode base, AnswerNode newnode) {
        if (newnode != null) {
            if (base == null) {
                base = new AnswerNode();
            }
            base.count++;

            AnswerNode newkids[] = new AnswerNode[Math.max(base.children.length, newnode.children.length)];
            for (int i = 1; i < newkids.length; i++) {
                newkids[i] = merge(i < base.children.length ? base.children[i] : null,
                        i < newnode.children.length ? newnode.children[i] : null);
            }
            base.children = newkids;
        }

        return base;
    }

    /**
     * This is a node of the input tree.
     *
     * @author vanb
     */
    public class TreeNode {

        /**
         * Children
         */
        public LinkedList<TreeNode> children = new LinkedList<TreeNode>();

        /**
         * Build the Answer Tree for this node.
         *
         * @return Root of the Answer Tree
         */
        public AnswerNode buildAnswerTree() {
            AnswerNode root = null;
            for (TreeNode child : children) {
                root = merge(root, child.buildAnswerTree());
            }

            AnswerNode result = new AnswerNode();

            if (root != null) {
                result.children = new AnswerNode[root.count + 1];
                for (int i = 1; i <= root.count; i++) {
                    result.children[i] = root.copy(i);
                }
            }

            return result;
        }

        /**
         * Convert the subtree rooted at this node into a pretty string. This is
         * useful for debugging, and also for the judges creating data.
         */
        public String toString() {
            String result = "(";

            for (TreeNode child : children) {
                result += child.toString();
            }
            result += ")";

            return result;
        }
    }

    /**
     * Input text, which will be parsed into a tree.
     */
    public char text[];
    /**
     * Pointer into the input text.
     */
    public int p;

    /**
     * Parse a single node of the input tree.
     *
     * @return Parsed node
     */
    public TreeNode parseNode() {
        TreeNode treeNode = new TreeNode();
        if (text[p] != '(') {
            System.err.println("PANIC! Expecting '(' got '" + text[p] + "'");
        }
        ++p; // Go past the '(' 
        while (p < text.length && text[p] == '(') {
            treeNode.children.add(parseNode());
        }
        if (text[p] != ')') {
            System.err.println("PANIC! Expecting ')' got '" + text[p] + "'");
        }
        ++p; // Go past the ')'
        return treeNode;
    }

    /**
     * Driver.
     *
     * @throws Exception
     */
    public void doit() throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        ps = System.out;
        text = br.readLine().trim().toCharArray();
        //System.err.println( text.length );
        p = 0;
        if (text[p] == '0') {
            System.exit(0);
        }

        // Parse the input
        TreeNode root = parseNode();

        if (p < text.length) {
            System.err.println("PANIC! Mismatch!");
        }

        // Build the Answer Tree
        AnswerNode answer = root.buildAnswerTree();

        // Show the Answer Tree
        answer.showAnswers("");
    }

    /**
     * Build a random tree with a given number of nodes. The judges use this to
     * generate random data.
     *
     * @param size Number of nodes in the desired random tree
     * @return A random tree with 'size' nodes
     */
    public String buildRandomTree(int size) {
        Random random = new Random();

        TreeNode nodes[] = new TreeNode[size];

        nodes[0] = new TreeNode();
        for (int i = 1; i < size; i++) {
            nodes[i] = new TreeNode();

            // Make each node the child of some randomly chosen previous node.
            nodes[random.nextInt(i)].children.add(nodes[i]);
        }

        return nodes[0].toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //long start = System.currentTimeMillis();
        new uniformsubtrees_vanb().doit();
        //System.err.println( System.currentTimeMillis() - start );
    }
}