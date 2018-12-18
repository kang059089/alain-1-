import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IButton } from 'app/shared/model/button.model';
import { Principal } from 'app/core';
import { ButtonService } from './button.service';

@Component({
    selector: 'jhi-button',
    templateUrl: './button.component.html'
})
export class ButtonComponent implements OnInit, OnDestroy {
    buttons: IButton[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private buttonService: ButtonService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.buttonService.query().subscribe(
            (res: HttpResponse<IButton[]>) => {
                this.buttons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInButtons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IButton) {
        return item.id;
    }

    registerChangeInButtons() {
        this.eventSubscriber = this.eventManager.subscribe('buttonListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
