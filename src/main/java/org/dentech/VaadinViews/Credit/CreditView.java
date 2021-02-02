package org.dentech.VaadinViews.Credit;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.Credit;
import org.dentech.Services.CreditService;
import org.dentech.VaadinViews.MyUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Credits")
public class CreditView extends VerticalLayout implements View {

    @Autowired
    CreditService creditService;

    public static Grid<Credit> creditGrid = new Grid<>(Credit.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");

    @PostConstruct
    void init(){
        MyUI.setStyleForButton(3);
        Page.getCurrent().setTitle("Credits");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteButton.setIcon(VaadinIcons.MINUS);
        editButton.setIcon(VaadinIcons.PENCIL);
        addButton.setIcon(VaadinIcons.PLUS);
        addButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, editButton, deleteButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        buttonsLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        addComponent(buttonsLayout);

        creditGrid.setSizeFull();
        creditGrid.setColumns("creditLimit","creditPercent");
        creditGrid.setItems(creditService.getAll());

        addComponent(creditGrid);

        creditGrid.addSelectionListener(valueChangeEvent -> {
            if (!creditGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Credit credit = new Credit();
            CreditWindow creditWindow = new CreditWindow(creditService, credit);
            getUI().addWindow(creditWindow);
        });

        editButton.addClickListener(e -> {
            Credit credit = creditGrid.asSingleSelect().getValue();
            CreditWindow creditWindow = new CreditWindow(creditService, credit);
            getUI().addWindow(creditWindow);
        });

        deleteButton.addClickListener(e -> {
            Credit credit = creditGrid.asSingleSelect().getValue();
            try {
                creditService.delete(credit);
                updateCreditGrid(creditService);
                Notification notification = new Notification( "Кредитное предложение успешно удалено",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification("Ошибка! Что-то пошло не так :( Попробуйте позже",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
            }
        });


    }

    static void updateCreditGrid(CreditService creditService) {

        creditGrid.setItems(creditService.getAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
