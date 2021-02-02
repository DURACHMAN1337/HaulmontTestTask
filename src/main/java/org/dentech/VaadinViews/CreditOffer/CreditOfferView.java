package org.dentech.VaadinViews.CreditOffer;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.Client;
import org.dentech.Entities.Credit;
import org.dentech.Services.*;
import org.dentech.VaadinViews.Client.ClientWindow;
import org.dentech.VaadinViews.MyUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringView(name = "CreditOffer")
public class CreditOfferView extends VerticalLayout implements View {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private PaymentScheduleService scheduleService;
    @Autowired
    CreditOfferService creditOfferService;
    @Autowired
    BankService bankService;


    HorizontalLayout headerLayout = new HorizontalLayout();
    VerticalLayout mainLayout = new VerticalLayout();

    static NativeSelect<Client> clientSelect;
    private NativeSelect<Credit> creditSelect;
    private Button addNewButton;
    private Button makeCredit;
    private final NativeSelect<Integer> creditPeriodSelect = new NativeSelect<>
            ("На срок", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    private final TextField creditAmount = new TextField("Я хочу");

    @PostConstruct
    void init() {
        MyUI.setStyleForButton(3);
        Page.getCurrent().setTitle("CreditOffer");
        Label header = new Label("$$$ Оформление кредита $$$");
        header.addStyleName(ValoTheme.LABEL_H1);
        headerLayout.setWidth("100%");
        headerLayout.addComponent(header);
        headerLayout.setComponentAlignment(header, Alignment.TOP_CENTER);
        headerLayout.addStyleName(ValoTheme.LAYOUT_CARD);

        clientSelect = new NativeSelect<>("Выберите Существующего клиента", clientService.getAll());
        clientSelect.setRequiredIndicatorVisible(true);

        addNewButton = new Button("Новый Клиент");

        addNewButton.addClickListener(clickEvent -> {
            Client client = new Client();
            ClientWindow clientWindow = new ClientWindow(clientService, client);
            getUI().addWindow(clientWindow);
        });

        HorizontalLayout limitAndDate = new HorizontalLayout();
        limitAndDate.addComponents(creditAmount, new Label("руб."), creditPeriodSelect, new Label("лет"));
        creditAmount.setRequiredIndicatorVisible(true);
        creditAmount.setPlaceholder("100 000");
        creditPeriodSelect.setSelectedItem(1);
        creditPeriodSelect.setRequiredIndicatorVisible(true);


        makeCredit = new Button("Подобрать Наши Предложения");
        makeCredit.setStyleName(ValoTheme.BUTTON_HUGE);
        makeCredit.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        makeCredit.addClickListener(clickEvent -> {
            try {
                CreditOfferWindow creditOfferForm = new CreditOfferWindow(creditService,
                        Long.parseLong(creditAmount.getValue()), creditPeriodSelect.getValue(),
                        clientSelect.getValue(), creditOfferService, scheduleService, bankService);
                getUI().addWindow(creditOfferForm);
            } catch (Exception e) {
                Notification error = new Notification("Проверьте корректность введеных данных");
                error.setDelayMsec(1500);
                error.show(getUI().getPage());
            }
        });


        VerticalLayout buttonAndSelect = new VerticalLayout();
        buttonAndSelect.addComponents(clientSelect, addNewButton, limitAndDate, makeCredit);
        buttonAndSelect.setComponentAlignment(makeCredit, Alignment.MIDDLE_CENTER);


        mainLayout.addComponents(buttonAndSelect);
        addComponents(headerLayout, mainLayout);

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
