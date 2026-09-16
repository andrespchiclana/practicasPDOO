require_relative 'Weapon'
require_relative 'Shield'
require_relative 'GameState'
require_relative 'Dice'
require_relative 'Directions'
require_relative 'GameCharacter'
require_relative 'Orientation'

class Main
  def self.run
    # Weapon
    my_weapon = Weapon.new(7.0, 5)
    
    puts "attack: #{my_weapon.attack}"
    puts "to_string: #{my_weapon.to_s}"
    puts "discard: #{my_weapon.discard}"
    
    # Shield
    my_shield = Shield.new(5.0, 5)
    
    puts "protect: #{my_shield.protect}"
    puts "to_string: #{my_shield}"
    puts "discard: #{my_shield.discard}"
    
    # Dice
    my_dice = Dice.new
    
    puts "random_pos: #{my_dice.random_pos(10)}"
    puts "who_starts: #{my_dice.who_starts(5)}"
    puts "random_intelligence: #{my_dice.random_intelligence}"
    puts "random_strength: #{my_dice.random_strength}"
    puts "resurrect_player: #{my_dice.resurrect_player}"
    puts "weapons_reward: #{my_dice.weapons_reward}"
    puts "shields_reward: #{my_dice.shields_reward}"
    puts "health_reward: #{my_dice.health_reward}"
    puts "weapon_power: #{my_dice.weapon_power}"
    puts "shield_power: #{my_dice.shield_power}"
    puts "uses_left: #{my_dice.uses_left}"
    puts "intensity: #{my_dice.intensity(10.0)}"
    puts "discard_element: #{Dice.discard_element(4)}"
    
    # GameState
    my_game_state = GameState.new("miLaberinto", "misJugadores", "misMonstruos", 5, true, "miLog")
    puts "get_labyrinth: #{my_game_state.get_labyrinth}"
    puts "get_players: #{my_game_state.get_players}"
    puts "get_monsters: #{my_game_state.get_monsters}"
    puts "get_current_player: #{my_game_state.get_current_player}"
    puts "get_winner: #{my_game_state.get_winner}"
    puts "get_log: #{my_game_state.get_log}"

    # Directions
    puts "left: #{Directions::LEFT}"
    puts "right: #{Directions::RIGHT}"
    puts "up: #{Directions::UP}"
    puts "down: #{Directions::DOWN}"

    # GameCharacter
    puts "game_character: #{GameCharacter::PLAYER}"
    puts "game_character: #{GameCharacter::MONSTER}"

    # Orientation
    puts "orientation: #{Orientation::VERTICAL}"
    puts "orientation: #{Orientation::HORIZONTAL}"
  end
end

Main.run if __FILE__ == $0
