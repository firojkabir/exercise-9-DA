/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author rimon
 */
@WebServlet
public class Bmi extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Resource(mappedName = "jms/NewQueue")
    private Queue messageQueue;

    @Inject
    @JMSConnectionFactory("jms/NewQueueFactory")
    private JMSContext context;
    
    @EJB
    private Calculate calculator;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            Double height = Double.parseDouble(request.getParameter("height"));
            Double weight = Double.parseDouble(request.getParameter("weight"));
            Double bmi = calculator.calculateBMI(height, weight);
            
            String message = "Hello " + name + ", your BMI value is " + bmi.toString() + "\n";
            sendMessage(message);
            
            response.setContentType("text/html");
            PrintWriter pw=response.getWriter();
            pw.write(message);
            
        }
        catch(Exception ex) {
            System.out.println("Entered exception " + ex.getMessage());
            RequestDispatcher view = request.getRequestDispatcher("/bmi.jsp");
            view.forward(request, response);
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
    }
    
    private void sendMessage(String message) {
        context.createProducer().send(messageQueue, message);
    }

}
