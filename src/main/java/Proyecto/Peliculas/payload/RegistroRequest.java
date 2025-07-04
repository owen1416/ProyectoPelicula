package Proyecto.Peliculas.payload;

public class RegistroRequest {

    private String username;
    private String password;
    private String email;
    private String rol; // Opcional: para permitir especificar el rol (ej. "ADMIN", "USER")

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
