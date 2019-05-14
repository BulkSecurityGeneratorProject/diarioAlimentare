import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pasto',
        loadChildren: './pasto/pasto.module#DiarioAlimentarePastoModule'
      },
      {
        path: 'alimento',
        loadChildren: './alimento/alimento.module#DiarioAlimentareAlimentoModule'
      },
      {
        path: 'pasto',
        loadChildren: './pasto/pasto.module#DiarioAlimentarePastoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiarioAlimentareEntityModule {}
