import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlainSharedModule } from 'app/shared';
import {
    DictComponent,
    DictDetailComponent,
    DictUpdateComponent,
    DictDeletePopupComponent,
    DictDeleteDialogComponent,
    dictRoute,
    dictPopupRoute
} from './';

const ENTITY_STATES = [...dictRoute, ...dictPopupRoute];

@NgModule({
    imports: [AlainSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DictComponent, DictDetailComponent, DictUpdateComponent, DictDeleteDialogComponent, DictDeletePopupComponent],
    entryComponents: [DictComponent, DictUpdateComponent, DictDeleteDialogComponent, DictDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlainDictModule {}
