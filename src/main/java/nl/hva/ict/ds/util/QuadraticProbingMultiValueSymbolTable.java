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

        int counter = 0;
        int index = counterKeyHash(key, counter);
        while (!(quadraticProbleList[index] == null)) {
            counter++;
            quadraticCollisionCount++;
            index = counterKeyHash(key, counter);
        }
        quadraticProbleList[index] = (value);
        System.out.println("quadratic probe collision count: " + getQuadraticCollisionCount());
    }


    @Override
    public List<Player> get(String key) {
        List<Player> returnList = new ArrayList<>();
//        System.out.println(key);
        int counter = 0;
        int index = counterKeyHash(key, counter);
        while (quadraticProbleList[index].getLastName() != key) {
            counter++;
            index = counterKeyHash(key, counter);
        }
        returnList.add(quadraticProbleList[index]);
        return returnList;

    }

    public int counterKeyHash(String key, int collisionCounter) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i);
        }
        //Nadat collision is opgetreden is wordt dit uitgevoerd
        if (collisionCounter != 0) {
            System.out.println(collisionCounter);

            if (secondReset == 2) {
                System.out.println("test");
                hash += 3;
                secondReset = 0;
            }

            System.out.println(hash);
            hash += collisionCounter + (collisionCounter * collisionCounter);
        }
        if (hash < 0) {
            hash = 1;
        }
        hash = hash % quadraticProbleList.length;
        secondReset++;

        return hash;
    }

}
