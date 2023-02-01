package com.example.ds_project;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class XMLGraph {

    public static ArrayList<ArrayList <Integer> >xmlGraphNodes ;
    private static ArrayList<ArrayList <Integer> > xmlGraphFriends ;
    public static ArrayList<Integer> mutualFriends = new ArrayList<Integer>();
    static String id;
    static  PriorityQueue<Pair<Integer, Integer> > pq = new PriorityQueue<>((a, b) -> b.getKey() - a.getKey());
    private static ArrayList<Integer> inputArray;
    public static int activ;
    public static int activfollow;
    public static String sug=" ";

    public static void constructGraph(XMLTreeNode xmlTreeNode){

        if(xmlTreeNode.getValue().equals("users")){
            xmlGraphNodes = new ArrayList<ArrayList<Integer> >(xmlTreeNode.getChildren().size());
            for (int i = 0; i < xmlTreeNode.getChildren().size()+1; i++){
                xmlGraphNodes.add(new ArrayList<Integer>());
            }
        }

        if (xmlTreeNode.getChildren().isEmpty()) {
            return;
        }

        if(xmlTreeNode.getValue().equals("id")) {
            id = xmlTreeNode.getChildren().get(0).getValue();
        }

        if(xmlTreeNode.getValue().equals("followers")) {
            pq.add(new Pair<> ( xmlTreeNode.getChildren().size(),Integer.parseInt(id.trim())));
            for (int i = 0; i < xmlTreeNode.getChildren().size(); i++) {

                addEdge(xmlGraphNodes,Integer.parseInt(id.trim()),Integer.parseInt(xmlTreeNode.getChildren().get(i).getChildren().get(0).getChildren().get(0).getValue().trim()));
            }
        }
        for (int i = 0; i < xmlTreeNode.getChildren().size(); i++) {
            constructGraph(xmlTreeNode.getChildren().get(i));
        }

    }

    public static void addEdge(ArrayList<ArrayList<Integer> > adj,int u, int v)
    {
        adj.get(u).add(v);
    }

    public static void printAdjacencyList()
    {
        for (int i = 0; i < xmlGraphNodes.size(); i++) {
            System.out.println("Adjacency list of " + i);
            for (int j = 0; j < xmlGraphNodes.get(i).size(); j++) {
                System.out.print(xmlGraphNodes.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public static void getMostActive(){
        xmlGraphFriends=new ArrayList<ArrayList<Integer> >(xmlGraphNodes.size());
        for (int i = 0; i < xmlGraphNodes.size(); i++){
            xmlGraphFriends.add(new ArrayList<Integer>());
        }
        int[] active = new int[xmlGraphNodes.size()];
        for(int i=1 ; i< xmlGraphNodes.size(); i++){
            for (int j=0 ; j< xmlGraphNodes.get(i).size() ; j++){

                if( i == xmlGraphNodes.get(i).get(j)){
                    continue;
                }
                else
                {    active[xmlGraphNodes.get(i).get(j)]++;
                    addEdge(xmlGraphFriends, xmlGraphNodes.get(i).get(j),i);

                }
            }
        }
        int max=0,index=0;
        for (int i=0 ; i<active.length ; i++){

            if(max<active[i]){
                max=active[i];
                index=i;
            }
        }
        activ = index;
        activfollow = max;
        System.out.println("The Most active user has ID "+index+" follow "+max+ " user");
    }


    public static void getInfluencer(){
        System.out.println("the Infuencer has ID "+pq.peek().getValue()+" and has "+ pq.peek().getKey()+" followers");
    }
    public static void suggestFollowers(){

        ArrayList<Integer> followrsOfFollowers = new ArrayList<>();
        ArrayList<Integer> suggestions = new ArrayList<>();

        for(int k=0 ; k< xmlGraphNodes.size() ; k++) {

            sug+="Suggestions for User ID "+k+'\n';
            int id=k;

            for(int i=0 ; i<xmlGraphNodes.get(id).size() ; i++) {
                followrsOfFollowers.addAll(xmlGraphNodes.get(xmlGraphNodes.get(id).get(i)));
            }
            inputArray=followrsOfFollowers;

            sortGivenArray();

            for (int i=1 ; i< followrsOfFollowers.size() ; i++){
                if(followrsOfFollowers.get(i-1)==followrsOfFollowers.get(i)){
                    followrsOfFollowers.remove(i);
                }
            }

            for(int i=0;i<followrsOfFollowers.size();i++){
                if(binarySearch(xmlGraphFriends.get(id),followrsOfFollowers.get(i))==0){
                    if(id==followrsOfFollowers.get(i)) continue;
                    else suggestions.add(followrsOfFollowers.get(i));
                }

            }
            sug+="Users "+suggestions+"\n";
            suggestions.clear();
        }

        System.out.println(sug);
    }

    public static void mutualfriend(int ID1,int ID2){
        for(int i=0;i<xmlGraphNodes.get(ID1).size();i++){
            if (binarySearch(xmlGraphNodes.get(ID2),xmlGraphNodes.get(ID1).get(i))==1)
                mutualFriends.add(xmlGraphNodes.get(ID1).get(i));
        }
        System.out.println(mutualFriends);
    }

    private static int binarySearch(ArrayList<Integer> arr, int x)
    {
        int left = 0, right = arr.size() - 1;
        while (left <= right)
        {
            int mid = left + (right - left) / 2;
            if (arr.get(mid) == x)
                return 1;
            if (arr.get(mid) < x)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return 0;
    }

    private static void merge(int startIndex,int midIndex,int endIndex){

        ArrayList<Integer> mergedSortedArray = new ArrayList<Integer>();

        int leftIndex = startIndex;
        int rightIndex = midIndex+1;

        while((leftIndex <= midIndex) && (rightIndex<=endIndex)){
            if(inputArray.get(leftIndex) <= inputArray.get(rightIndex))
                mergedSortedArray.add(inputArray.get(leftIndex++));
            else
                mergedSortedArray.add(inputArray.get(rightIndex++));
        }

        while(leftIndex<=midIndex)
            mergedSortedArray.add(inputArray.get(leftIndex++));

        while(rightIndex<=endIndex)
            mergedSortedArray.add(inputArray.get(rightIndex++));

        int i = 0;
        int j = startIndex;
        while(i<mergedSortedArray.size()){
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    public static void MergeSortAll(){
        for(int i = 0;i<xmlGraphNodes.size();i++){
            inputArray = xmlGraphNodes.get(i);
            sortGivenArray();
        }
    }

    private static void sortGivenArray(){
        MergeSort(0, inputArray.size()-1);
    }

    private static void MergeSort(int startIndex,int endIndex){
        if(startIndex<endIndex && (endIndex-startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            MergeSort(startIndex, mid);
            MergeSort(mid+1, endIndex);
            merge(startIndex,mid,endIndex);
        }
    }
}