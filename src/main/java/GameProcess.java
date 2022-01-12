import java.util.Scanner;
import static util.Util.validCoordinates;

public class GameProcess {
    private boolean gameIsOn;
    private boolean isPlayer;
    private int player1ShipCount;
    private int player2ShipCount;

    public GameProcess() {
        this.player1ShipCount = 2;
        this.player2ShipCount = 2;

        gameIsOn = true;
        isPlayer = Math.random() >= 0.5;

    }

    public void playerTurn(GameField player1, GameField player2){ //
            while (checkCountShips(player1ShipCount, player1)) {
                System.out.printf("%s Выставь корабли заново!", player1.getName());
                player1.insertShips();
            }
            while (checkCountShips(player2ShipCount, player2)) {
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
            return false;
        }else{
            System.out.printf("На поле игрока %s установлены не все корабли!", player.getName());
            return true;
        }
    }

    private boolean hitShip(boolean isPlayer, String userInput, GameField player){//удар по кораблю
        String[] xy = userInput.split(",");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        if(player.getPlayerField()[x][y]==1) {
            System.out.println("Ранил!");
            player.getPlayerField()[x][y] = -2;
            if (isPlayer) {
                player2ShipCount--;
            } else {
                player1ShipCount--;
            }
        }else if(player.getPlayerField()[x][y]==-2) {
            System.out.println("Ты уже попал сюда!");
        }else{
            System.out.println("Мимо!");
            isPlayer = !isPlayer;
        }
        return isPlayer;
    }
}
