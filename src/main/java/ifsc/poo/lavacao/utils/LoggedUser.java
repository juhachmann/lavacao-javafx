package ifsc.poo.lavacao.utils;

import java.time.LocalDateTime;

public class LoggedUser {

    private String name;
    private LocalDateTime loginDateTime;

    public LoggedUser(String name, LocalDateTime loginDateTime) {
        this.name = name;
        this.loginDateTime = loginDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

}
