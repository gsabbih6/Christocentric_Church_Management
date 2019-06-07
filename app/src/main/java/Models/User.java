package Models;


public class User {


    private String jwt;

    private UserDTO user;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUserDTO(UserDTO user) {
        this.user = user;
    }
}