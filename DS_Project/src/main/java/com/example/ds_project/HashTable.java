package com.example.ds_project;

import java.util.ArrayList;

public class HashTable {



   private ArrayList<String>[] hashtable ;
   private static final int size = 500;

    public HashTable(){
        this.hashtable = new ArrayList[size];
    }

    long computeHash(String keyword){
        char[] word = keyword.toCharArray();
        int p = 31;
        long hash_value = 0;
        long p_pow = 1;
        for (char c : word) {
            hash_value = ((hash_value + (c - 'a' + 1) * p_pow) % size);
            p_pow = (p_pow * p) % size;
        }
        return hash_value;
    }

    void insert(String keyword){
        int key =  (int) computeHash(keyword);
        System.out.println(key);
        if(hashtable[key]==null){
            hashtable[key] = new ArrayList<String>();
        }
        hashtable[key].add(keyword);
    }

    boolean contains(String word){
        int key = (int) computeHash(word);
        if(hashtable[key]==null)
            return false;
        else
            return hashtable[key].contains(word);
    }

    void print(){
        for(int i = 0; i<size;i++){
            System.out.print("\t "+ i + ":\t"  );
            hashtable[i].forEach((s)-> System.out.print(s + "\t"));
            System.out.println();
        }
    }

    void sizePrint(){

        for(int i = 0; i < size; i++){
            if(hashtable[i]==null)
                continue;
            System.out.println("index: " + i + "size: " +  hashtable[i].size());

        }

    }

}
