# JavaTechnologies2024
This is a repository for java homework

Laboratory 1

Compulsory (1p)
For this part, I create the StringListServlet with a GET method that receives a parameter 'stringList' in the request URL and returns the an ordered list of its characters.

Homework (2p)
In this part, I create the AdjacencyMatrixServlet, which contains a POST method that receives two numbers, representing vertices and edges. 
I use the generateRandomGraph function to create a graph by by shuffling all possible edges between vertices, then selecting the specified number of edges to update the adjacency matrix. I sent this response back to be displayed in an HTML table.
I tried to use the ServletContext context.log(), to log a message created using logRequest fruition in the RequestLog class.
I used a Python program(in folder pythonForLab1) to call the API /adjacency-matrix I used the request.getHeader("User-Agent"); to see if the request contains Python or requests. In this way, I knew when to return a plain text format(for Python) or an HTML Table

Laboratory 2

Compulsory (1p)
For this part I created an input.jsp page for uploading a text file, including fields for the file name, description. 
The result is displayed in result.jsp and includes the details, that are are retrieved from the session, of the uploaded text file, including its name, description, and a list of shuffled lines from the file.
In FileUploadServlet I processes the file upload. The servlet retrieves the file name and description from the request, reads the contents of the uploaded text file into a list, and stores the details in a FileLinesBean object before saving it to the session and redirecting to the result page.

Homework (2p)
For the LoggingFilter I use the logRequest function created in the first Laboratory to log the detalis about all the requests that contains /input.jsp/*.
The AppConfigListener saves in sessions the contect init parameters coda and prelude(declared in web.xml) and the ResponseWrapperFilter add this two to all responses.
For the Captcha I used Google [reCaptcha](https://www.google.com/recaptcha/about/). They handle the verification, so the one from backend is somehow a formal verification. I ensure that the g-recaptcha-response token is valid by sending it to the Google API. Google responds whether the user passed or failed the CAPTCHA. If it has error, I display an error message.
