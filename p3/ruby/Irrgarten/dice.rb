module Irrgarten

  class Dice
    @@generator = Random.new

    MAX_USES = 5 unless const_defined?(:MAX_USES)
    MAX_INTELLIGENCE = 10.0 unless const_defined?(:MAX_INTELLIGENCE)
    MAX_STRENGTH = 10.0 unless const_defined?(:MAX_STRENGTH)
    RESURRECTION_PROB = 0.3 unless const_defined?(:RESURRECTION_PROB)
    WEAPONS_REWARD = 2 unless const_defined?(:WEAPONS_REWARD)
    SHIELDS_REWARD = 3 unless const_defined?(:SHIELDS_REWARD)
    HEALTH_REWARD = 5 unless const_defined?(:HEALTH_REWARD)
    MAX_ATTACK = 3 unless const_defined?(:MAX_ATTACK)
    MAX_SHIELD = 2 unless const_defined?(:MAX_SHIELD)

    def self.discard_element(uses_left)
      return false if uses_left >= MAX_USES

      @@generator.rand < (1.0 - uses_left.to_f / MAX_USES)
    end

    def self.intensity(competence)
      @@generator.rand * competence
    end

    def self.random_pos(max)
      @@generator.rand(max)
    end

    def self.who_starts(nplayers)
      @@generator.rand(nplayers)
    end

    def self.random_intelligence
      @@generator.rand * MAX_INTELLIGENCE
    end

    def self.random_strength
      @@generator.rand * MAX_STRENGTH
    end

    def self.resurrect_player
      @@generator.rand < RESURRECTION_PROB
    end

    def self.weapons_reward
      @@generator.rand(WEAPONS_REWARD + 1)
    end

    def self.shields_reward
      @@generator.rand(SHIELDS_REWARD + 1)
    end

    def self.health_reward
      @@generator.rand(HEALTH_REWARD + 1)
    end

    def self.weapon_power
      @@generator.rand * MAX_ATTACK
    end

    def self.shield_power
      @@generator.rand * MAX_SHIELD
    end

    def self.uses_left
      @@generator.rand(MAX_USES + 1)
    end
  end
end