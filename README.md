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

