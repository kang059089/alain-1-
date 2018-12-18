import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMenu } from 'app/shared/model/menu.model';

@Component({
    selector: 'jhi-menu-detail',
    templateUrl: './menu-detail.component.html'
})
export class MenuDetailComponent implements OnInit {
    menu: IMenu;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
        });
    }

    previousState() {
        window.history.back();
    }
}
