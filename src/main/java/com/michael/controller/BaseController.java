package com.michael.controller;

import com.michael.model.User;

import javax.servlet.http.HttpSession;

public abstract class BaseController {

    public User authUser(HttpSession session) {

        return (User) session.getAttribute("user_session");

    }
}
