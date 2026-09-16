package Irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;

    private Monster[][] monsters;
    private char[][] labyrinth;
    private Player[][] players;
    
    
    private void initializeLabyrinth() {
        labyrinth = new char[nRows][nCols];
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (i == exitRow && j == exitCol) {
                    labyrinth[i][j] = EXIT_CHAR;
                } else if (i == 0 || i == nRows - 1 || j == 0 || j == nCols - 1) {
                    labyrinth[i][j] = BLOCK_CHAR;
                } else {
                    labyrinth[i][j] = EMPTY_CHAR;
                }
            }
        }
    }

    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;

        initializeLabyrinth();
    }

    public void spreadPlayers(ArrayList<Player> players){
        throw new UnsupportedOperationException();
    }

    public boolean haveAWinner(){
        return (labyrinth[exitRow][exitCol] == EXIT_CHAR && players[exitRow][exitCol] != null);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<nRows; i++){
            for(int j=0; j<nCols; j++){
                sb.append(labyrinth[i][j]).append(' ');
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void addMonster(int row, int col, Monster monster){
        if(posOK(row, col) && emptyPos(row, col)){
            monsters[row][col] = monster;
            labyrinth[row][col] = MONSTER_CHAR;
            monster.setPos(row, col);
        }
    }

    public Monster putPlayer(Directions direction, Player player){
        throw new UnsupportedOperationException();
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        throw new UnsupportedOperationException();
    }

    public Directions[] validMoves(int row, int col){
        throw new UnsupportedOperationException();
    }

    private boolean posOK(int row, int col){
        return row >= 0 && row < nRows && col >= 0 && col < nCols;
    }

    private boolean emptyPos(int row, int col){
        return labyrinth[row][col] == EMPTY_CHAR;
    }

    private boolean monsterPos(int row, int col){
        return labyrinth[row][col] == MONSTER_CHAR;
    }

    private boolean exitPos(int row, int col){
        return labyrinth[row][col] == EXIT_CHAR;
    }

    private boolean combatPos(int row, int col){
        return labyrinth[row][col] == COMBAT_CHAR;
    }

    private boolean canStepOn(int row, int col){
        return posOK(row, col) && (emptyPos(row, col) || monsterPos(row, col) || exitPos(row, col));
    }

    private void updateOldPos(int row, int col){
        if(posOK(row, col)){
            if(combatPos(row, col)){
                labyrinth[row][col] = MONSTER_CHAR;
            } else{
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction){
        return switch (direction) {
            case UP -> new int[]{row-1, col};
            case DOWN -> new int[]{row+1, col};
            case LEFT -> new int[]{row, col-1};
            case RIGHT -> new int[]{row, col+1};
            default -> new int[]{row, col};
        };
    }

    private int[] randomEmptyPos(){
        int row = 0;
        int col = 0;

        do{
            row = Dice.randomPos(nRows);
            col = Dice.randomPos(nCols);
        } while(!emptyPos(row, col));

        return new int[]{row, col}; 
    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        throw new UnsupportedOperationException();
    }

   
}
