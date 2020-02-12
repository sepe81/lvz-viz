import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Article } from './article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
  }

  getx(page: string, size: string, sort: string) {
    const options = {
      params: new HttpParams().set('page', page).set('size', size).set('sort', sort)
    };
    return this.http.get<Article[]>(this.apiUrl + '/getx', options);
  }
}
