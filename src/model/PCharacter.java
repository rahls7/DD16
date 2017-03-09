package model;

import java.util.ArrayList;
import java.util.List;

public class PCharacter extends PCellContent{
    private String id;
    private String name;
    private List<PItem> equipment;
    private List<PItem> backpack;
    public int[][] basicStats;
    public int[] basicAttributes;
    private int[][] stats;
    private int[] attributes;

    public PCharacter(String id, String isHostile) {
        super();
    }
}
