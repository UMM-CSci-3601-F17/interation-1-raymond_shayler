import {browser, element, by} from 'protractor';
import {Key} from "selenium-webdriver";

export class CardCreatePage {
    navigateTo() {
        return browser.get('/card-create');
    }

    //http://www.assertselenium.com/protractor/highlight-elements-during-your-protractor-test-run/
    highlightElement(byObject) {
        function setStyle(element, style) {
            const previous = element.getAttribute('style');
            element.setAttribute('style', style);
            setTimeout(() => {
                element.setAttribute('style', previous);
            }, 200);
            return "highlighted";
        }

        return browser.executeScript(setStyle, element(byObject).getWebElement(), 'color: red; background-color: yellow;');
    }


    navigateToSpecific(url: string) {
        return browser.get(url);
    }

    selectTextById(id: string) {
        let form = element(by.id(id));
        return form.getText();
    }

    clickButton(buttonType: string) {
        element(by.id(buttonType + "Button")).click();
    }

    clickById(id: string){
        element(by.id(id)).click();
    }

    getById(id: string){
        return element(by.id(id));
    }

    typeById(id: string, text: string){
        let form = element(by.id(id));
        form.click();
        form.sendKeys(text);
    }

}
