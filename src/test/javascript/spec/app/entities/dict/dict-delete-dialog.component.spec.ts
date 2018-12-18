/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AlainTestModule } from '../../../test.module';
import { DictDeleteDialogComponent } from 'app/entities/dict/dict-delete-dialog.component';
import { DictService } from 'app/entities/dict/dict.service';

describe('Component Tests', () => {
    describe('Dict Management Delete Component', () => {
        let comp: DictDeleteDialogComponent;
        let fixture: ComponentFixture<DictDeleteDialogComponent>;
        let service: DictService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlainTestModule],
                declarations: [DictDeleteDialogComponent]
            })
                .overrideTemplate(DictDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DictDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DictService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
