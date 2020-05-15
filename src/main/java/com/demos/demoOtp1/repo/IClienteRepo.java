/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.demoOtp1.repo;

import com.demos.demoOtp1.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author lmanrique
 */
public interface IClienteRepo extends JpaRepository<Cliente,String> {
   
}
