package org.zeta.model;

public class Client extends User {
    Client(String id,String name,String password,String role){
        super(id,name,password, Role.CLIENT);
    }
}