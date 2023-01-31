package Data.Structure.and.Algorithms.Project;

import java.util.ArrayList;
import java.util.Locale;

public class PostDatabase {

    private static ArrayList<Post> posts = new ArrayList<>();
    private static XMLTreeNode root;
    private int id;

    public PostDatabase(XMLTreeNode root){
        this.root = root;
        treeTraversal(root);
    }

    private void treeTraversal(XMLTreeNode node){

        switch (node.getValue()) {
            case "followers":
            case "name":
                return;

            case "users":
            case "user":
                for (int i = 0; i < node.getChildren().size(); i++) {
                    treeTraversal(node.getChildren().get(i));
                }
                break;

            case "id":
                id = Integer.parseInt(node.getChildren().get(0).getValue());
                break;

            case "posts":
                for (int i = 0; i < node.getChildren().size(); i++) {
                    XMLTreeNode post_node = node.getChildren().get(i);
                    String body = " ";
                    ArrayList <String> topics = new ArrayList<>();
                    for(int j = 0; j < 2; j++){
                        System.out.println();
                        if(post_node.getChildren().get(j).getValue().equals("body"))
                            body = post_node.getChildren().get(j).getChildren().get(0).getValue();
                        else if (post_node.getChildren().get(j).getValue().equals("topics")){
                            XMLTreeNode topics_node = post_node.getChildren().get(j);
                            for(int k=0; k<topics_node.getChildren().size();k++){
                                topics.add(topics_node.getChildren().get(k).getChildren().get(0).getValue());
                            }
                        }
                    }
                    posts.add(new Post(id, topics, body));

                }
                break;
        }
    }

    public void print(){
        for (int i=0;i< posts.size();i++){
            System.out.println("post "+i+": ");
            System.out.println("user_id:"+posts.get(i).getUserID());
            System.out.println("body:"+posts.get(i).getBody());
            System.out.println("Topics:"+posts.get(i).getTopics());
        }
    }

    public ArrayList<Post> search (String word){
        ArrayList<Post> result = new ArrayList<Post>();
        for (int i = 0; i< posts.size(); i++){
            if(posts.get(i).contains(word.toLowerCase()))
                result.add(posts.get(i));
        }
        return result;
    }


}
