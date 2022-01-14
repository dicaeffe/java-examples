package my.demo.exploration.libs.spring.service.restful.datajpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.ToString;

/** Customer is an example of simple entity definition. */
/* The @Entity annotation identifies the class as a JPA entity.
 * Because no @Table(name = "customer") annotation exists, it is assumed that this entity is mapped to a table named Customer. */
@Entity
@Getter
@ToString
public class Customer {

	/** The Customer object’s id property is annotated with @Id so that JPA recognizes it as the object’s ID.
	 * The id property is also annotated with @GeneratedValue to indicate that the ID should be generated automatically. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/** The firstName is left unannotated: it will be mapped to columns that share the same name as the property itself. */
	private String firstName;
	
	/** The lastName is left unannotated: it will be mapped to columns that share the same name as the property itself. */
	private String lastName;
	
	
	/** The default constructor is protected because has been defined only for the sake of JPA. */
	protected Customer() {}
	
	
	/** This constructor is used to create instances of Customer to be saved to the database. 
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}