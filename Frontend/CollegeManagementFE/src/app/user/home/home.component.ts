import { Component, OnInit } from '@angular/core';
import { StudentService } from 'src/app/service/student/student-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user: any
  name :any
  constructor(private st_service: StudentService) { 
    this.user = st_service.getData("user")
    this.name = this.user.username
    


  }

  logout() {
    localStorage.clear()
    
  }
  ngOnInit(): void {
  }

}
