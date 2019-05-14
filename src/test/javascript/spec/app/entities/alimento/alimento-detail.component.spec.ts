/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiarioAlimentareTestModule } from '../../../test.module';
import { AlimentoDetailComponent } from 'app/entities/alimento/alimento-detail.component';
import { Alimento } from 'app/shared/model/alimento.model';

describe('Component Tests', () => {
  describe('Alimento Management Detail Component', () => {
    let comp: AlimentoDetailComponent;
    let fixture: ComponentFixture<AlimentoDetailComponent>;
    const route = ({ data: of({ alimento: new Alimento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiarioAlimentareTestModule],
        declarations: [AlimentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlimentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlimentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alimento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
