import java.util.Scanner;

import static util.Util.validCoordinates;

public class GameProcess {
    private boolean gameIsOn;
    private boolean isPlayer;

    public GameProcess() {
        this.gameIsOn = true;
        this.isPlayer = Math.random() >= 0.5 ;

    }
    public boolean getGameIsOn() {
        return gameIsOn;
    }
    public boolean getIsPlayer() {
        return isPlayer;
    }

    public void playerTurn(GameField player1, GameField player2){ //
            while (checkCountShips(player1)) {
                System.out.printf("%s Выставь корабли заново!", player1.getName());
                player1.insertShips();
            }
            while (checkCountShips(player2)) {
                System.out.printf("%s Выставь корабли заново!", player2.getName());
                player2.insertShips();
            }
            System.out.println("Начнем игру!");
            while(gameIsOn){
                Scanner scanner = new Scanner(System.in);
                if(isPlayer){
                    System.out.printf("Ход игрока %s, введи координаты для удара (x,y): ", player1.getName());
                    String userInput = scanner.nextLine();
                    while(validCoordinates(userInput)){
                        System.out.println("Введи корректные координаты!");                     ////заключить этот часть кода в try/catch!!!
                        userInput = scanner.nextLine();
                    }
                    hitShip(userInput, player2, isPlayer);
                }else{
                    System.out.printf("Ход игрока %s, введи координаты для удара (x,y): ", player2.getName());
                    String userInput = scanner.nextLine();
                    while(validCoordinates(userInput)){
                        System.out.println("Введи корректные координаты!");
                        userInput = scanner.nextLine();
                    }
                    hitShip(userInput, player1, isPlayer);
                }
                if(player1.getPlayerShipsCount()==0){
                    System.out.printf("%s победил!", player2.getName());
                    gameIsOn = false;
                }else if(player2.getPlayerShipsCount()==0){
                    System.out.printf("%s победил!", player1.getName());
                    gameIsOn = false;
                }
            }
        }

    public static boolean checkCountShips(GameField player){//проверка установки всех кораблей на поле
        int countShips = 0;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if (player.getPlayerField()[i][j] == 1){
                    countShips++;
                }
            }
        }
        if (countShips == player.getPlayerShipsCount()){
            return false;
        }else{
            System.out.printf("На поле игрока %s установлены не все корабли!", player.getName());
            return true;
        }
    }
    public static boolean hitShip(String userInput, GameField player, boolean isPlayer){//удар по кораблю
        String[] xy = userInput.split(",");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        if(player.getPlayerField()[x][y]==1){
            System.out.println("Ранил!");
            player.getPlayerField()[x][y]=-2;
            player.setPlayerShipsCount(player.getPlayerShipsCount()-1);
        }else if(player.getPlayerField()[x][y]==-2){
            System.out.println("Ты уже попал сюда!");
        }else{
            System.out.println("Мимо!");
            return !isPlayer;
        }
        return isPlayer;
    }
}
