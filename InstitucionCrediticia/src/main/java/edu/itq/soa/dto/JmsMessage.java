package edu.itq.soa.dto;

import java.util.Enumeration;
import java.util.Map;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

public class JmsMessage {
    
    private String message;
    private Map<String, String> properties = new java.util.HashMap<>();
    
    public JmsMessage(TextMessage textMessage) {
        try {
            this.message = textMessage.getText();
            Enumeration<?> propertyNames = textMessage.getPropertyNames();
            while (propertyNames.hasMoreElements()) {
                String propertyName = (String) propertyNames.nextElement();
                if (propertyName.startsWith("JMS")) {
                    continue;
                }
                String propertyValue = textMessage.getStringProperty(propertyName);
                this.properties.put(propertyName, propertyValue);
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Map<String, String> getProperties() {
        return properties;
    }
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    
    @Override
    public String toString() {
        return "JmsMessage [message=" + message + ", properties=" + properties + "]";
    }

}
