
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author hakimhassani97
 * 
 * un Jeu est l'ensemble des strategies et des joueurs
 */
public class Jeu {
    ArrayList<ArrayList<Forme>> mat;
    int nbs1,nbs2;
    String[] s1,s2;
    public Jeu(int nb1,int nb2){
        nbs1=nb1;nbs2=nb2;
        mat=new ArrayList<>();
        this.init();
    }
    public void init(){//initialise la matrice avec des elements random
        for(int i=0;i<nbs1;i++){
            //ajouter une ligne pour le J1
            mat.add(new ArrayList<>());
            for(int j=0;j<nbs2;j++){
                //ajouter une colonne pour le J2
                Forme f=new Forme();
                f.init();
                mat.get(i).add(f);
            }
        }
    }
    public void getExample(int example){
        if(example==-1){
            Scanner sc=new Scanner(System.in);
            for(int i=0;i<mat.size();i++){
                for(int k=0;k<mat.get(0).size();k++){
                    System.out.print("J1(S"+i+",S"+k+") : ");
                    mat.get(i).get(k).paiements[0]=sc.nextInt();
                    System.out.print("J2(S"+i+",S"+k+") : ");
                    mat.get(i).get(k).paiements[1]=sc.nextInt();
                }
            }
        }
        if(example==0){
            mat.get(0).get(0).paiements[0]=-2;mat.get(0).get(0).paiements[1]=-2;
            mat.get(0).get(1).paiements[0]=-5;mat.get(0).get(1).paiements[1]=0;
            mat.get(1).get(0).paiements[0]=0;mat.get(1).get(0).paiements[1]=-5;
            mat.get(1).get(1).paiements[0]=-4;mat.get(1).get(1).paiements[1]=-4;
        }
        if(example==1){
            mat.get(0).get(0).paiements[0]=-1;mat.get(0).get(0).paiements[1]=1;
            mat.get(0).get(1).paiements[0]=6;mat.get(0).get(1).paiements[1]=3;
            mat.get(1).get(0).paiements[0]=5;mat.get(1).get(0).paiements[1]=2;
            mat.get(1).get(1).paiements[0]=1;mat.get(1).get(1).paiements[1]=1;
        }
    }
    void supprimerLigne(int l){//supprime la ligne l de la matrice
        mat.remove(l);
    }
    void supprimerColonne(int c){//supprime la colonne c de la matrice
        for(int i=0;i<nbs1;i++){
            if(i==c) mat.get(i).remove(c);
        }
    }
    public int domine(int s1,int s2,int jj){//comparer 2 strategies d'un joueur
        if(jj==1){
            for(int j=0;j<nbs2;j++){
                if(mat.get(s1).get(j).paiements[0]<mat.get(s2).get(j).paiements[0])
                    return -1;
                else if(mat.get(s1).get(j).paiements[0]==mat.get(s2).get(j).paiements[0])
                    return 0;
            }
        }else{
            for(int i=0;i<nbs1;i++){
                if(mat.get(i).get(s1).paiements[1]<mat.get(i).get(s2).paiements[1])
                    return -1;
                else if(mat.get(i).get(s1).paiements[1]==mat.get(i).get(s2).paiements[1])
                    return 0;
            }
        }
        return 1;
    }
    public ArrayList<String> sousEns(int k){//liste des sous ens de taaille k
        int m=mat.size(),n=mat.get(0).size();
        ArrayList<String> resl=new ArrayList<>();
        if(m<n) n=m;
        for(int j=1;j<Math.pow(2, n);j++){
            String s=Integer.toBinaryString(j);
            int nb1=0;
            for(int i=0;i<s.length();i++) if(s.charAt(i)=='1') nb1++;
            if(s.length()<n){
                String ss="";
                for(int ii=0;ii<n-s.length();ii++) ss+="0";
                s=ss+s;
            }
            if(nb1==k) resl.add(s);
        }
        return resl;
    }
    public void symplexe(){
        int m=mat.size(),n=mat.get(0).size();
        for(int k=1;k<Math.min(m,n);k++){
            ArrayList<String> sousens=sousEns(k);
            for(String s:sousens){
                double[][] matrice=new double[k][k];
                double[][] constants=new double[k][1];
                int i=0;
                while(i<s.length()){////////////////////
                    char c=s.charAt(i);
                    if(c=='1')
                        for(int ii=0;ii<k;ii++){
                            matrice[ii][ii]=mat.get(ii).get(i).paiements[1];
                        }
                    i++;
                }
                afficheMat(matrice);
                Linear lin=new Linear(k, matrice, constants);
                System.out.println("d****************************");
                System.out.println("sol: ");
                afficheMat(lin.solve());
                System.out.println("f****************************");
            }
        }
    }
    public void afficheMat(double[][] mat){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.printf(" %3.0f",mat[i][j]);
            }System.out.println("\n");
        }System.out.println("______________________________");
    }
    public void afficherDominances(){
        System.out.println("dominances joueur 1:");
        for(int i=0;i<nbs1;i++){
            for(int j=0;j<nbs1;j++){
                if(i!=j && domine(i,j,1)==1)System.out.println("\tS"+i+" domine fortement S"+j+" ");
                if(i!=j && domine(i,j,1)==0)System.out.println("\tS"+i+" domine faiblement S"+j+" ");
                //if(i!=j && domine(i,j,1)==-1)System.out.println("\tS"+i+" dominée par S"+j+" ");
            }
        }
        System.out.println("dominances joueur 2:");
        for(int i=0;i<nbs2;i++){
            for(int j=0;j<nbs2;j++){
                if(i!=j && domine(i,j,2)==1)System.out.println("\tS"+i+" domine fortement S"+j+" ");
                if(i!=j && domine(i,j,2)==0)System.out.println("\tS"+i+" domine faiblement S"+j+" ");
                //if(i!=j && domine(i,j,2)==-1)System.out.println("\tS"+i+" dominée par S"+j+" ");
            }
        }
    }
    public ArrayList<Integer> meilleurChoix(int jj,int s){//le meilleur choix du joueur j en fixant la strategie s
        int strategieMax=0;
        ArrayList<Integer> smax=new ArrayList<>();
        float paiementMax;
        if(jj==1){
            paiementMax=mat.get(0).get(s).paiements[0];
            for(int i=0;i<nbs1;i++){
                Forme p=mat.get(i).get(s);
                if(p.paiements[0]>paiementMax){
                    smax.clear();
                    smax.add(i);
                    paiementMax=p.paiements[0];
                    strategieMax=i;
                }else if(p.paiements[0]==paiementMax){
                    smax.add(i);
                }
            }
        }else{
            paiementMax=mat.get(s).get(0).paiements[1];
            for(int j=0;j<nbs2;j++){
                Forme p=mat.get(s).get(j);
                if(p.paiements[1]>paiementMax){
                    smax.clear();
                    smax.add(j);
                    paiementMax=p.paiements[1];
                    strategieMax=j;
                }else if(p.paiements[1]==paiementMax){
                    smax.add(j);
                }
            }
        }
        return smax;
    }
    public ArrayList<Integer> maxStrategie(int jj,int l){//le max d'une strategie pour un joueur
        float max=0;
        ArrayList<Integer> maxStrategies=new ArrayList<>();
        int maxS=0;
        if(jj==1){
            max=mat.get(0).get(l).paiements[0];
            for(int i=0;i<mat.size();i++){
                if(mat.get(i).get(l).paiements[0]>max){
                    maxStrategies.clear();
                    maxStrategies.add(i);
                    max=mat.get(i).get(l).paiements[0];
                    maxS=i;
                }else if(mat.get(i).get(l).paiements[0]==max){
                    maxStrategies.add(i);
                }
            }
        }else{
            max=mat.get(l).get(0).paiements[1];
            maxS=0;
            for(int i=0;i<mat.get(0).size();i++){
                if(mat.get(l).get(i).paiements[1]>max){
                    maxStrategies.clear();
                    maxStrategies.add(i);
                    max=mat.get(l).get(i).paiements[1];
                    maxS=i;
                }else if(mat.get(l).get(i).paiements[1]==max){
                    maxStrategies.add(i);
                }
            }
        }
        return maxStrategies;
    }
    public ArrayList<Profil> equilibreNash(){
        ArrayList<Profil> equilibreNash=new ArrayList<>();
        ArrayList<Integer> pos1=new ArrayList<>();
        ArrayList<Integer> l1=new ArrayList<>();
        ArrayList<Integer> pos2=new ArrayList<>();
        ArrayList<Integer> l2=new ArrayList<>();
        for(int i=0;i<mat.get(0).size();i++){
            ArrayList<Integer> maxStrategies=maxStrategie(1, i);
            pos1.addAll(maxStrategies);
            for(int j=0;j<maxStrategies.size();j++){
                l1.add(i);
            }
        }
        for(int i=0;i<mat.size();i++){
            ArrayList<Integer> maxStrategies=maxStrategie(2, i);
            pos2.addAll(maxStrategies);
            for(int j=0;j<maxStrategies.size();j++){
                l2.add(i);
            }
        }
        for(int i=0;i<pos1.size();i++){
            for(int j=0;j<pos2.size();j++){
                if(pos1.get(i)==l2.get(j) && pos2.get(j)==l1.get(i))
                    equilibreNash.add(new Profil(pos1.get(i),pos2.get(j)));
            }
        }
        return equilibreNash;
    }
    public ArrayList<Profil> equilibreNash1(){
        ArrayList<Profil> equilibreNash=new ArrayList<>();
        for(int i=0;i<mat.get(0).size();i++){
            ArrayList<Integer> pos1=maxStrategie(1, i);
            System.out.println("pos1:"+pos1);
            for(Integer st1:pos1){
                ArrayList<Integer> pos2=maxStrategie(2, st1);
                System.out.println("pos2:"+pos2);
                for(Integer st2:pos2){
                    if(st2==i){
                        equilibreNash.add(new Profil(st1, st2));
                    }
                }
            }
        }
        return equilibreNash;
    }
    public ArrayList<Profil> equilibreNash2(){
        ArrayList<Profil> meilleursRoponseJ1=afficherMeilleureReponse(1);
        ArrayList<Profil> meilleursRoponseJ2=afficherMeilleureReponse(2);
        ArrayList<Profil> equilibreNash=new ArrayList<>();
        for(int i=0;i<meilleursRoponseJ1.size();i++){
            boolean exist=false;
            for(int j=0;j<meilleursRoponseJ2.size();j++){
                if(meilleursRoponseJ1.get(i).equals(meilleursRoponseJ2.get(j))){
                    exist=true;
                    break;
                }
            }
            if(!exist)equilibreNash.add(meilleursRoponseJ1.get(i));
        }
        return equilibreNash;
    }
    public void afficherEquilibreNash(){
        System.out.println("l'equilibre de Nash est :"+equilibreNash());
    }
    public ArrayList<Profil> pareto(){
        ArrayList<Profil> pareto=new ArrayList<>();
        ArrayList<Profil> maxJ1=new ArrayList<>();
        ArrayList<Profil> maxJ2=new ArrayList<>();
        float m1=mat.get(0).get(0).paiements[0], m2=mat.get(0).get(0).paiements[1];
        for(int i=0;i<mat.size();i++){
            for(int j=0;j<mat.get(0).size();j++){
                if(mat.get(i).get(j).paiements[0]>m1){
                    maxJ1.clear();
                    maxJ1.add(new Profil(i, j));
                    m1=mat.get(i).get(j).paiements[0];
                }else if(mat.get(i).get(j).paiements[0]==m1){
                    maxJ1.add(new Profil(i, j));
                }
                if(mat.get(i).get(j).paiements[1]>m2){
                    maxJ2.clear();
                    maxJ2.add(new Profil(i, j));
                    m2=mat.get(i).get(j).paiements[1];
                }else if(mat.get(i).get(j).paiements[1]==m2){
                    maxJ2.add(new Profil(i, j));
                }
            }
        }
        for(int i=0;i<maxJ1.size();i++){
            for(int j=0;j<maxJ2.size();j++){
                if(maxJ1.get(i).equals(maxJ2.get(j)))
                    pareto.add(maxJ1.get(i));
            }
        }
        System.out.println("pareto dominants J1 :"+maxJ1);
        System.out.println("pareto dominants J2 :"+maxJ2);
        return pareto;
    }
    public ArrayList<Profil> pareto2(){
        ArrayList<Profil> paretos=new ArrayList<>();
        Forme f=mat.get(0).get(0);
        for(int i=0;i<nbs1;i++){
            for(int j=0;j<nbs2;j++){
                if(mat.get(i).get(j).plusGrand(f)==1){
                    paretos.clear();
                    paretos.add(new Profil(i, j));
                    f=mat.get(i).get(j);
                }else if(mat.get(i).get(j).plusGrand(f)==0){
                    paretos.add(new Profil(i, j));
                    f=mat.get(i).get(j);
                }
            }
        }
        return paretos;
    }
    public float securite(int jj,int s){//le max d'une strategie pour un joueur
        float min=0;
        if(jj==1){
            min=mat.get(s).get(0).paiements[0];
            for(int i=0;i<mat.get(0).size();i++){
                if(mat.get(s).get(i).paiements[0]<min){
                    min=mat.get(s).get(i).paiements[0];
                }
            }
        }else{
            min=mat.get(0).get(s).paiements[1];
            for(int i=0;i<mat.size();i++){
                if(mat.get(i).get(s).paiements[1]<min){
                    min=mat.get(i).get(s).paiements[1];
                }
            }
        }
        return min;
    }
    public void afficheSecurite(){
        System.out.println("securite joueur 1:");
        double max=securite(1, 0);
        for(int i=0;i<mat.size();i++){
            System.out.println("\tsecurite S"+i+" : "+securite(1, i));
            if(securite(1, i)>max) max=securite(1, i);
        }
        System.out.println("\tsecurité : "+max);
        System.out.println("securite joueur 2:");
        max=securite(2, 0);
        for(int i=0;i<mat.get(0).size();i++){
            System.out.println("\tsecurite S"+i+" : "+securite(2, i));
            if(securite(2, i)>max) max=securite(2, i);
        }
        System.out.println("\tsecurité : "+max);
    }
    public ArrayList<Profil> afficherMeilleureReponse(int jj){//trouve la meilleure reponse d'un joueur jj
        ArrayList<Profil> meilleureReponses=new ArrayList<>();
        if(jj==1){
            for(int j=0;j<nbs2;j++){
                System.out.println("en fixant S"+j+" : S"+meilleurChoix(1,j)+" du joueur 1 est meilleur choix");
                ArrayList<Integer> meilleursChoix=meilleurChoix(1,j);
                for(Integer s:meilleursChoix)
                    meilleureReponses.add(new Profil(s,j));
            }
        }else{
            for(int j=0;j<nbs1;j++){
                System.out.println("en fixant S"+j+" : S"+meilleurChoix(2,j)+" du joueur 2 est meilleur choix");
                ArrayList<Integer> meilleursChoix=meilleurChoix(1,j);
                for(Integer s:meilleursChoix)
                    meilleureReponses.add(new Profil(s,j));
            }
        }
        return meilleureReponses;
    }
    public void eleminer(){
        System.out.println("j1:");
        for(int i=0;i<nbs1;i++){
            for(int j=0;j<nbs1;j++){
                //if(i!=j && domine(i,j,1))System.out.println("s"+i+" domine s"+j+" ");
            }
        }
        System.out.println("j2:");
        for(int i=0;i<nbs2;i++){
            for(int j=0;j<nbs2;j++){
                //if(i!=j && domine(i,j,2))System.out.println("s"+i+" domine s"+j+" ");
            }
        }
    }
    @Override
    public String toString(){
        String s="   ";
        for(int j=0;j<nbs2;j++)
            s+=String.format("     S%d    ",j);
        s+="\n";
        for(int i=0;i<nbs1;i++){
            s+=String.format("S%d ",i);
            for(int j=0;j<nbs2-1;j++){
                //s+=matrice[i][j].toString();
                s+=mat.get(i).get(j).toString();
            }
            //s+=matrice[i][nbs2-1].toString()+"\n";
            s+=mat.get(i).get(nbs2-1).toString()+"\n";
        }
        return s;
    }
}
