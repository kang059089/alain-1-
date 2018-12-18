import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AlainAppModule } from './app/app.module';
import { AlainMenuModule } from './menu/menu.module';
import { AlainButtonModule } from './button/button.module';
import { AlainOrgModule } from './org/org.module';
import { AlainDictModule } from './dict/dict.module';
import { AlainDictTypeModule } from './dict-type/dict-type.module';
import { AlainRoleModule } from './role/role.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AlainAppModule,
        AlainMenuModule,
        AlainButtonModule,
        AlainOrgModule,
        AlainDictModule,
        AlainDictTypeModule,
        AlainRoleModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlainEntityModule {}
