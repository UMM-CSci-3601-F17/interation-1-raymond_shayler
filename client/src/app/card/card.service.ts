import {Injectable} from '@angular/core';
import {Http, RequestOptions} from '@angular/http';

import {Observable} from "rxjs";
import "rxjs/add/operator/map";

import {Card} from "./card/card";
import {environment} from "../../environments/environment";


@Injectable()
export class CardService {
    private cardUrl: string = environment.API_URL + "cards";

    constructor(private http: Http) {
    }

    getUsers(): Observable<Card[]> {
        let observable: Observable<any> = this.http.request(this.cardUrl);
        return observable.map(res => res.json());
    }

    getUserById(id: string): Observable<Card> {
        return this.http.request(this.cardUrl + "/" + id).map(res => res.json());
    }

    addNewCard(word: string, synonym: string, antonym : string, general_sense : string, example_usage: string): Observable<Boolean> {
        const body = {word:word, synonym:synonym, antonym: antonym, general_sense: general_sense, example_usage: example_usage};
        console.log(body);
        //Send post request to add a new user with the card data as the body with specified headers.
        return this.http.post(this.cardUrl + "/new", body).map(res => res.json());
    }
}
