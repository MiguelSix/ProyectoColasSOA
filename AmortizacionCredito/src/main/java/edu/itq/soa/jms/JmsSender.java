package edu.itq.soa.jms;

import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Service
public class JmsSender {
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    public void send(String queue, JmsMessage message) {
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message.getMessage());
                Set<Entry<String,String>> entrySet = message.getProperties().entrySet();
                for (Entry<String, String> entry : entrySet) {
                    textMessage.setStringProperty(entry.getKey(), entry.getValue());
                }
                return textMessage;
            }
        });
    }
}