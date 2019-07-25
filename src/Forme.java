
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hakimhassani97
 * 
 * une Form de jeu est le nombre des joueurs
 */
public class Forme {
    static int nbJoueurs=2;
    static int cpt=0;
    int id=0;
    static boolean sommeNulle=false;
    float[] paiements;
    public Forme(){
        this.paiements=new float[Forme.nbJoueurs];
        this.id=cpt++;
    }
    public void init(){
        if(!sommeNulle)
            for(int i=0;i<nbJoueurs;i++){
                paiements[i]=(float) (int)(Math.random()*50-10);
            }
        else{
            paiements[0]=(float) (int)(Math.random()*50-25);
            paiements[1]=-paiements[0];
        }
    }
    public int plusGrand(Forme f){
        if(this.paiements[0]>f.paiements[0] && this.paiements[1]>f.paiements[1]) return 1;
        if(this.paiements[0]<f.paiements[0] || this.paiements[1]<f.paiements[1]) return -1;
        return 0;
    }
    public int compareTo(Forme f,int joueur){
        if(this.paiements[joueur]>f.paiements[joueur]) return 1;
        if(this.paiements[joueur]<f.paiements[joueur]) return -1;
        return 0;
    }
    @Override
    public String toString(){
        String s="";
        for(int i=0;i<nbJoueurs-1;i++) {
            //s+=paiements[i]+",";
            s+=String.format("%3.0f, ",paiements[i]);
        }
        //s+=paiements[nbJoueurs-1];
        s+=String.format("%3.0f ",paiements[nbJoueurs-1]);
        return "("+s+")";
    }
}
