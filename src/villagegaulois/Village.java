package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			String nomVillageois = gaulois.getNom();
			if (nomVillageois != null && nomVillageois.equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if (this.chef == null) {
			throw new VillageSansChefException("pas de chef");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- ").append(villageois[i].getNom()).append("\n");
			}
		}
		return chaine.toString();
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nombreEtal = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nombreEtal++;
				}
			}
			Etal[] etalsAvecProduit = new Etal[nombreEtal];
			int parcour = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsAvecProduit[parcour] = etals[i];
					parcour++;
				}
			}
			return etalsAvecProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				Gaulois vendeur = etals[i].getVendeur();
				if (vendeur!=null && vendeur.equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int etalsVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					etalsVide++;
				}
			}
			if (etalsVide != 0) {
				chaine.append("Il reste " + etalsVide + " etals non utilises dans le marche. \n");
			}
			chaine.append("\n");
			return chaine.toString();
		}
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + " \n");
		int numEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
		chaine.append(
				"Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l etal numero " + numEtal + ". \n");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalDeProduits = marche.trouverEtals(produit);
		if (etalDeProduits.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marche. \n");
		} else if (etalDeProduits.length == 1) {
			String vendeur = etalDeProduits[0].getVendeur().getNom();
			chaine.append("Seul le vendeur " + vendeur + "porpose des " + produit + " au marche. \n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont : \n");
			for (int i = 0; i < etalDeProduits.length; i++) {
				String vendeur = etalDeProduits[i].getVendeur().getNom();
				chaine.append("- ").append(vendeur).append(" \n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etalPartir = this.rechercherEtal(vendeur);
		return etalPartir.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marche du village \" " + getNom() + " \" possède plusieurs etals : \n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}
