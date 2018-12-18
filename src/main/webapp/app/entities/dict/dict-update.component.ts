import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDict } from 'app/shared/model/dict.model';
import { DictService } from './dict.service';

@Component({
    selector: 'jhi-dict-update',
    templateUrl: './dict-update.component.html'
})
export class DictUpdateComponent implements OnInit {
    dict: IDict;
    isSaving: boolean;

    constructor(private dictService: DictService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dict }) => {
            this.dict = dict;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dict.id !== undefined) {
            this.subscribeToSaveResponse(this.dictService.update(this.dict));
        } else {
            this.subscribeToSaveResponse(this.dictService.create(this.dict));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDict>>) {
        result.subscribe((res: HttpResponse<IDict>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
