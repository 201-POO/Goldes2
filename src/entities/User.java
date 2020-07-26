package entities;

import util.PasswordED;

/**
 *
 * @author Asullom
 */
public class User {

    private int id;
    private int rol;
    private String pin;
    private String username;
    
    private String salt;
    //user.setClave(String.valueOf(this.txtPassword.getPassword())); 
    //user.encriptarPass();
    public void encriptarPass()
    {
        StringBuilder sb = new StringBuilder(this.id+this.username.length()
                +this.rol);
        this.salt = sb.reverse().append(this.rol).toString();
        PasswordED ep = new PasswordED(this.salt);
        this.pin = ep.encrypt(this.pin);        
    }
    
    public String desencriptarPass()
    {
        PasswordED ep = new PasswordED(this.salt);           
        String desEncrypted  = ep.decrypt(this.pin);
        return  desEncrypted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }



   
}
