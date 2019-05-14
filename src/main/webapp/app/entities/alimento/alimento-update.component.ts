import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAlimento, Alimento } from 'app/shared/model/alimento.model';
import { AlimentoService } from './alimento.service';

@Component({
  selector: 'jhi-alimento-update',
  templateUrl: './alimento-update.component.html'
})
export class AlimentoUpdateComponent implements OnInit {
  alimento: IAlimento;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [],
    descrizione: []
  });

  constructor(protected alimentoService: AlimentoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ alimento }) => {
      this.updateForm(alimento);
      this.alimento = alimento;
    });
  }

  updateForm(alimento: IAlimento) {
    this.editForm.patchValue({
      id: alimento.id,
      nome: alimento.nome,
      descrizione: alimento.descrizione
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const alimento = this.createFromForm();
    if (alimento.id !== undefined) {
      this.subscribeToSaveResponse(this.alimentoService.update(alimento));
    } else {
      this.subscribeToSaveResponse(this.alimentoService.create(alimento));
    }
  }

  private createFromForm(): IAlimento {
    const entity = {
      ...new Alimento(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descrizione: this.editForm.get(['descrizione']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlimento>>) {
    result.subscribe((res: HttpResponse<IAlimento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
