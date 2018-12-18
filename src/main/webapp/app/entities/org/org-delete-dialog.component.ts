import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrg } from 'app/shared/model/org.model';
import { OrgService } from './org.service';

@Component({
    selector: 'jhi-org-delete-dialog',
    templateUrl: './org-delete-dialog.component.html'
})
export class OrgDeleteDialogComponent {
    org: IOrg;

    constructor(private orgService: OrgService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orgService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orgListModification',
                content: 'Deleted an org'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-org-delete-popup',
    template: ''
})
export class OrgDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ org }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrgDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.org = org;
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
