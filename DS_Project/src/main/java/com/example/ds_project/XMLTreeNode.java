package com.example.ds_project;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class XMLTree {

    static XMLTreeNode root = null;
    private BufferedReader br;
    static ArrayList<Integer> errorLines = new ArrayList<>();
    static ArrayList<String> errorMessages = new ArrayList<>();
    static String objectXMLPretify ="";
    boolean dataFlag = false;


    public XMLTree(File XMLFileReader) throws IOException {

        br = new BufferedReader(new FileReader(XMLFileReader));
        constructTree();

    }

    public XMLTree(StringReader XMLStringReader) throws IOException {

        br = new BufferedReader(XMLStringReader);
        constructTree();

    }

    public static XMLTreeNode getRoot()
    {
        return root;
    }

    public ArrayList<Integer> getErrorLines()
    {
        return errorLines;
    }

    public ArrayList<String> getErrorMessages()
    {
        return errorMessages;
    }

    public String getObjectXMLPretify ()
    {
        return objectXMLPretify;
    }


    private void openingTag(Stack<XMLTreeNode> stack, String tagName, int lineNumber) {

        XMLTreeNode node = new XMLTreeNode(tagName);
        tagName = "";
        if (stack.isEmpty()) {
            if(root == null) {
                root = node;
                stack.push(root);
            }
            else {
                errorLines.add(lineNumber);
                errorMessages.add("Missing Root for the XML file ");
                XMLTreeNode rootNode = new XMLTreeNode("ROOT");
                rootNode.addChild(root);
                root = rootNode;
                stack.push(root);
                stack.peek().addChild(node);
                stack.push(node);
            }

        }
        else {
            stack.peek().addChild(node);
            stack.push(node);
        }
    }

    private void closingTag(Stack<XMLTreeNode> stack, String tagName, int lineNumber) {
        if (stack.empty()) {

            errorLines.add(lineNumber);
            errorMessages.add("error Missing Opening for Closing Tag: </" + tagName + ">");
        }
        else {
            if (stack.peek().getValue().equals(tagName)) {
                stack.pop();
            }
            else {
                errorLines.add(lineNumber);
                if(dataFlag) {
                    String parent = stack.peek().getParent();
                    if(parent != null) {
                        if(tagName.equals(parent)) {
                            errorMessages.add("Missing closing tag of: <" + stack.pop().getValue() + ">");
                            stack.pop();
                        }
                        else {
                            errorMessages.add("error Missing Opening for Closing Tag: </" + tagName + ">");
                            XMLTreeNode data = stack.peek().removeLastChild();
                            XMLTreeNode node = new XMLTreeNode(tagName);
                            node.addChild(data);
                            stack.peek().addChild(node);
                        }
                    }
                }
                else {
                    String parent = stack.peek().getParent();
                    if((parent != null)&&(tagName.equals(parent))) {
                        errorMessages.add("error Missing Closing for Opening Tag: </" + stack.pop().getValue() + ">");
                        stack.pop();
                    }
                    else {
                        errorMessages.add("error Missing Opening for Closing Tag: </" + tagName + ">");
                        XMLTreeNode emptyNode = new XMLTreeNode(tagName);
                        emptyNode.addChild(new XMLTreeNode(" "));
                        stack.peek().addChild(emptyNode);
                    }
                }
            }
        }
    }

    private void data(Stack<XMLTreeNode> stack, String tagName, int lineNumber,  boolean errorFlag) {
        if(errorFlag) {
            if(tagName.charAt(0)!='/')
                openingTag(stack, tagName, lineNumber);
            else
                closingTag(stack, tagName.substring(1), lineNumber);
        }
        else {

            XMLTreeNode node = new XMLTreeNode(tagName);
            stack.peek().addChild(node);
            dataFlag = true;
        }

    }

    public void constructTree() throws IOException {

        Stack<XMLTreeNode> stack = new Stack();
        int lineNumber = 0;
        String line = "";
        String tagName = "";
        boolean errorFlag = false;


        while ((line = br.readLine()) != null) {

            lineNumber++;
            int i = 0;

            while (i < line.length()) {

                while (line.charAt(i) == ' ') {
                    i++;
                    if(i == line.length())
                        break;
                }
                if(i == line.length())
                    continue;


                if (line.charAt(i) == '<') {
                    i++;

                    if (line.charAt(i) == '/') {

                        //closing tag
                        i++;
                        while (line.charAt(i) != '>') {
                            tagName += line.charAt(i);
                            i++;
                            if(i==line.length()) {
                                errorLines.add(lineNumber);
                                errorMessages.add("Missing character '>'");
                                break;
                            }
                        }
                        i++;

                        closingTag(stack, tagName, lineNumber);
                        dataFlag = false;
                        tagName = "";

                    }

                    else {
                        //opening tag

                        if (dataFlag) {
                            dataFlag = false;
                            errorLines.add(lineNumber);
                            errorMessages.add("error Missing Closing for Opening Tag: <" + stack.pop().getValue() + ">");
                        }

                        while (line.charAt(i) != '>') {
                            tagName += line.charAt(i);
                            i++;
                            if(i==line.length()) {
                                errorLines.add(lineNumber);
                                errorMessages.add("Missing character '>'");
                                break;
                            }
                            if(line.charAt(i)==' ') {
                                errorLines.add(lineNumber);
                                errorMessages.add("Missing character '>'");
                                break;
                            }
                        }

                        i++;

                        openingTag(stack, tagName, lineNumber);
                        tagName = "";
                    }
                }
                else {

                    while ((i < line.length()) && (line.charAt(i) != '<')) {
                        if(line.charAt(i)=='>') {
                            errorLines.add(lineNumber);
                            errorMessages.add("Missing character '<'");
                            errorFlag = true;
                            break;
                        }
                        if((line.charAt(i)=='/')&&(tagName.length()!=0)) {
                            data(stack, tagName, lineNumber,  errorFlag);
                            tagName = "";
                            continue;
                        }

                        tagName += line.charAt(i);
                        i++;
                    }

                    data(stack, tagName, lineNumber,  errorFlag);
                    if(errorFlag) {
                        i++;
                        errorFlag = false;
                    }
                    tagName = "";
                }
            }
        }

        if (!stack.isEmpty()) {
            errorLines.add(lineNumber);

            while (!stack.isEmpty()) {
                if(stack.peek().getValue().equals("ROOT"))
                    return;
                errorMessages.add("Missing Closing Tag for: <" + stack.pop().getValue() + ">");
            }
        }
    }

    public void XMLPrettify(){

        (root, 0);

    }

    private void prettify(XMLTreeNode node, int indentation){


        if (node.getChildren().size()==0) {

            objectXMLPretify += (node.getValue().indent(indentation));
            return;

        }

        objectXMLPretify += (("<" + node.getValue() + ">").indent(indentation));

        for (int i = 0; i < node.getChildren().size(); i++) {

            prettify(node.getChildren().get(i), indentation + 4);

        }

        objectXMLPretify += (("</" + node.getValue() + ">").indent(indentation));
    }

}

