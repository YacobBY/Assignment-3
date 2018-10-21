package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class cDoubleHashingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    List<List<Player>> doubleHashingList = new ArrayList<>();

    public cDoubleHashingMultiValueSymbolTable(int arraySize) {
        for (int i = 0; i < arraySize; i++) {
            doubleHashingList.add(new ArrayList<>());
        }
    }

    @Override
    public void put(String key, Player value) {
        int counter = 0;
        int index = doubleCounterKeyHash(key, counter);
        while ((!doubleHashingList.get(index).isEmpty()) &&
                (doubleHashingList.get(index).get(0).getFirstName() + doubleHashingList.get(index).get(0).getLastName()) != key) {
            counter++;
            index = doubleCounterKeyHash(key, counter);
        }
        doubleHashingList.get(index).add(value);
    }


    @Override
    public List<Player> get(String key) {
        return null;
    }


    public int doubleCounterKeyHash(String key, int collisionCounter) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i);
        }
        //Nadat collision is opgetreden is wordt dit uitgevoerd
        if (collisionCounter != 0) {
            hash += (collisionCounter * collisionCounter);
        }
        hash = hash % doubleHashingList.size();
        return hash;
    }
}