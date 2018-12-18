import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRole } from 'app/shared/model/role.model';
import { Principal } from 'app/core';
import { RoleService } from './role.service';

@Component({
    selector: 'jhi-role',
    templateUrl: './role.component.html'
})
export class RoleComponent implements OnInit, OnDestroy {
    roles: IRole[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private roleService: RoleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.roleService.query().subscribe(
            (res: HttpResponse<IRole[]>) => {
                this.roles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRoles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRole) {
        return item.id;
    }

    registerChangeInRoles() {
        this.eventSubscriber = this.eventManager.subscribe('roleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
