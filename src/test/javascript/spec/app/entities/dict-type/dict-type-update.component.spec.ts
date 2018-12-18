/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AlainTestModule } from '../../../test.module';
import { DictTypeUpdateComponent } from 'app/entities/dict-type/dict-type-update.component';
import { DictTypeService } from 'app/entities/dict-type/dict-type.service';
import { DictType } from 'app/shared/model/dict-type.model';

describe('Component Tests', () => {
    describe('DictType Management Update Component', () => {
        let comp: DictTypeUpdateComponent;
        let fixture: ComponentFixture<DictTypeUpdateComponent>;
        let service: DictTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlainTestModule],
                declarations: [DictTypeUpdateComponent]
            })
                .overrideTemplate(DictTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DictTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DictTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DictType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dictType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DictType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dictType = entity;
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
