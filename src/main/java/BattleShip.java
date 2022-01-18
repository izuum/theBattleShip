import static util.Util.printCompletePlayerField;

public class BattleShip {
    public static void main(String[] args) throws InterruptedException {
        GameField player1 = new GameField("Gosha");
        GameField player2 = new GameField("Igor");

        player1.insertShips();
        printCompletePlayerField(player1.getPlayerField());
        player2.insertShips();
        printCompletePlayerField(player2.getPlayerField());

        GameProcess game = new GameProcess();
        game.playerTurn(player1, player2);
    }
}
