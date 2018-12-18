import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlainSharedModule } from 'app/shared';
import {
    OrgComponent,
    OrgDetailComponent,
    OrgUpdateComponent,
    OrgDeletePopupComponent,
    OrgDeleteDialogComponent,
    orgRoute,
    orgPopupRoute
} from './';

const ENTITY_STATES = [...orgRoute, ...orgPopupRoute];

@NgModule({
    imports: [AlainSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [OrgComponent, OrgDetailComponent, OrgUpdateComponent, OrgDeleteDialogComponent, OrgDeletePopupComponent],
    entryComponents: [OrgComponent, OrgUpdateComponent, OrgDeleteDialogComponent, OrgDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlainOrgModule {}
