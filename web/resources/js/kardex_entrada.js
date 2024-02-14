/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function recorrerCampos(boton){
    const botones = document.querySelectorAll(".boton-entrada");
    let pos = getElementPosition(boton,botones);
    let cantidad = botones.length; 
    if(window.event.keyCode === 38 && pos > 0){
        botones[pos-1].focus();
        
    }
    if(window.event.keyCode === 40 && pos < cantidad-1 ){
        botones[pos+1].focus();
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



function enfocarNumeroFactura(){
    document.querySelector(".campo_numeroFactura").focus();
}