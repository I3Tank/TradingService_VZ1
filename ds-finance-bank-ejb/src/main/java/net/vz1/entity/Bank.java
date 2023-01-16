package net.vz1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Bank")
public class Bank implements Serializable {
    @Id
    private int id = 1;

    private BigDecimal volume;

    public Bank(){}

    public Bank(BigDecimal volume){
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }
    public void setVolume(BigDecimal volume){
        this.volume = volume;
    }
}
