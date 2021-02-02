package org.dentech.VaadinViews.Client;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.Client;
import org.dentech.Services.ClientService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientWindow extends Window implements View {

    private final TextField firstname = new TextField("Имя");
    private final TextField surname = new TextField("Фамилия");
    private final TextField patronymic = new TextField("Отчество");
    private final TextField phoneNumber = new TextField("Номер Телефона +7(...)");
    private final TextField email = new TextField("E-mail Example@example.com");
    private final TextField passport = new TextField("Паспорт");

    private final Button save = new Button("Сохранить", VaadinIcons.CLOUD);
    private final Button cancel = new Button("Отмена");

    private final ClientService clientService;
    private final Client client;

    public ClientWindow(ClientService clientService, Client client) {
        this.clientService = clientService;
        this.client = client;
        setCaption("Добавление Клиента");
        setModal(true);
        center();
        setContent(createContent());
    }

    public Component createContent() {
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        buttonsLayout.addComponents(save, cancel);
        row1.addComponents(firstname, surname, patronymic);
        row2.addComponents(passport, phoneNumber, email);
        main.addComponents(row1, row2, buttonsLayout);
        try {
            firstname.setValue(client.getFirstname());
            surname.setValue(client.getSurname());
            patronymic.setValue(client.getPatronymic());
            phoneNumber.setValue(String.valueOf(client.getPhoneNumber()));
            email.setValue(client.getEmail());
            passport.setValue(String.valueOf(client.getPassport()));

        } catch (Exception ignored) {
        }

        firstname.setRequiredIndicatorVisible(true);
        surname.setRequiredIndicatorVisible(true);
        phoneNumber.setRequiredIndicatorVisible(true);
        passport.setRequiredIndicatorVisible(true);

        cancel.addClickListener(event -> getUI().removeWindow(ClientWindow.this));
        save.addClickListener(event -> this.save());
        save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        return main;

    }

    private void save() {
        if (validateEmail(email.getValue()) && validatePhone(phoneNumber.getValue()) &&
                validateFIO(firstname.getValue(), surname.getValue(), patronymic.getValue())) {

                client.setFirstname(firstname.getValue().trim());
                client.setSurname(surname.getValue().trim());
                client.setPatronymic(patronymic.getValue().trim());
                client.setPassport(Long.parseLong(passport.getValue().trim()));
                client.setEmail(email.getValue().trim());
                client.setPhoneNumber(Long.parseLong(phoneNumber.getValue().trim()));
                clientService.save(client);
                Notification notification = new Notification("Клиент успешно добавлен",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(ClientWindow.this);
                ClientView.updateClientGrid(clientService);


        }else {
            new Notification("Ошибка! Проверьте что в вашем имени нету цифр, телефон введен верно и e-mail достоверный ",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }


    }


    public boolean validateEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    public boolean validatePhone(String phoneNumber) {
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    public boolean validateFIO(String firstname, String surname, String patronymic) {
        char[] firstnameArray = firstname.toCharArray();
        char[] surnameArray = surname.toCharArray();
        char[] patronymicArray = patronymic.toCharArray();

        for (Character ch : firstnameArray)
            if (Character.isDigit(ch)) return false;

        for (Character ch : surnameArray)
            if (Character.isDigit(ch)) return false;

        for (Character ch : patronymicArray)
            if (Character.isDigit(ch)) return false;

        return true;
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
