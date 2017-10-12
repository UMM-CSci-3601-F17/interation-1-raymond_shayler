import {CardCreatePage} from './card-create.po';
import {browser} from "protractor";

describe('angular-spark-lab', () => {
    let page: CardCreatePage;

    beforeEach(() => {
        page = new CardCreatePage();
    });

    it('should load', () => {
        page.navigateTo();
    });

    it("should have all the right forms", () => {
        page.navigateTo();
        expect(page.getById("word")).toBeTruthy();
        expect(page.getById("synonym")).toBeTruthy();
        expect(page.getById("antonym")).toBeTruthy();
        expect(page.getById("general_sense")).toBeTruthy();
        expect(page.getById("example_usage")).toBeTruthy();
    });

    it("should be able to accept inputs", () => {
        page.navigateTo();
            page.typeById("word", "Gregorian");
            page.typeById("synonym", "Monkish");
            page.typeById("antonym", "Aeloian");
            page.typeById("general_sense", "a style of vocal hymms");
            page.typeById("example_usage", "I meditate to gregorian chanting");

            page.clickById("submitButton");
            browser.sleep(150);

            // expect(page.selectTextById("word")).toEqual("Gregorian");
            // expect(page.selectTextById("synonym")).toEqual("Monkish");
            // expect(page.selectTextById("antonym")).toEqual("Aeloian");
            // expect(page.selectTextById("general_sense")).toEqual("a style of vocal hymms");
            // expect(page.selectTextById("example_usage")).toEqual("I meditate to gregorian chanting");
        });

    it("should be cancelable", ()=>{
        page.navigateTo();

        page.typeById("word", "Gregorian");
        page.typeById("synonym", "Monkish");
        page.typeById("antonym", "Aeloian");

        page.clickById("cancelButton");
        expect(browser.getCurrentUrl).not.toContain("card-create");
    });

});


