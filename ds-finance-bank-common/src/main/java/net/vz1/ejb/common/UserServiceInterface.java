package net.vz1.ejb.common;

import javax.ejb.Remote;

@Remote
public interface UserServiceInterface {

    public boolean isEmployee();
}
