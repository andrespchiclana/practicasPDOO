require_relative 'dice'
require_relative 'player'
require_relative 'monster'

module Irrgarten

  class Labyrinth
    BLOCK_CHAR = 'X'
    EMPTY_CHAR = '-'
    MONSTER_CHAR = 'M'
    COMBAT_CHAR = 'C'
    EXIT_CHAR = 'E'
    ROW = 0
    COL = 1

    #getters y setters(attr_writer)
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

      @n_rows.times do |i|
        @n_cols.times do |j|
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
      players.each do |player| #for-each de java, coleccion (array)
        pos = random_empty_pos
        put_player_2d(-1, -1, pos[ROW], pos[COL], player)
      end
    end

    def have_a_winner?
      !@players[@exit_row][@exit_col].nil?
    end

    def to_s
      result = ""
      @labyrinth.each do |row|
        result += row.join(' ') + "\n" #concatenar, crear arrays
      end
      result.chomp #elimar un salto de línea
    end

    def add_monster(row, col, monster)
      if pos_ok?(row, col) && empty_pos?(row, col)
        @monsters[row][col] = monster
        @labyrinth[row][col] = MONSTER_CHAR
        monster.set_pos(row, col)
      end
    end

    def put_player(direction, player)
      old_row = player.row
      old_col = player.col

      new_pos = dir_to_pos(old_row, old_col, direction)

      put_player_2d(old_row, old_col, new_pos[ROW], new_pos[COL], player)
    end

    def add_block(orientation, start_row, start_col, length)
      inc_row, inc_col = if orientation == Orientation::VERTICAL
                          [1, 0]
                        else
                          [0, 1]
                        end

      row = start_row
      col = start_col

      while pos_ok?(row, col) && empty_pos?(row, col) && length.positive? #.positive = >0
        @labyrinth[row][col] = BLOCK_CHAR
        length -= 1
        row += inc_row
        col += inc_col
      end
    end

    def valid_moves(row, col)
      directions = []
      directions << Directions::DOWN if can_step_on?(row + 1, col)
      directions << Directions::UP if can_step_on?(row - 1, col)
      directions << Directions::RIGHT if can_step_on?(row, col + 1)
      directions << Directions::LEFT if can_step_on?(row, col - 1)
      directions
    end

    private

    def pos_ok?(row, col)
      row.between?(0, @n_rows - 1) && col.between?(0, @n_cols - 1) #between (entre)
    end

    def empty_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == EMPTY_CHAR
    end

    def monster_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == MONSTER_CHAR
    end

    def exit_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == EXIT_CHAR
    end

    def combat_pos?(row, col)
      pos_ok?(row, col) && @labyrinth[row][col] == COMBAT_CHAR
    end

    def can_step_on?(row, col)
      pos_ok?(row, col) && (empty_pos?(row, col) || monster_pos?(row, col) || exit_pos?(row, col))
    end

    def update_old_pos(row, col)
      return unless pos_ok?(row, col)

      @labyrinth[row][col] = if combat_pos?(row, col)
                              MONSTER_CHAR
                            else
                              EMPTY_CHAR
                            end
    end

    def dir_to_pos(row, col, direction)
      case direction #switch
      when Directions::UP
        [row - 1, col]
      when Directions::DOWN
        [row + 1, col]
      when Directions::LEFT
        [row, col - 1]
      when Directions::RIGHT
        [row, col + 1]
      else
        [row, col]
      end
    end

    def random_empty_pos
      loop do #do-while
        row = Dice.random_pos(@n_rows)
        col = Dice.random_pos(@n_cols)
        return [row, col] if empty_pos?(row, col)
      end
    end

    def put_player_2d(old_row, old_col, row, col, player)
      return unless can_step_on?(row, col)

      if pos_ok?(old_row, old_col)
        p = @players[old_row][old_col]
        if p == player
          update_old_pos(old_row, old_col)
          @players[old_row][old_col] = nil
        end
      end

      if monster_pos?(row, col)
        @labyrinth[row][col] = COMBAT_CHAR
        output = @monsters[row][col]
      else
        @labyrinth[row][col] = player.number
        output = nil
      end

      @players[row][col] = player
      player.set_pos(row, col)

      output
    end
  end

end