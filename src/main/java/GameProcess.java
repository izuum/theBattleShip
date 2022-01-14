import java.util.Scanner;
import static util.Util.validCoordinates;

public class GameProcess {
    private boolean gameIsOn;
    private boolean isPlayer;
    private int player1ShipCount;
    private int player2ShipCount;

    public GameProcess() {
        this.player1ShipCount = 4;
        this.player2ShipCount = 4;

        gameIsOn = true;
        isPlayer = Math.random() >= 0.5;

    }

    public void playerTurn(GameField player1, GameField player2){
            while (!checkCountShips(player1ShipCount, player1)) {
                System.out.printf("%s Выставь корабли заново!", player1.getName());
                player1.insertShips();
            }
            while (!checkCountShips(player2ShipCount, player2)) {
                System.out.printf("%s Выставь корабли заново!", player2.getName());
                player2.insertShips();
            }
            System.out.println("Начнем игру!");
            Scanner scanner = new Scanner(System.in);
            while(gameIsOn){
                if (isPlayer){
                    System.out.printf("Ход игрока %s, введи координаты для удара (x,y): ", player1.getName());
                    String userInput = scanner.nextLine();
                    while(validCoordinates(userInput)){
                        System.out.println("Введи корректные координаты!");
                        userInput = scanner.nextLine();
                    }
                    isPlayer = hitShip(isPlayer, userInput, player2);
                }
                if (!isPlayer){
                    System.out.printf("Ход игрока %s, введи координаты для удара (x,y): ", player2.getName());
                    String userInput = scanner.nextLine();
                    while(validCoordinates(userInput)){
                        System.out.println("Введи корректные координаты!");
                        userInput = scanner.nextLine();
                    }
                    isPlayer = hitShip(isPlayer, userInput, player1);
                }
                if(player1ShipCount==0){
                    System.out.printf("%s победил!", player2.getName());
                    gameIsOn = false;
                }else if(player2ShipCount==0){
                    System.out.printf("%s победил!", player1.getName());
                    gameIsOn = false;
                }
            }
        }

    private boolean checkCountShips(int playerShipCount, GameField player){//проверка установки всех кораблей на поле
        int countShips = 0;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if (player.getPlayerField()[i][j] == 1){
                    countShips++;
                }
            }
        }
        if (countShips == playerShipCount){
            return true;
        }else{
            System.out.printf("На поле игрока %s установлены не все корабли!", player.getName());
            return false;
        }
    }

    private boolean hitShip(boolean isPlayer, String userInput, GameField player){//удар по кораблю
        String[] xy = userInput.split(",");
        if(player.getPlayerField()[Integer.parseInt(xy[0])][Integer.parseInt(xy[1])]==1) {
            player.getPlayerField()[Integer.parseInt(xy[0])][Integer.parseInt(xy[1])] = -2;
            if(shipSank(player.getPlayerField(), xy)) {
                System.out.println("Утопил!");
                if (isPlayer) {
                    player2ShipCount--;
                } else {
                    player1ShipCount--;
                }
            }else{
                System.out.println("Ранил");
            }
        }else if(player.getPlayerField()[Integer.parseInt(xy[0])][Integer.parseInt(xy[1])]==-2) {
            System.out.println("Ты уже попал сюда! Ходи еще раз!");
        }else{
            System.out.println("Мимо!");
            isPlayer = !isPlayer;
        }
        return isPlayer;
    }
    private boolean shipSank(int[][] playerField, String[] hitCoordinates){
        int x = Integer.parseInt(hitCoordinates[0]);
        int y = Integer.parseInt(hitCoordinates[1]);
        while (playerField[x][y] != 0) {
            if(x > 0) {
                x -= 1;
                if (playerField[x][y] == 1) {
                    return false;
                }
            }
        }
        x = Integer.parseInt(hitCoordinates[0]);
        while (playerField[x][y] != 0) {
            if(x < 9) {
                x += 1;
                if (playerField[x][y] == 1) {
                    return false;
                }
            }
        }
        x = Integer.parseInt(hitCoordinates[0]);
        while (playerField[x][y] != 0) {
            if(y > 0){
                y -= 1;
                if (playerField[x][y] == 1) {
                    return false;
                }
            }
        }
        y = Integer.parseInt(hitCoordinates[0]);
        while (playerField[x][y] != 0) {
            if(y < 9) {
                y += 1;
                if (playerField[x][y] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
