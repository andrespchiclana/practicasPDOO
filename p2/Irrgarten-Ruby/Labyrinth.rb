require_relative 'Dice'
require_relative 'Player'
require_relative 'Monster'

class Labyrinth
  BLOCK_CHAR = 'X'
  EMPTY_CHAR = '-'
  MONSTER_CHAR = 'M'
  COMBAT_CHAR = 'C'
  EXIT_CHAR = 'E'
  ROW = 0
  COL = 1

  attr_accessor :n_rows, :n_cols, :exit_row, :exit_col, :monsters, :labyrinth, :players

  def initialize(n_rows, n_cols, exit_row, exit_col)
    @n_rows = n_rows
    @n_cols = n_cols
    @exit_row = exit_row
    @exit_col = exit_col

    initialize_labyrinth
  end

  def initialize_labyrinth
    @labyrinth = Array.new(@n_rows) { Array.new(@n_cols) }
    @monsters = Array.new(@n_rows) { Array.new(@n_cols) }
    @players = Array.new(@n_rows) { Array.new(@n_cols) }

    for i in 0...@n_rows
      for j in 0...@n_cols
        if i == @exit_row && j == @exit_col
          @labyrinth[i][j] = EXIT_CHAR
        elsif i == 0 || i == @n_rows - 1 || j == 0 || j == @n_cols - 1
          @labyrinth[i][j] = BLOCK_CHAR
        else
          @labyrinth[i][j] = EMPTY_CHAR
        end
      end
    end
  end

  def spread_players(players)
    raise NotImplementedError
  end

  def have_a_winner?
    @labyrinth[@exit_row][@exit_col] == EXIT_CHAR && !@players[@exit_row][@exit_col].nil?
  end

  def to_s
    @labyrinth.map { |row| row.join(' ') }.join("\n")
  end

  def add_monster(row, col, monster)
    if pos_ok?(row, col) && empty_pos?(row, col)
      @monsters[row][col] = monster
      @labyrinth[row][col] = MONSTER_CHAR
      monster.set_pos(row, col)
    end
  end

  def put_player(direction, player)
    raise NotImplementedError
  end

  def add_block(orientation, start_row, start_col, length)
    raise NotImplementedError
  end

  def valid_moves(row, col)
    raise NotImplementedError
  end

  private

  def pos_ok?(row, col)
    row >= 0 && row < @n_rows && col >= 0 && col < @n_cols
  end

  def empty_pos?(row, col)
    @labyrinth[row][col] == EMPTY_CHAR
  end

  def monster_pos?(row, col)
    @labyrinth[row][col] == MONSTER_CHAR
  end

  def exit_pos?(row, col)
    @labyrinth[row][col] == EXIT_CHAR
  end

  def combat_pos?(row, col)
    @labyrinth[row][col] == COMBAT_CHAR
  end

  def can_step_on?(row, col)
    pos_ok?(row, col) && (empty_pos?(row, col) || monster_pos?(row, col) || exit_pos?(row, col))
  end

  def update_old_pos(row, col)
    if pos_ok?(row, col)
      @labyrinth[row][col] = combat_pos?(row, col) ? MONSTER_CHAR : EMPTY_CHAR
    end
  end

  def dir2pos(row, col, direction)
    case direction
    when :up
      [row - 1, col]
    when :down
      [row + 1, col]
    when :left
      [row, col - 1]
    when :right
      [row, col + 1]
    else
      [row, col]
    end
  end

  def random_empty_pos
    loop do
      row = Dice.random_pos(@n_rows)
      col = Dice.random_pos(@n_cols)
      return [row, col] if empty_pos?(row, col)
    end
  end

  def put_player_2d(old_row, old_col, row, col, player)
    raise NotImplementedError
  end
end