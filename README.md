# EXPLORATION-NOTES
This is where I take note of useful java code. Useful for me.

Is a matrioska project made for the exploration of opportunities from Java and related libraries.
Is full of comments useful to note some information. Probably too much comments, but the aim of this project is to be a place of notes and not an operative microservice.

I do not think anyone except me will find it of any help.

### Run
You can run the application by using

    ./mvnw spring-boot:run

Alternatively, you can build the JAR file with
     
    ./mvnw clean package
and then run the JAR file, as follows:
    
    java -jar target/exploration-<version>.jar

### Run
Yes, is full of bugs.

### References
An incomplete list of sources where I took the examples

<table>
  <tr>
    <th>Guide</th>
    <th>package</th>
    <th>reference</th>
  </tr>
  <tr>
    <td>Spring RESTful service</td><td>my.demo.exploration.spring.restfulservice</td><td>https://spring.io/guides/gs/rest-service/</td>
  </tr>
  <tr>
    <td>Spring configuration of RESTful service</td><td>my.demo.exploration.spring.service.restful.configuration</td><td>https://www.baeldung.com/bootstraping-a-web-application-with-spring-and-java-based-configuration</td>
  </tr>
  <tr>
    <td>Spring configuration of MVC service</td><td>my.demo.exploration.spring.service.mvc.configuration</td><td>https://www.baeldung.com/bootstraping-a-web-application-with-spring-and-java-based-configuration</td>
  </tr>
  <tr>
    <td>Spring Data JPA access</td><td>my.demo.exploration.spring.service.restful.datajpa</td><td>https://spring.io/guides/gs/accessing-data-jpa/</td>
  </tr>
  <tr>
    <td>Spring Data JPA access with Rest</td><td>my.demo.exploration.spring.service.mvc.datajparest</td><td>https://spring.io/guides/gs/accessing-data-rest/</td>
  </tr>
  <tr>
    <td>Spring Security for Authorization</td><td>my.demo.exploration.spring.service.restful.security.authorization</td><td>https://docs.spring.io/spring-security/site/docs/5.0.7.RELEASE/reference/html/el-access.html</td>
  </tr>
  <tr>
    <td>Spring Security for Authentication</td><td>my.demo.exploration.spring.service.mvc.security.authentication</td><td>https://spring.io/guides/gs/securing-web/</td>
  </tr>
</table>