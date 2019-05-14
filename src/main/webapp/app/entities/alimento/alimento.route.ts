import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Alimento } from 'app/shared/model/alimento.model';
import { AlimentoService } from './alimento.service';
import { AlimentoComponent } from './alimento.component';
import { AlimentoDetailComponent } from './alimento-detail.component';
import { AlimentoUpdateComponent } from './alimento-update.component';
import { AlimentoDeletePopupComponent } from './alimento-delete-dialog.component';
import { IAlimento } from 'app/shared/model/alimento.model';

@Injectable({ providedIn: 'root' })
export class AlimentoResolve implements Resolve<IAlimento> {
  constructor(private service: AlimentoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAlimento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Alimento>) => response.ok),
        map((alimento: HttpResponse<Alimento>) => alimento.body)
      );
    }
    return of(new Alimento());
  }
}

export const alimentoRoute: Routes = [
  {
    path: '',
    component: AlimentoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.alimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlimentoDetailComponent,
    resolve: {
      alimento: AlimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.alimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlimentoUpdateComponent,
    resolve: {
      alimento: AlimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.alimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlimentoUpdateComponent,
    resolve: {
      alimento: AlimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.alimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const alimentoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AlimentoDeletePopupComponent,
    resolve: {
      alimento: AlimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.alimento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
