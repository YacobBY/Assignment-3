package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class aLinearProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    List<List<Player>> linearProbeList = new ArrayList<>();

    public aLinearProbingMultiValueSymbolTable(int arraySize) {
        for (int i = 0; i < arraySize; i++) {
            linearProbeList.add(new ArrayList<>());
        }
    }


    @Override
    //First name is key, gehele player object is value.
    public void put(String key, Player value) {
        int index = keyHash(key);
        while (!linearProbeList.get(index).isEmpty()) {
            index++;
            if (index >= linearProbeList.size()) {
                index = 0;
            }
        }
        linearProbeList.get(index).add(value);
    }

    @Override
    public List<Player> get(String key) {
        int index = keyHash(key);
        while (linearProbeList.get(index).get(0).getFirstName() != key) {
            index++;
            if (index >= linearProbeList.size()) {
                index = 0;
            }
        }
        return linearProbeList.get(keyHash(key));

    }

    public int keyHash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i);
        }
        hash = hash % linearProbeList.size();
        return hash;
    }
}
