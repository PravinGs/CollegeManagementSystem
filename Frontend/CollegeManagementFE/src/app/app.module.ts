import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StudentService } from './service/student/student-service.service';
import { RegisterComponent } from './user/register/register.component';
import { LoginComponent } from './user/login/login.component';
import { LabelFLow } from './user/login/label-span-directive';
import { HomeComponent } from './user/home/home.component';
import { NavBarComponent } from './user/nav-bar/nav-bar.component';
import { LabelDirective } from './label.directive';
import { AccountantComponent } from './user/accountant/accountant.component';
import { AdminService } from './service/admin/adminservice.service';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    LabelFLow,
    HomeComponent,
    NavBarComponent,
    LabelDirective,
    AccountantComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
     {path:"test", component:NavBarComponent},
     {path:"", component:HomeComponent},
     {path:"register", component:RegisterComponent},
     {path:"login", component:LoginComponent},
     {path:"admin", component:AccountantComponent}
    ])
  ],
  providers: [StudentService, AdminService],
  bootstrap: [AppComponent]
})
export class AppModule { }
