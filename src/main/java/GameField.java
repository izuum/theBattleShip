import java.util.Arrays;

public class GameField {
    private String name;
    private int[][] playerField;

    //корабль = 1
    //ареол корабля = 0
    //пустая клетка = -1
    //подбитый корабль = -2

    public GameField(String name) {
        this.name = name;
        this.playerField = new int[10][10];
        for (int[] row: playerField){
            Arrays.fill(row, -1);
        }
    }

    public String getName() {
        return name;
    }

    public int[][] getPlayerField() {
        return playerField;
    }


}
