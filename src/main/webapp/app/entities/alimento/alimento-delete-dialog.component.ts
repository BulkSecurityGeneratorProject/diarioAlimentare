import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlimento } from 'app/shared/model/alimento.model';
import { AlimentoService } from './alimento.service';

@Component({
  selector: 'jhi-alimento-delete-dialog',
  templateUrl: './alimento-delete-dialog.component.html'
})
export class AlimentoDeleteDialogComponent {
  alimento: IAlimento;

  constructor(protected alimentoService: AlimentoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.alimentoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'alimentoListModification',
        content: 'Deleted an alimento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-alimento-delete-popup',
  template: ''
})
export class AlimentoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ alimento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AlimentoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.alimento = alimento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/alimento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/alimento', { outlets: { popup: null } }]);
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
