
module Control

  class Controller
    def initialize(game,view)
      @game = game
      @view = view
    end

    def play
      end_of_game = false
      while (!end_of_game)
        @view.show_game(@game.get_gamestate)
        direction = @view.next_move 
        end_of_game = @game.next_step(direction)
      end
      @view.show_game(@game.get_gamestate)
      puts "El ganador es el jugador "+@game.get_gamestate.current_player.to_s
    end
  end # class   
end # module  

