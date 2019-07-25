/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hakimhassani97
 * 
 * un Profil est la representation de strategies ex: (S0,S1)...
 */
public class Profil {
    int sj1,sj2;
    public Profil(int s1,int s2){
        this.sj1=s1;this.sj2=s2;
    }
    @Override
    public boolean equals(Object o){
        Profil p=(Profil) o;
        return this.sj1==p.sj1 && this.sj2==p.sj2;
    }
    @Override
    public String toString(){
        return "(S"+sj1+",S"+sj2+")";
    }
}
