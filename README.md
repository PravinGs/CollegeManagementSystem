# CollegeManagementSystem
This project manages all the day-to-day college works of both staff's and students from attendance to fees payment and Academic tracker of the student. This REST API implements the spring security with JWT token implementation.
It helps to optimize the college regular works
## Technologies Used
* Spring Boot - version 2.7.2
* Java - version 17
* MySQL - version 8.1.2
* Hibernate - version 6.0
* Spring Data Jpa - version 2.1

## **Modules** 
* #### **Student**
* #### **Teacher**
* #### **Accountant**
## **Student**
* Student module manages all the work related to students. 
* Regular Tasks assigned by their respective class Teacher.
* View the statistics of the academic details.
* Accountant work will be notified from the accountant.
* Every Communication to be handled through Email.
## **Teacher**
  * Allowed to give Attendance.
  * Created method to manage course details like 
     - Adding subject to the respective courses
  * Assign exam to the students.
  * Allowed to add and remove students.
  * Allowed to change subjects.
## Accountant
  * Accountant allowed to do all the operations on the student module.
  * Accountant allowed to create and manage student's Fees details.
  * Accountant have to report the permission to the admin.
