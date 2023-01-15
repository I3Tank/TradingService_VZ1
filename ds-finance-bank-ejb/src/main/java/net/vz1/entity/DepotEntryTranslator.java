package net.vz1.entity;

import net.vz1.ejb.common.DepotEntryDTO;
import net.vz1.ejb.common.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;

public class DepotEntryTranslator {

    public DepotEntry toEntity(DepotEntryDTO depotEntryDTO) {
        if (depotEntryDTO == null) return null;
        return new DepotEntry(depotEntryDTO.getSymbol(), depotEntryDTO.getQuantity());
    }

    public DepotEntryDTO toDTO(DepotEntry depotEntry) {
        if (depotEntry == null) return null;
        return new DepotEntryDTO(depotEntry.getSymbol(), depotEntry.getQuantity(), depotEntry.getQuantity());
    }

    public List<DepotEntryDTO> depotEntryListToDTO(List<DepotEntry> depotEntries){
        var depotDTOEntries = new ArrayList<DepotEntryDTO>();
        for (DepotEntry depotEntry:depotEntries) {
            depotDTOEntries.add(new DepotEntryDTO(depotEntry.getSymbol(), depotEntry.getDepotID(), depotEntry.getQuantity()));
        }
        return depotDTOEntries;
    }
}
