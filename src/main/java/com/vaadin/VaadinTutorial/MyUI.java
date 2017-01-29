package com.vaadin.VaadinTutorial;

import com.vaadin.Hibernate.ParcelsEntity;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.util.Date;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    // add the next two lines:
    private CustomerService service = CustomerService.getInstance();
    private Grid grid = new Grid();
    private Label info = new Label("Filter");
    private Label shipped = new Label("Shipped");
    private Label received = new Label("Received");
    private TextField filterByPn = new TextField("PN");
    private TextField filterByPartName = new TextField("Part Name");
    private TextField filterByVendor = new TextField("Vendor");
    private TextField filterByQty = new TextField("Qty");
    private DateField  filterByShippedAfter = new DateField ("Shipped after");
    private DateField  filterByShippedBefore = new DateField ("before");
    private DateField filterByReceivedAfter = new DateField("Received after");
    private DateField filterByReceivedBefore = new DateField("before");
    private Button filter = new Button("Filter");

    private CustomerForm form = new CustomerForm(this);


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        filterByPn.setInputPrompt("filter by PN...");
        filterByPartName.setInputPrompt("filter by Part Name...");
        filterByVendor.setInputPrompt("filter by Vendor...");
        filterByQty.setInputPrompt("filter by Qty...");
        filterByShippedAfter.setDateFormat("MMM dd,yyyy");
        filterByShippedBefore.setDateFormat("MMM dd,yyyy");
        filterByReceivedAfter.setDateFormat("MMM dd,yyyy");
        filterByReceivedBefore.setDateFormat("MMM dd,yyyy");

//        filterByPn.addTextChangeListener(e -> {
//            grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class,
//                    service.findAll(e.getText(),e.getComponent().getCaption())));
//        });
//        List<ParcelsEntity> customers = service.findAll(filterByPn.getValue());
//
//        filterByPartName.addTextChangeListener(e -> {
//            grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class,
//                    service.findAll(e.getText(),e.getComponent().getCaption())));
//        });
//        customers = service.findAll(filterByPartName.getValue());
//
//        filterByVendor.addTextChangeListener(e -> {
//            grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class,
//                    service.findAll(e.getText(),e.getComponent().getCaption())));
//        });
//        customers = service.findAll(filterByPartName.getValue());
//
//        filterByQty.addTextChangeListener(e -> {
//            grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class,
//                    service.findAll(e.getText(),e.getComponent().getCaption())));
//        });
//        customers = service.findAll(filterByPartName.getValue());
//
//        filterByShippedAfter.addTextChangeListener(e -> {
//            grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class,
//                    service.findAll(e.getText(),e.getComponent().getCaption())));
//        });
//        customers = service.findAll(filterByPartName.getValue());
//
//        filterByShippedAfter.addTextChangeListener(e -> {
//            grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class,
//                    service.findAll(e.getText(),e.getComponent().getCaption())));
//        });
//        customers = service.findAll(filterByPartName.getValue());

        filter.addClickListener(e ->{
            String pn = filterByPn.getValue();
            String partName = filterByPartName.getValue();
            String vendor = filterByVendor.getValue();
            String qty = filterByQty.getValue();
            Date shippedAfter = filterByShippedAfter.getValue();
            Date shippedBefore = filterByShippedBefore.getValue();
            Date receivedAfter = filterByReceivedAfter.getValue();
            Date receivedBefore = filterByReceivedBefore.getValue();
            grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class,
                    service.findAll(pn,partName,vendor,qty,shippedAfter,shippedBefore,receivedAfter,receivedBefore)));
        });

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterByPn.clear();
            filterByPartName.clear();
            filterByQty.clear();
            filterByVendor.clear();
            filterByShippedAfter.clear();
            filterByShippedBefore.clear();
            filterByReceivedAfter.clear();
            filterByReceivedBefore.clear();
            updateList();
        });

        HorizontalLayout horLayoutShipped = new HorizontalLayout();

        FormLayout flSA = new FormLayout();
        FormLayout flSB = new FormLayout();

        flSA.addComponent(filterByShippedAfter);
        flSB.addComponent(filterByShippedBefore);

        horLayoutShipped.addComponent(flSA);
        horLayoutShipped.addComponent(flSB);

        FormLayout flRA = new FormLayout();
        FormLayout flRB = new FormLayout();

        flRA.addComponent(filterByReceivedAfter);
        flRB.addComponent(filterByReceivedBefore);

        HorizontalLayout horLayoutReceived = new HorizontalLayout();
        horLayoutReceived.addComponent(flRA);
        horLayoutReceived.addComponent(flRB);

        HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.addComponents(filter,clearFilterTextBtn);

        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(filterByPn);
        formLayout.addComponent(filterByPartName);
        formLayout.addComponent(filterByVendor);
        formLayout.addComponent(filterByQty);

        HorizontalLayout main = new HorizontalLayout(grid);
        main.setSpacing(true);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);
        layout.addComponents(info, formLayout, horLayoutShipped, horLayoutReceived, btnLayout, main);

        updateList();

        layout.setMargin(true);
        setContent(layout);

        form.setVisible(false);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                form.setVisible(false);
            } else {
                ParcelsEntity parcel = (ParcelsEntity) event.getSelected().iterator().next();
                form.setParcelsEntity(parcel);
            }
        });
    }


    public void updateList() {
        // fetch list of Customers from service and assign it to Grid
        List<ParcelsEntity> customers = service.findAll();
        grid.getContainerDataSource().removeAllItems();
        grid.setContainerDataSource(new BeanItemContainer<>(ParcelsEntity.class, customers));
        grid.removeAllColumns();
        grid.addColumn("pn");
        grid.addColumn("partName");
        grid.addColumn("vendor");
        grid.addColumn("qty");
        grid.addColumn("shipped");
        grid.addColumn("receive");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
