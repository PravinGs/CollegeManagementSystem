import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  baseUrl = "http://localhost:8080"
  constructor(private http: HttpClient) { }

  public registerStaff(model: any) : Observable<any>{
    console.log("Service Getting called")
    return this.http.post<any>(this.baseUrl+"/accountant/register", model)
  }
}
