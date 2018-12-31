package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class QuadraticProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    List<List<Player>> quadraticProbleList = new ArrayList<>();
    int quadraticCollisionCount;
    int secondReset = 0;

    public QuadraticProbingMultiValueSymbolTable(int arraySize) {
        for (int i = 0; i < arraySize; i++) {
            quadraticProbleList.add(new ArrayList<>());
        }
    }

    public int getQuadraticCollisionCount() {
        return quadraticCollisionCount;
    }

    @Override
    public void put(String key, Player value) {

        int counter = 0;
        int index = counterKeyHash(key, counter);
        while (index >= 0 && (!quadraticProbleList.get(index).isEmpty())) {
            counter++;
            quadraticCollisionCount++;
            index = counterKeyHash(key, counter);
        }
        quadraticProbleList.get(index).add(value);
        System.out.println("quadratic probe collision count: " + getQuadraticCollisionCount());
    }


    @Override
    public List<Player> get(String key) {
//        System.out.println(key);
        int counter = 0;
        int index = counterKeyHash(key, counter);
        while (index > 0 && !quadraticProbleList.get(index).isEmpty()) {
            counter++;
            index = counterKeyHash(key, counter);
        }
        return quadraticProbleList.get(index);

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
            hash +=collisionCounter+ (collisionCounter * collisionCounter);
        }
        if (hash < 0) {
            hash = 1;
        }
        hash = hash % quadraticProbleList.size();
        secondReset++;

        return hash;
    }

}
