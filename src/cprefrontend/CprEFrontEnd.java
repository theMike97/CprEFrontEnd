/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mike
 */
public class CprEFrontEnd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Windows look and feel */
        /* If Windows (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        try {

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrameTemplate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.err.println("Could not load com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            System.err.println("Loading default metal");
        }

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            new Window().setVisible(true);
        });
    }

}
