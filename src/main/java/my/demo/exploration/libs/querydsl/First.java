package my.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;
import my.demo.exploration.libs.querydsl.model.QBlogPost;
import my.demo.exploration.libs.querydsl.model.QUser;
import my.demo.exploration.libs.querydsl.model.User;

@Slf4j
@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
		
		// n.b.: before running the code, remember to run "mvn compile" to create the queryDSL classes "Q*.java"
		
		// Create a query factory
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my.demo.querydsl.intro");
		EntityManager em = entityManagerFactory.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		
		//Create the query
		QUser qUser = QUser.user;
		QBlogPost qBlogPost = QBlogPost.blogPost;
		
		// Select one user
		User result = queryFactory.selectFrom(qUser)
								  .where(qUser.login.eq("David"))
								  .fetchOne();
		log.info("David -> {}", result);
		
		// Select a list of users ordered by login field
		List<User> resultList = queryFactory.selectFrom(qUser)
								   .orderBy(qUser.login.asc())
								   .fetch();
		log.info("orderBy login asc -> {}", resultList);
		
		// Select a list of records with alias, groupedBy and orderBy
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		List<Tuple> userTitleCounts = queryFactory.select(qBlogPost.title, qBlogPost.id.count().as(count))
												  .from(qBlogPost)
												  .groupBy(qBlogPost.title)
												  .orderBy(count.desc())
												  .fetch();
		log.info("list of records with alias, groupedBy and orderBy -> {}", userTitleCounts);
		
		/* ***** COMPLEX QUERIES ***** */
		
		// Inner Join
		List<User> users = queryFactory.selectFrom(qUser)
									   .innerJoin(qUser.blogPosts, qBlogPost)
									   .on(qBlogPost.title.eq("Hello World!"))
									   .fetch();
		log.info("inner join -> {}", users);
		
		// Subquery
		users = queryFactory.selectFrom(qUser)
							.where(
									qUser.id.in(
												JPAExpressions.select(qBlogPost.user.id)
															  .from(qBlogPost)
															  .where(qBlogPost.title.eq("Hello World!")))
							)
							.fetch();
		log.info("subquery -> {}", users);
	}

}
