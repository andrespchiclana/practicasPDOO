require_relative 'weapon'
require_relative 'shield'
require_relative 'dice'

module Irrgarten

  class Player
    MAX_WEAPONS = 2
    MAX_SHIELDS = 3
    INITIAL_HEALTH = 10
    HITS2LOSE = 3

    attr_accessor :name, :number, :intelligence, :strength, :health, :row, :col, :consecutive_hits, :weapons, :shields

    def initialize(number, intelligence, strength)
      @name = "Player #{number}"
      @number = number
      @intelligence = intelligence
      @strength = strength
      @health = INITIAL_HEALTH
      @row = -1
      @col = -1
      @consecutive_hits = 0
      @weapons = []
      @shields = []
    end

    def resurrect
      @weapons.clear
      @shields.clear
      @health = INITIAL_HEALTH
      @consecutive_hits = 0
    end

    def get_row
      @row
    end

    def get_col
      @col
    end

    def get_number
      @number
    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    def dead?
      @health <= 0
    end

    def move(direction, valid_moves)
      return direction if valid_moves.empty? || valid_moves.include?(direction)

      valid_moves.first
    end

    def attack
      @strength + sum_weapons
    end

    def defend(received_attack)
      manage_hit(received_attack)
    end

    def receive_reward
      Dice.weapons_reward.times { receive_weapon(new_weapon) } #times->repetir el codigo
      Dice.shields_reward.times { receive_shield(new_shield) }
      @health += Dice.health_reward
    end

    def to_s
      "Player{name= #{@name}, intelligence= #{@intelligence}, strength= #{@strength}, health= #{@health}, row= #{@row}, col= #{@col}, consecutive_hits= #{@consecutive_hits}, weapons= #{@weapons}, shields= #{@shields}}\n"
    end

    private

    def receive_weapon(weapon)
      @weapons.reject!(&:discard) #reject!()->elimina los elementos que cumplen la condición
      @weapons << weapon if @weapons.size < MAX_WEAPONS
    end

    def receive_shield(shield)
      @shields.reject!(&:discard) #&->llama a discard en un bucle for-each. &->llamar al metodo
      @shields << shield if @shields.size < MAX_SHIELDS
    end

    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    def new_shield
      Shield.new(Dice.shield_power, Dice.uses_left)
    end

    def sum_weapons
      @weapons.sum(&:attack)
    end

    def sum_shields
      @shields.sum(&:protect)
    end

    def defensive_energy
      @intelligence + sum_shields
    end

    def manage_hit(received_attack)
      if defensive_energy < received_attack
        got_wounded
        inc_consecutive_hits
      else
        reset_hits
      end

      lose = @consecutive_hits == HITS2LOSE || dead?
      reset_hits if lose
      lose
    end

    def reset_hits
      @consecutive_hits = 0
    end

    def got_wounded
      @health -= 1 if @health > 0
    end

    def inc_consecutive_hits
      @consecutive_hits += 1
    end
  end

end