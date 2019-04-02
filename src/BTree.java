import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class BTree implements java.io.Serializable {

    static final String btreeFile = "btree/btree.txt"; //file to keep track of B tree root and constants
    static final String nodesFile = "btree/nodes.txt";
    static final int K = 16;  //constant used throughout the entire Btree
    static final int nodeSize = 2047; // to keep track of where different nodes are in the file
    Node root;
    int totalNumberOfNodes;

    static class Node implements java.io.Serializable {
        int currentNumberOfChildren;
        int currentNumberOfKeys;
        WordCount[] keys;
        Long[] children;
        int leaf; //leaf = 1
        long nodeID;


        public Node(long id) {
            nodeID = id;
            currentNumberOfChildren = 0;
            currentNumberOfKeys = 0;
            keys = new WordCount[2 * K - 1];
            children = new Long[2 * K];
        }

    }

    BTree() throws Exception {

        totalNumberOfNodes = 0;
        Node temp = new Node(totalNumberOfNodes);
        temp.leaf = 1;
        temp.currentNumberOfKeys = 0;
        write(temp);
        root = temp;
    }

    //recursive function to look for keys in the tree
    WordCount search(Node n, WordCount w) throws Exception {
        int i = 0;

        while (i < n.currentNumberOfKeys - 1 && w.compareTo(n.keys[i]) > 0) {
            i++;
        }

        if (i <= n.currentNumberOfKeys && w.compareTo(n.keys[i]) == 0) {
            return n.keys[i];
        } else if (n.leaf == 1) { //if not found and its a leaf, then key does not exist
            return null;
        } else {
            //searchCluster the appropriate child node and searchCluster it
            if (w.compareTo(n.keys[i]) < 0) {
                if (n.children[i] != 0) {
                    return search(read(n.children[i]), w);
                } else {
                    // node with id 0 is a null node
                    return null;
                }
            } else {
                //look at child node with biggest value
                return search(read(n.children[i + 1]), w);
            }

        }
    }

    boolean contains(Node n, WordCount w) throws Exception {
        int i = 0;

        //this below works for everything except if the WordFreq value is at the far right end of the child nodes
        while (i < n.currentNumberOfKeys - 1 && w.compareTo(n.keys[i]) > 1) {
            i++;
        }
        //just look to see if id matches (easier to searchCluster that way)
        if (i <= n.currentNumberOfKeys && w.compareTo(n.keys[i]) == 0) {
            return true;
        } else if (n.leaf == 1) { //if not found and its a leaf, then key does not exist
            return false;
        } else {
            //searchCluster the appropriate child node and searchCluster it
            if (w.compareTo(n.keys[i]) < 0) {
                return contains(read(n.children[i]), w);
            } else {
                return contains(read(n.children[i + 1]), w);
            }
        }
    }


    void insert(WordCount w) throws Exception {
        Node r = root;

            //need to split if full and insert into not full node
            if (root.currentNumberOfKeys == 2 * K - 1) {
                totalNumberOfNodes++;
                Node newNode = new Node(totalNumberOfNodes);
                root = newNode;
                newNode.leaf = 0;
                newNode.currentNumberOfKeys = 0;
                newNode.children[0] = r.nodeID;
                newNode.currentNumberOfChildren++;
                split(newNode, r);
                insertNotFull(newNode, w);
            } else {
                insertNotFull(r, w);
            }
    }


    void insertNotFull(Node x, WordCount w) throws Exception {

        int i = x.currentNumberOfKeys - 1;
        if (x.leaf == 1) {
            while (i > -1 && w.compareTo(x.keys[i]) < 0) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }

            i++;
            x.keys[i] = w;
            x.currentNumberOfKeys++;
            write(x);
        } else {
            while (i > -1 && w.compareTo(x.keys[i]) < 0) { //find appropriate spot
                i--;
            }

            i++;
            Node temp = read(x.children[i]);

            if (temp.currentNumberOfKeys == (2 * K - 1)) {
                split(x, temp);
                if (w.compareTo(x.keys[i]) > 0) {
                    temp = read(x.children[i + 1]);
                }
            }

            insertNotFull(temp, w);
        }
    }


    void split(Node x, Node y) throws IOException {
        totalNumberOfNodes++;
        Node z = new Node(totalNumberOfNodes);
        z.leaf = y.leaf;
        for (int i = 0; i < K - 1; i++) { //move second half of y's keys to to first half of z's keys
            z.keys[i] = y.keys[i + K];
            z.currentNumberOfKeys++; //just added a key, increment numKeys
            y.keys[i + K] = null;
            y.currentNumberOfKeys--;
        }

        if (y.leaf == 0) {
            //move second half of y's pointers to be first half of z's pointers
            for (int i = 0; i < K; i++) {
                z.children[i] = y.children[i + K];
                z.currentNumberOfChildren++;
                y.children[i + K] = null;
                y.currentNumberOfChildren--;
            }
        }

        // Z node can never point at 0
        int index = x.currentNumberOfKeys - 1;
        while (index > -1 && y.keys[K - 1].compareTo(x.keys[index]) < 0) {
            x.keys[index + 1] = x.keys[index];
            index--;
        }

        index++;
        x.keys[index] = y.keys[K - 1];
        x.currentNumberOfKeys++;
        y.keys[K - 1] = null; /// might give an error also -- used to be y.keys[K - 1] = 0
        y.currentNumberOfKeys--;


        int index2 = x.currentNumberOfChildren - 1;
        while (index2 > index) {
            x.children[index2 + 1] = x.children[index2];
            index2--;
        }

        index2++;
        x.children[index2] = z.nodeID;
        x.currentNumberOfChildren++;

        write(x);
        write(y);
        write(z);

    }

    //write root info to btree file
    void writeRoot() throws IOException {
        RandomAccessFile btf = new RandomAccessFile(btreeFile, "rw");
        btf.seek(0);
        FileChannel fc = btf.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(nodeSize * 2);

        //write total number of nodes to the file
        bb.putInt(totalNumberOfNodes);

        bb.putInt(root.leaf);
        bb.putLong(root.nodeID);

        bb.putInt(root.currentNumberOfKeys);
        for (int i = 0; i < root.currentNumberOfKeys; i++) {

            WordCount current = root.keys[i];

            //write the name of the current WordFreq
            byte[] name = current.getWord().getBytes();
            bb.putInt(name.length);
            bb.put(name);

            //write the count of current WordFreq
            bb.putInt(current.getCount());

        }

        bb.putInt(root.currentNumberOfChildren);
        for (int i = 0; i < root.currentNumberOfChildren; i++) {
            bb.putLong(root.children[i]);
        }


        bb.flip();
        fc.write(bb);
        bb.clear();
        fc.close();
        btf.close();


    }


    void write(Node n) throws IOException {
        try {
            RandomAccessFile file = new RandomAccessFile(nodesFile, "rw");
            file.seek(n.nodeID * nodeSize);
            FileChannel fc = file.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(nodeSize);

            bb.putInt(n.leaf);
            bb.putLong(n.nodeID);

            bb.putInt(n.currentNumberOfKeys);
            for (int i = 0; i < n.currentNumberOfKeys; i++) {

                WordCount current = n.keys[i];

                //write the name of the current yelp object
                byte[] name = current.getWord().getBytes();
                bb.putInt(name.length);
                bb.put(name);

                //write the id of current yelpObject
                bb.putInt(current.getCount());
            }

            bb.putInt(n.currentNumberOfChildren);
            for (int i = 0; i < n.currentNumberOfChildren; i++) {
                bb.putLong(n.children[i]);
            }


            bb.flip();
            fc.write(bb);
            bb.clear();
            fc.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static BTree readRoot() throws Exception {
        try {
            Node temp = new Node(0);
            RandomAccessFile btf = new RandomAccessFile(btreeFile, "rw");
            btf.seek(0);
            FileChannel fc = btf.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(nodeSize * 2);
            fc.read(bb);
            bb.flip();

            BTree bt = new BTree();
            bt.root = temp;
            bt.totalNumberOfNodes = bb.getInt();

            temp.leaf = bb.getInt();
            temp.nodeID = bb.getLong();

            temp.currentNumberOfKeys = bb.getInt(); //recover keys
            for (int i = 0; i < temp.currentNumberOfKeys; i++) {
                //recover all info that was written for every single
                // yelpdate iobject that was puyt ibn the nodes
                //make sure to refcord the number of byte in the byte buffer so i know
                //when to stop reading and not to get an overflow
                //WordFreq w = new WordFreq(null, null, null, 0, 0); // crashes because itll try to do math.abs(null) for int hash


                //read name
                int wordLen = bb.getInt();
                byte[] wordBuf = new byte[wordLen];
                bb.get(wordBuf);
                String word = new String(wordBuf);

                //read id
                int count = bb.getInt();

            }

            temp.currentNumberOfChildren = bb.getInt(); //recover children
            for (int i = 0; i < temp.currentNumberOfChildren; i++) {
                temp.children[i] = bb.getLong();
            }

            bb.clear();
            fc.close();
            btf.close();
            return bt;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //returns a node full of WordFreq objects
    Node read(long x) throws Exception {
        try {

            Node temp = new Node(0);
            RandomAccessFile file = new RandomAccessFile(nodesFile, "rw");
            file.seek(x * nodeSize);
            FileChannel fc = file.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(nodeSize);
            fc.read(bb);
            bb.flip();
            temp.leaf = bb.getInt();
            temp.nodeID = bb.getLong();

            temp.currentNumberOfKeys = bb.getInt(); //recover keys
            for (int i = 0; i < temp.currentNumberOfKeys; i++) {
                //recover all info that was written for every single
                // wordcount object that was puyt ibn the nodes
                //make sure to refcord the number of byte in the byte buffer so i know
                //when to stop reading and not to get an overflow
                //WordFreq w = new WordFreq(null, null, null, 0, 0); // crashes because itll try to do math.abs(null) for int hash


                //read name
                int wordLen = bb.getInt();
                byte[] wordBuf = new byte[wordLen];
                bb.get(wordBuf);
                String word = new String(wordBuf);

                //read id
                int count = bb.getInt();

                //once everything has been read from the file, add the object to the node
                WordCount w = new WordCount(word, count);
                temp.keys[i] = w;
            }

            temp.currentNumberOfChildren = bb.getInt(); //recover children
            for (int i = 0; i < temp.currentNumberOfChildren; i++) {
                temp.children[i] = bb.getLong();
            }

            bb.clear();
            fc.close();
            file.close();
            return temp;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}