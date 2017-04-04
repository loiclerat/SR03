package beans;

import java.util.Comparator;

public class NameComp implements Comparator<Utilisateur> {

	@Override
	public int compare(Utilisateur u1, Utilisateur u2) {
		return u1.getNom().compareTo(u2.getNom());
	}

}
