/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

/**
 *
 * @author Carlitos
 */
import java.util.Iterator;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Carlitos
 */

@FacesValidator("validatorDouble")

public class ValidatorDouble implements Validator{

    
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String label =""; 
        HtmlInputText htmlInputText = (HtmlInputText)component;
         if(htmlInputText.getLabel()==null || htmlInputText.getLabel().trim().equals("") )
         {
             label = htmlInputText.getId();
         }
         else
         {
             label = htmlInputText.getLabel();
         }
         double valor = Double.parseDouble(value.toString().trim());

             if(valor == 0)
             {
                 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", label+":La Cantidad ingresada no puede ser 0"));  
             }
    }
    
}

