import java.util.Arrays;
import java.util.Scanner;
import static util.Util.*;

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

    public void insertShips() throws InterruptedException {
        System.out.printf("Расставляем корабли на поле! Выставляет %s, второй игрок отвернись!", name);
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);

        System.out.println("Введите координаты четырехпалубного корабля. (x,y; x,y; x,y; x,y)");
        String userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 4, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты трехпалубного корабля. (x,y; x,y; x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 3, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты трехпалубного корабля. (x,y; x,y; x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 3, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты двухпалубного корабля. (x,y; x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 2, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты двухпалубного корабля. (x,y; x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 2, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты двухпалубного корабля. (x,y; x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 2, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты однопалубного корабля. (x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 1, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты однопалубного корабля. (x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 1, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты однопалубного корабля. (x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 1, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
        System.out.println("Введите координаты однопалубного корабля. (x,y)");
        userInput = scanner.nextLine();
        while(!arrangedShip(userInput, 1, playerField)){
            userInput = scanner.nextLine();
        }
        Thread.sleep(2000);
    }
    public boolean arrangedShip(String userInput, int sizeShip, int[][] playerField) throws InterruptedException {
        if(countCoordinates(userInput, sizeShip)){
            System.out.println("Неподходящее кол-во координат, введите повторно!");
            return false;
        }
        if(validCoordinates(userInput)){
            System.out.println("Некорректные координаты.Введенные " +
                    "координаты должны быть целочисленными, в диапазоне от 0 до 9.");
            return false;
        }
        if(validShip(userInput, sizeShip)){
            System.out.println("Неверные координаты для корабля. Корбль должен быть ровным, вертикальным или горизонтальным!");
            return false;
        }
        if(freeCoordinatesOnField(userInput, playerField)){
            System.out.println("Эти координаты заняты, введите другие координаты.");
            return false;
        }
        if(oreolOfShip(userInput, sizeShip, playerField)){
            System.out.println("Слишком близко к другому короблю. Введите другие координаты");
            return false;
        }
        setupShips(userInput, sizeShip, playerField);
        return true;
    }
}
