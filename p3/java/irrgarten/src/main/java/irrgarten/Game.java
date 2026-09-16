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
public class Game {
    
    //cte que no viene en el diagrama, declarada para manejar con facilidad el tamaño del laberinto
    private static final int TAM=10;
    
    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;

    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;


    public Game(int nPlayers){
        
        this.players = new ArrayList<>(nPlayers); // Inicializar la lista de jugadores
        this.monsters = new ArrayList<>();

        this.currentPlayerIndex = Dice.whoStarts(nPlayers);
        this.log = null;

        for (int i = 0; i < nPlayers; i++) {
            Player player = new Player((char) (i + '0'), Dice.randomIntelligence(), Dice.randomStrength());
            players.add(i, player);
        }

        this.currentPlayer = players.get(currentPlayerIndex);
        
        
        //Inicializamos aleatoriamente la casilla de salida del laberinto, evitando que se genere en los bordes
        int exitRow = Dice.randomPos(TAM - 2 ) + 1;
        int exitCol = Dice.randomPos(TAM - 2) + 1;
        
        this.labyrinth = new Labyrinth(TAM, TAM, exitRow, exitCol);

        configureLabyrinth();
        labyrinth.spreadPlayers(players);

    }

    public boolean finished(){
        return labyrinth.haveAWinner();
    }

    public boolean nextStep(Directions preferredDirection){
        
        log="";
        
        boolean dead = currentPlayer.dead();
        
        if(!dead){
            Directions direction = actualDirection(preferredDirection);
            
            if(direction != preferredDirection){
                
                logPlayerNoOrders();
                
            }
            
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
            
            if(monster == null){
                
                logNoMonster();
                
            } else{
                
                GameCharacter winner = combat(monster);
                
                manageReward(winner);
                
            }
        } else{
            
            manageResurrection();
            
        }
        
        boolean endGame = finished();
        
        if(!endGame){
            nextPlayer();
        }
        
        return endGame;
        
    }

    public GameState getGameState(){
        return new GameState(labyrinth.toString(), players.toString(), monsters.toString(), currentPlayerIndex, finished(), log);
    }

    private void configureLabyrinth(){
        
        final int MAX_BLOCKS=7;
        final int MAX_MONSTERS=7;  
        
        for(int i=0; i<MAX_BLOCKS; i++){
            
            Orientation orientation = Dice.randomPos(2) == 0 ? Orientation.HORIZONTAL : Orientation.VERTICAL;
            
            //Ningún bloque puede colocarse en los bordes (ya hay bloques)
            int row = Dice.randomPos(TAM - 2) + 1;
            int col = Dice.randomPos(TAM - 2) + 1;
            int length = Dice.randomPos(2) == 0 ? 1 : 2;
            
            labyrinth.addBlock(orientation, row, col, length);
                    
        }
        
        for(int i=0; i<MAX_MONSTERS; i++){
            
            int row = Dice.randomPos(TAM);
            int col = Dice.randomPos(TAM);
            
            Monster monstruo = new Monster("monstruo " + row + "-" + col , Dice.randomIntelligence(), Dice.randomStrength());
            
            //Pasamos el estado de labyrinth a un String auxiliar
            String labyrinthString = labyrinth.toString();
            
            //Posición en el string de labyrinth donde queremos añadir un monstruo 
            int positionIndex = row * (TAM * 2 + 1) + col * 2;
            
            /*Comprobamos si la posición en la que intentamos añadir el monstruo esta vacía. Esto lo hacemos porque en el método addMonster de Labyrinth comprobamos
            si la posición está vacía, y en caso de no estarlo no añadimos el monstruo, pero para el ArrayList 'monsters' no tenemos esa opción
            */
            if(labyrinthString.charAt(positionIndex) == '-'){
                
                labyrinth.addMonster(row, col, monstruo);
                monsters.add(monstruo);
                
            }
                         
        }
        
    }

    private void nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    private Directions actualDirection(Directions preferredDirection){
        
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        
        ArrayList<Directions> validMoves = labyrinth.validMoves(currentRow, currentCol);
        
        Directions output = currentPlayer.move(preferredDirection, validMoves);
        
        return output;
        
    }

    private GameCharacter combat(Monster monster){
        
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        
        double playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while((!lose) && (rounds < MAX_ROUNDS)){
            
            winner = GameCharacter.MONSTER;
            rounds++;
            
            double monsterAttack = monster.attack();
            
            lose = currentPlayer.defend(monsterAttack);
            
            if(!lose){
                
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
                
            }
                
        }
        
        logRounds(rounds, MAX_ROUNDS);
        
        return winner;
        
    }

    private void manageReward(GameCharacter winner){
        
        if(winner == GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            
            logPlayerWon();
            
        } else{
            
            logMonsterWon();
            
        }
        
    }

   private void manageResurrection(){
        
       boolean resurrect = Dice.resurrectPlayer();
       
       if(resurrect){
           currentPlayer.resurrect();
           
           logResurrected();
           
       } else{
           
           logPlayerSkipTurn();
           
       }
       
    }


    private void logPlayerWon(){
        log += "El jugador ha ganado el combate\n";
    }

    private void logMonsterWon(){
        log += "El monstruo ha ganado el combate\n";
    } 

    private void logResurrected(){
        log += "El jugador ha resucitado\n";
    }

    private void logPlayerSkipTurn(){
        log += "El jugador ha perdido el turno por estar muerto\n";
    }

    private void logPlayerNoOrders(){
        log += "El jugador no ha seguido las instrucciones del jugador humano (no fue posible)\n";
    }

    private void logNoMonster(){
        log += "El jugador se ha movido a una celda vacia o no le ha sido posible moverse\n";
    } 

    private void logRounds(int rounds, int max){
        log += "Se han producido " + rounds + " de " + max + " max rondas de combate\n";
    }
    
}
