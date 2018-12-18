/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AlainTestModule } from '../../../test.module';
import { ButtonDeleteDialogComponent } from 'app/entities/button/button-delete-dialog.component';
import { ButtonService } from 'app/entities/button/button.service';

describe('Component Tests', () => {
    describe('Button Management Delete Component', () => {
        let comp: ButtonDeleteDialogComponent;
        let fixture: ComponentFixture<ButtonDeleteDialogComponent>;
        let service: ButtonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AlainTestModule],
                declarations: [ButtonDeleteDialogComponent]
            })
                .overrideTemplate(ButtonDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ButtonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ButtonService);
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
