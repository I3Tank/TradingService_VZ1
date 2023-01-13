package net.vz1.entity;

import net.vz1.ejb.common.DepotDTO;

import javax.inject.Inject;

public class DepotTranslator {
    @Inject
    CustomerDAO customerDAO;
    public Depot toEntity(DepotDTO depotDTO){
        if(depotDTO == null) return null;
        var customer = customerDAO.findById(depotDTO.getCustomerID());
        return new Depot(customer);
    }

    public DepotDTO toDTO(Depot depot){
        if(depot == null) return null;

        return new DepotDTO(depot.getDepotID(), depot.getCustomer().getCustomerID());
    }
}
