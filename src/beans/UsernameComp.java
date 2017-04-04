package beans;

import java.util.Comparator;

public class UsernameComp implements Comparator<Utilisateur> {

	@Override
	public int compare(Utilisateur u1, Utilisateur u2) {
		return u1.getLogin().compareTo(u2.getLogin());
	}

}
