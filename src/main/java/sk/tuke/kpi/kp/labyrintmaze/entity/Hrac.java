package sk.tuke.kpi.kp.labyrintmaze.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Hrac {
    @Id
    @GeneratedValue
    private int ident;
    private String name;
    private String heslo;
    private Date registeredOn;


    public Hrac(){}
    public Hrac(String name,String heslo,Date registeredOn){
        this.name =name;
        this.heslo=heslo;
        this.registeredOn=registeredOn;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeslo() {
        return heslo;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Override
    public String toString() {
        return "Hrac{" +
                "name='" + name + '\'' +
                ", heslo='" + heslo + '\'' +
                ", registeredOn=" + registeredOn +
                '}';
    }
}
