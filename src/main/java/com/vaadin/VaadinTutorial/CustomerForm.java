package com.vaadin.VaadinTutorial;


import com.vaadin.Hibernate.ParcelsEntity;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by deft on 20.11.2016.
 */
public class CustomerForm extends FormLayout {
    protected TextField pn = new TextField("PN");
//    protected TextField partName = new TextField("Part Name");
    protected TextField vendor = new TextField("Vendor");
    protected TextField qty = new TextField("Qty");
    protected PopupDateField shipped = new PopupDateField("Shipped");
    protected PopupDateField receive = new PopupDateField("Receive");
    //    protected Button save = new Button("Save");
//    protected Button delete = new Button("Delete");
    protected Button filter = new Button("Filter");

    private CustomerService service = CustomerService.getInstance();
    private ParcelsEntity parcelsEntity;
    private MyUI myUI;

    public CustomerForm(MyUI myUI) {
        this.myUI = myUI;

        setSizeUndefined();
//        HorizontalLayout buttons = new HorizontalLayout(save, delete);
//        buttons.setSpacing(true);
//        addComponents(pn,partName, partNumber, vendor, qty, shipped, buttons);
//        addComponents(pn, partName, partNumber, vendor, qty, shipped);

//        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
//        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        filter.setStyleName(ValoTheme.BUTTON_PRIMARY);
        filter.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        filter.addClickListener(e->this.filter());
//        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
//        save.addClickListener(e -> this.save());
//        delete.addClickListener(e -> this.delete());
    }

    public void setParcelsEntity(ParcelsEntity parcelsEntity) {
        this.parcelsEntity = parcelsEntity;
        BeanFieldGroup.bindFieldsUnbuffered(parcelsEntity, this);

        // Show delete button for only customers already in the database
//        delete.setVisible(parcelsEntity.isPersisted());
        setVisible(true);
//        partName.selectAll();
    }

    private void delete() {
//        service.delete(parcelsEntity);
//        myUI.updateList();
//        setVisible(false);
    }

    private void save() {
//        service.save(parcelsEntity);
//        myUI.updateList();
//        setVisible(false);
    }

    private void filter() {

    }
}
