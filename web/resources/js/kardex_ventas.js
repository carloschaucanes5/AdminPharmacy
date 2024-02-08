/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function enfocarCampoBuscar(){
    document.querySelector('.idBuscador').focus();
}



function enfocarTareas(){
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
        botones[0].focus();
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