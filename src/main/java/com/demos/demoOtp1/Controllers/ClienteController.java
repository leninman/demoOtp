/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.demoOtp1.Controllers;

import com.demos.demoOtp1.Models.Cliente;
import com.demos.demoOtp1.services.IClienteServices;
import com.demos.demoOtp1.services.IClienteServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmanrique
 */

//@Controller
@RestController
@RequestMapping("OtpCliente")
public class ClienteController {
    
    
    @Autowired
    IClienteServices clienteService;
    String Id;
    
    
  //*METODO DEL REST CONTROLLER*//  
     @GetMapping("/consultarcliente/{Id}")
    public Cliente ConsultaCliente(@PathVariable(name="Id") String Id){    
       return clienteService.ConsultarClientePorId(Id);     
    }
    
    
    
//    @GetMapping("/consultarcliente")
//    public String ConsultaCliente(Model model){
//        Id="100";
//        model.addAttribute("titulo","CONSULTA DE CLIENTES");
//        model.addAttribute("cliente",clienteService.ConsultarClientePorId(Id));
//       return "Cliente";
//    }
    
}
