/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.demoOtp1.services;

import com.demos.demoOtp1.Models.Cliente;

/**
 *
 * @author lmanrique
 */
public interface IClienteServices {
    
    public Cliente ConsultarClientePorId(String PartyId);
}
