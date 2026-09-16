require_relative '../game'
require_relative '../UI/textUI'
require_relative '../Control/controller'

game = Irrgarten::Game.new(3)
view = UI::TextUI.new
controller = Control::Controller.new(game, view)

controller.play