/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import UI.GraphicalUI;
import controller.Controller;
import irrgarten.Game;

/**
 *
 * @author chpoz
 */
public class main {
    
    public static void main(String[] args) {
        
        Game juego = new Game(3);
        
        GraphicalUI vista = new GraphicalUI();
        
        Controller c = new Controller(juego, vista);
        
        c.play();
        
    }
    
}
