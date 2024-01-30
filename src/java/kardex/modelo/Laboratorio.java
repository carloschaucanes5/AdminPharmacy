/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.modelo;

/**
 *
 * @author Carlitos
 */

/*
cod_laboratorio serial NOT NULL,
  nombre character varying(100),
*/
public class Laboratorio {
    private int cod_laboratorio;
    private String nombre_laboratorio;

    public int getCod_laboratorio() {
        return cod_laboratorio;
    }

    public void setCod_laboratorio(int cod_laboratorio) {
        this.cod_laboratorio = cod_laboratorio;
    }

    public String getNombre_laboratorio() {
        return nombre_laboratorio;
    }

    public void setNombre_laboratorio(String nombre_laboratorio) {
        this.nombre_laboratorio = nombre_laboratorio;
    }


    
    
}
