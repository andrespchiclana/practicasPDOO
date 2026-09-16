require_relative 'dice'

class Monster
  INITIAL_HEALTH = 5

  attr_accessor :name, :intelligence, :strength, :health, :row, :col

  def initialize(name, intelligence, strength)
    @name = name
    @intelligence = intelligence
    @strength = strength
    @health = INITIAL_HEALTH
    @row = -1
    @col = -1
  end

  def dead?
    @health <= 0
  end

  def attack
    Dice.intensity(@strength)
  end

  def defend(received_attack)
    raise NotImplementedError
  end

  def set_pos(row, col)
    @row = row
    @col = col
  end

  def to_s
    "Monster{name= #{@name}, intelligence= #{@intelligence}, strength= #{@strength}, health= #{@health}, row= #{@row}, col= #{@col}}\n"
  end

  def got_wounded
    @health -= 1 if @health > 0
  end
end