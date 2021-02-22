/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author rimon
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NewQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class BmiMessage implements MessageListener {
    
     public BmiMessage() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        try {
            TextMessage textMessage = (TextMessage) message;
            if (textMessage != null) {                
                Path filePath = Paths.get("/home/rimon/log.txt");
                Files.write(filePath, textMessage.getText().getBytes("utf8"), StandardOpenOption.APPEND);
                String str = textMessage.getText();
                System.out.println("Message received: " + str);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
