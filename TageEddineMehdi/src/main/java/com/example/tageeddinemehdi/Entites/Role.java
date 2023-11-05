package com.example.tageeddinemehdi.Entites;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Role
{

    private final IntegerProperty idRole;
    private final StringProperty roleName;

    public Role(IntegerProperty idRole, StringProperty roleName) {
        this.idRole = idRole;
        this.roleName = roleName;
    }

    public int getIdRole() {
        return idRole.get();
    }

    public IntegerProperty idRoleProperty() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole.set(idRole);
    }

    public String getRoleName() {
        return roleName.get();
    }

    public StringProperty roleNameProperty() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }
}
