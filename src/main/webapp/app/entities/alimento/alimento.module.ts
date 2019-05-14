import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DiarioAlimentareSharedModule } from 'app/shared';
import {
  AlimentoComponent,
  AlimentoDetailComponent,
  AlimentoUpdateComponent,
  AlimentoDeletePopupComponent,
  AlimentoDeleteDialogComponent,
  alimentoRoute,
  alimentoPopupRoute
} from './';

const ENTITY_STATES = [...alimentoRoute, ...alimentoPopupRoute];

@NgModule({
  imports: [DiarioAlimentareSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AlimentoComponent,
    AlimentoDetailComponent,
    AlimentoUpdateComponent,
    AlimentoDeleteDialogComponent,
    AlimentoDeletePopupComponent
  ],
  entryComponents: [AlimentoComponent, AlimentoUpdateComponent, AlimentoDeleteDialogComponent, AlimentoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiarioAlimentareAlimentoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
