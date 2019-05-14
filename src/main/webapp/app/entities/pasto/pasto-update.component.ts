import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPasto, Pasto } from 'app/shared/model/pasto.model';
import { PastoService } from './pasto.service';
import { IAlimento } from 'app/shared/model/alimento.model';
import { AlimentoService } from 'app/entities/alimento';

@Component({
  selector: 'jhi-pasto-update',
  templateUrl: './pasto-update.component.html'
})
export class PastoUpdateComponent implements OnInit {
  pasto: IPasto;
  isSaving: boolean;

  alimentos: IAlimento[];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    data: [],
    quantita: [],
    alimentoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pastoService: PastoService,
    protected alimentoService: AlimentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pasto }) => {
      this.updateForm(pasto);
      this.pasto = pasto;
    });
    this.alimentoService
      .query({ filter: 'pasto-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAlimento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAlimento[]>) => response.body)
      )
      .subscribe(
        (res: IAlimento[]) => {
          if (!this.pasto.alimentoId) {
            this.alimentos = res;
          } else {
            this.alimentoService
              .find(this.pasto.alimentoId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAlimento>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAlimento>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAlimento) => (this.alimentos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(pasto: IPasto) {
    this.editForm.patchValue({
      id: pasto.id,
      data: pasto.data,
      quantita: pasto.quantita,
      alimentoId: pasto.alimentoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pasto = this.createFromForm();
    if (pasto.id !== undefined) {
      this.subscribeToSaveResponse(this.pastoService.update(pasto));
    } else {
      this.subscribeToSaveResponse(this.pastoService.create(pasto));
    }
  }

  private createFromForm(): IPasto {
    const entity = {
      ...new Pasto(),
      id: this.editForm.get(['id']).value,
      data: this.editForm.get(['data']).value,
      quantita: this.editForm.get(['quantita']).value,
      alimentoId: this.editForm.get(['alimentoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPasto>>) {
    result.subscribe((res: HttpResponse<IPasto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAlimentoById(index: number, item: IAlimento) {
    return item.id;
  }
}
