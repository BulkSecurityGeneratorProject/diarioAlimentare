import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPasto } from 'app/shared/model/pasto.model';

@Component({
  selector: 'jhi-pasto-detail',
  templateUrl: './pasto-detail.component.html'
})
export class PastoDetailComponent implements OnInit {
  pasto: IPasto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pasto }) => {
      this.pasto = pasto;
    });
  }

  previousState() {
    window.history.back();
  }
}
