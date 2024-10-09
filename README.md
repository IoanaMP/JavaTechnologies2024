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
