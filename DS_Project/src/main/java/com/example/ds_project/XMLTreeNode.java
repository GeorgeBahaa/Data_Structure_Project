package com.example.ds_project;

import java.util.ArrayList;

public class XMLTreeNode {

    private XMLTreeNode parent;
    private String value;
    private ArrayList<XMLTreeNode> children = new ArrayList<>();

    public XMLTreeNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getParent() {
        if(parent != null)
            return parent.getValue();
        return null;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setParent(XMLTreeNode parent) {
        this.parent = parent;
    }


    public ArrayList<XMLTreeNode> getChildren() {
        return children;
    }

    public void addChild(XMLTreeNode node) {
        node.setParent(this);
        this.children.add(node);
    }

    public XMLTreeNode removeLastChild() {
        return this.children.remove(children.size()-1);
    }

    public boolean hasChildren(){
        return !(this.children.isEmpty());
    }
    public boolean MatchingTag(int i){
        return (this.children.get(0).children.get(0).value.contains(this.children.get(1).children.get(0).value)&& this.children.get(i).children.size()==1);
    }
}
