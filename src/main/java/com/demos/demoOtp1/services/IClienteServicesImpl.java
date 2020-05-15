/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.demoOtp1.services;

import com.demos.demoOtp1.Models.Cliente;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;

import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecUsernameToken;

import org.apache.ws.security.message.token.SecurityTokenReference;
import org.apache.ws.security.util.WSSecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import org.w3c.dom.NodeList;

/**
 *
 * @author lmanrique
 */
@Service
public class IClienteServicesImpl implements IClienteServices {

    private String endpoint;

    Document doc = null;

  
    @Value("${Party.WsSecurity.User}")
    private String usuarioWsSecurity;

  
     @Value("${Party.WsSecurity.Password}")
    private String passwordWsSecurity;

    private String celPhone;
    
    private String homePhone;

    private String issuedIdentType;

    private String issuedIdentValue;

    private String issuedDocType;

    private String descripcion;
    
    private String fullname;

    private String email;
    
  
   
    
    
    
    
    Cliente cliente;

    
    @Override
    public Cliente ConsultarClientePorId(String Id) {

        try {

            SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
            javax.xml.soap.SOAPConnection connection = sfc.createConnection();
            String endpoint = "https://DPSRVDESA/WSParty";
            SOAPMessage response = connection.call(createSOAPRequest(Id), endpoint);
            doc = response.getSOAPBody().getOwnerDocument();

            cliente = new Cliente();
            cliente.setCelPhone(getCelPhoneNumber());
            cliente.setHomePhone(getHomePhoneNumber());
            cliente.setEmailAddr(getEmail());
            cliente.setIssuedIdentType(getIssuedIdentType());
            cliente.setIssuedIdentValue(getIssuedIdentValue());
            cliente.setIssuedDocType(getIssuedDocType());
            cliente.setDescripcion(getDescripcion());
            cliente.setFullName(getFullName());
            cliente.setPartyId(Id);
    

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cliente;
    }

    public SOAPMessage createSOAPRequest(String Id) throws Exception {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.addNamespaceDeclaration("ifx", "http://www.bod.com/IFX");

        //SOAP BODY para el servicio PartyInqCode
        /* <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ifx="http://www.bod.com/IFX">
   <soapenv:Header/>
   
   <soapenv:Body>
      <ifx:PartyInqCodeRq>
         <ifx:RqUID>11</ifx:RqUID>
         <ifx:MsgRqHdr>
            
            <ifx:BankId>01</ifx:BankId>
            <ifx:ChannelCode></ifx:ChannelCode>
         </ifx:MsgRqHdr>
         <ifx:PartyId>2029410</ifx:PartyId>
      </ifx:PartyInqCodeRq>
   </soapenv:Body>
</soapenv:Envelope>*/
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();

        SOAPElement PartyInqCodeRq = (SOAPElement) soapBody.addChildElement("PartyInqCodeRq", "ifx");
        SOAPElement RqUID = PartyInqCodeRq.addChildElement("RqUID", "ifx");
        SOAPElement MsgRqHdr = PartyInqCodeRq.addChildElement("MsgRqHdr", "ifx");
        SOAPElement BankId = MsgRqHdr.addChildElement("BankId", "ifx");
        SOAPElement ChannelCode = MsgRqHdr.addChildElement("ChannelCode", "ifx");
        SOAPElement PartyId = PartyInqCodeRq.addChildElement("PartyId", "ifx");

        RqUID.addTextNode("11");
        BankId.addTextNode("01");
        ChannelCode.addTextNode("INT");
        PartyId.addTextNode(Id);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        String endpoint = "https://DPSRVDESA/WSParty";
       
        headers.addHeader("SOAPAction", endpoint);

        soapMessage = addUserTokensPlainPwd(envelope);

        soapMessage.saveChanges();


        return soapMessage;
    }
    
    
    public SOAPMessage addUserTokensPlainPwd(SOAPEnvelope unsignedEnvelope)
            throws Exception {

        Document doc = unsignedEnvelope.getOwnerDocument(); // //getAsDocument();

        WSSecUsernameToken builder = new WSSecUsernameToken();
        WSSecHeader wsSecHeader = new WSSecHeader();

        wsSecHeader.insertSecurityHeader(doc);

        builder.setPasswordType(WSConstants.PASSWORD_TEXT); //PASSWORD_TEXT);

        builder.setUserInfo(this.usuarioWsSecurity, this.passwordWsSecurity);
       
        Document usernameTokenDoc = builder.build(doc, wsSecHeader);// 

        SecurityTokenReference secRef = new SecurityTokenReference(doc) {
        };
        // adding the namespace
        WSSecurityUtil.setNamespace(secRef.getElement(),
                WSConstants.WSSE_NS,
                WSConstants.WSSE_PREFIX);

        DOMSource src = new DOMSource(usernameTokenDoc);

        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage soapMsg = mf.createMessage();

        soapMsg.getSOAPPart().setContent(src);
        return soapMsg;
    }

    public String getIssuedIdentType() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);

