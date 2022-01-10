

public class BattleShip {
    public static void main(String[] args) {
        GameField player1 = new GameField("Gosha");
        GameField player2 = new GameField("Igor");

        player1.insertShips();
        player2.insertShips();

        GameProcess game = new GameProcess();
        game.playerTurn(player1, player2);
    }
}
