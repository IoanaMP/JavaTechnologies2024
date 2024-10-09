import requests

servlet_url = "http://localhost:8080/lab1/adjacency-matrix"

data = {"numVertices": 5, "numEdges": 6}

try:
    response = requests.post(servlet_url, data=data)

    if response.status_code == 200:
        print(response.text)

    else:
        print(f"Error: {response.status_code}")
except requests.exceptions.RequestException as e:
    print(f"Error: {e}")
