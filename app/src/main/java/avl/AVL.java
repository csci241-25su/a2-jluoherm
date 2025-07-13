package avl;

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }

  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
    if (w.compareTo(n.word) < 0) {
      if (n.left == null) {
        n.left = new Node(w,n);
        n.left.height = n.left.parent.height + 1;
        size++;
      } else if (w.compareTo(n.left.word) < 0) {
        if (n.left.left == null) {
          n.left.left = new Node(w,n.left);
          n.left.left.height = n.left.left.parent.height + 1;
          size++;
        }else bstInsert(n.left.left, w);
      } else if (w.compareTo(n.left.word) > 0) {
        if (n.left.right == null) {
          n.left.right = new Node(w,n.left);
          n.left.right.height = n.left.right.parent.height + 1;
          size++;
        }else bstInsert(n.left.right, w);
      }  // Else w.compareTo(root.left.word) == 0
        // Do nothing, word is the same word in root.left node
    } else if (w.compareTo(n.word) > 0) {
      if (n.right == null) {
        n.right = new Node (w,n);
        n.right.height = n.right.parent.height + 1;
        size++;
      } else if (w.compareTo(n.right.word) < 0) {
        if (n.right.left == null) {
          n.right.left = new Node(w,n.right);
          n.right.left.height = n.right.left.parent.height + 1;
          size++;
        }else bstInsert(n.right.left, w);
      } else if (w.compareTo(n.right.word) > 0) {
        if (n.right.right == null) {
          n.right.right = new Node(w, n.right);
          n.right.right.height = n.right.right.parent.height + 1;
          size++;
        }else bstInsert(n.right.right, w);
      }  // Else w.compareTo(root.right.word) == 0
        // Do nothing, word is the same word in root.right node
    }
    // Else w.compareTo(root.word) == 0
    // Do nothing, word is the same word in root node
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
    // TODO
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    // TODO
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    // For comments below, y = x.right
    // Retain original y
    Node tempNode = x.right;

    // If y.left not null, update x.right to = y.left
    //and update y.left parent to be x
    if (x.right.left != null){
      x.right.left.parent = x;
      x.right = x.right.left;
    }else x.right = null;

    // Update y's parent to be x's former parent
    if (x.parent != null){
      Node tempParent = x.parent;

      if (tempParent.right == x){
        x.parent.right = tempNode;
      }else x.parent.left = tempNode;
      // Update y's parent to be x's old parent
      tempNode.parent = tempParent;
      //Update y to root if x is root & has no parent
    } else if (x == root) {
      tempNode.parent = null;
      root = tempNode;
    }
    //Update x's parent to be y
    x.parent = tempNode;
    //Update y's left node to be x
    x.parent.left = x;

  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    // For comments below, x = y.left
    // Retain original x
    Node tempNode = y.left;

    // If x.right not null, update y.left to = x.right
    //and update x.right.parent to be y
    if (y.left.right != null) {
      y.left.right.parent = y;
      y.left = y.left.right;
    } else y.left = null;

    // Update x's parent to be y's former parent
    if (y.parent != null) {
      Node tempParent = y.parent;

      if (tempParent.right == y) {
        y.parent.right = tempNode;
      } else y.parent.left = tempNode;
      // Update y's parent to be x's old parent
      tempNode.parent = tempParent;
      //Update x to root if y is root
    } else if (y == root) {
      tempNode.parent = null;
      root = tempNode;
    }
    //Update y's parent to be x
    y.parent = tempNode;
    //Update x's right node to be y
    y.parent.right = y;
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    // TODO
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}