            NodeList docIssuedIdent = nodePersonData.getChildNodes();
            Node nodeIssuedIdent = (Node) docIssuedIdent.item(1);
            NodeList docIssuedIdentType = nodeIssuedIdent.getChildNodes();
            Node nodeIssuedIdentType = (Node) docIssuedIdentType.item(0);
            issuedIdentType = nodeIssuedIdentType.getTextContent();
        } catch (Exception e) {
        }
        return issuedIdentType;

    }

    public String getIssuedIdentValue() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);

            NodeList docIssuedIdent = nodePersonData.getChildNodes();
            Node nodeIssuedIdent = (Node) docIssuedIdent.item(1);
            NodeList docIssuedIdentValue = nodeIssuedIdent.getChildNodes();
            Node nodeIssuedIdentValue = (Node) docIssuedIdentValue.item(1);
            issuedIdentValue = nodeIssuedIdentValue.getTextContent();
        } catch (Exception e) {
        }
        return issuedIdentValue;

    }

    public String getIssuedDocType() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);

            NodeList docIssuedIdent = nodePersonData.getChildNodes();
            Node nodeIssuedIdent = (Node) docIssuedIdent.item(1);
            NodeList docIssuedIdentValue = nodeIssuedIdent.getChildNodes();
            Node nodeIssuedIdentValue = (Node) docIssuedIdentValue.item(2);
            issuedDocType = nodeIssuedIdentValue.getTextContent();
        } catch (Exception e) {
        }
        return issuedDocType;

    }

    public String getDescripcion() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);

            NodeList docIssuedIdent = nodePersonData.getChildNodes();
            Node nodeIssuedIdent = (Node) docIssuedIdent.item(1);
            NodeList docIssuedIdentValue = nodeIssuedIdent.getChildNodes();
            Node nodeIssuedIdentValue = (Node) docIssuedIdentValue.item(3);
            descripcion = nodeIssuedIdentValue.getTextContent();
        } catch (Exception e) {
        }
        return descripcion;

    }
    
    
    public String getFullName() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);

            NodeList docPersonName = nodePersonData.getChildNodes();
            Node nodePersonName = (Node) docPersonName.item(2);
            NodeList docFullName = nodePersonName.getChildNodes();
            Node nodeFullName = (Node) docFullName.item(0);
            fullname = nodeFullName.getTextContent();
        } catch (Exception e) {
        }
        return fullname;

    }
    
    public String getEmail() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);
            NodeList docContact = nodePersonData.getChildNodes();
            Node nodeContact = (Node) docContact.item(0);
            NodeList docLocator = nodeContact.getChildNodes();
            Node nodeLocator = (Node) docLocator.item(3);
            NodeList docEmail = nodeLocator.getChildNodes();
            Node nodeEmail = (Node) docEmail.item(0);
            email = nodeEmail.getTextContent();

        } catch (Exception e) {
        }
        return email;
    }

    public String getCelPhoneNumber() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);
            NodeList docContact = nodePersonData.getChildNodes();
            Node nodeContact = (Node) docContact.item(0);
            NodeList docLocator = nodeContact.getChildNodes();
            Node nodeLocator = (Node) docLocator.item(0);
            NodeList docCelPhone = nodeLocator.getChildNodes();
            Node nodeCelPhone = (Node) docCelPhone.item(1);
            celPhone = nodeCelPhone.getTextContent();

        } catch (Exception e) {
        }
        return "0"+celPhone;
    }
    
    
    
    public String getHomePhoneNumber() {

        try {
            NodeList docBody = doc.getDocumentElement().getChildNodes();
            Node nodebody = (Node) docBody.item(0);
            NodeList docParty = nodebody.getChildNodes();
            Node nodeParty = (Node) docParty.item(0);
            NodeList docPartyInqCodeRs = nodeParty.getChildNodes();
            Node nodePartyInqCodeRs = (Node) docPartyInqCodeRs.item(1);
            NodeList docPersonData = nodePartyInqCodeRs.getChildNodes();
            Node nodePersonData = (Node) docPersonData.item(0);
            NodeList docContact = nodePersonData.getChildNodes();
            Node nodeContact = (Node) docContact.item(0);
            NodeList docLocator = nodeContact.getChildNodes();
            Node nodeLocator = (Node) docLocator.item(1);
            NodeList docHomePhone = nodeLocator.getChildNodes();
            Node nodeHomePhone = (Node) docHomePhone.item(1);
            homePhone = nodeHomePhone.getTextContent();

        } catch (Exception e) {
        }
        return "0"+homePhone;
    }

    

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUsuarioWsSecurity() {
        return usuarioWsSecurity;
    }

    public String getPasswordWsSecurity() {
        return passwordWsSecurity;
    }

    public void setPasswordWsSecurity(String passwordWsSecurity) {
        this.passwordWsSecurity = passwordWsSecurity;
    }

}
