package my.demo.exploration.libs.spring.service.restful.datajpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/** CustomerRepository is a repository interface that works with Customer entities. 
 * Spring Data JPA focuses on using JPA to store data in a relational database.
 * Its most compelling feature is the ability to create repository implementations automatically, at runtime, from a repository interface.
 * By extending CrudRepository, CustomerRepository inherits several methods for working with Customer persistence, including methods for saving, deleting, and finding Customer entities.
 * 
 * @param Customer is the class that describes the entity type.
 * @param Long is the class that describes the Id type.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	/* Is possible to define query method to be added to those provided by CrudRepository. */

	/* N.B.: In a typical Java application, you might expect to write a class that implements CustomerRepository.
	 * However, that is what makes Spring Data JPA so powerful: You need not write an implementation of the repository interface.
	 * Spring Data JPA creates an implementation when you run the application. */

	/* ----- ----- ----- EXAMPLES ----- ----- ----- */

	/* ----- ----- ----- Automatic Custom Queries ----- ----- ----- */
	/* By using a set of keywords, you can define a specific query implicit in the method name.
	 * More details at https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.query-methods.query-creation */

	List<Customer> findByLastName(String lastName);

	Customer findById(long id);

	/* ----- ----- ----- Manual Custom Queries ----- ----- ----- */
	/* By using the @Query annotation you can define a custom query. */
	
	/* Example of @Param annotation to give a method parameter a concrete name and bind the name in the query. */
	@Query("SELECT c FROM Customer c WHERE LOWER(f.lastName) = LOWER(:lastName)")
	Customer retrieveByName(@Param("lastName") String name);
	
	/* Example with position-based parameter binding (the 1 indicates the first parameter). */
	@Query("SELECT c FROM Customer c WHERE f.lastName = ?1")
	Customer retrieveByLastName(String lastName);

	/* Example with LIKE and Sort. */
	@Query("select c FROM Customer c WHERE c.firstname like %?1")
	List<Customer> findByFirstnameEndsWith(String firstname, Sort sort);
	
	/* Example of Native SQL query. */
	@Query(value = "SELECT * FROM Customer WHERE id = ?1", nativeQuery = true)
	Customer retrieveById(long id);

	/* Example of Native SQL query with pagination. */
	@Query( value = "SELECT * FROM Customer WHERE id = ?1",
			countQuery = "SELECT count(*) FROM Customer WHERE id = ?1",
			nativeQuery = true)
	Page<Customer> findById(long id, Pageable pageable);
	
	/* Example of SpEL Expression.
	 * This case of entityName can be useful for custom repository interfaces creation. */
	@Query("select x from #{#entityName} x where x.lastname = ?1")
	List<Customer> findByLastnameSpEL(String lastname);
	
	/* Example of Modifying query.
	 * Doing so triggers the query annotated to the method as an updating query instead of a selecting one.
	 * The @Modifying annotation is only relevant in combination with the @Query annotation. Derived query methods or custom methods do not require this annotation. */
	@Modifying
	@Query("update Customer c set c.firstname = ?1 where c.lastname = ?2")
	int setFixedFirstnameFor(String firstname, String lastname);
}