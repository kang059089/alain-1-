import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDictType } from 'app/shared/model/dict-type.model';

type EntityResponseType = HttpResponse<IDictType>;
type EntityArrayResponseType = HttpResponse<IDictType[]>;

@Injectable({ providedIn: 'root' })
export class DictTypeService {
    public resourceUrl = SERVER_API_URL + 'api/dict-types';

    constructor(private http: HttpClient) {}

    create(dictType: IDictType): Observable<EntityResponseType> {
        return this.http.post<IDictType>(this.resourceUrl, dictType, { observe: 'response' });
    }

    update(dictType: IDictType): Observable<EntityResponseType> {
        return this.http.put<IDictType>(this.resourceUrl, dictType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDictType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDictType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
