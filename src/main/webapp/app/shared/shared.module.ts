import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DiarioAlimentareSharedLibsModule, DiarioAlimentareSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [DiarioAlimentareSharedLibsModule, DiarioAlimentareSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [DiarioAlimentareSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiarioAlimentareSharedModule {
  static forRoot() {
    return {
      ngModule: DiarioAlimentareSharedModule
    };
  }
}
