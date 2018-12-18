import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDictType } from 'app/shared/model/dict-type.model';
import { DictTypeService } from './dict-type.service';
import { IDict } from 'app/shared/model/dict.model';
import { DictService } from 'app/entities/dict';

@Component({
    selector: 'jhi-dict-type-update',
    templateUrl: './dict-type-update.component.html'
})
export class DictTypeUpdateComponent implements OnInit {
    dictType: IDictType;
    isSaving: boolean;

    dicts: IDict[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private dictTypeService: DictTypeService,
        private dictService: DictService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dictType }) => {
            this.dictType = dictType;
        });
        this.dictService.query().subscribe(
            (res: HttpResponse<IDict[]>) => {
                this.dicts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dictType.id !== undefined) {
            this.subscribeToSaveResponse(this.dictTypeService.update(this.dictType));
        } else {
            this.subscribeToSaveResponse(this.dictTypeService.create(this.dictType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDictType>>) {
        result.subscribe((res: HttpResponse<IDictType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDictById(index: number, item: IDict) {
        return item.id;
    }
}
