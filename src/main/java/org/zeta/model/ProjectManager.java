package org.zeta.model;

public class ProjectManager extends User {
    ProjectManager(String id,String name,String password,String role){
        super(id,name,password, Role.PROJECT_MANAGER);
    }
}