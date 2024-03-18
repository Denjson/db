package mysqlfiller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import mysqlfiller.entity.EmailGenerator;
import mysqlfiller.entity.HibernateConfiguration;
import mysqlfiller.entity.Person;

/* 
 * This code is filling MySQL data base with randomly generated people.
 * Please fill rowsQ filed for required quantity of people.
 * Provide actual data base configuration in HibernateConfiguration.java
 * Change schema name from den_db to your one in this file, and in HibernateConfiguration.java
 * Start local MySQL data base and run this code. Generate one million people in several minutes 
 * for your fun and practice!  
*/
public class PeopleFiller {
	static int rowsQ = 1000;	// quantity of rows to be added into MySQL DB
	private static SessionFactory sessionFactory;
	public static void main(String[] args) throws IOException {
		sessionFactory = HibernateConfiguration.getSessionFactory();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			String textToSQL = """
					SELECT count(*)
					FROM information_schema.TABLES
					WHERE (TABLE_SCHEMA = 'den_db')
					AND (TABLE_NAME = 'people'); 
					""";	// provide your actual TABLE_SCHEMA
			NativeQuery<Integer> tableExists = session.createNativeQuery(textToSQL, Integer.class);
			int exists = tableExists.getSingleResult();	// if zero, then table does not exist, need to create it
//			System.out.println("Table existing: " + exists);
			if (exists==0) {	// if zero (table does not exists), then creating it	
			textToSQL = """
					CREATE TABLE people (
					id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
					name varchar(15),
					surname varchar(15),
					birthday date,
					sex char,
					married char,
					children int,
					country varchar(32),
					language varchar(55),
					education varchar(17),
					email varchar(30),
					phone varchar(16),
					salary int,
					tax DECIMAL(3,2)
					);
					""";
			session.createNativeQuery(textToSQL, Person.class).executeUpdate();	// creating new table
			}
			
			List<String> names = new ArrayList<String>();
			List<String> surnames = new ArrayList<String>();
			List<String> countries = new ArrayList<String>();
			List<String> languages = new ArrayList<String>();
			List<String> education = new ArrayList<String>();
			List<String> emails = new ArrayList<String>();
			List<String> phones = new ArrayList<String>();
			// https://docs.google.com/spreadsheets/d/1-t5IkZhyxkP7luunBuSYZHLRY3QxEkrADaX9ExQFMz0/edit#gid=0
//			URL oracle = new URL(
//					"https://docs.google.com/spreadsheets/d/e/2PACX-1vTpBahXQXwwUT1KBXQ_imymoXPZ4IejsSTH-93uXt4kO0NG4noEUPEIFccF6lqxY1ox7U1rwuIrCsNc/pub?gid=0&single=true&output=tsv"); // TSV
//			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			BufferedReader in = new BufferedReader(new FileReader("src\\mysqlfiller\\TopNames.tsv"));	// reading local file, but possible to receive file from link as shown above
			String inputLine;
			String[] fullname;
			while ((inputLine = in.readLine()) != null) {
				fullname = inputLine.split("\t");
				names.add(fullname[0]);
				surnames.add(fullname[1]);
				if (!fullname[2].equals("0")) // if value is zero, then no country in this cell
					countries.add(fullname[2]);
				if (!fullname[3].equals("0")) // if value is zero, then no language in this cell
					languages.add(fullname[3]);
				if (!fullname[4].equals("0")) // if value is zero, then no education in this cell
					education.add(fullname[4]);
				if (!fullname[5].equals("0")) // if value is zero, then no email in this cell
					emails.add(fullname[5]);
				if (!fullname[6].equals("0")) // if value is zero, then no phone code in this cell
					phones.add(fullname[6]);
				System.out.println(inputLine);
			}
			in.close();
			Random random = new Random();
			LocalDate endDate = LocalDate.now();
			LocalDate startDate = endDate.minusYears(100); // 100 years is good life time for human being
			List<LocalDate> birthDays = startDate.datesUntil(endDate).collect(Collectors.toList()); // creating a list of birthdays between two dates
			char[] sex = { 'M', 'F', 'U' }; // array for sex
			char[] maritalStatus = { 'N', 'Y' };
			emails = EmailGenerator.generate(emails, rowsQ); // getting List with ready e-mails, size is 1 million (can be custom size)
			for (int i = 0; i < rowsQ; i++) {	// adding one million of rows
				int nameIndex = random.nextInt(names.size()); // getting random name from full list
				int surnameIndex = random.nextInt(surnames.size()); // getting random surname from full list
				int dateIndex = random.nextInt(birthDays.size()); // getting random date of Birth from full list
				int sexIndex = random.nextInt(sex.length); // getting random sex from array list
				int maritalIndex = random.nextInt(maritalStatus.length); // getting random marital status from array list
				if (endDate.getYear() - birthDays.get(dateIndex).getYear() < 18) {
					maritalIndex = 0;
//					System.out.println("Cannot get married!");
				}
				int qtyChildren = random.nextInt(12);	// quantity of children: 0-11
				if (endDate.getYear() - birthDays.get(dateIndex).getYear() < 14) {
					qtyChildren = 0;
				}
				int countryIndex = random.nextInt(countries.size()); // getting random country from array list
				int languageIndex = random.nextInt(languages.size()); // getting random language from array list
				int educationIndex = random.nextInt(education.size()); // getting random education from array list
				int annualRevenue = random.nextInt(100) * 1000; // annual salary
				float taxRate = ((float) random.nextInt(50)) / 100; // taxes rate
				int phoneIndex = random.nextInt(phones.size()); // getting random phone code
				String phone = "+(" + phones.get(phoneIndex) + ")" + random.nextInt(Integer.MAX_VALUE);
//				System.out.println("Person " + (i + 1) + " = " + names.get(nameIndex) + " " + surnames.get(surnameIndex)
//						+ ", Date of birth: " + birthDays.get(dateIndex) + ", Sex: " + sex[sexIndex] + ", Married: "
//						+ maritalStatus[maritalIndex] + ", Children: " + qtyChildren + ", Country: "
//						+ countries.get(countryIndex) + ", Language: " + languages.get(languageIndex) + ", Education: "
//						+ education.get(educationIndex) + ", e-mail: " + emails.get(i) + ", phone: " + phone
//						+ ", Salary: " + annualRevenue + ", Tax: " + taxRate);
				Person person = new Person(names.get(nameIndex), surnames.get(surnameIndex), birthDays.get(dateIndex),
						sex[sexIndex], maritalStatus[maritalIndex], qtyChildren, countries.get(countryIndex),
						languages.get(languageIndex), education.get(educationIndex), emails.get(i), phone,
						annualRevenue, taxRate); // Object of Entity
				session.persist(person); // adding data into data base
			}
//			int nameLengthMax = 0;
//			for (String s : emails) {
//            System.out.println(s);
//				if (s.length() > nameLengthMax)
//					nameLengthMax = s.length();
//			}
//			System.out.println("Max lenght of name is: " + nameLengthMax);
			session.getTransaction().commit(); // commit the transaction
		} finally {
			session.close(); // Felix is closing session too
			sessionFactory.close(); // Closing factory anyway
		}
	} // end of main
}
