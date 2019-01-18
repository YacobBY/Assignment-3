package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class DoubleHashingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {

    Player[] doubleHashingList;

    int doubleHashCollisionCount=0;
//bestaat dit
    public int getDoubleHashCollisionCount() {
        return doubleHashCollisionCount;
    }

    public DoubleHashingMultiValueSymbolTable(int arraySize) {
        doubleHashingList = new Player[arraySize];
    }

    @Override
    public void put(String key, Player value) {
        int collisionCounter = 0;
        int index = doubleCounterKeyHash(key, collisionCounter);
//        System.out.println(key);

        while (!(doubleHashingList[index] == null)) {
            collisionCounter++;
            doubleHashCollisionCount++;
            index = doubleCounterKeyHash(key, collisionCounter);
        }
        doubleHashingList[index] = (value);
        System.out.println("double hash collision count: "+getDoubleHashCollisionCount());
    }


    @Override
    public List<Player> get(String key) {
        List<Player> returnList = new ArrayList<>();
//        System.out.println(key);
        int collisionCounter = 0;
        int index = doubleCounterKeyHash(key, collisionCounter);
        while (doubleHashingList[index] !=null && !(getFullName(doubleHashingList[index]).equals(key))) {

            System.out.println("doublehashing");
            collisionCounter++;
            doubleHashCollisionCount++;
            index = doubleCounterKeyHash(key, collisionCounter);
        }
        while (doubleHashingList[index] != null && (getFullName(doubleHashingList[index]).equals(key))) {
            System.out.println(getFullName(doubleHashingList[index]));
            System.out.println("adding target to list");
            returnList.add(doubleHashingList[index]);
            collisionCounter++;
            doubleHashCollisionCount++;
            index = doubleCounterKeyHash(key, collisionCounter);
            printAllNamesInArray(returnList);
//            System.out.println("came here");
        }
        return returnList;
    }
    public void printAllNamesInArray(List<Player> playerArray){
        for (int i = 0; i <playerArray.size() ; i++) {
            if (playerArray.get(i) == null){
                System.out.println("empty index " + i);
            }
            else System.out.println(playerArray.get(i).getLastName());
        }
    }

    public String getFullName(Player player) {
        return (player.getFirstName() + player.getLastName());

    }
    public int doubleCounterKeyHash(String key, int collisionCounter) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i)*461;
        }
        //Nadat collision is opgetreden is wordt dit uitgevoerd
        if (collisionCounter ==1){
            hash += collisionCounter*479;
        }
        //Nadat collision is opgetreden is wordt dit uitgevoerd
        if (collisionCounter >1 ) {
            hash += collisionCounter*673;
        }
        if (hash < 0) {
            hash = 0;
        }
        hash = hash % doubleHashingList.length;
        return hash;
    }



}