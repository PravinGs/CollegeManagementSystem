import { Directive, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appLabel]'
})
export class LabelDirective {

  constructor(private elementRef: ElementRef, private renderer: Renderer2) { 
    console.log(this.elementRef.nativeElement.innerText)
    this.elementRef.nativeElement.innerHTML = this.elementRef.nativeElement.innerText.split('').map((letter:string, idx:number) => `<span style = "transition-delay: ${idx * 50}ms">${letter}</span>` ).join('')
  }

}
