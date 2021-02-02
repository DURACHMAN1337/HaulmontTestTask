package org.dentech.VaadinViews.CreditOffer;

import com.vaadin.event.ShortcutAction;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.*;
import org.dentech.Services.BankService;
import org.dentech.Services.CreditOfferService;
import org.dentech.Services.CreditService;
import org.dentech.Services.PaymentScheduleService;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class CreditOfferWindow extends Window implements View {

    private VerticalLayout windowLayout = new VerticalLayout();
    private Button okButton = new Button("Выбрать");
    private Button backButton = new Button("Назад");
    private CreditService creditService;
    private CreditOfferService creditOfferService;
    private PaymentScheduleService paymentScheduleService;
    private BankService bankService;
    private long creditAmount;
    private int creditPeriod;
    private Client client;
    private Credit credit;
    private NativeSelect<Credit> creditSelect;

    public CreditOfferWindow(CreditService creditService, long creditAmount, int creditPeriod,
                             Client client, CreditOfferService creditOfferService,
                             PaymentScheduleService paymentScheduleService, BankService bankService) {
        this.creditService = creditService;
        this.creditAmount = creditAmount;
        this.creditPeriod = creditPeriod;
        this.client = client;
        this.creditOfferService = creditOfferService;
        this.paymentScheduleService = paymentScheduleService;
        this.bankService = bankService;

        setCaption("Наши предложения");
        center();
        setContent(createContent());
    }

    private Component createContent() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        okButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        backButton.setStyleName(ValoTheme.BUTTON_DANGER);
        buttonsLayout.addComponents(okButton, backButton);

        Label creditLabel = new Label("Выберете подходящее предложение по кредиту :");
        if (creditService.findCreditByAmount(creditAmount).size() == 0) {
            windowLayout.addComponents(new Label("К сожалению для вас нету подходящих предложений"));
        } else {
            List<Credit> credits = creditService.findCreditByAmount(creditAmount);
            Collections.sort(credits);
            creditSelect = new NativeSelect<>("Доступные вам кредиты", credits);
            windowLayout.addComponents(creditLabel, creditSelect, buttonsLayout);


            okButton.addClickListener(event -> this.confirmation(creditSelect.getValue(), client, creditAmount, creditPeriod));
            okButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
            okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            backButton.addClickListener(event -> getUI().removeWindow(CreditOfferWindow.this));


        }


        return windowLayout;
    }

    private void confirmation(Credit credit, Client client, Long creditAmount, int creditPeriod) {
        this.credit = credit;
        setCaption("Подтверждение Данных");
        windowLayout.removeAllComponents();
        center();
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        Label checkInfo = new Label("Проверьте Введенные данные");
        checkInfo.addStyleName(ValoTheme.LABEL_SUCCESS);
        header.addComponent(checkInfo);
        header.setComponentAlignment(checkInfo, Alignment.MIDDLE_CENTER);
        Button accept = new Button("Принять");
        Button cancel = new Button("Отмена");
        accept.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancel.addStyleName(ValoTheme.BUTTON_DANGER);
        HorizontalLayout buttons = new HorizontalLayout(accept, cancel);
        double firstPayment = (creditAmount * 0.7 / (creditPeriod * 12)) +
                ((creditAmount * 0.7 * (credit.getCreditPercent() / 100)) / (creditPeriod * 12));
        DecimalFormat df = new DecimalFormat("#.##");
        windowLayout.addComponents(
                header,
                new Label(client.toString()),
                new Label("\nСумма кредита: " + creditAmount + " рублей "),
                new Label("\nСрок кредита: " + creditPeriod + " лет"),
                new Label("\nПроцентная ставка: " + credit.getCreditPercent() + "% "),
                new Label("\nПервоначальный взнос: " +
                        df.format(creditAmount * 0.2) + " рублей"),
                new Label("\nПлатеж за первый месяц : " +
                        df.format(firstPayment) + " рублей "),
                new Label("\nФиксированный ежемесячный платеж : " +
                        df.format(creditAmount * 0.8 / (creditPeriod * 12))),
                buttons
        );
        accept.addClickListener(clickEvent -> this.saveCredit());
        cancel.addClickListener(clickEvent -> getUI().removeWindow(CreditOfferWindow.this));
        windowLayout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
    }

    private void saveCredit() {
        try {
            Bank bank = new Bank(client, credit);
            bankService.save(bank);

            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.valueOf(localDateTime.toLocalDate());
            double scale = Math.pow(10, 2);

            double ostatok = creditAmount * 0.8;
            double percent = credit.getCreditPercent();
            int period = creditPeriod * 12;
            double paymentBody = Math.ceil(ostatok / period * scale) / scale;

            for (int i = 0; i < creditPeriod * 12; i++) {
                double paymentPercent = Math.ceil(((ostatok * (percent / 100)) / period) * scale) / scale;
                double paymentPerMonth = Math.ceil((paymentBody + paymentPercent) * scale) / scale;
                if (paymentPercent < 0) paymentPercent = 0;
                PaymentSchedule schedule = new PaymentSchedule(date, paymentPerMonth, paymentBody, paymentPercent);
                ostatok -= paymentPerMonth;
                localDateTime = localDateTime.plusMonths(1);
                date = Date.valueOf(localDateTime.toLocalDate());
                paymentScheduleService.save(schedule);
                CreditOffer creditOffer = new CreditOffer(client, credit, creditAmount, schedule, bank.getId());
                creditOfferService.save(creditOffer);
            }
            Notification success = new Notification("Спасибо за то, что выбрали наш банк :)",
                    Notification.Type.HUMANIZED_MESSAGE);
            success.setDelayMsec(1500);
            success.show(getUI().getPage());
            getUI().removeWindow(CreditOfferWindow.this);
        } catch (Exception e) {
            Notification success = new Notification("Что то пошло не так, попробуйте снова :(",
                    Notification.Type.ERROR_MESSAGE);
            success.setDelayMsec(1500);
            success.show(getUI().getPage());
            e.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}