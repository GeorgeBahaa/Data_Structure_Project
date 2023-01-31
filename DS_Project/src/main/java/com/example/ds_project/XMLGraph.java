package com.example.ds_project;

import java.util.ArrayList;

public class XMLGraph {

    private ArrayList<ArrayList<Integer>> xmlGraphNodes;
    private String id;


    static void addEdge(ArrayList<ArrayList<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public void constructGraph(XMLTreeNode xmlTreeNode) {
        xmlGraphNodes = new ArrayList<>();

        if (xmlTreeNode.getChildren().isEmpty()) {
            return;
        }

        if (xmlTreeNode.getValue() == "id") {
            id = xmlTreeNode.getChildren().get(0).getValue();
        }

        if (xmlTreeNode.getValue() == "followers") {
            for (int i = 0; i < xmlTreeNode.getChildren().size(); i++) {
                xmlGraphNodes.add(new ArrayList<Integer>());
                addEdge(xmlGraphNodes, Integer.parseInt(id), Integer.parseInt(xmlTreeNode.getChildren().get(0).getChildren().get(0).getValue()));
            }
            for (int i = 0; i < xmlTreeNode.getChildren().size(); i++) {
                constructGraph(xmlTreeNode.getChildren().get(i));

            }
        }
    }

    static void printAdjacencyList(ArrayList<ArrayList<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("Adjacency list of " + i);
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(adj.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

}
