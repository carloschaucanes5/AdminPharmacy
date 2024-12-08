/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kardex.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class Factura {
    private String nombre_empresa;
    private String nit_empresa;
    private String direccion;
    private String telefono;
    private String ciudad;
    private int numero_factura;
    private String  nombre;
    private String fecha;
    private String hora;
    private double totalPago;
    private String lema;
    private String advertencia;
    private String horario_mensaje;
    private String horario_ordinario;
    private String horario_festivos;
    private String domicilios_mensaje;
    private String url_foto;
    private String whatsapp;
    private double cambio;
    private List<Recibo> lineas = new ArrayList<Recibo>();

    public Factura() {
        this.nombre_empresa = "";
        this.nit_empresa = "";
        this.direccion = "";
        this.telefono = "";
        this.ciudad = "";
        this.numero_factura = 0;
        this.nombre ="";
        this.fecha="";
        this.hora ="";
        this.totalPago = 0;
        this.lema ="";
        this.advertencia="";
        this.horario_mensaje="";
        this.horario_ordinario ="";
        this.horario_festivos = "";
        this.domicilios_mensaje = "";
        this.url_foto ="";
        this.whatsapp ="";
        this.cambio = 0;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public List<Recibo> getLineas() {
        return lineas;
    }

    public void setLineas(List<Recibo> lineas) {
        this.lineas = lineas;
    }


    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
    
    
    public String getHorario_ordinario() {
        return horario_ordinario;
    }

    public void setHorario_ordinario(String horario_ordinario) {
        this.horario_ordinario = horario_ordinario;
    }

    public String getHorario_festivos() {
        return horario_festivos;
    }

    public void setHorario_festivos(String horario_festivos) {
        this.horario_festivos = horario_festivos;
    }

    public String getDomicilios_mensaje() {
        return domicilios_mensaje;
    }

    public void setDomicilios_mensaje(String domicilios_mensaje) {
        this.domicilios_mensaje = domicilios_mensaje;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    
    
    public String getHorario_mensaje() {
        return horario_mensaje;
    }

    public void setHorario_mensaje(String horario_mensaje) {
        this.horario_mensaje = horario_mensaje;
    }

    
    
    public String getAdvertencia() {
        return advertencia;
    }

    public void setAdvertencia(String advertencia) {
        this.advertencia = advertencia;
    }
    
    

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }
    
    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getNit_empresa() {
        return nit_empresa;
    }

    public void setNit_empresa(String nit_empresa) {
        this.nit_empresa = nit_empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(int numero_factura) {
        this.numero_factura = numero_factura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }



    
    
}
