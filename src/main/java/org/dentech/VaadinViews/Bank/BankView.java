package org.dentech.VaadinViews.Bank;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.Bank;
import org.dentech.Entities.CreditOffer;
import org.dentech.Services.*;
import org.dentech.VaadinViews.MyUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = "Bank")
public class BankView extends VerticalLayout implements View {

    @Autowired
    PaymentScheduleService paymentScheduleService;
    @Autowired
    private BankService bankService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private CreditOfferService creditOfferService;

    public static Grid<Bank> bankGrid = new Grid<>(Bank.class);
    public static long bank_id;
    private final Button addButton = new Button("Добавить");
    private final Button deleteButton = new Button("Удалить");
    private final Button detailsButton = new Button("Детали кредита");

    @PostConstruct
    void init() {
        bank_id = 0;
        MyUI.setStyleForButton(1);
        Page.getCurrent().setTitle("Den Bank");
        deleteButton.setEnabled(false);
        detailsButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        addButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, detailsButton, deleteButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        buttonsLayout.setComponentAlignment(detailsButton, Alignment.TOP_CENTER);
        addComponent(buttonsLayout);

        bankGrid.setSizeFull();
        bankGrid.setColumns("client", "bankCredit");
        bankGrid.setItems(bankService.getAll());

        addComponent(bankGrid);

        bankGrid.addSelectionListener(valueChangeEvent -> {
            if (!bankGrid.asSingleSelect().isEmpty()) {
                deleteButton.setEnabled(true);
                detailsButton.setEnabled(true);
            } else {
                deleteButton.setEnabled(false);
                detailsButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Bank bank = new Bank();
            BankWindow bankWindow = new BankWindow(bankService, bank, clientService, creditService);
            getUI().addWindow(bankWindow);
        });

        deleteButton.addClickListener(e -> {
            Bank bank = bankGrid.asSingleSelect().getValue();
            bank_id = bankGrid.asSingleSelect().getValue().getId();
            try {
                List<CreditOffer> creditOffers = creditOfferService.findAllOffersForClient(BankView.bank_id);
                List<Long> idsOfPaymentSchedules = new ArrayList<>();

                for (CreditOffer creditOffer : creditOffers)
                    idsOfPaymentSchedules.add(creditOffer.getPaymentSchedule().getId());

                creditOfferService.deleteAllOffersForClient(BankView.bank_id);

                for (Long l : idsOfPaymentSchedules)
                    paymentScheduleService.deleteById(l);

                bankService.delete(bank);
                updateBankGrid(bankService);
                Notification notification = new Notification(bank.toString() + " был успешно удален",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification("Ошибка! Попробуйте еще раз позже",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
                deleteException.printStackTrace();
            }
        });

        detailsButton.addClickListener(e -> {
            bank_id = bankGrid.asSingleSelect().getValue().getId();
            getUI().getNavigator().navigateTo("BankPayments");
        });


        updateBankGrid(bankService);


    }


    static void updateBankGrid(BankService bankService) {
        bankGrid.setItems(bankService.getAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


    }
}
