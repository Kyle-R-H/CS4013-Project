CS4013 Software Development Project 
= 
*UL Student Records System* - Group 8 

## Overview 

A UL Student Records System for both students and administrators to view or alter student records depending on login type. 

## How to Use 
Run the program and chose the type of user you are: 
* Student 
* Administrator 

Data for student and administrator logins are both in their individual csv files titled 'Student.csv' and 'Administrator.csv'. Additional logins can be added here. 

Student information is split between three files 'Courses.csv', 'Modules.csv' and 'Student.csv'. 

* Courses.csv contains the student's course code, the number of semesters, the grade of each module per semester and its calculated QCA. To add information to a student you must follow the formatting convention shown in the file where the number of rows below the student ID is equivalent to the number of semesters which should also be the figure to the index right of the student number while on the left should be the student's course code. Only add grade for the number of modules of the course and leave the rest of the columns blank while adding the "`,`"s to ensure consistency. 

* Modules.csv contains a course header which contains the course code, course route, course name and a number of course years in the format of `yyyy/yy` equivalent to the total semesters in the 'Courses.csv' file. Below this contains 3 sections which equal in rows which contain the modules codes per semester, the corresponding modules' name, and the module credits. Each row of all three sections must contain the same number of columns, for example, the row starting with `1` must have the same number of columns as the row starting with `11` and `21` and must repeat for the number of semesters remaining.  

* Student.csv contains the student numbers, the student's password, the students' prefix, forename, surname, and the course they are registered to. To add information to this file, follow the user formatting prior. 

### **Student:** 
To log into a student account and view your academic records you choose the "Student Login" button, and it will direct you to the student login page. In this page you can either choose to get back to the homepage using the "Home" button or enter your credentials into the two text fields. Once you enter correct credentials and click the "Login" button, you will be directed to another page where you will see your student transcript. On this page you will still have the choice to go "Home" unless you decide to close the page. 

### **Administrator:** 
To log into an administrator account, view a specific student's results, and alter it if needed you can choose the "Admin Login" button and it will direct you to the admin login page. From here you can input your admin email and associated password into the two text fields and press "Login" to enter or press the "Home" button to return to the homepage. Once the credentials are correct and you are logged in you will be redirected to the admin "Control panel/Page". Here you will see three choice boxes (ComboBoxes) at the top of the page, a small text field on the left under which there is another choice box and two small buttons. Taking up most of your interface will be a large uneditable tetField that will allow the user to view data later on. As you will see these data selectors and empty fields will have what they contain written on them. Fill them out in order and you will see them all populated with options. Once you've navigated to the course then to the student then to the semester you will see the larger text area become populated with all of the modules that the student is undertaking and the modules respective current grades, to alter a grade for a module select a module from the choice box, then enter the grade correction in the text box above this and press the "Save" button, to update the students final QCA for the semester press the "Update QCA" button. If you would like to refresh the textArea on the right reselect the semester in the choice box. 

### **Examples** 

#### **Student:** 

Enter a valid 8-digit student number and the correct password which is assigned in the Students.csv file. 

#### **Administrator:** 

Enter a valid administrator email address (Present in Administrators.csv) and password, make a selection in each choice box in order type in the grade change and press the "Save" button. 

If QCA Calculation is required also press the "Update QCA" button. 

## Credits 
  - David Bracken - 22342044 
  - Kyle Hellstrom - 22343261 
  - Sean Mulcahy - 22354166 
  - Sean Fitzgerald - 22370595 