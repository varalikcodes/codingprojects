package venom;

import java.util.ArrayList;

/**
 * The Venom class represents a binary search tree of SymbioteHost objects.
 * Venom is a sentient alien symbiote with a liquid-like form that requires a
 * host to bond with for its survival. The host is granted superhuman abilities
 * and the symbiote gains a degree of autonomy. The Venom class contains methods
 * that will help venom find the most compatible host. You are Venom.
 * 
 * @author Ayla Muminovic
 * @author Shane Haughton
 * @author Elian D. Deogracia-Brito
 */
public class Venom {
    private SymbioteHost root;

    /**
     * DO NOT EDIT THIS METHOD
     * 
     * Default constructor.
     */
    public Venom() {
        root = null;
    }

    /**
     * This method is provided to you
     * Creates an array of SymbioteHost objects from a file. The file should
     * contain the number of people on the first line, followed by the name,
     * compatibility, stability, and whether they have antibodies on each
     * subsequent line.
     * 
     * @param filename the name of the file
     * @return an array of SymbioteHosts (should not contain children)
     */
    public SymbioteHost[] createSymbioteHosts(String filename) {
        // DO NOT EDIT THIS METHOD
        StdIn.setFile(filename);
        int numOfPeople = StdIn.readInt();
        SymbioteHost[] people = new SymbioteHost[numOfPeople];
        for (int i = 0; i < numOfPeople; i++) {
            StdIn.readLine();
            String name = StdIn.readLine();
            int compatibility = StdIn.readInt();
            int stability = StdIn.readInt();
            boolean hasAntibodies = StdIn.readBoolean();
            SymbioteHost newPerson = new SymbioteHost(name, compatibility, stability, hasAntibodies, null, null);
            people[i] = newPerson;
        }
        return people;
    }

    private SymbioteHost insert(SymbioteHost curr, SymbioteHost symbioteHost) {
        if (curr == null) {
            return symbioteHost;
        }
        int cmp = symbioteHost.getName().compareTo(curr.getName());
        if (cmp < 0) {
            curr.setLeft(insert(curr.getLeft(), symbioteHost));
        }
        else if (cmp > 0) {
            curr.setRight(insert(curr.getRight(), symbioteHost));
        }
        else {
            curr.setHasAntibodies(symbioteHost.hasAntibodies());
            curr.setMentalStability(symbioteHost.getMentalStability());
            curr.setSymbioteCompatibility(symbioteHost.getSymbioteCompatibility());
        }
        return curr;
    }
    /**
     * Inserts a SymbioteHost object into the binary search tree.
     * 
     * @param symbioteHost the SymbioteHost object to insert
     */
    public void insertSymbioteHost(SymbioteHost symbioteHost) {
        root = insert(root, symbioteHost);
    }

    /**
     * Builds a binary search tree from an array of SymbioteHost objects.
     * 
     * @param filename filename to read
     */
    public void buildTree(String filename) {
        SymbioteHost[] symbioteHosts = createSymbioteHosts(filename);
        for (SymbioteHost symbioteHost : symbioteHosts) {
            insertSymbioteHost(symbioteHost);
        }
    }

    private SymbioteHost recursiveFindMostSuitable(SymbioteHost curr, SymbioteHost top) {
        if(curr == null) {
            return top;
        }
        int currentSuitability = curr.calculateSuitability();
        int topSuitability;
        if (top == null) {
            topSuitability = Integer.MIN_VALUE;
        } else {
            topSuitability = top.calculateSuitability();
        }
        if (currentSuitability > topSuitability) {
            top = curr;
        }
    
        // Pre-order traversal: visit current, then left, then right
        top = recursiveFindMostSuitable(curr.getLeft(), top);
        top = recursiveFindMostSuitable(curr.getRight(), top);
    
        return top;
    }
    /**
     * Finds the most compatible host in the tree. The most compatible host
     * is the one with the highest suitability that does not have antibodies.
     * PREorder traversal is used to traverse the tree. The host with the highest suitability
     * is returned. If the tree is empty, null is returned.
     * 
     * USE the calculateSuitability method on a SymbioteHost instance to get
     * a host's suitability.
     * 
     * @return the most compatible SymbioteHost object
     */
    public SymbioteHost findMostSuitable() {
        return recursiveFindMostSuitable(root, null);
    }

    private void inOrder(SymbioteHost curr, ArrayList<SymbioteHost> withAntibodies) {
        if (curr == null) {
            return;
        }
        
        inOrder(curr.getLeft(), withAntibodies);

        if (curr.hasAntibodies()) {
            withAntibodies.add(curr);
        }
        
        inOrder(curr.getRight(), withAntibodies);
    }

