/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AlainTestModule } from '../../../test.module';
import { DictUpdateComponent } from 'app/entities/dict/dict-update.component';
import { DictService } from 'app/entities/dict/dict.service';
import { Dict } from 'app/shared/model/dict.model';

describe('Component Tests', () => {
    describe('Dict Management Update Component', () => {
        let comp: DictUpdateComponent;
        let fixture: ComponentFixture<DictUpdateComponent>;
        let service: DictService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlainTestModule],
                declarations: [DictUpdateComponent]
            })
                .overrideTemplate(DictUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DictUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DictService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Dict(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dict = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Dict();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dict = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
