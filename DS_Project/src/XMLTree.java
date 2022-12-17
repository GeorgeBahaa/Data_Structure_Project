

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class XMLTree {

    static XMLTreeNode root;
    private BufferedReader br;
    static ArrayList<Integer> errorLines = new ArrayList<>();
    static ArrayList<String> errorMessages = new ArrayList<>();
    private String objectXMLPretify ="";

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


    public XMLTree(File XMLFileReader) throws IOException {
    	
        br = new BufferedReader(new FileReader(XMLFileReader));
        constructTree();

    }
    
    public XMLTree(StringReader XMLStringReader) throws IOException {
 
        br = new BufferedReader(XMLStringReader);
        constructTree();

    }

    public void XMLPrettify(){

        prettify(root, 0);

    }
    public static void preOrderPrint(XMLTreeNode node) {
        if (node.children.isEmpty()) {
            System.out.println("Node Value: " + node.getValue() + " " +node.parent);
            return;
        }
        System.out.println("Node Name: " + node.getValue());
        for (int i = 0; i < node.children.size(); i++) {
            preOrderPrint(node.children.get(i));
        }
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

    public String getObjectXMLPretify() {
        return objectXMLPretify;
    }

    public void constructTree() throws IOException {

        Stack<XMLTreeNode> stack = new Stack();
        int lineNumber = 0;
        String line = "";
        String tagName = "";

        boolean dataFlag = false;


        while ((line = br.readLine()) != null) {

            lineNumber++;
            int i = 0;

            while (i < line.length()) {

                while (line.charAt(i) == ' ')
                    i++;

                if (line.charAt(i) == '<') {
                    i++;

                    if (line.charAt(i) == '/') {
                        //closing tag
                        i++;
                        while (line.charAt(i) != '>') {
                            tagName += line.charAt(i);
                            i++;
                        }
                        i++;

                        if (stack.peek().getValue().equals(tagName)) {
                            stack.pop();
                        }
                        else {
                            errorLines.add(lineNumber);
                            errorMessages.add("error Unmatched Opening and Closing Tags: <" + stack.pop().getValue() + "> and </" + tagName + ">");
                        }
                        dataFlag = false;
                        tagName = "";

                    }

                    else {
                        //Opening tag

                        if (dataFlag) {
                            dataFlag = false;
                            errorLines.add(lineNumber);

                            errorMessages.add("Missing closing tag of: <" + stack.pop().getValue() + ">");
                        }

                        while (line.charAt(i) != '>') {
                            tagName += line.charAt(i);
                            i++;
                        }

                        i++;
                        XMLTreeNode node = new XMLTreeNode(tagName, new ArrayList<XMLTreeNode>());
                        tagName = "";
                        if (stack.isEmpty()) {
                            root = node;
                            stack.push(root);
                        }
                        else {
                            stack.peek().addChild(node);
                            stack.push(node);
                        }

                    }
                }
                else {

                    while ((i < line.length()) && (line.charAt(i) != '<')) {
                        tagName += line.charAt(i);
                        i++;
                    }
                    XMLTreeNode node = new XMLTreeNode(tagName);
                    stack.peek().addChild(node);
                    dataFlag = true;
                    tagName = "";

                }
            }
        }
        if (!stack.isEmpty()) {
        	
            errorLines.add(lineNumber);

            while (!stack.isEmpty()) {
                errorMessages.add("Missing Closing Tag for: <" + stack.pop().getValue() + ">");
            }

        }
    }

}
