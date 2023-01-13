package net.vz1.service;

import net.vz1.ejb.common.UserServiceInterface;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jms.Session;
import javax.persistence.criteria.CriteriaBuilder;
import java.rmi.RemoteException;

@Stateless(name="UserService")
@PermitAll
public class UserServiceImpl implements UserServiceInterface {

    @Resource
    SessionContext sessionContext;

    @Override
    public boolean isEmployee() {
        return sessionContext.isCallerInRole("Employee");
    }
}
