import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDict } from 'app/shared/model/dict.model';

@Component({
    selector: 'jhi-dict-detail',
    templateUrl: './dict-detail.component.html'
})
export class DictDetailComponent implements OnInit {
    dict: IDict;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dict }) => {
            this.dict = dict;
        });
    }

    previousState() {
        window.history.back();
    }
}
