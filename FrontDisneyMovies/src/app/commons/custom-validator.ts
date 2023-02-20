import { AbstractControl, AsyncValidatorFn, ValidatorFn } from "@angular/forms";
import { CharacterService } from "../services/character.service";

export class CustomValidator {
    static numbersOnly(): ValidatorFn {
        const regExp : RegExp = /^\d+$/;

        return (control: AbstractControl): {[key: string]: any} | null => {
            const numbersOnly = regExp.test(control.value);
            return !numbersOnly ? {'numbersOnly': {value: control.value}} : null;
          };
    }

    static hasLeadingSpace (): ValidatorFn {
        const regExp : RegExp = /^ /;

        return (control: AbstractControl): {[key: string]: any} | null => {
            const hasLeadingSpace = regExp.test(control.value);
            return hasLeadingSpace ? {'hasLeadingSpace': {value: control.value}} : null;
          };
    }

    static characterNameExist(characterService : CharacterService): AsyncValidatorFn {
        return (control: AbstractControl): Promise<{ [key: string]: any } | null> => {
            if (control.value == '') {
                return null;
            } else {
                return characterService.findByName(control.value)
                    .then(response => {
                        console.log(control.value);
                        
                        console.log(response);
                        
                        return response ? { 'characterNameExist': {value: control.value } } : null ;
                    })
                    .catch(err => {
                        return { 'characterNameExist': {value: control.value } };
                    })

            }
          };
    }

}
