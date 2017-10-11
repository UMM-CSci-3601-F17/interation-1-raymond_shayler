import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CardComponent} from './card.component';
import {CardService} from "../card.service";
import {MATERIAL_COMPATIBILITY_MODE} from "@angular/material";


describe('CardComponent', () => {
    let component: CardComponent;
    let fixture: ComponentFixture<CardComponent>;

    let cardServiceStub: {
        addNewCard:(word: string, synonym: string, antonym: string, general_sense: string, example_usage: string) => true;
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CardComponent],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CardComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });


});
