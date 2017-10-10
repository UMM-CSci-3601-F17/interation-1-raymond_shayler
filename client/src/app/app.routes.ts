// Imports
import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {UserListComponent} from "./users/user-list.component";
import {CardCreateComponent} from "./card/card-create/card-create.component";
import {CardComponent} from "./card/card/card.component";

// Route Configuration
export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'users', component: UserListComponent},
    {path: 'cardcreate', component: CardCreateComponent},
    {path: 'card', component: CardComponent}
];

export const Routing: ModuleWithProviders = RouterModule.forRoot(routes);
