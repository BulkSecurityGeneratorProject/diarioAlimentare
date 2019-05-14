import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DiarioAlimentareSharedModule } from 'app/shared';
import {
  PastoComponent,
  PastoDetailComponent,
  PastoUpdateComponent,
  PastoDeletePopupComponent,
  PastoDeleteDialogComponent,
  pastoRoute,
  pastoPopupRoute
} from './';

const ENTITY_STATES = [...pastoRoute, ...pastoPopupRoute];

@NgModule({
  imports: [DiarioAlimentareSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PastoComponent, PastoDetailComponent, PastoUpdateComponent, PastoDeleteDialogComponent, PastoDeletePopupComponent],
  entryComponents: [PastoComponent, PastoUpdateComponent, PastoDeleteDialogComponent, PastoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiarioAlimentarePastoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
