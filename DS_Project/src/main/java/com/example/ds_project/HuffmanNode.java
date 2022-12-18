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

        return (n.leftnode == null) && (n.rightnode == null);
    }

    private HuffmanNode(int freq, char c){
        this.freq = freq;
        this.c = c;
        leftnode = null;
        rightnode = null;

    }
    private HuffmanNode(int freq, char c, HuffmanNode left, HuffmanNode right){
        this.freq = freq;
        this.c = c;
        this.leftnode = left;
        this.rightnode= right;

    }

    String codedStr(String str){
        String coded = "";
        char[] chars = str.toCharArray();
        for(char c : chars){
            coded = coded + symbolTable[c];
        }
        return coded;
    }

    public void buildCode(String[] symbolTable,HuffmanNode node ,String code){
        if(isLeaf(node)){
            symbolTable[node.c] = code;
            return;
        }
        buildCode(symbolTable, node.leftnode, code + '0');
        buildCode(symbolTable, node.rightnode, code + '1');

    }
    public static String minify(String file){
        String minified = new String();
        for(int i = 0; i< file.length();i++){
            if(file.charAt(i)=='\n'){
                continue;
            }
            else if((file.charAt(i)== ' ' && file.charAt(i+1)==' ')){
                continue;
            }
            else minified += file.charAt(i) ;
        }
        return minified;
    }
    public String Compress(String str) throws IOException {
        str = minify(str);
        char[] chars = str.toCharArray();
        int[] freq = new int[256];
        for (int i = 0; i < chars.length; i++) {
            freq[chars[i]]++;
        }
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(256, new MyComparator());
        for (char c = 0; c < 256; c++) {
            if (freq[c] > 0)
                queue.add(new HuffmanNode(freq[c], c));
        }
        while(queue.size()>1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode parent = new HuffmanNode(left.freq + right.freq, '\0', left, right);
            queue.add(parent);
        }
        root = queue.peek();
        //printTree(root);
        buildCode(symbolTable,root,code);
//        for (char c = 0; c < 256; c++) {
//            if (freq[c] > 0)
//                System.out.println(c + " " + symbolTable[c]);
//        }
        codew = codedStr(str);
        return codew;
    }
    static void writeBinaryToFile(String binary, String filePath) throws IOException {
        FileOutputStream fileOS = new FileOutputStream(filePath);
        DataOutputStream dataOS = new DataOutputStream(fileOS);
        byte flag = 0;
        byte toByte = 0;
        byte count = 7;
        byte remainder = (byte) (binary.length() % 8);
        if (remainder != 0) {
            flag = (byte) (8 - remainder);
        }
        dataOS.writeByte(flag);
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                toByte |= (1 << count);
            }
            // everytime you form 8 bits write them as a byte to the file
            if (count == 0) {
                dataOS.writeByte(toByte);
                toByte = 0;
                count = 8;
            }
            count--;
        }
        // the last byte will be padded by zeros equal to the flag value
        dataOS.writeByte(toByte);
        dataOS.close();
    }
    private static StringBuilder charToBinaryString(char c) {
        StringBuilder result = new StringBuilder();
        String binary = Integer.toBinaryString(c & 0xFF);
        for(int i = 0 ; i < (8 - binary.length()) ; i++) {
            result.append('0');
        }
        result.append(binary);
        return result;
    }

    static String readBinaryFromFile(String filePath) throws IOException {
        FileInputStream fileIS = new FileInputStream(filePath);
        DataInputStream dataIS = new DataInputStream(fileIS);
        StringBuilder encoded=new StringBuilder();
        char[] read = new char[dataIS.available()];
        int count = 0;
        while (dataIS.available() > 0) {
            read[count++] = (char) dataIS.readByte();
        }
        dataIS.close();
        char flag = read[0];
        for (int i = 1; i < count; i++) {
            encoded.append(charToBinaryString(read[i]));
        }
        if (flag != 0) {
            encoded.delete(encoded.length() - flag, encoded.length());
        }
        return encoded.toString();
    }
    static Boolean [] codedBinary(String str){
        Boolean [] boolCode = new Boolean[str.length()];
        for(int i = 0; i<str.length()-1;i++){
            if(str.charAt(i) == '0') {
                boolCode[i] = false;
            } else if (str.charAt(i) == '1') {
                boolCode[i] = true;
            }
        }
        return boolCode;
    }

    private void printTree(HuffmanNode root) {
        if (isLeaf(root)){
            System.out.println(root.c + " "+ root.freq);
            return;
        }
        else {
            System.out.println(root.c + " " + root.freq );
        }
        printTree(root.leftnode);
        printTree(root.rightnode);
    }

    String Decompression(String path,HuffmanNode root) throws IOException {
        HuffmanNode node = root;
        String decompressed = "";
        String str = readBinaryFromFile(path);
        for(int i = 0; i<str.length();i++){
            if(str.charAt(i) == '0'){
                node = node.leftnode;
            }
            else if(str.charAt(i) == '1'){
                node = node.rightnode;
            }
            if (node.leftnode == null && node.rightnode == null) {
                decompressed = decompressed + node.c;
                node = root;
            }
        }
        decompressed = decompressed + '\0';
        return decompressed;
    }
}

class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.freq - y.freq;
    }
}
