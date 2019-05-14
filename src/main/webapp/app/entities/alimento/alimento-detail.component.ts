import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlimento } from 'app/shared/model/alimento.model';

@Component({
  selector: 'jhi-alimento-detail',
  templateUrl: './alimento-detail.component.html'
})
export class AlimentoDetailComponent implements OnInit {
  alimento: IAlimento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ alimento }) => {
      this.alimento = alimento;
    });
  }

  previousState() {
    window.history.back();
  }
}