    /**
     * Finds all hosts in the tree that have antibodies. INorder traversal is used to
     * traverse the tree. The hosts that have antibodies are added to an
     * ArrayList. If the tree is empty, null is returned.
     * 
     * @return an ArrayList of SymbioteHost objects that have antibodies
     */
    public ArrayList<SymbioteHost> findHostsWithAntibodies() {
        
        ArrayList<SymbioteHost> hostsWithAntibodies = new ArrayList<>();
        SymbioteHost curr = root;
        
        inOrder(curr, hostsWithAntibodies);
        
        return hostsWithAntibodies;
    }

    /**
     * Finds all hosts in the tree that have a suitability between the given
     * range. The range is inclusive. Level order traversal is used to traverse the tree. The
     * hosts that fall within the range are added to an ArrayList. If the tree
     * is empty, null is returned.
     * 
     * @param minSuitability the minimum suitability
     * @param maxSuitability the maximum suitability
     * @return an ArrayList of SymbioteHost objects that fall within the range
     */
    public ArrayList<SymbioteHost> findHostsWithinSuitabilityRange(int minSuitability, int maxSuitability) {
        ArrayList<SymbioteHost> hosts= new ArrayList<>();

        if (root == null) {
            return hosts;
        }

        Queue<SymbioteHost> queue = new Queue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            SymbioteHost curr = queue.dequeue();

            int suitability = curr.calculateSuitability();

            // Check if the current node's suitability falls within the given range
            if (suitability >= minSuitability && suitability <= maxSuitability) {
                hosts.add(curr);
            }

            // Enqueue the left and right children, if they exist
            if (curr.getLeft() != null) {
                queue.enqueue(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.enqueue(curr.getRight());
            }
        }
        return hosts;
    }

    private SymbioteHost deleteNode(SymbioteHost curr, String name) {
        if (curr == null) {
            return null;
        }
    
        int cmp = name.compareTo(curr.getName());
    
        if (cmp < 0) {
            curr.setLeft(deleteNode(curr.getLeft(), name));
        } else if (cmp > 0) {
            curr.setRight(deleteNode(curr.getRight(), name));
        } else {
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                // Case 2: One child (left child)
                return curr.getLeft();
            } else {
                // Case 3: Two children
                // Find the inorder successor (smallest node in the right subtree)
                SymbioteHost successor = curr.getRight();
                while (successor.getLeft() != null) {
                    successor = successor.getLeft();
                }
                
                // Copy the inorder successor's data to current node
                curr.setName(successor.getName());
                curr.setMentalStability(successor.getMentalStability());
                curr.setSymbioteCompatibility(successor.getSymbioteCompatibility());
                curr.setHasAntibodies(successor.hasAntibodies());
    
                // Delete the inorder successor from the right subtree
                curr.setRight(deleteNode(curr.getRight(), successor.getName()));
            }
        }
    
        return curr;
    }
    /**
     * Deletes a node from the binary search tree with the given name.
     * If the node is not found, nothing happens.
     * 
     * @param name the name of the SymbioteHost object to delete
     */
    public void deleteSymbioteHost(String name) {
        root = deleteNode(root, name);
    }

    /**
     * Challenge - worth zero points
     *
     * Heroes have arrived to defeat you! You must clean up the tree to
     * optimize your chances of survival. You must remove hosts with a
     * suitability between 0 and 100 and hosts that have antibodies.
     * 
     * Cleans up the tree by removing nodes with a suitability of 0 to 100
     * and nodes that have antibodies (IN THAT ORDER).
     */
    public void cleanupTree() {
        // WRITE YOUR CODE HERE
    }

    /**
     * Gets the root of the tree.
     * 
     * @return the root of the tree
     */
    public SymbioteHost getRoot() {
        return root;
    }

    /**
     * Prints out the tree.
     */
    public void printTree() {
        if (root == null) {
            return;
        }

        // Modify no. of '\t' based on depth of node
        printTree(root, 0, false, false);
    }

    private void printTree(SymbioteHost root, int depth, boolean isRight, boolean isLeft) {
        System.out.print("\t".repeat(depth));

        if (isRight) {
            System.out.print("|-R- ");
        } else if (isLeft) {
            System.out.print("|-L- ");
        } else {
            System.out.print("+--- ");
        }

        if (root == null) {
            System.out.println("null");
            return;
        }

        System.out.println(root);

        if (root.getLeft() == null && root.getRight() == null) {
            return;
        }

        printTree(root.getLeft(), depth + 1, false, true);
        printTree(root.getRight(), depth + 1, true, false);
    }
}
