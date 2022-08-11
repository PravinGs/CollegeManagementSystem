import { Component, OnInit, ViewChild } from '@angular/core';
import { StudentService } from 'src/app/service/student/student-service.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  user: any
  name :any
  constructor(private st_service: StudentService) { 
    this.user = st_service.getData("user")
    this.name = this.user.username
  }

  logoutAndRemoveCache(){
    localStorage.clear()
  }
  ngOnInit(): void {
  }

}
