/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DiarioAlimentareTestModule } from '../../../test.module';
import { AlimentoUpdateComponent } from 'app/entities/alimento/alimento-update.component';
import { AlimentoService } from 'app/entities/alimento/alimento.service';
import { Alimento } from 'app/shared/model/alimento.model';

describe('Component Tests', () => {
  describe('Alimento Management Update Component', () => {
    let comp: AlimentoUpdateComponent;
    let fixture: ComponentFixture<AlimentoUpdateComponent>;
    let service: AlimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiarioAlimentareTestModule],
        declarations: [AlimentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlimentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlimentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlimentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alimento(123);
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
        const entity = new Alimento();
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
