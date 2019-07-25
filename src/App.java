import java.util.Scanner;

/**
 *
 * @author hakimhassani97
 */
public class App {
    public static void main(String args[]){
        boolean repeat=true;
        while(repeat){
            //attendre le choix de l'utilisateur
            Scanner sc=new Scanner(System.in);
            System.out.println("************************************************");
            System.out.println("1 --> remplir une matrice manuellement");
            System.out.println("2 --> remplir une matrice aléatoirement");
            System.out.println("3 --> utiliser la matrice du dilleme du prisonnier");
            System.out.print("choix : ");
            int choix=sc.nextInt();
            Jeu j;
            switch (choix) {
                case 1://matrice manuelle
                    {
                        System.out.print("la taille de la matrice [n*m]: ");
                        String taille=sc.next();
                        String[] tailles=new String[1];
                        tailles=taille.split("[*]");
                        while(tailles.length!=2){
                            System.out.print("entrez la taille dans ce format n*m: ");
                            taille=sc.next();
                            tailles=taille.split("[*]");
                        }
                        int n,m;
                        n=Integer.parseInt(tailles[0].trim());
                        m=Integer.parseInt(tailles[1].trim());
                        j=new Jeu(n,m);
                        j.getExample(-1);
                        break;
                    }
                case 2://matrice aléatoire
                    {
                        System.out.print("la taille de la matrice [n*m]: ");
                        String taille=sc.next();
                        String[] tailles;
                        tailles=taille.split("[*]");
                        while(tailles.length!=2){
                            System.out.print("entrez la taille dans ce format n*m: ");
                            taille=sc.next();
                            tailles=taille.split("[*]");
                        }
                        int n,m;
                        n=Integer.parseInt(tailles[0]);
                        m=Integer.parseInt(tailles[1]);
                        System.out.println("voulez vous que le jeu soit à somme nulle ? [o/n]");
                        String sNulle=sc.next();
                        if(sNulle.equalsIgnoreCase("oui") || sNulle.equalsIgnoreCase("o"))
                            Forme.sommeNulle=true;
                        j=new Jeu(n,m);
                        break;
                    }
                case 3://matrice dilemme du prisonnier
                    j=new Jeu(2,2);
                    j.getExample(0);
                    break;
                default:
                    j=new Jeu(2,2);
                    break;
            }
            //traitements et affichages
            System.out.println("matrice de jeu :");
            System.out.println(j.toString());
            j.afficherDominances();
            j.afficheSecurite();
            j.afficherMeilleureReponse(1);
            j.afficherMeilleureReponse(2);
            j.afficherEquilibreNash();
            System.out.println("optimum de pareto :"+j.pareto2());
            j.symplexe();
            //FIN
            System.out.println("**********************  FIN  **************************");
            System.out.println("voulez vous répéter ? [o/n]");
            String rep=sc.next();
            if(rep.equalsIgnoreCase("oui") || rep.equalsIgnoreCase("o"))
                repeat=true;
            else repeat=false;
        }
    }
}
