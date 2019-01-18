package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class QuadraticProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    Player[] quadraticProbleList;
    int quadraticCollisionCount;
    int secondReset = 0;

    public QuadraticProbingMultiValueSymbolTable(int arraySize) {
        quadraticProbleList = new Player[arraySize];
    }

    public int getQuadraticCollisionCount() {
        return quadraticCollisionCount;
    }

    @Override
    public void put(String key, Player value) {

        int collisionCounter = 0;
        int index = counterKeyHash(key, collisionCounter);
        while (!(quadraticProbleList[index] == null)) {
            collisionCounter++;
            quadraticCollisionCount++;

            index = counterKeyHash(key, collisionCounter);
        }
        quadraticProbleList[index] = (value);
        System.out.println("quadratic probe collision count: " + getQuadraticCollisionCount());
    }


    @Override
    public List<Player> get(String key) {
        List<Player> returnList = new ArrayList<>();
//        System.out.println(key);
        int collisionCounter = 0;
        int index = counterKeyHash(key, collisionCounter);
        while (quadraticProbleList[index] !=null && !(quadraticProbleList[index].getLastName().equals(key))) {
            collisionCounter++;
            quadraticCollisionCount++;
            index = counterKeyHash(key, collisionCounter);
        }
        while (quadraticProbleList[index] != null && quadraticProbleList[index].getLastName().equals(key)) {
//            System.out.println("adding target to list");
            returnList.add(quadraticProbleList[index]);
            //Removes the index so it can't be reached again through a repeating loop
//            quadraticProbleList[index] = null;
            collisionCounter++;
            quadraticCollisionCount++;
            index = counterKeyHash(key, collisionCounter);
            printAllNamesInArray(returnList);
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

    public int counterKeyHash(String key, int collisionCounter) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i)*461;
        }
        //Nadat collision is opgetreden is wordt dit uitgevoerd
        if (collisionCounter != 0) {
//            System.out.println(collisionCounter);

            if (secondReset == 2) {
                System.out.println("test");
                hash += 3;
                secondReset = 0;
            }

//            System.out.println(hash);
            hash += collisionCounter + 3 + (collisionCounter * collisionCounter);
        }
        if (hash < 0) {
            hash = 1;
        }

        hash = hash % quadraticProbleList.length;

        return hash;
    }
}
