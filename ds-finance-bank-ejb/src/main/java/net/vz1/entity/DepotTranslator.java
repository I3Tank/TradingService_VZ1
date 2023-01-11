package net.vz1.entity;

import net.vz1.ejb.common.DepotDTO;

public class DepotTranslator {

    public Depot toEntity(DepotDTO depotDTO){
        if(depotDTO == null) return null;
        return new Depot(depotDTO.getOwnerID(), depotDTO.getDepotID(), depotDTO.getDepotEntries());
    }

    public DepotDTO toDTO(Depot depot){
        if(depot == null) return null;
        return new DepotDTO(depot.getOwnerID(), depot.getDepotID(), depot.getDepotEntries());
    }
}
