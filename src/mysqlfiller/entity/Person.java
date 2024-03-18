package mysqlfiller.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="people")	// annotation Table connecting this class with table employees in MySQL
public class Person {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name="id")
	   private int id;
	   @Column(name="name")
	   private String name;
	   @Column(name="surname")
	   private String surname;	   
	   @Column(name="birthday")
	   private LocalDate birthday;
	   @Column(name="sex")
	   private char sex;
	   @Column(name="married")
	   private char married;
	   @Column(name="children")
	   private int children;
	   @Column(name="country")
	   private String country;
	   @Column(name="language")
	   private String language;
	   @Column(name="education")
	   private String education;
	   @Column(name="email")
	   private String email;
	   @Column(name="phone")
	   private String phone;
	   @Column(name="salary")
	   private int salary;
	   @Column(name="tax")
	   private float tax;
	public Person(String name, String surname, LocalDate birthday, char sex, char married, int children, String country,
			String language, String education, String email, String phone, int salary, float tax) {
		super();
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.sex = sex;
		this.married = married;
		this.children = children;
		this.country = country;
		this.language = language;
		this.education = education;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.tax = tax;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", birthday=" + birthday + ", sex="
				+ sex + ", married=" + married + ", children=" + children + ", country=" + country + ", language="
				+ language + ", education=" + education + ", email=" + email + ", phone=" + phone + ", salary=" + salary
				+ ", tax=" + tax + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public char getMarried() {
		return married;
	}
	public void setMarried(char married) {
		this.married = married;
	}
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
		this.children = children;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
   
}