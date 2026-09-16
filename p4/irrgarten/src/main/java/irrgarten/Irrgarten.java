/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package irrgarten;

import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;

/**
 *
 * @author chpoz
 */
public class Irrgarten {

    public static void main(String[] args) {
        
        Game juego = new Game(3);
        
        TextUI vista = new TextUI();
        
        Controller c = new Controller(juego, vista);
        
        c.play();
        
    }
}
