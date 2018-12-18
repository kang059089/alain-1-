import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';

@Component({
    selector: 'jhi-menu-update',
    templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
    menu: IMenu;
    isSaving: boolean;

    menus: IMenu[];

    constructor(private jhiAlertService: JhiAlertService, private menuService: MenuService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
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
        if (this.menu.id !== undefined) {
            this.subscribeToSaveResponse(this.menuService.update(this.menu));
        } else {
            this.subscribeToSaveResponse(this.menuService.create(this.menu));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
        result.subscribe((res: HttpResponse<IMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
