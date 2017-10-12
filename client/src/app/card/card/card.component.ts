import { Component, OnInit } from '@angular/core';
import {MATERIAL_COMPATIBILITY_MODE} from "@angular/material";
import {CardService} from "../card.service";
import {Card} from "./card";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

    public word: string = null;
    public synonym: string = null;
    public antonym: string = null;
    public general_sense: string = null;
    public example_usage: string = null;

    public hiddenSynonym: string = null;
    public hiddenAntonym: string = null;
    public hiddenGeneral_sense: string = null;
    public hiddenExample_usage: string = null;

    public hintArray: any[4];
    public hintCounter: number = 0;

    constructor(public cardService: CardService){

    }

    fetchNewCard(){
        console.log("Button click?");
            this.synonym = null;
            this.antonym = null;
            this.general_sense = null;
            this.example_usage = null;
        this.cardService.getRandomCard().subscribe(succeeded => {
            this.word = succeeded.word;
            this.hiddenSynonym = succeeded.synonym;
            this.hiddenAntonym = succeeded.antonym;
            this.hiddenGeneral_sense = succeeded.general_sense;
            this.hiddenExample_usage = succeeded.example_usage;
        })
    }

    displayHint(){
        let hintNumber = Math.floor(Math.random() * 4);
        if (hintNumber == 0) {
            if (this.synonym != null) {
                this.displayHint();
            } else {
                this.synonym = this.hiddenSynonym;
                this.hintCounter++;
            }
        }
        else if (hintNumber == 1) {
            if (this.antonym != null) {
                this.displayHint();
            } else {
                this.antonym = this.hiddenAntonym;
                this.hintCounter++;
            }

    }
        else if (hintNumber == 2) {
            if (this.general_sense != null) {
                this.displayHint();
            } else {
                this.general_sense = this.hiddenGeneral_sense;
                this.hintCounter++;
            }
        }
        else if (hintNumber == 3){
            if (this.example_usage != null) {
                this.displayHint();
            } else {
                this.example_usage = this.hiddenExample_usage;
                this.hintCounter++;
            }
        }
    }



    /*
    displayHint(){
        let hintNumber = Math.floor(Math.random() * 3);

        if (hintNumber == 0) {
           if(this.synonym != null){

           }
        }
    }
    */
  // constructor(word: string, synonym: string, antonym: string, general_sense: string, example_usage: string) {
  //     this.word = word;
  //     this.synonym = synonym;
  //     this.antonym = antonym;
  //     this.general_sense = general_sense;
  //     this.example_usage = example_usage;
  // }


  ngOnInit() {
        this.fetchNewCard();
  }

}
