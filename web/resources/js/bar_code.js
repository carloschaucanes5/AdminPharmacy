/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function generarCode(code){
    const cantidad = document.querySelector(".cantidad");
    const contenedorCodes = document.querySelector(".contenedor-codes");
    const secondItem = contenedorCodes.querySelector(".item:nth-child(2)");
    for(let i = 1;i<=parseInt(cantidad.value);i++){
        const ele =  document.createElement("img");
        ele.setAttribute("id","codigo");
        contenedorCodes.insertBefore(ele,secondItem);
    }
    JsBarcode("#codigo", code);  
    
}

function imprimirBarCode(){
  //JsBarcode("#codigo", code); 
    let contenedor = document.querySelector(".contenedor-codes").innerHTML;
    let page = document.body.innerHTML;
    window.document.body.innerHTML = contenedor;
    window.print();
    location.reload();
}
