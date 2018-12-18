/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlainTestModule } from '../../../test.module';
import { OrgDetailComponent } from 'app/entities/org/org-detail.component';
import { Org } from 'app/shared/model/org.model';

describe('Component Tests', () => {
    describe('Org Management Detail Component', () => {
        let comp: OrgDetailComponent;
        let fixture: ComponentFixture<OrgDetailComponent>;
        const route = ({ data: of({ org: new Org(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlainTestModule],
                declarations: [OrgDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrgDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrgDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.org).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
