/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlainTestModule } from '../../../test.module';
import { DictDetailComponent } from 'app/entities/dict/dict-detail.component';
import { Dict } from 'app/shared/model/dict.model';

describe('Component Tests', () => {
    describe('Dict Management Detail Component', () => {
        let comp: DictDetailComponent;
        let fixture: ComponentFixture<DictDetailComponent>;
        const route = ({ data: of({ dict: new Dict(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlainTestModule],
                declarations: [DictDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DictDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DictDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dict).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
