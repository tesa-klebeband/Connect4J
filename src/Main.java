

public class Main {
    public static void main(String[] args) {
        Connect4Gui guiGame = new Connect4Gui();
        guiGame.initGame();
        guiGame.initSettings();
        guiGame.gameLoop();
    }
}