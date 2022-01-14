package my.demo.exploration.libs.spring.service.mvc.datajparest;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** CustomerRepository is a repository interface that works with Customer entities. 
 * Spring Data JPA focuses on using JPA to store data in a relational database.
 * Its most compelling feature is the ability to create repository implementations automatically, at runtime, from a repository interface.
 * By extending CrudRepository, CustomerRepository inherits several methods for working with Customer persistence, including methods for saving, deleting, and finding Customer entities.
 * 
 * @param Customer is the class that describes the entity type.
 * @param Long is the class that describes the Id type.
 */
/* At runtime, Spring Data REST automatically creates an implementation of PagingAndSortingRepository interface.
 * Then it uses the @RepositoryRestResource annotation to direct Spring MVC to create RESTful endpoints at /customer.
 * N.B.: @RepositoryRestResource is not required for a repository to be exported. It is used only to change the export details, such as using /customer instead of the default value of /customers. */
@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	/* Is possible to define query method to be added to those provided by CrudRepository. */
	
	/* N.B.: In a typical Java application, you might expect to write a class that implements CustomerRepository.
	 * However, that is what makes Spring Data JPA so powerful: You need not write an implementation of the repository interface.
	 * Spring Data JPA creates an implementation when you run the application. */
	
	List<Customer> findByLastName(@Param("name") String name);
}