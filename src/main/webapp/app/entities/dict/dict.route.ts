import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dict } from 'app/shared/model/dict.model';
import { DictService } from './dict.service';
import { DictComponent } from './dict.component';
import { DictDetailComponent } from './dict-detail.component';
import { DictUpdateComponent } from './dict-update.component';
import { DictDeletePopupComponent } from './dict-delete-dialog.component';
import { IDict } from 'app/shared/model/dict.model';

@Injectable({ providedIn: 'root' })
export class DictResolve implements Resolve<IDict> {
    constructor(private service: DictService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Dict> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Dict>) => response.ok),
                map((dict: HttpResponse<Dict>) => dict.body)
            );
        }
        return of(new Dict());
    }
}

export const dictRoute: Routes = [
    {
        path: 'dict',
        component: DictComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dict/:id/view',
        component: DictDetailComponent,
        resolve: {
            dict: DictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dict/new',
        component: DictUpdateComponent,
        resolve: {
            dict: DictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dict/:id/edit',
        component: DictUpdateComponent,
        resolve: {
            dict: DictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dict.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dictPopupRoute: Routes = [
    {
        path: 'dict/:id/delete',
        component: DictDeletePopupComponent,
        resolve: {
            dict: DictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dict.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
