import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IButton } from 'app/shared/model/button.model';
import { ButtonService } from './button.service';

@Component({
    selector: 'jhi-button-delete-dialog',
    templateUrl: './button-delete-dialog.component.html'
})
export class ButtonDeleteDialogComponent {
    button: IButton;

    constructor(private buttonService: ButtonService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.buttonService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'buttonListModification',
                content: 'Deleted an button'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-button-delete-popup',
    template: ''
})
export class ButtonDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ button }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ButtonDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.button = button;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
