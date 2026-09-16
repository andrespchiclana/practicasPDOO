package Irrgarten;

import java.util.ArrayList;

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
        
        this.currentPlayerIndex = Dice.whoStarts(nPlayers);
        
        log="";
        
        this.currentPlayer = players.get(currentPlayerIndex);
        
        this.players=new ArrayList<>(nPlayers);
        this.monsters=new ArrayList<>();
        
        for(int i=0; i<nPlayers; i++){
           Player player = new Player((char) ('P' + i), Dice.randomIntelligence(), Dice.randomStrength());
           players.add(player);
           
        }
        
        this.labyrinth = new Labyrinth(TAM, TAM, Dice.randomPos(TAM), Dice.randomPos(TAM));
        
        configureLabyrinth();
        labyrinth.spreadPlayers(players);
        
    }

    public boolean finished(){
        return labyrinth.haveAWinner();
    }

    public boolean nextStep(Directions preferredDirections){
       throw new UnsupportedOperationException();
    }

    public GameState getGameState(){
        return new GameState(labyrinth.toString(), players.toString(), monsters.toString(), currentPlayerIndex, finished(), log);
    }

    private void configureLabyrinth(){
        
        final int MAX_BLOCKS=10;
        final int MAX_MONSTERS=10;  
        
        for(int i=0; i<MAX_BLOCKS; i++){
            
            Orientation orientation = Dice.randomPos(2) == 0 ? Orientation.HORIZONTAL : Orientation.VERTICAL;
            int row = Dice.randomPos(TAM);
            int col = Dice.randomPos(TAM);
            int length = Dice.randomPos(2) == 0 ? 1 : 2;
            
            labyrinth.addBlock(orientation, row, col, length);
                    
        }
        
        for(int i=0; i<MAX_MONSTERS; i++){
            
            int row = Dice.randomPos(TAM);
            int col = Dice.randomPos(TAM);
            Monster monstruo = new Monster("monstruo " + row + "-" + col , Dice.randomIntelligence(), Dice.randomStrength());
            
            labyrinth.addMonster(row, col, monstruo);
            
        }
        
    }

    private void nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1) % players.size();
    }

    private void actualDirection(Directions preferredDirection){
        throw new UnsupportedOperationException();
    }

    private GameCharacter combat(Monster monster){
        throw new UnsupportedOperationException();
    }

    private void manageReward(GameCharacter winner){
        throw new UnsupportedOperationException();
    }

   private void manageResurrection(){
        throw new UnsupportedOperationException();
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
        log += "El jugador se ha movido a una celda vacía o no le ha sido posible moverse\n";
    } 

    private void logRounds(int rounds, int max){
        log += "Se han producido " + rounds + " de " + max + " max rondas de combate\n";
    }

    }
