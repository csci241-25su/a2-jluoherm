package avl;

/**
 * AVL tree enhanced to count how many times each word is inserted.
 * Tracks `maxCount` and `maxCountNode` for access to the most frequently occurring line
 * If more than one string equals the maxCount, the string that was first set as the maxCount should remain
 * as the maxCount string
 */

public class AVL {

  public Node root;

  private int size;

  private int maxCount = 0;

  public int getSize() {return size;}

  public int getMaxCount() {
    return maxCount == 0 ? root.count : maxCount;
  }

  public void setMaxCount (int count) {maxCount = count;}

  public Node maxCountNode = null;

  public Node getMaxCountNode() {
    return maxCountNode == null ? root : maxCountNode;
  }

  public void setMaxCountNode(Node n){maxCountNode = n;}


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
        n.left = new Node(w, n);
        size++;
      } else if (w.compareTo(n.left.word) < 0) {
        if (n.left.left == null) {
          n.left.left = new Node(w, n.left);
          size++;
        } else {
          bstInsert(n.left.left, w);
        }
      } else if (w.compareTo(n.left.word) > 0) {
        if (n.left.right == null) {
          n.left.right = new Node(w, n.left);
          size++;
        } else {
          bstInsert(n.left.right, w);
        }
      }  /* Else w.compareTo(root.left.word) == 0
       * Do nothing, word = word already in root.left node*/
    } else if (w.compareTo(n.word) > 0) {
      if (n.right == null) {
        n.right = new Node(w, n);
        size++;
      } else if (w.compareTo(n.right.word) < 0) {
        if (n.right.left == null) {
          n.right.left = new Node(w, n.right);
          size++;
        } else {
          bstInsert(n.right.left, w);
        }
      } else if (w.compareTo(n.right.word) > 0) {
        if (n.right.right == null) {
          n.right.right = new Node(w, n.right);
          size++;
        } else {
          bstInsert(n.right.right, w);
        }
      }  /* Else w.compareTo(root.right.word) == 0
       * Do nothing, word = word already in root.right node */
    }
    /* Else w.compareTo(root.word) == 0
     * Do nothing, word = word already in root node */
  }

  /** Recalculate height of n ancestors up to and including root
   *  precondition: n is not null and already part of a valid AVL tree
   **/
  private void reCalculateHeight(Node n) {
    int rightHeight = n.right != null ? n.right.height : 0;
    int leftHeight = n.left != null ? n.left.height : 0;

    if (n.right == null && n.left == null) {
      n.height = 0;
    } else n.height = 1 + Math.max(leftHeight, rightHeight);

    if (n.parent != null) {
      reCalculateHeight(n.parent);
    }
  }

  /** Helper method to increment count of occurrences of unique string inserts
   * Updates maxCount node to point to the current node if the
   *  occurrence count is greater than the existing node pointed to by maxCount
   * */
  private void updateMaxCount (Node n){
    n.count++;
    if (n.count > getMaxCount()){
      setMaxCount(n.count);
      setMaxCountNode(n);
    }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    if (w.compareTo(n.word) < 0) {
      if (n.left == null) {
        n.left = new Node(w, n);
        n.left.height = 0;
        reCalculateHeight(n);
        if (n.parent != null && n.parent.parent != null){
          rebalance(n);
        }
        size++;
      } else if (w.compareTo(n.left.word) < 0) {
        if (n.left.left == null) {
          n.left.left = new Node(w, n.left);
          n.left.left.height = 0;
          reCalculateHeight(n.left);
          rebalance(n.left);
          size++;
        } else {
          avlInsert(n.left.left, w);
        }
      } else if (w.compareTo(n.left.word) > 0) {
        if (n.left.right == null) {
          n.left.right = new Node(w, n.left);
          n.left.right.height = 0;
          reCalculateHeight(n.left);
          rebalance(n.left);
          size++;
        } else {
          avlInsert(n.left.right, w);
        }
      } else updateMaxCount(n.left);
    } else if (w.compareTo(n.word) > 0) {
      if (n.right == null) {
        n.right = new Node(w, n);
        n.right.height = 0;
        reCalculateHeight(n);
        if (n.parent != null && n.parent.parent != null) {
          rebalance(n);
        }
        size++;
      } else if (w.compareTo(n.right.word) < 0) {
        if (n.right.left == null) {
          n.right.left = new Node(w, n.right);
          n.right.left.height = 0;
          reCalculateHeight(n.right);
          rebalance(n.right);
          size++;
        } else {
          avlInsert(n.right.left, w);
        }
      } else if (w.compareTo(n.right.word) > 0) {
        if (n.right.right == null) {
          n.right.right = new Node(w, n.right);
          n.right.right.height = 0;
          reCalculateHeight(n.right);
          rebalance(n.right);
          size++;
        } else {
          avlInsert(n.right.right, w);
        }
      } else updateMaxCount(n.right);
    }else updateMaxCount(n);
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    Node nodeY = x.right;

    // If y's left node not null, update x.right to = y.left
    //and update y.left parent to be x
    if (x.right.left != null) {
      x.right.left.parent = x;
      x.right = x.right.left;
    } else x.right = null;

    // Update y's parent to be x's former parent
    if (x.parent != null) {
      Node yNewParent = x.parent;
      if (yNewParent.left == x) {
        x.parent.left = nodeY;
      } else x.parent.right = nodeY;
      nodeY.parent = yNewParent;
    } else if (x == root) {
      nodeY.parent = null;
      root = nodeY;
    }
    //Update x's parent to be y
    x.parent = nodeY; //y
    //Update y's left node to be x
    x.parent.left = x;
    reCalculateHeight(x);
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    Node x = y.left;

    // If x's right node not null, update y.left to = x.right
    //and update x.right.parent to be y
    if (y.left.right != null) {
      y.left.right.parent = y;
      y.left = y.left.right;
    } else y.left = null;

    // Update x's parent to be y's former parent
    if (y.parent != null) {
      Node xNewParent = y.parent;
      if (xNewParent.left == y) {
        y.parent.left = x;
      } else y.parent.right = x;
      x.parent = xNewParent;
    } else if (y == root) {
      x.parent = null;
      root = x;
    }
    //Update y's parent to be x
    //Update x's right node to be y
    y.parent = x;
    y.parent.right = y;
    reCalculateHeight(y);
  }

  /** get Node n's balance to use in the rebalance method
   *  precondition: none of n's descendants violates the AVL property */
  private int getBalance(Node n) {
    int rightHeight = n.right != null ? n.right.height : -1;
    int leftHeight = n.left != null ? n.left.height : -1;
    return rightHeight - leftHeight;
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    int nbalance = getBalance(n);
    int nleftBalance = n.left != null ? getBalance(n.left) : 0;
    int nrightBalance = n.right != null ? getBalance(n.right) : 0;

    //Case 1 & 2
    if (nbalance < -1) {
      if (nleftBalance < 0) {
        rightRotate(n);
      } else {
        leftRotate(n.left);
        rightRotate(n);
      }
      //Case 3 & 4
    } else if (nbalance > 1){
      if (nrightBalance < 0) {
        rightRotate(n.right);
        leftRotate(n);
      } else {
        leftRotate(n);
      }
    }
    if (n.parent != null){
      rebalance(n.parent);
    }
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
    public int count = 1;

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
