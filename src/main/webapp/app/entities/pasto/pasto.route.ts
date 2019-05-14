import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pasto } from 'app/shared/model/pasto.model';
import { PastoService } from './pasto.service';
import { PastoComponent } from './pasto.component';
import { PastoDetailComponent } from './pasto-detail.component';
import { PastoUpdateComponent } from './pasto-update.component';
import { PastoDeletePopupComponent } from './pasto-delete-dialog.component';
import { IPasto } from 'app/shared/model/pasto.model';

@Injectable({ providedIn: 'root' })
export class PastoResolve implements Resolve<IPasto> {
  constructor(private service: PastoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPasto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pasto>) => response.ok),
        map((pasto: HttpResponse<Pasto>) => pasto.body)
      );
    }
    return of(new Pasto());
  }
}

export const pastoRoute: Routes = [
  {
    path: '',
    component: PastoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.pasto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PastoDetailComponent,
    resolve: {
      pasto: PastoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.pasto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PastoUpdateComponent,
    resolve: {
      pasto: PastoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.pasto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PastoUpdateComponent,
    resolve: {
      pasto: PastoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.pasto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pastoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PastoDeletePopupComponent,
    resolve: {
      pasto: PastoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'diarioAlimentareApp.pasto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
