/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlainTestModule } from '../../../test.module';
import { DictTypeDetailComponent } from 'app/entities/dict-type/dict-type-detail.component';
import { DictType } from 'app/shared/model/dict-type.model';

describe('Component Tests', () => {
    describe('DictType Management Detail Component', () => {
        let comp: DictTypeDetailComponent;
        let fixture: ComponentFixture<DictTypeDetailComponent>;
        const route = ({ data: of({ dictType: new DictType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlainTestModule],
                declarations: [DictTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DictTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DictTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dictType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
