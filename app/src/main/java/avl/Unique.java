package avl;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
public class Unique {

    /** Main program: prints the number of unique lines in a given file by one
     * of two methods
     * Enhancement: Added option to print the most frequency occurring unique line in
     * a given file using the avlMaxCount method.
     * */
    public static void main(String[] args) {
        if (args.length != 2) {
          System.out.println("Requires 2 arguments: naive or avl and a filename.");
          return;
        }
        try {
            File f = new File(args[1]);
            Scanner sc = new Scanner(f);
            if (args[0].equals("maxCount")){
                System.out.println("Finding most frequency occurring line in " + args[1]);
                AVL.Node n = avlMaxCount(sc);
                System.out.println("Line: " + n.word);
                System.out.println("Count: " + n.count);
            }else {
                System.out.println("Finding unique lines in " + args[1]);
                if (args[0].equals("naive")) {
                    System.out.println("Naive:");
                    System.out.println(naiveUnique(sc));
                } else {
                    System.out.println(args[1]);
                    System.out.println("AVL:");
                    System.out.println(avlUnique(sc));
                }
            }
        } catch (FileNotFoundException exc) {
            System.out.println("Could not find file " + args[1]);
        }
    }

    /** Return the number of unique lines available to be read by sc */
    private static int naiveUnique(Scanner sc) {
      // unique lines seen so far
      ArrayList<String> seen = new ArrayList<String>();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();

        // check if we've seen it:
        int i = 0;
        while (i < seen.size() && !line.equals(seen.get(i))) {
          i++;
        }
        if (i == seen.size()) {
          seen.add(line);
        }
      }
      return seen.size();
    }

    /** Return the number of unique lines available to be read by sc */
    private static int avlUnique(Scanner sc) {
        //Unique lines seen so far
        AVL seen = new AVL();
        int uniqueLines = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            AVL.Node n = seen.search(line);

            // check if it's in the tree
            if (n == null){
                seen.avlInsert(line);
                uniqueLines++;
            }
        }
        return uniqueLines;
    }

    /** Return the most frequency occurring line in the file */
    private static AVL.Node avlMaxCount(Scanner sc) {
        //Lines seen so far
        AVL seen = new AVL();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            seen.avlInsert(line);

        }
        return seen.getMaxCountNode();
    }
}
