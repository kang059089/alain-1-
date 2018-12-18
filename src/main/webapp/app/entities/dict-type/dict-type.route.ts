import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DictType } from 'app/shared/model/dict-type.model';
import { DictTypeService } from './dict-type.service';
import { DictTypeComponent } from './dict-type.component';
import { DictTypeDetailComponent } from './dict-type-detail.component';
import { DictTypeUpdateComponent } from './dict-type-update.component';
import { DictTypeDeletePopupComponent } from './dict-type-delete-dialog.component';
import { IDictType } from 'app/shared/model/dict-type.model';

@Injectable({ providedIn: 'root' })
export class DictTypeResolve implements Resolve<IDictType> {
    constructor(private service: DictTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DictType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DictType>) => response.ok),
                map((dictType: HttpResponse<DictType>) => dictType.body)
            );
        }
        return of(new DictType());
    }
}

export const dictTypeRoute: Routes = [
    {
        path: 'dict-type',
        component: DictTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dictType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dict-type/:id/view',
        component: DictTypeDetailComponent,
        resolve: {
            dictType: DictTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dictType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dict-type/new',
        component: DictTypeUpdateComponent,
        resolve: {
            dictType: DictTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dictType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dict-type/:id/edit',
        component: DictTypeUpdateComponent,
        resolve: {
            dictType: DictTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dictType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dictTypePopupRoute: Routes = [
    {
        path: 'dict-type/:id/delete',
        component: DictTypeDeletePopupComponent,
        resolve: {
            dictType: DictTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.dictType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
