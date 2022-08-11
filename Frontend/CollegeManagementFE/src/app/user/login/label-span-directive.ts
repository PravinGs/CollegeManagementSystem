import { Directive, ElementRef, HostListener, OnInit } from "@angular/core";


@Directive({
    selector: '[labelSpan]'
})
export class LabelFLow implements OnInit{

    constructor( private element: ElementRef) { }
    ngOnInit(){
        // console.log(this.element.nativeElement.innerText.split('').map())
        this.element.nativeElement.innerHTML = this.element.nativeElement.innerText.split('').map((letter:string, idx:number) => `<span style = "transition-delay: ${idx * 50}ms">${letter}</span>` ).join('')
    }

    @HostListener('click') onClick() {
        // this.element.nativeElement.innerHTML = this.element.nativeElement.innerText.split('').map((letter:any, idx:any) => `<span style = "transition-delay: ${idx * 50}ms">${letter}</span>` ).join('')
        // window.alert("Current Dom Element")

    }
}