package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Hrac;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;

public interface HracService {

    String getHeslo(String name) throws HracException;
    void addHrac(Hrac hrac);
    Hrac getHrac(String name);
}
