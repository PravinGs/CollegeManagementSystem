import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Student } from 'src/app/data/Student';
import { BehaviorSubject, catchError, Observable, retry, throwError } from 'rxjs';
import  *  as CryptoJS from  'crypto-js';
import { Token } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  baseUrl = "http://localhost:8080"
  key = '123'

  constructor(private http: HttpClient) { 

  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
  public register(model: Student) : Observable<any> {
    const email = JSON.parse(this.getData('user')).email
    const password = JSON.parse(this.getData('user')).password
    const jwtToken = JSON.parse(this.getData("user")).token;
    const headers = new HttpHeaders({Authorization: 'Bearer ' +jwtToken })
    return this.http.post(this.baseUrl+"/student/register",model,{headers}).pipe(
      retry(3),
      catchError(this.handleError)
      )
  }
  public login(model:any) : Observable<any>{
    console.log(model)
    // const headers = new HttpHeaders({Authorization: 'Basic '+btoa(model.email+":"+model.password)})
    return this.http.post(this.baseUrl + "/accountant/login",model)
  }
  private encrypt(txt:string): string {
    return CryptoJS.AES.encrypt(txt,this.key).toString()
  }
  private decrypt(txt:string): string {
    return CryptoJS.AES.decrypt(txt, this.key).toString(CryptoJS.enc.Utf8)
  }
  public saveData(key:string, value:string) {
    localStorage.removeItem(key);
    localStorage.setItem(key, this.encrypt(value))
  }
  public saveToken(key:string, value:string) {
    localStorage.removeItem(key);
    localStorage.setItem(key, this.encrypt(value))
  }
  public getData(key: string) {
    let data = localStorage.getItem(key)|| "";
    return this.decrypt(data);
  }
  public removeData(key: string) {
    localStorage.removeItem(key)
  }
  public clearData() {
    localStorage.clear()
  }
}
