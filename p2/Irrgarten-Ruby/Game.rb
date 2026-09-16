require_relative 'Player'
require_relative 'Monster'
require_relative 'Labyrinth'

class Game
  TAM = 10
  MAX_ROUNDS = 10

  attr_reader :currentPlayerIndex, :log, :currentPlayer, :players, :monsters, :labyrinth

  def initialize(nPlayers)
    @currentPlayerIndex = Dice.who_starts(nPlayers)
    @log = ""
    @players = []
    @monsters = []

    nPlayers.times do |i|
      player = Player.new(('P'.ord + i).chr, Dice.random_intelligence, Dice.random_strength)
      @players << player
    end

    @currentPlayer = @players[@currentPlayerIndex]
    @labyrinth = Labyrinth.new(TAM, TAM, Dice.random_pos(TAM), Dice.random_pos(TAM))

    configure_labyrinth
    @labyrinth.spread_players(@players)
  end

  def finished?
    @labyrinth.have_a_winner?
  end

  def next_step(preferredDirections)
    raise NotImplementedError
  end

  def get_game_state
    GameState.new(@labyrinth.to_s, @players.to_s, @monsters.to_s, @currentPlayerIndex, finished?, @log)
  end

  private

  def configure_labyrinth
    MAX_BLOCKS = 10
    MAX_MONSTERS = 10

    MAX_BLOCKS.times do
      orientation = Dice.random_pos(2).zero? ? :horizontal : :vertical
      row = Dice.random_pos(TAM)
      col = Dice.random_pos(TAM)
      length = Dice.random_pos(2).zero? ? 1 : 2

      @labyrinth.add_block(orientation, row, col, length)
    end

    MAX_MONSTERS.times do
      row = Dice.random_pos(TAM)
      col = Dice.random_pos(TAM)
      monster = Monster.new("monstruo #{row}-#{col}", Dice.random_intelligence, Dice.random_strength)

      @labyrinth.add_monster(row, col, monster)
    end
  end

  def next_player
    @currentPlayerIndex = (@currentPlayerIndex + 1) % @players.size
  end

  def actual_direction(preferredDirection)
    raise NotImplementedError
  end

  def combat(monster)
    raise NotImplementedError
  end

  def manage_reward(winner)
    raise NotImplementedError
  end

  def manage_resurrection
    raise NotImplementedError
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