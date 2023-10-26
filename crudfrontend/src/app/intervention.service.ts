import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InterventionService {

  private baseURL = "http://localhost:8080/api/intervention";

  constructor(private httpClient: HttpClient) { 
  }
}
