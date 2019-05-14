import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPasto } from 'app/shared/model/pasto.model';

type EntityResponseType = HttpResponse<IPasto>;
type EntityArrayResponseType = HttpResponse<IPasto[]>;

@Injectable({ providedIn: 'root' })
export class PastoService {
  public resourceUrl = SERVER_API_URL + 'api/pastos';

  constructor(protected http: HttpClient) {}

  create(pasto: IPasto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pasto);
    return this.http
      .post<IPasto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pasto: IPasto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pasto);
    return this.http
      .put<IPasto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPasto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPasto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pasto: IPasto): IPasto {
    const copy: IPasto = Object.assign({}, pasto, {
      data: pasto.data != null && pasto.data.isValid() ? pasto.data.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pasto: IPasto) => {
        pasto.data = pasto.data != null ? moment(pasto.data) : null;
      });
    }
    return res;
  }
}
