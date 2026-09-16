/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author chpoz
 */
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
    
    
    //método privado para inicializar el laberinto
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
        
        for(int i=0; i<players.size(); i++){
            Player p = players.get(i);
            int[] pos = this.randomEmptyPos();
            this.putPlayer2D(-1, -1, pos[ROW], pos[COL], p); 
        }
        
    }

    public boolean haveAWinner(){
        return (players[exitRow][exitCol] != null);
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
            
            this.monsters[row][col] = monster;
            labyrinth[row][col] = MONSTER_CHAR;
            monster.setPos(row, col);
        }
    }

    public Monster putPlayer(Directions direction, Player player){
        
        int oldRow = player.getRow(); 
        int oldCol = player.getCol();
        
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
        
        return monster;
        
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        
        int incRow, incCol;
        
        if(orientation == Orientation.VERTICAL){
            incRow=1;
            incCol=0;
        } else{
            incRow=0;
            incCol=1;
        }
        
        int row = startRow;
        int col = startCol;
        
        while(posOK(row, col) && (emptyPos(row, col) && length > 0)){
            labyrinth[row][col]=BLOCK_CHAR;
            
            length-=1;
            row+=incRow;
            col+=incCol;
        }
        
        
    }

    public ArrayList<Directions> validMoves(int row, int col){
        
        ArrayList<Directions> output = new ArrayList<>();
        
        if(canStepOn(row+1, col)){
            output.add(Directions.DOWN);
        }
        
        if(canStepOn(row-1, col)){
            output.add(Directions.UP);
        }
        
        if(canStepOn(row, col+1)){
            output.add(Directions.RIGHT);
        }
        
        if(canStepOn(row, col-1)){
            output.add(Directions.LEFT);
        }
        
        return output;
        
      
    }

    private boolean posOK(int row, int col){
        return ((row >= 0 && row < nRows) && (col >= 0 && col < nCols));
    }

    private boolean emptyPos(int row, int col){
        return posOK(row, col) && labyrinth[row][col] == EMPTY_CHAR;
    }

    private boolean monsterPos(int row, int col){
        return posOK(row, col) && labyrinth[row][col] == MONSTER_CHAR;
    }

    private boolean exitPos(int row, int col){
        return posOK(row, col) && labyrinth[row][col] == EXIT_CHAR;
    }

    private boolean combatPos(int row, int col){
        return posOK(row, col) && labyrinth[row][col] == COMBAT_CHAR;
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
        
        Monster output=null;
        
        if(canStepOn(row, col)){
            if(posOK(oldRow, oldCol)){
                Player p = players[oldRow][oldCol];
                
                if(p==player){
                    updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            
            }
            
            boolean monsterPos = this.monsterPos(row, col);
            
            if(monsterPos){
                
                labyrinth[row][col] = COMBAT_CHAR;
                
                output = monsters[row][col];
            } else{
                
                char number = player.getNumber();
                
                labyrinth[row][col] = number; 
            }
            
            players[row][col] = player;
            
            player.setPos(row, col);
        }
        
        return output;
        
    }
    
    
}
