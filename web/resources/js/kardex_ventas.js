/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function enfocarCampoBuscar(){
    document.querySelector('.idBuscador').focus();
}

function enfocarCampoVentaContado(){
    document.querySelector('.btnVentaContado').focus();
}

function enfocarEliminarItems(btn){

    if(window.event.keyCode === 40 || window.event.keyCode===38 ){
        return desplazamientoPorTablaItems(btn);
    }
    if(window.event.keyCode ===39){
        return  enfocarCampoVentaContado();
    }
}

function desplazamientoPorTablaItems(btn){
    const botones = document.querySelectorAll(".boton-eliminar");
    let position = getElementPosition(btn,botones);
    let cantidad_elementos = botones.length;
    if(cantidad_elementos>1){
        if(window.event.keyCode === 40 && position < cantidad_elementos-1){
          botones[position+1].focus();
        }
        if(window.event.keyCode === 38 && position > 0){
            botones[position - 1].focus();
          }
        if(window.event.keyCode === 39){
            document.querySelector(".btnVentaContado").focus();
        }
    }
    else{
        if(window.event.keyCode === 38){
            enfocarCampoBuscar();
        }
        if(window.event.keyCode === 40){
            enfocarCampoVentaContado();
        }
    }
}

function enfocarTareasBuscador(){
    if(window.event.keyCode ===39){
        enfocarFilasTablaBuscar();
    }
    //flecha hacia abajo
    if(window.event.keyCode === 40)
    {
       document.querySelector(".btnVentaContado").focus();
    }
    //flecha hacia arriba
    if(window.event.keyCode ===38){
        document.querySelector('.idBuscador').focus();
    }
    //const botones = document.querySelectorAll(".boton-eliminar");
    if(window.event.keyCode ===37){
        const botones = document.querySelectorAll(".boton-eliminar");
        botones[botones.length -1].focus();
    }
}

function enfocarTareasVentas(){
    if(window.event.keyCode ===39){
        enfocarFilasTablaBuscar();
    }
    //flecha hacia abajo
    if(window.event.keyCode === 40)
    {
        enfocarBotonFacturar();
    }
    //flecha hacia arriba
    if(window.event.keyCode ===38){
        document.querySelector('.idBuscador').focus();
    }
    //const botones = document.querySelectorAll(".boton-eliminar");
    if(window.event.keyCode ===37){
        const botones = document.querySelectorAll(".boton-eliminar");
        botones[botones.length -1].focus();
    }
}

function  enfocarFilasTablaBuscar(){
    const botones = document.querySelectorAll(".boton-adicionar");
    for(let i = 0;i< botones.length;i++){
        if(i === 0)botones[0].focus();
    }
}

function desplazamientoPorTablaBusqueda(btn){
    const botones = document.querySelectorAll(".boton-adicionar");
    let position = getElementPosition(btn,botones);
    let cantidad_elementos = botones.length;
    if(cantidad_elementos>1){
        if(window.event.keyCode === 40 && position < cantidad_elementos-1){
          botones[position+1].focus();
        }
        if(window.event.keyCode === 38 && position > 0){
            botones[position - 1].focus();
          }
        if(window.event.keyCode === 37){
            document.querySelector(".btnVentaContado").focus();
        }
    }
    else{
        if(window.event.keyCode === 38){
            enfocarCampoBuscar();
        }
        if(window.event.keyCode === 40){
            enfocarCampoVentaContado();
        }
        
    }
}

function getElementPosition(btn,botones){
    let position = 0;
        for(let i = 0;i< botones.length;i++){
        if(botones[i].isEqualNode(btn)){
            position = i;
        }
    }
    return position;
}

function imprimir(){
    let contenedor = document.querySelector(".contenedor_impresora").innerHTML;
    let page = document.body.innerHTML;
    window.document.body.innerHTML = contenedor;
    window.print();
    location.reload();
    //document.body.innerHTML = page;
}


function direccionBotonFacturar(){
    //37 izquierda  39 derecha
    if(window.event.keyCode === 39){
        document.querySelector(".boton-imprimir").focus();
    }
}

function direccionBotonImprimir(){
    if(window.event.keyCode === 37){
        document.querySelector(".boton-facturar").focus();
    }
}

function enfocarBotonFacturar(){
        document.querySelector(".boton-facturar").focus();
    
}