import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { StudentService } from 'src/app/service/student/student-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model = {'email':'', "password":''}
  modelUser: any;
  errorMessage: any;
 
  constructor(private st_service: StudentService, private router: Router) { }

  onSubmit(){
    this.st_service.login(this.model).subscribe(
      data => {
        this.st_service.saveToken('token', data.token)
        this.st_service.saveData('user', data)
      },
      err => {
        this.errorMessage = err.error.message
        console.log(err)
      }
    )
  }

  ngOnInit(): void {
  }

}
 