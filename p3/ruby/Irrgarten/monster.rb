require_relative 'dice'

module Irrgarten

  class Monster
    INITIAL_HEALTH = 5

    attr_accessor :name, :intelligence, :strength, :health, :row, :col

    def initialize(name, intelligence, strength)
      @name = name
      @intelligence = intelligence
      @strength = strength
      @health = INITIAL_HEALTH
      @row = nil
      @col = nil
    end

    def dead?
      @health <= 0
    end

    def attack
      Dice.intensity(@strength)
    end

    def defend(received_attack)
      return true if dead?

      defensive_energy = Dice.intensity(@intelligence)
      if defensive_energy < received_attack
        got_wounded
      end

      dead?
    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    def to_s
      "Monster{name= #{@name}, intelligence= #{@intelligence}, strength= #{@strength}, health= #{@health}, row= #{@row}, col= #{@col}}\n"
    end

    private

    def got_wounded
      @health -= 1 if @health > 0
    end
  end

end