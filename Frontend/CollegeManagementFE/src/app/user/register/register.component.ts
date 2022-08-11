import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Student } from 'src/app/data/Student';
import { StudentService } from 'src/app/service/student/student-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
}) 
export class RegisterComponent implements OnInit {

  genders = ['Male', 'FeMale', 'Other']

  model = new Student("Name", "210901", "Father", new Date,"student@gmail.com","7869334768","Gender","Dept",0,"Place","");

  submitted = false;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = ''
  constructor(private st_service: StudentService, private router: Router) { }

  onSubmit(){
    this.submitted = true
    this.st_service.register(this.model).subscribe(
      data => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.isSignUpFailed = true;
        this.errorMessage = err.error.message;
      }
    )
  }

  newStudent(){this.model = new Student('', '', '', new Date, '', '', '','',0,'','')}

  ngOnInit(): void {
  }

}
