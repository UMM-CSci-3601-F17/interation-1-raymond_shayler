import { NgModule } from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';

import { MATERIAL_COMPATIBILITY_MODE } from '@angular/material';


import {HttpModule, JsonpModule} from '@angular/http';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {UserComponent} from "./users/user.component";
import {UserListComponent} from './users/user-list.component';
import {UserListService} from './users/user-list.service';
import {CardCreateComponent} from "./card/card-create/card-create.component";
import {Routing} from './app.routes';
import {APP_BASE_HREF} from "@angular/common";

import {SharedModule} from "./shared.module";
import {CardComponent} from "./card/card/card.component";
import {CardService} from "./card/card.service";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        JsonpModule,
        Routing,
        SharedModule,
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        UserListComponent,
        UserComponent,
        CardCreateComponent,
        CardComponent,
    ],
    providers: [
        UserListService,
        CardService,
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: MATERIAL_COMPATIBILITY_MODE, useValue: true}
    ],
    bootstrap: [AppComponent]
})

export class AppModule {
}
