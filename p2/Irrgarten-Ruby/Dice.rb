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

  def self.discard_element(uses_left)
    return false if uses_left >= MAX_USES

    rand < (1.0 - uses_left.to_f / MAX_USES)
  end

  def self.intensity(competence)
    rand * competence
  end

  def self.random_pos(max)
    rand(max)
  end

  def self.who_starts(nplayers)
    rand(nplayers)
  end

  def self.random_intelligence
    rand * MAX_INTELLIGENCE
  end

  def self.random_strength
    rand * MAX_STRENGTH
  end

  def self.resurrect_player
    rand < RESURRECTION_PROB
  end

  def self.weapons_reward
    rand(WEAPONS_REWARD + 1)
  end

  def self.shields_reward
    rand(SHIELDS_REWARD + 1)
  end

  def self.health_reward
    rand(HEALTH_REWARD + 1)
  end

  def self.weapon_power
    rand * MAX_ATTACK
  end

  def self.shield_power
    rand * MAX_SHIELD
  end

  def self.uses_left
    rand(MAX_USES + 1)
  end

end