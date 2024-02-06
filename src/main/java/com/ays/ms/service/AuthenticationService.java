package com.ays.ms.service;

import com.ays.ms.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class AuthenticationService {

    private final String IS_LOGGED = "is_logged";
    private final String USER_EMAIL = "email";
    private final String USER_ID = "user_id";
    private final String IS_ADMIN = "is_admin";

    public void login(User user) {
        HttpSession session = this.getSession();

        session.setAttribute(IS_LOGGED, Boolean.TRUE);
        session.setAttribute(USER_EMAIL, user.getEmail());
        session.setAttribute(USER_ID, user.getId());
        session.setAttribute(IS_ADMIN, user.isAdmin());
    }

    public String getEmailLoginUser() {
        return String.valueOf(this.getSession().getAttribute(USER_EMAIL));
    }

    public HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    private void destroySession() {
        this.getSession().invalidate();
    }

    public Boolean isLogged() {
        Boolean isLogged = (Boolean) this.getSession().getAttribute(IS_LOGGED);
        return isLogged != null ? isLogged : Boolean.FALSE;
    }

    public Boolean isAdmin() {
        Boolean isAdmin = (Boolean) this.getSession().getAttribute(IS_ADMIN);
        return isAdmin != null ? isAdmin : Boolean.FALSE;
    }

    public long getIdLoginUser() {
        return (long) this.getSession().getAttribute(USER_ID);
    }

    public void logout() {
        this.destroySession();
    }

}
