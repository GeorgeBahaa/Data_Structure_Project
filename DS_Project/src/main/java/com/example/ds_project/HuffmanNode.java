package com.example.ds_project;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanNode {
    int freq;
    char c;
    String[] symbolTable = new String[256];
    String code = "";
    String codew = "";
    HuffmanNode leftnode;
    HuffmanNode rightnode;
    HuffmanNode root;

    public HuffmanNode() {
    }

    private boolean isLeaf(HuffmanNode n) {
        return n.leftnode == null && n.rightnode == null;
    }

    private HuffmanNode(int freq, char c) {
        this.freq = freq;
        this.c = c;
        this.leftnode = null;
        this.rightnode = null;
    }

    private HuffmanNode(int freq, char c, HuffmanNode left, HuffmanNode right) {
        this.freq = freq;
        this.c = c;
        this.leftnode = left;
        this.rightnode = right;
    }

    String codedStr(String str) {
        String coded = "";
        char[] chars = str.toCharArray();
        char[] var4 = chars;
        int var5 = chars.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            char c = var4[var6];
            coded = coded + this.symbolTable[c];
        }

        return coded;
    }

    public void buildCode(String[] symbolTable, HuffmanNode node, String code) {
        if (this.isLeaf(node)) {
            symbolTable[node.c] = code;
        } else {
            this.buildCode(symbolTable, node.leftnode, code + "0");
            this.buildCode(symbolTable, node.rightnode, code + "1");
        }
    }

    Boolean[] codedBinary() {
        Boolean[] boolCode = new Boolean[this.codew.length()];

        for(int i = 0; i < this.codew.length() - 1; ++i) {
            if (this.codew.charAt(i) == '0') {
                boolCode[i] = false;
            } else if (this.codew.charAt(i) == '1') {
                boolCode[i] = true;
            }
        }

        return boolCode;
    }

    public void Compress(String str) throws IOException {
        char[] chars = str.toCharArray();
        int[] freq = new int[256];

        for(int i = 0; i < chars.length; ++i) {
            ++freq[chars[i]];
        }

        PriorityQueue<HuffmanNode> queue = new PriorityQueue(256, new MyComparator());

        char c;
        for(c = 0; c < 256; ++c) {
            if (freq[c] > 0) {
                queue.add(new HuffmanNode(freq[c], c));
            }
        }

        while(queue.size() > 1) {
            HuffmanNode left = (HuffmanNode)queue.poll();
            HuffmanNode right = (HuffmanNode)queue.poll();
            HuffmanNode parent = new HuffmanNode(left.freq + right.freq, '\u0000', left, right);
            queue.add(parent);
        }

        this.root = (HuffmanNode)queue.peek();
        this.printTree(this.root);
        this.buildCode(this.symbolTable, this.root, this.code);

        for(c = 0; c < 256; ++c) {
            if (freq[c] > 0) {
                System.out.println("" + c + " " + this.symbolTable[c]);
            }
        }

        this.codew = this.codedStr(str);
        System.out.println(this.codew);
        writeBinaryToFile(this.codew, "C:\\Users\\dell\\Downloads\\path");
        System.out.println(this.Decompression("C:\\Users\\dell\\Downloads\\pathp.txt", this.root));
    }

    private static void writeBinaryToFile(String binary, String filePath) throws IOException {
        FileOutputStream fileOS = new FileOutputStream(filePath + "p.txt");
        DataOutputStream dataOS = new DataOutputStream(fileOS);
        byte flag = 0;
        byte toByte = 0;
        byte count = 7;
        byte remainder = (byte)(binary.length() % 8);
        if (remainder != 0) {
            flag = (byte)(8 - remainder);
        }

        dataOS.writeByte(flag);

        for(int i = 0; i < binary.length(); ++i) {
            if (binary.charAt(i) == '1') {
                toByte = (byte)(toByte | 1 << count);
            }

            if (count == 0) {
                dataOS.writeByte(toByte);
                toByte = 0;
                count = 8;
            }

            --count;
        }

        dataOS.writeByte(toByte);
        dataOS.close();
    }

    private static StringBuilder charToBinaryString(char c) {
        StringBuilder result = new StringBuilder();
        String binary = Integer.toBinaryString(c & 255);

        for(int i = 0; i < 8 - binary.length(); ++i) {
            result.append('0');
        }

        result.append(binary);
        return result;
    }

    private static String readBinaryFromFile(String filePath) throws IOException {
        FileInputStream fileIS = new FileInputStream(filePath);
        DataInputStream dataIS = new DataInputStream(fileIS);
        StringBuilder encoded = new StringBuilder();
        char[] read = new char[dataIS.available()];

        int count;
        for(count = 0; dataIS.available() > 0; read[count++] = (char)dataIS.readByte()) {
        }

        dataIS.close();
        char flag = read[0];

        for(int i = 1; i < count; ++i) {
            encoded.append(charToBinaryString(read[i]));
        }

        if (flag != 0) {
            encoded.delete(encoded.length() - flag, encoded.length());
        }

        return encoded.toString();
    }

    private void printTree(HuffmanNode root) {
        if (this.isLeaf(root)) {
            System.out.println(root.c + " " + root.freq);
        } else {
            System.out.println(root.c + " " + root.freq);
            this.printTree(root.leftnode);
            this.printTree(root.rightnode);
        }
    }

    String Decompression(String str, HuffmanNode root) throws IOException {
        HuffmanNode node = root;
        str = readBinaryFromFile(str);
        String dstr = "";

        for(int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '0') {
                node = node.leftnode;
            } else if (str.charAt(i) == '1') {
                node = node.rightnode;
            }

            if (node.leftnode == null && node.rightnode == null) {
                dstr = dstr + node.c;
                node = root;
            }
        }

        dstr = dstr + "\u0000";
        return dstr;
    }
}
class MyComparator implements Comparator<HuffmanNode> {
    MyComparator() {
    }

    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.freq - y.freq;
    }
}

