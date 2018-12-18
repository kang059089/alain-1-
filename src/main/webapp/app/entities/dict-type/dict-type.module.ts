import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlainSharedModule } from 'app/shared';
import {
    DictTypeComponent,
    DictTypeDetailComponent,
    DictTypeUpdateComponent,
    DictTypeDeletePopupComponent,
    DictTypeDeleteDialogComponent,
    dictTypeRoute,
    dictTypePopupRoute
} from './';

const ENTITY_STATES = [...dictTypeRoute, ...dictTypePopupRoute];

@NgModule({
    imports: [AlainSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DictTypeComponent,
        DictTypeDetailComponent,
        DictTypeUpdateComponent,
        DictTypeDeleteDialogComponent,
        DictTypeDeletePopupComponent
    ],
    entryComponents: [DictTypeComponent, DictTypeUpdateComponent, DictTypeDeleteDialogComponent, DictTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlainDictTypeModule {}
