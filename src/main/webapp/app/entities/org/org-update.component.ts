import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrg } from 'app/shared/model/org.model';
import { OrgService } from './org.service';
import { IDictType } from 'app/shared/model/dict-type.model';
import { DictTypeService } from 'app/entities/dict-type';

@Component({
    selector: 'jhi-org-update',
    templateUrl: './org-update.component.html'
})
export class OrgUpdateComponent implements OnInit {
    org: IOrg;
    isSaving: boolean;

    types: IDictType[];

    orgs: IOrg[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private orgService: OrgService,
        private dictTypeService: DictTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ org }) => {
            this.org = org;
        });
        this.dictTypeService.query({ filter: 'org-is-null' }).subscribe(
            (res: HttpResponse<IDictType[]>) => {
                if (!this.org.type || !this.org.type.id) {
                    this.types = res.body;
                } else {
                    this.dictTypeService.find(this.org.type.id).subscribe(
                        (subRes: HttpResponse<IDictType>) => {
                            this.types = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.orgService.query().subscribe(
            (res: HttpResponse<IOrg[]>) => {
                this.orgs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.org.id !== undefined) {
            this.subscribeToSaveResponse(this.orgService.update(this.org));
        } else {
            this.subscribeToSaveResponse(this.orgService.create(this.org));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrg>>) {
        result.subscribe((res: HttpResponse<IOrg>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDictTypeById(index: number, item: IDictType) {
        return item.id;
    }

    trackOrgById(index: number, item: IOrg) {
        return item.id;
    }
}
