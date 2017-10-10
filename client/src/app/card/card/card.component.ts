import { Component, OnInit } from '@angular/core';
import {MATERIAL_COMPATIBILITY_MODE} from "@angular/material";

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

    constructor(){
        this.word = "Default"
    }
  // constructor(word: string, synonym: string, antonym: string, general_sense: string, example_usage: string) {
  //     this.word = word;
  //     this.synonym = synonym;
  //     this.antonym = antonym;
  //     this.general_sense = general_sense;
  //     this.example_usage = example_usage;
  // }

  ngOnInit() {
  }

}
