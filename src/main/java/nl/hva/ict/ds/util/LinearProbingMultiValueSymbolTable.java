package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class LinearProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    List<List<Player>> linearProbeList = new ArrayList<>();

    public int linearCollisionCount =0;

    public int getLinearCollisionCountt() {
        return this.linearCollisionCount;
    }

    public LinearProbingMultiValueSymbolTable(int arraySize) {
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
            linearCollisionCount++;
            if (index >= linearProbeList.size()) {
                index = 0;
            }
        }
        linearProbeList.get(index).add(value);
        System.out.println("linear probe collision count: "+getLinearCollisionCountt());
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
            hash = hash + key.charAt(i)*461;
        }
        hash = hash % linearProbeList.size();
        return hash;
    }
}
