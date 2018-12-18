import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Org } from 'app/shared/model/org.model';
import { OrgService } from './org.service';
import { OrgComponent } from './org.component';
import { OrgDetailComponent } from './org-detail.component';
import { OrgUpdateComponent } from './org-update.component';
import { OrgDeletePopupComponent } from './org-delete-dialog.component';
import { IOrg } from 'app/shared/model/org.model';

@Injectable({ providedIn: 'root' })
export class OrgResolve implements Resolve<IOrg> {
    constructor(private service: OrgService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Org> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Org>) => response.ok),
                map((org: HttpResponse<Org>) => org.body)
            );
        }
        return of(new Org());
    }
}

export const orgRoute: Routes = [
    {
        path: 'org',
        component: OrgComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.org.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'org/:id/view',
        component: OrgDetailComponent,
        resolve: {
            org: OrgResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.org.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'org/new',
        component: OrgUpdateComponent,
        resolve: {
            org: OrgResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.org.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'org/:id/edit',
        component: OrgUpdateComponent,
        resolve: {
            org: OrgResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.org.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orgPopupRoute: Routes = [
    {
        path: 'org/:id/delete',
        component: OrgDeletePopupComponent,
        resolve: {
            org: OrgResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.org.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
