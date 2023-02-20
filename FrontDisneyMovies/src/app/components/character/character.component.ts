import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CharacterModel } from 'src/app/models/character-model';
import { CharacterModelDto } from 'src/app/dtos/character-model-dto';
import { CharacterService } from 'src/app/services/character.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CustomValidator } from 'src/app/commons/custom-validator';

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent implements OnInit {

  charactersResumeArray : Array<CharacterModel> = [];
  character = undefined;
  flagUpdate = false;
  flagCreate = false;

  updateForm = new FormGroup({
    uName:    new FormControl('', [CustomValidator.hasLeadingSpace()]),
    uAge:     new FormControl('', [Validators.min(1), CustomValidator.numbersOnly()]),
    uWeight:  new FormControl('', [Validators.min(1), CustomValidator.numbersOnly()]),
    uStory:   new FormControl('', [CustomValidator.hasLeadingSpace()]) 
  });

  createForm = new FormGroup({
    cName:   new FormControl('',[Validators.required, CustomValidator.hasLeadingSpace()],[CustomValidator.characterNameExist(this.characterService)]),
    cAge:    new FormControl('',[Validators.required, Validators.min(1), CustomValidator.numbersOnly(), CustomValidator.hasLeadingSpace()]),
    cWeight: new FormControl('',[Validators.required, Validators.min(1), CustomValidator.numbersOnly(), CustomValidator.hasLeadingSpace()]),
    cImgUrl: new FormControl('assets/defaultCharacter.jpg', [CustomValidator.hasLeadingSpace(), CustomValidator.hasLeadingSpace()]),
    cStory:  new FormControl('',[Validators.required])
  });

  alertMessage = "";

  constructor(private characterService : CharacterService) { }

  get uName()   { return this.updateForm.get('uName');   }
  get uAge()    { return this.updateForm.get('uAge');    }
  get uWeight() { return this.updateForm.get('uWeight'); }
  get uStory()  { return this.updateForm.get('uStory');  }

  get cAge()    { return this.createForm.get('cAge');    }    
  get cWeight() { return this.createForm.get('cWeight'); }     
  get cImgUrl() { return this.createForm.get('cImgUrl'); }     
  get cName()   { return this.createForm.get('cName');   }     
  get cStory()  { return this.createForm.get('cStory');  }    
  

  ngOnInit(): void {
    this.alertMessage = "";
    this.character = undefined;
    this.flagCreate = false;

    const defaultCharacterPoster : string = "assets/defaultCharacter.jpg";
    const promise = this.characterService.resumeAllCharacters();

    promise
      .then(response => {
        response.forEach(element => {
          let character = new CharacterModel();
          if (element.img_url === '') {
            character.setImgUrl(defaultCharacterPoster);
          } else {
            character.setImgUrl(element.img_url);
          }

          character.setName(element.name);

          this.charactersResumeArray.push(character);
        });
        
      })
      .catch(err => {
        if (err instanceof HttpErrorResponse) {
          this.alertMessage = "Sorry, it seems like the server is down, please try again later";
        }
        console.log(err);
      });
  }

  showCharacterByName(name: string): void {
    this.flagCreate = false;
    this.alertMessage = "";

    this.charactersResumeArray = [];
    const defaultCharacterPoster : string = "assets/defaultCharacter.jpg";
    
    const promise = this.characterService.findByName(name);

    promise
      .then(response => {
        let char = new CharacterModel();

        char.setIdCharacter(response.idCharacter);
        char.setAge(response.age);
        if (response.imgUrl === '') {
          char.setImgUrl(defaultCharacterPoster);
        } else {
          char.setImgUrl(response.imgUrl);
        }
        char.setName(response.name);
        char.setStory(response.story);
        char.setWeight(response.weight);

        this.character = char;
      })
      .catch(err => {
        console.log(err);
        if (err instanceof HttpErrorResponse) {
          if (err.status === 0) {
            this.alertMessage = "Sorry, it seems like the server is down, please try again later";
          } else {
            this.alertMessage = err.error.description;
          }
        }
      });
  }

  updateCharacter(): void {
    this.alertMessage = "";
    this.flagUpdate = true;
    this.flagCreate = false;
  }

  updateSubmit(): void {
    this.alertMessage = "";

    let dto : CharacterModelDto = new CharacterModelDto();
    dto.setIdCharacter(this.character.getIdCharacter());
    dto.setImgUrl(this.character.getImgUrl());
    dto.setName(this.uName.value === '' ? this.character.getName() : this.uName.value);
    dto.setAge(this.uAge.value === '' ? this.character.getAge() : this.uAge.value);
    dto.setWeight(this.uWeight.value === '' ? this.character.getWeight() : this.uWeight.value);
    dto.setStory(this.uStory.value === '' ? this.character.getStory() : this.uStory.value);
    
    const promise = this.characterService.updateCharacter(dto);
    console.log(promise);
    
    promise
      .then(response => {
        this.flagUpdate = false;
        this.showCharacterByName(dto.getName());
      })
      .catch(err => {
        console.log(err);
        if (err instanceof HttpErrorResponse) {
          if (err.status === 0) {
            this.alertMessage = "Sorry, it seems like the server is down, please try again later";
          } else {
            this.alertMessage = err.error.description;
          }
        } 
      });
  }

  createCharacter(): void {
    this.charactersResumeArray = [];
    this.character = undefined;
    this.flagUpdate = false;
    this.alertMessage = "";
    this.flagCreate = true;
  }

  createSubmit(): void {
    this.alertMessage = "";

    let dto : CharacterModelDto = new CharacterModelDto();
    
    const defaultCharacterPoster : string = "assets/defaultCharacter.jpg";

    dto.setName(this.cName.value);
    dto.setAge(this.cAge.value);
    dto.setWeight(this.cWeight.value);
    dto.setStory(this.cStory.value);
    dto.setImgUrl(this.cImgUrl.value === '' ? defaultCharacterPoster : this.cImgUrl.value);
    
    const promise = this.characterService.createCharacter(dto);

    promise
      .then(response => {
        this.flagCreate = false;
        this.showCharacterByName(response.name);
        
      })
      .catch(err => {
        console.log(err);
        if (err instanceof HttpErrorResponse) {
          if (err.status === 0) {
            this.alertMessage = "Sorry, it seems like the server is down, please try again later";
          } else {
            this.alertMessage = err.error.description;
          }
        } 
      });
  }

  deleteCharacterById(id: number): void {
    this.alertMessage = "";

    const promise = this.characterService.deleteCharacterById(id);

    promise
      .then(response => {
        this.ngOnInit();
      })
      .catch(err => {
        console.log(err);
        if (err instanceof HttpErrorResponse) {
          if (err.status === 0) {
            this.alertMessage = "Sorry, it seems like the server is down, please try again later";
          } else {
            this.alertMessage = err.error.description;
          }
        }
      });
  }

}

