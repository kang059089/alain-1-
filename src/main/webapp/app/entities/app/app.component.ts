import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApp } from 'app/shared/model/app.model';
import { Principal } from 'app/core';
import { AppService } from './app.service';

@Component({
    selector: 'jhi-app',
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit, OnDestroy {
    apps: IApp[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private appService: AppService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.appService.query().subscribe(
            (res: HttpResponse<IApp[]>) => {
                this.apps = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApps();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApp) {
        return item.id;
    }

    registerChangeInApps() {
        this.eventSubscriber = this.eventManager.subscribe('appListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
