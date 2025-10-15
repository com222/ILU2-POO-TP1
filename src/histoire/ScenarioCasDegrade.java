package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import personnages.VillageSansChefException;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	public static void main(String[] args) throws VillageSansChefException {
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		
//		System.out.println("test 4 :" + village.afficherVillageois());
		
		village.setChef(abraracourcix);

		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Gaulois test = new Gaulois ("perso", 7);
		
		village.ajouterHabitant(bonemine);
		village.ajouterHabitant(abraracourcix);
		village.ajouterHabitant(test);
		
		village.installerVendeur(bonemine, "fleurs", 20);
		
		Etal etalTest = village.rechercherEtal(test);
		Etal etalTest3 = new Etal ();
		Etal etalFleur = village.rechercherEtal(bonemine);
		
		System.out.println("normal 1: " + etalFleur);
		System.out.println("test 1 : " + etalTest);
		
		System.out.println("-----------------------------");
		
		System.out.println("normal 2 : " + etalFleur.acheterProduit(10, abraracourcix));
		System.out.println("test 2.1 : " + etalFleur.acheterProduit(10, null)); 
//		System.out.println("test 2.2 : " + etalFleur.acheterProduit(-10, abraracourcix));
//		System.out.println("test 2.3 : " + etalTest3.acheterProduit(10, abraracourcix));
		
		System.out.println("-----------------------------");
		
		System.out.println("normal 3 : " + etalFleur.libererEtal());
		System.out.println("test 3 : " + etalTest3.libererEtal());
		
		System.out.println("fin test");
	}
}
