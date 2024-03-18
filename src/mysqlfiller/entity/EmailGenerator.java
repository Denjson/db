package mysqlfiller.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class EmailGenerator {
	public static List<String> generate(List<String> domains, int quantity) {
		List<Character> chars = new ArrayList<>();
		for (int i = 97; i < 123; i++) {
			chars.add((char) i);
		}
		for (int i = 48; i < 58; i++) {
			chars.add((char) i);
		}
		chars.add((char) 95);
		System.out.println(chars);
		int duplicated = 1;
		HashSet<String> allMails = new HashSet<>(); // collection with unique e-mails
		Random random = new Random();
		for (int a = 0; a < quantity*2; a++) {
			String email = "";
			int len = 5 + random.nextInt(6);
			for (int i = 0; i < len; i++) { // generating first part of e-mail
				int r = random.nextInt(chars.size());
				email += chars.get(r);
			}
			int domainIndex = random.nextInt(domains.size());
			email += "@" + domains.get(domainIndex); // adding one random domain

			Boolean b = allMails.add(email);
			if (!b) { // same as b==false
				System.out.println("Duplicate " + duplicated + " found: " + email);
				duplicated++;
			}
			if (allMails.size() == quantity)
				break;
		}
		System.out.println("Collection length is " + allMails.size());
		List<String> mailsList = new ArrayList<>(allMails);
		return mailsList;
	}
}
