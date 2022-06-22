import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import {User} from 'src/app/model/user';

@Injectable({
  providedIn: 'root'
})
export class RestcontrollerService {
  ipadresse: any = 'http://localhost:9090';

  constructor(private httpClient: HttpClient) { }
  
  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  //Getting all cropgroups
  getAllCropGroups(): Observable<any> {
    return this.httpClient.get(this.ipadresse + '/cropgroups');
  }

  //Update specified cropgroup with different name
  updateCropGroup(cropgroup: any){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('PUT', this.ipadresse + '/cropgroups', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send(JSON.stringify({'id': cropgroup.id, 'name': cropgroup.name}));
    return JSON.parse(xmlhttp.response);
  }

  //Getting all crops
  getAllCrop(){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', this.ipadresse + '/crops', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send();
    return JSON.parse(xmlhttp.response);
    //return this.httpClient.get(this.ipadresse + '/crops');
  }

  //Update specified crop with more alternativenames
  updateCrop(crop: any){
    console.log(crop);
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('PUT', this.ipadresse + '/crops', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send(JSON.stringify({'id': crop.id, 'family':{'id':crop.family.id, 'name': crop.family.name}, 'name': crop.name, 'otherNames':[crop.otherNames]  }));
    return JSON.parse(xmlhttp.response);
  }

  //login
  getLogin(username: string, password:string){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('POST', this.ipadresse + '/login', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send(JSON.stringify({'username': username , 'password': password}));
    return JSON.parse(xmlhttp.response);
  }

  //Getting all user with their roles
  getAllUser(){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', this.ipadresse + '/users', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send();
    return JSON.parse(xmlhttp.response);
    //return [{'username': 'Xenia Wagner', 'admin': true, 'data': false, 'analyst': false}, {'username': 'Michael Wagner', 'admin': false, 'data': true, 'analyst': true}, {'username': 'Dittmar Lassen', 'admin': false, 'data': false, 'analyst': false}];
  }

  //Update/Change roles of a user
  updateUser(user: User){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('PUT', this.ipadresse + '/users', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send(JSON.stringify({'id': user.id, 'username': user.username, 'password': user.password, 'administrator': user.admin, 'analyst': user.analyst, 'datamanager': user.data}));
    //return JSON.parse(xmlhttp.response);
  }

  //Delete user from system
  deleteUser(user: User){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('DELETE', this.ipadresse + '/users', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send(JSON.stringify({'id': user.id, 'username': user.username, 'password': user.password, 'administrator': user.admin, 'analyst': user.analyst, 'datamanager': user.data}));
  }

  //Getting all available Grabber
  getAllGrabber(){
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open('GET', this.ipadresse + '/datasources', false);
    xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
    xmlhttp.send();
    return JSON.parse(xmlhttp.response);
  }

  //Getting last execution of selected Grabber
  getlastExecutionOfGrabber(id: string){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', this.ipadresse + '/grabbingjobs/'+id, false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send();
        return JSON.parse(xmlhttp.response);
  }

  //Start chosen Grabber for Load Job
  //Funktioniert; Eventuell fällt ein Rückgabetyp
  startGrabber(grabber: any){
    console.log(grabber);
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('POST', this.ipadresse + '/grab', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send(JSON.stringify({'id': grabber.id, 'name': grabber.name, 'description': grabber.description, 'url': grabber.url, 'intervallnDays': grabber.intervallnDays}));
        console.log(JSON.parse(xmlhttp.response));
        return JSON.parse(xmlhttp.response);
  }

  //Getting all Measurments
  getAllMeasurements(){
    const xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', this.ipadresse + '/measurements', false);
        xmlhttp.setRequestHeader('Content-type', 'application/json;charset=UTF-8');
        xmlhttp.send();
        return JSON.parse(xmlhttp.response);
    
  }
}
