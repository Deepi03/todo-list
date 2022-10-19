# todo-list

This application does the following
   * a personal TODO application that requires users to be logged in before they can call the APIs
   * One user can create multiple todo items and one todo item can only belong to a single user

## Prerequisite
Following tools required,
 * java 17
 * Maven 3.8
 * PostgreSQL 14.5




 ## API 
``
mvn spring-boot:run
``

### Using postman

Method: 
 ``
 POST
``

URL:
 ``
 http://localhost:8080/api/v1/signup
 ``

 Request :
``
{
    "email":"magizh@mail",
    "password":"qwertyuiop"
}
``



Response:
``
{
    "message": "UserInfo registered successfully"
}
``



Method: 
``POST``

URL:
 `` 
 http://localhost:8080/api/v1/signin
 ``

 Request :
``
{  
    "email":"magizh@mail",
    "password":"qwertyuiop"
}
``

Response:
``
{
    "token": "Bearer Token"
}
``


Method: 
``PUT``

URL:
 `` 
 http://localhost:8080/api/v1/changePassword
 ``
 Authorization:
 ``
 Bearer_Token
 ``

 Request :
``
{
    "email":"magizh@mail",
    "oldPasssword":"1234567",
    "newPassword":"qwertyuiop"
}
``

Response:
``
{
    "message": "Password changed successfully"
}
``


Method: 
``GET``

URL:
 `` 
 http://localhost:8080/api/v1/todos
 ``

Authorization:
 ``
 Bearer_Token
 ``


Response:
``
{
    [
    {
        "id": 7,
        "name": "name",
        "description": "description",
        "createdTimeStamp": "17/10/2022, 15.03",
        "updatedTimeStamp": null,
        "status": "STATUS_NOTSTARTED",
        "user": {
            "id": 1,
            "email": "malini@mail",
            "password": "$2a$10$Sb4XsIcRRZxhXtXtD3yOd.7kKuwgTJDuEUsZ/.7czDX5GPwkfqRmO",
            "createdTimeStamp": "17/10/2022, 13.56",
            "updatedTimeStamp": "17/10/2022, 13.56",
            "enabled": true,
            "authorities": [
                {
                    "authority": "ADMIN"
                }
            ],
            "username": "malini@mail",
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "accountNonExpired": true
        }
    },
    {
        "id": 8,
        "name": "name",
        "description": "description",
        "createdTimeStamp": "17/10/2022, 15.04",
        "updatedTimeStamp": null,
        "status": "STATUS_NOTSTARTED",
        "user": {
            "id": 1,
            "email": "malini@mail",
            "password": "$2a$10$Sb4XsIcRRZxhXtXtD3yOd.7kKuwgTJDuEUsZ/.7czDX5GPwkfqRmO",
            "createdTimeStamp": "17/10/2022, 13.56",
            "updatedTimeStamp": "17/10/2022, 13.56",
            "enabled": true,
            "authorities": [
                {
                    "authority": "ADMIN"
                }
            ],
            "username": "malini@mail",
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "accountNonExpired": true
        }
    } 
]
}
``


Method: 
 ``
 POST
``

URL:
 ``
 http://localhost:8080/api/v1/todos
 ``

Authorization:
 ``
 Bearer_Token
 ``

 Request :
``
{
    "name":"grocery",
    "description":"grocery shopping on",
    "status":"STATUS_ONGOING"
}
``


Response:
``
{
    "message": "Todo item Created successfully"
}
``



Method: 
 ``
 PUT
``

URL:
 ``
 http://localhost:8080/api/v1/todos/:id
 ``

 Authorization:
 ``
 Bearer_Token
 ``

 Request :
``
{
    "name":"grocery",
    "description":"grocery shopping on",
    "status":"STATUS_ONGOING"
}
``

Response:
``
{
    {
    "message": "Todo item updated successfully"
}
}
``



Method: 
 ``
 DELETE
``

Authorization:
 ``
 Bearer_Token
 ``

URL:
 ``
 http://localhost:8080/api/v1/todos/:id
 ``

Response:
``
{
    {
     "message": "Todo item deleted successfully!"
}
}
``