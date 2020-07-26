/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Asullom
 */
public class App {

    private static File archivo = new File("goldes_db.db");

    public static void main(String[] args) {
        // TODO code application logic here
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRÓ LA BASE DE DATOS! Revise que la DB este junto al programa, o comuníquese con su proveedor");
            System.exit(0);
        }
        new MainFrame().setVisible(true);
    }

}
