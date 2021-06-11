# COSC2391 Further Programming Project

- Name: Monkolsophearith Prum (s3848409)
- Tutor: Estrid He
- Lecturer: Amir Homayoon Ashrafzadeh
- Assignment 2 Project

# Design
- MVC Pattern
- Singleton Pattern

By using MVC pattern, I was able to achieved great length with the project. However, to overcome user data passing from 
one controller to another, I had to implement another pattern called 'Singleton' and by using that object to store info
such as user's credentials, booking, and more.

# Difficulties
- While developing the app, I have faced challenges in using Java and SQL such as:
   - Converting date between SQL and Java
   - Working with tables and data
   - Realtime changes (For example: When locking and unlocking seats).
   - Defense mechanism against user's input 
   - Object oriented design
   - Code reuse
   
# Lesson learnt
- So far, I have gained massive knowledge towards Java, SQL, and MVC design.
- I would say, this course has taught me
  a lot in writing defensive programming language and handling errors. 
- Moreover, I was able to applied techniques and
  theories on concepts such as encapsulation, inheritance and polymorphism.

# Packaging
The main class is Main.java

Packaging for classes:
 - main.controller
   - user
   - admin
 - main.model
    - user
    - admin
 - main.object
 - main.session
 - main.ui
   - user
   - admin

Packaging for test:
 - src.test

## How to run the app
1. Have IntelliJ Idea ready, clone or download the project


2. Open project via IntelliJ, and build the project


3. Go to directory `/src/main/main.java`


4. Right click on `main.java` and select `Run`


5. Now you should be able to see the login Page

```
Login info:

Username: test
Password: test
```

## How to clean up

1. Simply click the red button on IntelliJ Idea or on the program


2. Close IntelliJ Idea, and you should be good to go.


## Screenshots

- To avoid flooding README file, please refer to project directory for screenshots of the app running


- You can find them at `/screenshots`