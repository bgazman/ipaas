package consulting.gazman.ipaas.workflow.implementations.oms.model;


import java.util.List;

public class OrderData {
    private String customerId;
    private List<OrderItem> items;

    // Constructors
    public OrderData() {}

    public OrderData(String customerId, List<OrderItem> items) {
        this.customerId = customerId;
        this.items = items;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}