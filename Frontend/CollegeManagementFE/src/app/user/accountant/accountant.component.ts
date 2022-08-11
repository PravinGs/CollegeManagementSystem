import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/service/admin/adminservice.service';

@Component({
  selector: 'app-accountant',
  templateUrl: './accountant.component.html',
  styleUrls: ['./accountant.component.css']
})
export class AccountantComponent implements OnInit {

  model = {'name':'Name', 'email':'staff@gmail.com', 'password':'password', 'role':'ROLE_STAFF'}
  constructor(private ad_service: AdminService) { }

  onSubmit(){
    console.log(this.model)
    this.ad_service.registerStaff(this.model).subscribe(data => console.log(JSON.stringify(data)))
  }
  ngOnInit(): void {
  }

}
