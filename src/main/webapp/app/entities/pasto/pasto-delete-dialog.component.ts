import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPasto } from 'app/shared/model/pasto.model';
import { PastoService } from './pasto.service';

@Component({
  selector: 'jhi-pasto-delete-dialog',
  templateUrl: './pasto-delete-dialog.component.html'
})
export class PastoDeleteDialogComponent {
  pasto: IPasto;

  constructor(protected pastoService: PastoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pastoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pastoListModification',
        content: 'Deleted an pasto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pasto-delete-popup',
  template: ''
})
export class PastoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pasto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PastoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pasto = pasto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pasto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pasto', { outlets: { popup: null } }]);
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
