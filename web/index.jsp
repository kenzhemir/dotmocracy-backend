<%--
  Created by IntelliJ IDEA.
  User: Miras
  Date: 2/19/2018
  Time: 1:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>.mocracy</title>
    <script>
      var api_request = new XMLHttpRequest();

      api_request.onreadystatechange = function () {
          if (api_request.readyState == 4 && api_request.status == 200) {
              json = JSON.parse(api_request.responseText);
              var img = document.createElement("img");
              img.setAttribute("src", json.message);
              img.setAttribute("alt", "Random dog image");
              document.getElementById("container").appendChild(img);
          }
      };

      api_request.open('GET', 'https://dog.ceo/api/breeds/image/random', true);
      api_request.send(null);
    </script>
  </head>
  <body>
  <div id="container">
    <h1>Welcome to .mocracy!</h1>
  </div>
  </body>
</html>
