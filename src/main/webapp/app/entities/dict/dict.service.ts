import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDict } from 'app/shared/model/dict.model';

type EntityResponseType = HttpResponse<IDict>;
type EntityArrayResponseType = HttpResponse<IDict[]>;

@Injectable({ providedIn: 'root' })
export class DictService {
    public resourceUrl = SERVER_API_URL + 'api/dicts';

    constructor(private http: HttpClient) {}

    create(dict: IDict): Observable<EntityResponseType> {
        return this.http.post<IDict>(this.resourceUrl, dict, { observe: 'response' });
    }

    update(dict: IDict): Observable<EntityResponseType> {
        return this.http.put<IDict>(this.resourceUrl, dict, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDict>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDict[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
