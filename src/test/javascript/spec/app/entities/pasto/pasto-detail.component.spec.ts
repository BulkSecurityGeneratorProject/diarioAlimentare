/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiarioAlimentareTestModule } from '../../../test.module';
import { PastoDetailComponent } from 'app/entities/pasto/pasto-detail.component';
import { Pasto } from 'app/shared/model/pasto.model';

describe('Component Tests', () => {
  describe('Pasto Management Detail Component', () => {
    let comp: PastoDetailComponent;
    let fixture: ComponentFixture<PastoDetailComponent>;
    const route = ({ data: of({ pasto: new Pasto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiarioAlimentareTestModule],
        declarations: [PastoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PastoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PastoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pasto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
