import {Component, OnInit, EventEmitter, Output, NgModule} from '@angular/core';
import {CardService} from "../card.service"
import {MATERIAL_COMPATIBILITY_MODE} from "@angular/material";
import {MatButtonModule,MatCardModule, MatFormFieldModule} from "@angular/material";

@Component({
    selector: 'app-card-create',
    templateUrl: './card-create.component.html',
    styleUrls: ['./card-create.component.css'],
    providers: []

})
export class CardCreateComponent implements OnInit {
    @Output() submitCard: EventEmitter<any> = new EventEmitter();


    public word: string = null;
    public synonym: string = null;
    public antonym: string = null;
    public general_sense: string = null;
    public example_usage: string = null;

    constructor(public cardService: CardService) {
    }

    submitNewCard() {
        this.cardService.addNewCard(this.word,this.synonym,this.antonym,this.general_sense,this.example_usage).subscribe(succeeded => {
            console.log("added card succesfully: " + succeeded);
        })
    }

    ngOnInit() {
    }

}
