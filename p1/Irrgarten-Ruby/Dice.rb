class Dice

  MAX_USES = 5
  MAX_INTELLIGENCE = 10.0
  MAX_STRENGTH = 10.0
  RESURRECTION_PROB = 0.3
  WEAPONS_REWARD = 2
  SHIELDS_REWARD = 3
  HEALTH_REWARD = 5
  MAX_ATTACK = 3
  MAX_SHIELD = 2

  def random_pos(max)
    rand(max)

  end 

  def who_starts(nplayer)
    rand(nplayer)

  end 

  def random_intelligence
    rand * MAX_INTELLIGENCE # también se puede hacer rand(0.0..MAX_INTELLIGENCE)

  end 

  def random_strength
    rand * MAX_STRENGTH # rand(0.0..MAX_STRENGTH)

  end 

  def resurrect_player
    rand < RESURRECTION_PROB

  end 

  def weapons_reward
    rand(WEAPONS_REWARD + 1) # +1 para que el rango sea [0, WEAPONS_REWARD], también se puede hacer rand(0..WEAPONS_REWARD)

  end 

  def shields_reward
    rand(SHIELDS_REWARD + 1)

  end

  def health_reward
    rand(HEALTH_REWARD + 1)

  end 

  def weapon_power
    rand * MAX_ATTACK # rand(0.0..MAX_ATTACK)

  end 

  def shield_power
    rand * MAX_SHIELD # rand(0.0..MAX_SHIELD)

  end 

  def uses_left
    rand(MAX_USES + 1)

  end 

  def intensity(competence)
    rand * competence # rand(0.0..competence)

  end 

  def self.discard_element(uses_left)
    return false if uses_left > MAX_USES

    rand < (1.0 - uses_left.to_f / MAX_USES)

  end 
  
end # cerrar clase Dice