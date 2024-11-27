# JavaTechnologies2024
This is a repository for java homework

Laboratory 1

Compulsory and Homework:

For this part, I create the StringListServlet with a GET method that receives a parameter 'stringList' in the request URL and returns the an ordered list of its characters.
For Homework part, I create the AdjacencyMatrixServlet, which contains a POST method that receives two numbers, representing vertices and edges. 
I use the generateRandomGraph function to create a graph by by shuffling all possible edges between vertices, then selecting the specified number of edges to update the adjacency matrix. I sent this response back to be displayed in an HTML table.
I tried to use the ServletContext context.log(), to log a message created using logRequest fruition in the RequestLog class.
I used a Python program(in folder pythonForLab1) to call the API /adjacency-matrix I used the request.getHeader("User-Agent"); to see if the request contains Python or requests. In this way, I knew when to return a plain text format(for Python) or an HTML Table

Laboratory 2

Compulsory and Homework:

For this part I created an input.jsp page for uploading a text file, including fields for the file name, description. 
The result is displayed in result.jsp and includes the details, that are are retrieved from the session, of the uploaded text file, including its name, description, and a list of shuffled lines from the file.
In FileUploadServlet I processes the file upload. The servlet retrieves the file name and description from the request, reads the contents of the uploaded text file into a list, and stores the details in a FileLinesBean object before saving it to the session and redirecting to the result page.
For the LoggingFilter I use the logRequest function created in the first Laboratory to log the detalis about all the requests that contains /input.jsp/*.
The AppConfigListener saves in sessions the contect init parameters coda and prelude(declared in web.xml) and the ResponseWrapperFilter add this two to all responses.
For the Captcha I used Google [reCaptcha](https://www.google.com/recaptcha/about/). They handle the verification, so the one from backend is somehow a formal verification. I ensure that the g-recaptcha-response token is valid by sending it to the Google API. Google responds whether the user passed or failed the CAPTCHA. If it has error, I display an error message.

Laboratory 3(not presented - i wasn't able to attend the laboratory)

Compulsory and Homework:

As recommended, I use a PostgreSql database, JSF 2.3 with Maven, I switched the server to Payara 5, because 6 didn't let me add JSF, and components from PrimeFaces.
I made two pages products.xhtml and clienta.xhtml to display the data in a PrimeFaces dataTable format. 
The first page index.xhtml has a dropdown to choose the language(english or romanian) and two buttons to navigate to one of the two pages mentioned above. The language change will be applied to the headers and buttons. The translations can be found in /resources/messages_en or_ro.properties.
In webapp/WEB-INF/facs-config.xml I put resource bundle and the navigation rules.
Add and Edit Client with order were added later, with laboratory 4.

Laboratory 4

I used Payara Admin Console to create a  JBDC Connection Pool and a JBDC Resource. Then, I configured them in my application by adding a payara-web.xml where I put my JNDI name and modifying the Utils/DatabaseConnection file. I use this in Services with the Resource annotation @Resource(name = "myConn").
In webapp/WEB-INF/shared I defined componenets for header, footer and menu. This are used in /WEB-INF/templates/page.xhtml, the component with the basic structure of the application.
dataView.xhtml is a component that displays dataTAble components like clientTable and productsTable.
dataEdit.xhtml is a generic component for editing a form, used for the edit dialog in clientEdit. The dataEdit contains a composite component for displaying the last user who modified the data, and the timestamp of the operation defined in webapp/WEB-INF/templates/loadMessage

For the Bonus part, we created an algorithm that starts from the current date, 8:00 a.m. and starting from the warehouse at position (0,0) I look for the closest available customer, after this is found, I move to the customer and starting with the arrival time I look for the next closest available customer in the remaining time

Laboratory 5

For this tasks, I configured the resource created in the previous laboratory in persistence.xml and I made a test in test/java/ProductTest.java, testProductPersistence.
Then I modified the Entities for Product, Client, Orders and OrderItems using JPA-only annotations and I created basic queries using JPA-QL. For OrderItems I have a OrderItemsId that is an @Embeddable used as the composite key(orderId and productId) and @EmbeddedId in OrderItems embeds OrderItemsId as the primary key.
I created the repositories for Product, Client and Orders with simple CRUD operations. 
I made a complete test unit for the CRUD operations for Products in test/java/ProductTest.java


Laboratory 6

Rewrite the data access layer of the application created in the previous laboratories, implementing the repository classes as Enterprise Java Beans.
Use the support offered by the EJB technology for implementing transactions.

Extend the model of the application such that clients can order products from the warehouse. An order contains a set of products and their quantities. --> done this and also an [interface](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/vrp/src/main/webapp/view/orderTable.xhtml)
The warehouse has a predefined quantity (initial stock) of each product. The stocks will be decreased accordingly after each order. -->
The following enterprise beans must be implemented:
A stateless session bean that offers methods for determining the current stock of a product. --> [StockManager](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/vrp/src/main/java/info/uaic/vrp/Bean/StockManager.java)
A stateful session bean responsible with the creation of the order. --> [OrderManager](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/vrp/src/main/java/info/uaic/vrp/Bean/OrderManager.java)
A singleton session bean that keeps an in-memory map of the client orders. --> [OrderRegistry](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/vrp/src/main/java/info/uaic/vrp/Bean/OrderRegistry.java)
Log the invocations and compute the running times of at least one business method, using an EJB interceptor.


Laboratory 7
Create a JSF application where students evaluate teachers. A student submission will specify a teacher, an activity (the name of a course, for example), the type of activity (course or lab, for example), a grade (score) and a comment.
The application will allow the following:
An [authentication](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/entities/Login.java) mechanism based on username and password
[Register](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/webapp/register.xhtml) new users and assign them a specific role, for example admin, student, teacher, etc.
Specify a time range, in which students can submit evaluations.
The students can [submit](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/webapp/evaluation.xhtml) evaluations, the [teachers](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/webapp/teacher-dashboard.xhtml) can see only their own evaluations, the [admin](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/webapp/admin-dashboard.xhtml) can see all evaluations and various statistics.
Each submission will have a uniquely generated registration number.
All evaluations will have a timestamp (when where they submitted).

Use Contexts and Dependency Injection (CDI) for:
- the management of application's beans [@Inject](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/repositories/UserRepository.java) and transactions [@Transactional](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/repositories/EvaluationRepository.java)
- decoupling the components using dependency injection (for example, use a producer method to generate registration numbers) (@Produces) -> [generateRegistrationNumber](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/entities/EvaluationEntity.java)
- decoupling orthogonal concerns, such as logging; [@Interceptor](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/logging/LoggingInterceptor.java)
- decoupling bussines concerns, such as verifying the date for operations like registration and submission [@Decorator](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/repositories/SubmissionDecorator.java);
- implementing at least one event-based comunication (for instance, whenever a new evaluation is submitted a message is produced and all observers of this type of event will be notified) ([@Observes](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/utils/EvaluationEventListener.java));
- data validation, using Bean [Validation](https://github.com/IoanaMP/JavaTechnologies2024/blob/main/review/src/main/java/info/uaic/review/entities/EvaluationEntity.java) annotations.