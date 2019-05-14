/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DiarioAlimentareTestModule } from '../../../test.module';
import { PastoUpdateComponent } from 'app/entities/pasto/pasto-update.component';
import { PastoService } from 'app/entities/pasto/pasto.service';
import { Pasto } from 'app/shared/model/pasto.model';

describe('Component Tests', () => {
  describe('Pasto Management Update Component', () => {
    let comp: PastoUpdateComponent;
    let fixture: ComponentFixture<PastoUpdateComponent>;
    let service: PastoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiarioAlimentareTestModule],
        declarations: [PastoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PastoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PastoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PastoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pasto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pasto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
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
