import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrg } from 'app/shared/model/org.model';

@Component({
    selector: 'jhi-org-detail',
    templateUrl: './org-detail.component.html'
})
export class OrgDetailComponent implements OnInit {
    org: IOrg;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ org }) => {
            this.org = org;
        });
    }

    previousState() {
        window.history.back();
    }
}
