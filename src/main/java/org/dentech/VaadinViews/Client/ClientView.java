package org.dentech.VaadinViews.Client;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.Client;
import org.dentech.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Clients")
public class ClientView extends VerticalLayout implements View {

    @Autowired
    ClientService clientService;

    public static Grid<Client> clientGrid = new Grid<>(Client.class);


    @PostConstruct
    void init() {

        Button addButton = new Button("Добавить Клиента");
        Button editButton = new Button("Изменить Данные");
        Button deleteButton = new Button("Удалить Клиента");

        Page.getCurrent().setTitle("Clients");
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteButton.setIcon(VaadinIcons.MINUS);

        editButton.setEnabled(false);
        editButton.setIcon(VaadinIcons.PENCIL);

        addButton.setIcon(VaadinIcons.PLUS);
        addButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, editButton, deleteButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        buttonsLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);

        addComponent(createGridSearchFilterContent());
        addComponent(buttonsLayout);

        clientGrid.setSizeFull();
        clientGrid.setColumns("surname", "firstname", "patronymic", "passport", "phoneNumber","email");
        clientGrid.setItems(clientService.getAll());

        addComponent(clientGrid);

        clientGrid.addSelectionListener(valueChangeEvent -> {
            if (!clientGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Client client = new Client();
            ClientWindow clientWindow = new ClientWindow(clientService, client);
            getUI().addWindow(clientWindow);
        });

        editButton.addClickListener(e -> {
            Client client = clientGrid.asSingleSelect().getValue();
            ClientWindow clientWindow = new ClientWindow(clientService, client);
            getUI().addWindow(clientWindow);
        });

        deleteButton.addClickListener(e -> {
            Client client = clientGrid.asSingleSelect().getValue();
            try {
                clientService.delete(client);
                updateClientGrid(clientService);
                Notification notification = new Notification(client.toString() + " был успешно удален",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification("Что-то пошло не так :С",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
            }
        });
    }

    private HorizontalLayout createGridSearchFilterContent() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        Label label = new Label("Поиск: ");
        label.setSizeFull();
        HorizontalLayout labelLayout = new HorizontalLayout();
        labelLayout.setWidth("100%");
        labelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        labelLayout.addComponents(label);

        TextField firstnameField = new TextField();
        firstnameField.setPlaceholder("Имя");
        TextField surnameField = new TextField();
        surnameField.setPlaceholder("Фамилия");
        TextField patronymicField = new TextField();
        patronymicField.setPlaceholder("Отчество");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPlaceholder("Номер телефона");
        TextField emailField = new TextField();
        emailField.setPlaceholder("E-mail");
        TextField passportField = new TextField();
        passportField.setPlaceholder("Паспорт");


        HorizontalLayout filters = new HorizontalLayout(labelLayout, firstnameField,surnameField,patronymicField,
                passportField,phoneNumberField,emailField);

        firstnameField.addValueChangeListener(this::firstnameFilter);
        surnameField.addValueChangeListener(this::surnameFilter);
        patronymicField.addValueChangeListener(this::patronymicFilter);
        phoneNumberField.addValueChangeListener(this::phoneFilter);
        emailField.addValueChangeListener(this::emailFilter);
        passportField.addValueChangeListener(this::passportFilter);

        mainLayout.addComponents(filters);
        mainLayout.setWidth("100%");
        return mainLayout;
    }

    private void firstnameFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent){
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getFirstname, firstname ->
                firstname.toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void surnameFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent) {
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getSurname, surname ->
                surname.toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void patronymicFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent){
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getPatronymic,patronymic ->
                patronymic.toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void phoneFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent){
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getPhoneNumber,phoneNumber ->
                phoneNumber.toString().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void emailFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent){
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getEmail,email ->
                email.toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void passportFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent) {
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getPassport, passport ->
                passport.toString().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    static void updateClientGrid(ClientService clientService) {
        clientGrid.setItems(clientService.getAll());
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
