import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrg } from 'app/shared/model/org.model';

type EntityResponseType = HttpResponse<IOrg>;
type EntityArrayResponseType = HttpResponse<IOrg[]>;

@Injectable({ providedIn: 'root' })
export class OrgService {
    public resourceUrl = SERVER_API_URL + 'api/orgs';

    constructor(private http: HttpClient) {}

    create(org: IOrg): Observable<EntityResponseType> {
        return this.http.post<IOrg>(this.resourceUrl, org, { observe: 'response' });
    }

    update(org: IOrg): Observable<EntityResponseType> {
        return this.http.put<IOrg>(this.resourceUrl, org, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrg>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrg[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
