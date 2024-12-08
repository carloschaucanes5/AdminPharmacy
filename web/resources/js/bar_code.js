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

function descargarBarCode(){
    // Selecciona el div que quieres convertir en imagen
            const contenido = document.querySelector(".contenedor-codes");
            
            // Creamos un canvas
        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');

        // Establecemos el tamaño del canvas igual al del contenedor
        canvas.width = contenedor.offsetWidth;
        canvas.height = contenedor.offsetHeight;

        // Usamos el método `html2canvas` para capturar el contenedor
        html2canvas(contenido).then(function(canvas) {
            // Convertimos el canvas a una imagen PNG
            var imagenData = canvas.toDataURL("image/jpeg");

            // Creamos un enlace de descarga
            var enlace = document.createElement('a');
            enlace.href = imagenData;
            enlace.download = 'imagen_descargada.jpeg'; // Nombre del archivo
            enlace.click(); // Simulamos un clic en el enlace para descargar la imagen
        });
}
