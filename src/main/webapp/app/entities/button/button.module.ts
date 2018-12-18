import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlainSharedModule } from 'app/shared';
import {
    ButtonComponent,
    ButtonDetailComponent,
    ButtonUpdateComponent,
    ButtonDeletePopupComponent,
    ButtonDeleteDialogComponent,
    buttonRoute,
    buttonPopupRoute
} from './';

const ENTITY_STATES = [...buttonRoute, ...buttonPopupRoute];

@NgModule({
    imports: [AlainSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ButtonComponent, ButtonDetailComponent, ButtonUpdateComponent, ButtonDeleteDialogComponent, ButtonDeletePopupComponent],
    entryComponents: [ButtonComponent, ButtonUpdateComponent, ButtonDeleteDialogComponent, ButtonDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlainButtonModule {}
