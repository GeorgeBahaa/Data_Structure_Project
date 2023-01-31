package Data.Structure.and.Algorithms.Project;

import java.util.ArrayList;

public class Post {

    private String body;
    private Integer userID;
    private ArrayList <String> topics = new ArrayList<>();
    private ArrayList<String> words = new ArrayList<>();
    private HashTable hashTable = new HashTable();


    public Post(Integer userID, ArrayList<String> topics, String body) {
        this.userID = userID;
        this.topics = topics;
        this.body = body;
        words();
        for (int i = 0; i < words.size(); i++){
            hashTable.insert(words.get(i));
        }
    }

    public Post() {

    }

    public String getBody() {
        return body;
    }

    public int getUserID() {
        return userID;
    }

    public ArrayList <String> getTopics() {
        return topics;
    }

    public void words(){
        boolean dupFlag = false;

        body = body.toLowerCase();

        for(String word: body.split(" ")){


            for(int i = 0; i < words.size(); i++){
                if(words.get(i).equals(word.toLowerCase())){
                    dupFlag = true;
                }
            }
            if(!dupFlag){
                words.add(word.toLowerCase());
            }
            dupFlag = false;

        }

        for(int i = 0; i < words.size(); i++){

            if(words.get(i).contains(","))
                words.set(i, words.get(i).replace(",", ""));

            if(words.get(i).contains("."))
                words.set(i, words.get(i).replace(".", ""));

        }

        for(int i = 0; i < topics.size(); i++){

            words.add(topics.get(i));

        }

    }

    public void print(){
        System.out.println("post: ");
        System.out.println("user_id:"+ userID);
        System.out.println("body:"+ body);
        System.out.println("Topics:"+ topics);
    }

    public boolean contains(String word){
        return this.hashTable.contains(word);
    }

}
