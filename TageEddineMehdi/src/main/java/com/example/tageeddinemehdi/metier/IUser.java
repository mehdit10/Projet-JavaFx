package com.example.tageeddinemehdi.metier;

import com.example.tageeddinemehdi.Entites.User;

public interface IUser
{

    public void createUser(User user);
    public User readUser(String prenom);
    public void updateUser(User user);
    public void deleteUser(String prenom);
}
