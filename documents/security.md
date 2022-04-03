## Security

### Access Data from the API

In order to access data from the API a user or an application will need to pass an Access Token to the API. This access token along with the **ROLE** of the user will determine what kind of data can be accessed or returned.

- You can request an Access Token with a **username** and a **password**

To get an Access Token from a User Login you can do a POST request to:

|                                          URL                        | Method |                    Remarks                    | Valid Request Body |
|---------------------------------------------------------------------|--------|-----------------------------------------------|---------------------------|
|`http://localhost:8080/api/v1/auth/signin`                            | POST   |Bearer Token       | JSON          |

You will get a response similar to the one show below.

~~~json
{
  "id":1254,
  "username":"username",
  "email":"glebszypula1997@gmail.com",
  "company":"Company Name",
  "roles":["ROLE_USER"],
  "accessToken":"eyJhiOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY0ODQ3NzMzOCwiZXhwIjoxNjU2MjUzMzM4fQ.Q0M70O-AaSVV83NhL2Zxe3pwaGB0OVPoPE0LE8aQx-JGrtsZ1cnzQX0cbl_U376iyDlYcNS9U0JNnzn4Ow",
  "tokenType":"Bearer"
}
~~~

### Request Data with an Access Token

Now, that you have an **authenticationToken** and you can request data from the application using this token. Based on the permission of the current user they will be able to **CREATE**, **READ**, **UPDATE**, and **DELETE** content in the application.

### TODO: Token refresh

When the application detects the authenticationToken has expired it will need you to request a new authenticationToken.


### TODO: Preventing Brute Force Authentication Attempts

A basic solution for preventing brute force authentication attempts using Spring Security is implemented. The app keeps a record of the number of failed attempts originating from a single IP address. If that particular IP goes over a set number of requests – it will be blocked for a set amount of time.

### TODO: Session Timeout

If the application remains inactive for a specified period of time, the session will expire. The session after this period of time is considered invalid and the user has to login to the application again.

