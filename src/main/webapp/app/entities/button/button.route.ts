import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Button } from 'app/shared/model/button.model';
import { ButtonService } from './button.service';
import { ButtonComponent } from './button.component';
import { ButtonDetailComponent } from './button-detail.component';
import { ButtonUpdateComponent } from './button-update.component';
import { ButtonDeletePopupComponent } from './button-delete-dialog.component';
import { IButton } from 'app/shared/model/button.model';

@Injectable({ providedIn: 'root' })
export class ButtonResolve implements Resolve<IButton> {
    constructor(private service: ButtonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Button> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Button>) => response.ok),
                map((button: HttpResponse<Button>) => button.body)
            );
        }
        return of(new Button());
    }
}

export const buttonRoute: Routes = [
    {
        path: 'button',
        component: ButtonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.button.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'button/:id/view',
        component: ButtonDetailComponent,
        resolve: {
            button: ButtonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.button.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'button/new',
        component: ButtonUpdateComponent,
        resolve: {
            button: ButtonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.button.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'button/:id/edit',
        component: ButtonUpdateComponent,
        resolve: {
            button: ButtonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.button.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const buttonPopupRoute: Routes = [
    {
        path: 'button/:id/delete',
        component: ButtonDeletePopupComponent,
        resolve: {
            button: ButtonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'alainApp.button.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
