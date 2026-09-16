require_relative 'Dice'

class Weapon

  attr_accessor :power, :uses

  def initialize(power, uses)
    @power = power
    @uses = uses
  
  end #cerrar constructor

  def attack
    if @uses > 0
      @uses -= 1
      @power
    else
      0

    end #cerrar if

  end #cerrar funcion attack

  def to_s
    "W[#{@power}, #{@uses}]"
  
  end #cerrar funcion to_s

  def discard
    Dice.discard_element(@uses)

  end #cerrar funcion discard
  
end #cerrar clase