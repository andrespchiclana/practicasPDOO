require_relative 'player'
require_relative 'monster'
require_relative 'labyrinth'
require_relative 'dice'
require_relative 'game_state'
require_relative 'game_character'
require_relative 'orientation'

module Irrgarten
  
  class Game
    TAM = 10
    MAX_ROUNDS = 10
    MAX_BLOCKS = 7
    MAX_MONSTERS = 7

    #getters
    attr_reader :current_player_index, :log, :current_player, :players, :monsters, :labyrinth

    def initialize(n_players)
      @players = Array.new(n_players)
      @monsters = []

      @current_player_index = Dice.who_starts(n_players)
      @log = ""

      #bucle for-each de java, para un numero de elementos
      n_players.times do |i|
        player = Player.new(i.to_s, Dice.random_intelligence, Dice.random_strength)
        @players[i] = player
      end

      @current_player = @players[@current_player_index]

      exit_row = Dice.random_pos(TAM - 2) + 1
      exit_col = Dice.random_pos(TAM - 2) + 1

      @labyrinth = Labyrinth.new(TAM, TAM, exit_row, exit_col)

      configure_labyrinth
      @labyrinth.spread_players(@players)
    end

    def finished?
      @labyrinth.have_a_winner?
    end

    def next_step(preferred_direction)
      @log = ""

      if !@current_player.dead?
        direction = actual_direction(preferred_direction)

        log_player_no_orders if direction != preferred_direction

        monster = @labyrinth.put_player(direction, @current_player)

        if monster.nil? #null
          log_no_monster
        else
          winner = combat(monster)
          manage_reward(winner)
        end
      else
        manage_resurrection
      end

      end_game = finished?
      next_player unless end_game

      end_game
    end

    def get_gamestate
      GameState.new(@labyrinth.to_s, @players, @monsters, @current_player_index, finished?, @log)
    end

    private

    def configure_labyrinth
      MAX_BLOCKS.times do
        orientation = Dice.random_pos(2).zero? ? Orientation::HORIZONTAL : Orientation::VERTICAL #==0 (java) .zero? (ruby)
        row = Dice.random_pos(TAM - 2) + 1
        col = Dice.random_pos(TAM - 2) + 1
        length = Dice.random_pos(2).zero? ? 1 : 2
        @labyrinth.add_block(orientation, row, col, length)
      end
    
      MAX_MONSTERS.times do
        row = Dice.random_pos(TAM)
        col = Dice.random_pos(TAM)
        monster = Monster.new("monstruo #{row}-#{col}", Dice.random_intelligence, Dice.random_strength)
    
        if @labyrinth.send(:empty_pos?, row, col) #llamar a un metodo en tiempo de ejecucion, permite llamar a metodos privados
          @labyrinth.add_monster(row, col, monster)
          @monsters << monster #añadir a un array <<
        end
      end
    end

    def next_player
      @current_player_index = (@current_player_index + 1) % @players.size
      @current_player = @players[@current_player_index]
    end

    def actual_direction(preferred_direction)
      current_row = @current_player.row
      current_col = @current_player.col
      valid_moves = @labyrinth.valid_moves(current_row, current_col)
      @current_player.move(preferred_direction, valid_moves)
    end

    def combat(monster)
      rounds = 0
      winner = GameCharacter::PLAYER

      player_attack = @current_player.attack
      lose = monster.defend(player_attack)

      while !lose && rounds < MAX_ROUNDS
        winner = GameCharacter::MONSTER
        rounds += 1

        monster_attack = monster.attack
        lose = @current_player.defend(monster_attack)

        unless lose #unless == if !lose
          player_attack = @current_player.attack
          winner = GameCharacter::PLAYER
          lose = monster.defend(player_attack)
        end
      end

      log_rounds(rounds, MAX_ROUNDS)
      winner
    end

    def manage_reward(winner)
      if winner == GameCharacter::PLAYER
        @current_player.receive_reward
        log_player_won
      else
        log_monster_won
      end
    end

    def manage_resurrection
      if Dice.resurrect_player
        @current_player.resurrect
        log_resurrected
      else
        log_player_skip_turn
      end
    end

    def log_player_won
      @log += "El jugador ha ganado el combate\n"
    end

    def log_monster_won
      @log += "El monstruo ha ganado el combate\n"
    end

    def log_resurrected
      @log += "El jugador ha resucitado\n"
    end

    def log_player_skip_turn
      @log += "El jugador ha perdido el turno por estar muerto\n"
    end

    def log_player_no_orders
      @log += "El jugador no ha seguido las instrucciones del jugador humano (no fue posible)\n"
    end

    def log_no_monster
      @log += "El jugador se ha movido a una celda vacía o no le ha sido posible moverse\n"
    end

    def log_rounds(rounds, max)
      @log += "Se han producido #{rounds} de #{max} max rondas de combate\n"
    end
  end

end
