package consulting.gazman.ipaas.workflow.implementations.oms.step;

import static consulting.gazman.ipaas.workflow.service.WorkflowStepService.SUCCESS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.workflow.messaging.producer.WorkflowMessageProducer;
import consulting.gazman.ipaas.workflow.repository.WorkflowPayloadRepository;
import consulting.gazman.ipaas.workflow.repository.WorkflowStepRepository;
import consulting.gazman.ipaas.workflow.service.WorkflowStepService;

@Service
public class ValidateOrder extends WorkflowStepService {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    public String handleBusinessLogic(String payload) {
        logger.info("HANDLING BUSINESS LOGIC:" + payload);
        try {
            System.out.println("Thread sleeping for 30 seconds...");
            Thread.sleep(3000);  // Sleep for 30,000 milliseconds (30 seconds)
            System.out.println("Thread woke up after 30 seconds.");
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted!");
        }


        if(payload == "1"){
            return SUCCESS;
        }
        else{
            return "Order too big";
        }
    }

            
        



}