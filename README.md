# COSC2391 Further Programming Project

- Name: Monkolsophearith Prum (s3848409)
- Tutor: Estrid He
- Lecturer: Amir Homayoon Ashrafzadeh

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
   
# Lesson Learnt
- So far, I have gained massive knowledge towards Java, SQL, and MVC design. I would say, this course has taught me
a lot in writing defensive programming language and handling errors. 

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

## How to clone the project using intelliJIDEA and RUN the application
1- Download IntelliJ IDEA Ultimate Version (You had to apply for student license)

2- Open IntelliJ IDEA, select "File" from the top menu, select "New" and select "Project from Version Control"  

3- Copy your Github classroom repository and paste into URL, click on "Clone".
 Your project will be cloned and open in your IntelliJ IDEA window.
 
 However, you still need to add the SQLite jar file to your project so you can have access to your database. Follow next steps for adding the Jar file:
 
1- Download the SQLite JDBC jar file from week 7 Canvas module.

2- In your project under project root, make a new directory called lib and move the jar file into lib folder

3- Open IntelliJ IDEA, click on "File", open "Project Structure"

4- Under "Project Setting", select "Libraries"

5- Click + button, choose Java, and navigate to your project folder, then Lib folder, choose "sqlite-jdbc-3.34.0.jar", and click on "open"

6- Click on Apply and then OK to close the window

Now you are ready to Run the Application.

Simply right click on Main.java and choose Run.
Congratulations!

```
Login info:

Username: test
Password: test
```

## Prepare other content

Readme files are made for developers (including you), but also could be used for the final users.
So while you are writing your readme files please consider a few things:

1. What is about?
    - Your name and student number and course name on the top
    - Describe the content of your project or repository
    - Explain things the users would have a hard time understanding right away
2. What steps need to be taken?
    - Any specific steps for running your application, what is the main class?
    - Is there any requirements or dependencies?
    - After the installation, how they compile or run the code?
3. Execution examples
    - You could provide examples of execution with code and screenshots
    

other things you could add:

- Table of content
- Test cases
- Know bugs
- Things that have not been working or complete

