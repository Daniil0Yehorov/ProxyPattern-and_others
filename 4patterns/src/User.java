public class User {
    private int Id;
    private String login;
    private String password;
    private int roleKey;

    public User(int Id, String login, String password, int roleKey) {
        this.Id = Id;
        this.login = login;
        this.password = password;
        this.roleKey = roleKey;
    }



    public int getUserId() {
        return Id;
    }

    public void setUserId(int userId) {
        this.Id = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(int roleKey) {
        this.roleKey = roleKey;
    }
}