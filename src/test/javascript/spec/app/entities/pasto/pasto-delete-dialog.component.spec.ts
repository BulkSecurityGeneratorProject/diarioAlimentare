/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiarioAlimentareTestModule } from '../../../test.module';
import { PastoDeleteDialogComponent } from 'app/entities/pasto/pasto-delete-dialog.component';
import { PastoService } from 'app/entities/pasto/pasto.service';

describe('Component Tests', () => {
  describe('Pasto Management Delete Component', () => {
    let comp: PastoDeleteDialogComponent;
    let fixture: ComponentFixture<PastoDeleteDialogComponent>;
    let service: PastoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiarioAlimentareTestModule],
        declarations: [PastoDeleteDialogComponent]
      })
        .overrideTemplate(PastoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PastoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PastoService);
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
