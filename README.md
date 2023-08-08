
## Social Media App API

### Overview

Social Media Application.

### Features:
- Create User
- Login User
- Fetch user by Username
- Update user info
- A user can follow amd unfollow user
- A current login/post creator can 
  - Create a Post
  - Fetch the post by id
  - update the post
  - delete the post
- A current login user can comment on post
- A current login use can like a post
- can read a post



### Tools:
- Language/Framework: Java/Spring Boot
- Email notification service
- MySQL -RDBMS

- Create your database,

#### For Database configuration:
Add the followings in application-dev file:

    spring.datasource.driverClassName = com.mysql.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/{your-database-name}?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    spring.datasource.username = {your_database_username}
    spring.datasource.password = {your_database_password}

#### For Email configuration-smtp protocol:
Add the followings in application-dev file:

    spring.mail.host = {your_email_host}
    spring.mail.username = {your_email_username}
    spring.mail.password = {your_email_password}
    spring.mail.properties.mail.transport.protocol = {your_email_protocol}
    spring.mail.properties.mail.smtp.port = {your_email_port}
    spring.mail.properties.mail.smtp.auth = {true|false}
    spring.mail.properties.mail.smtp.starttls.enable = {true|false}
    spring.mail.properties.mail.smtp.starttls.required = {true|false}


## Accessing the Application:
-------------------

App base url: http://localhost:5001/user


