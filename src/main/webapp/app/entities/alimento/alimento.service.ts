import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAlimento } from 'app/shared/model/alimento.model';

type EntityResponseType = HttpResponse<IAlimento>;
type EntityArrayResponseType = HttpResponse<IAlimento[]>;

@Injectable({ providedIn: 'root' })
export class AlimentoService {
  public resourceUrl = SERVER_API_URL + 'api/alimentos';

  constructor(protected http: HttpClient) {}

  create(alimento: IAlimento): Observable<EntityResponseType> {
    return this.http.post<IAlimento>(this.resourceUrl, alimento, { observe: 'response' });
  }

  update(alimento: IAlimento): Observable<EntityResponseType> {
    return this.http.put<IAlimento>(this.resourceUrl, alimento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlimento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlimento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
