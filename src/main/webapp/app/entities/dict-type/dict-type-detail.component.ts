import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDictType } from 'app/shared/model/dict-type.model';

@Component({
    selector: 'jhi-dict-type-detail',
    templateUrl: './dict-type-detail.component.html'
})
export class DictTypeDetailComponent implements OnInit {
    dictType: IDictType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dictType }) => {
            this.dictType = dictType;
        });
    }

    previousState() {
        window.history.back();
    }
}
