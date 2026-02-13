package org.zeta.model;

public class Builder extends User {
    public Builder(String id,String name,String password){
        super(id,name,password,Role.BUILDER);
    }
}
