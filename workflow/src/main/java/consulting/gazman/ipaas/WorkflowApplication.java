package consulting.gazman.ipaas;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
//        String jsonMessage = "{\"workflowName\":\"submit-order\",\"workflowId\":\"bf4ad20d-3030-4c76-97e0-d783e71b42cf\",\"stepName\":null,\"stepChannel\":null,\"stepId\":null,\"action\":\"\",\"data\":null}";
//
//        // Create an ObjectMapper instance
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            // Deserialize the JSON message into a WorkflowMessage object
//            WorkflowMessage<String> workflowMessage = objectMapper.readValue(jsonMessage, new TypeReference<WorkflowMessage<String>>() {});
//            System.out.println("Deserialized Workflow Name: " + workflowMessage.getWorkflowName());
//            System.out.println("Deserialized Workflow ID: " + workflowMessage.getWorkflowId());
//            System.out.println("Deserialized Step Name: " + workflowMessage.getStepName());
//        } catch (Exception e) {
//            // Handle deserialization errors
//            e.printStackTrace();
//        }
    }
}