import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IButton } from 'app/shared/model/button.model';
import { ButtonService } from './button.service';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';

@Component({
    selector: 'jhi-button-update',
    templateUrl: './button-update.component.html'
})
export class ButtonUpdateComponent implements OnInit {
    button: IButton;
    isSaving: boolean;

    menus: IMenu[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private buttonService: ButtonService,
        private menuService: MenuService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ button }) => {
            this.button = button;
        });
        this.menuService.query().subscribe(
            (res: HttpResponse<IMenu[]>) => {
                this.menus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.button.id !== undefined) {
            this.subscribeToSaveResponse(this.buttonService.update(this.button));
        } else {
            this.subscribeToSaveResponse(this.buttonService.create(this.button));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IButton>>) {
        result.subscribe((res: HttpResponse<IButton>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMenuById(index: number, item: IMenu) {
        return item.id;
    }
}
