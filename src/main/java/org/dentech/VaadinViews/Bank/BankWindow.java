package org.dentech.VaadinViews.Bank;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.Bank;
import org.dentech.Entities.Client;
import org.dentech.Entities.Credit;
import org.dentech.Services.BankService;
import org.dentech.Services.ClientService;
import org.dentech.Services.CreditService;

import java.util.List;


public class BankWindow extends Window implements View {

    private  Button saveButton = new Button("Сохранить", VaadinIcons.CHECK);
    private  Button cancelButton = new Button("Отмена");

    private  ClientService clientService;
    private  CreditService creditService;
    private  BankService bankService;

    private  Bank bank;

    private NativeSelect<Client> clientSelect;
    private NativeSelect<Credit> creditSelect;

    public BankWindow(BankService bankService, Bank bank, ClientService clientService, CreditService creditService) {
        this.bankService = bankService;
        this.bank = bank;
        this.clientService = clientService;
        this.creditService = creditService;
        setCaption("Добавить новый кредит");

//        center();
        setModal(true);
        setContent(createContent());
    }

    private Component createContent(){
        List<Client> clients = clientService.getAll();
        List<Credit> credits = creditService.getAll();

        VerticalLayout mainLayout = new VerticalLayout();

        clientSelect = new NativeSelect<>(" Выберите Клиента", clients);
        clientSelect.setEmptySelectionAllowed(false);


        creditSelect = new NativeSelect<>(" Кредитная Ставка", credits);
        creditSelect.setEmptySelectionAllowed(false);


        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);

        mainLayout.addComponents(clientSelect, creditSelect, buttonsLayout);

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveButton.addClickListener(event -> this.save());

        cancelButton.addClickListener(event -> getUI().removeWindow(BankWindow.this));

        return mainLayout;
    }

    private void save() {
        try {
            bank.setClient(clientSelect.getValue());
            bank.setBankCredit(creditSelect.getValue());
            bankService.save(bank);

            Notification notification = new Notification("Успешно! Новый кредит оформлен",
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.setDelayMsec(1500);
            notification.show(getUI().getPage());
            getUI().removeWindow(BankWindow.this);

        } catch (Exception e) {
            new Notification("Ошибка! Что-то пошло не так :(",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
        BankView.updateBankGrid(bankService);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
