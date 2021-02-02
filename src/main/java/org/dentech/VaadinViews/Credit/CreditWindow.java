package org.dentech.VaadinViews.Credit;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entities.Credit;
import org.dentech.Services.CreditService;

public class CreditWindow extends Window implements View {

    private final TextField creditLimitField = new TextField("Лимит по кредиту");
    private final TextField creditPercentField = new TextField("Процент по кредиту");
    private final Button save = new Button("Сохранить", VaadinIcons.CHECK);
    private final Button cancel = new Button("Отмена");

    private final CreditService creditService;
    private final Credit credit;

    public CreditWindow(CreditService creditService, Credit credit) {
        this.creditService = creditService;
        this.credit = credit;
        setCaption("Заполните данные по кредиту");

        setModal(true);
        center();
        setContent(createContent());
    }

    public Component createContent() {
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(save, cancel);
        HorizontalLayout textLayout = new HorizontalLayout();
        textLayout.addComponents(creditLimitField, creditPercentField);
        main.addComponents(textLayout, buttonsLayout);

        try {
            creditLimitField.setValue(credit.getCreditLimit().toString());
            creditPercentField.setValue(credit.getCreditPercent().toString());
        } catch (Exception ignored) {
        }

        save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        creditLimitField.setRequiredIndicatorVisible(true);
        creditPercentField.setRequiredIndicatorVisible(true);

        save.addClickListener(event -> this.save());
        cancel.addClickListener(event -> getUI().removeWindow(CreditWindow.this));
        return main;
    }

    private void save() {
        try {
            if (creditService.getCredit(Long.parseLong(creditLimitField.getValue()),
                    Double.parseDouble(creditPercentField.getValue())).isEmpty()) {
                credit.setCreditLimit(Long.parseLong(creditLimitField.getValue().trim()));
                credit.setCreditPercent(Double.parseDouble(creditPercentField.getValue().trim()));
                creditService.save(credit);
                Notification notification = new Notification("Кредит успешно добавлен!",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(CreditWindow.this);
            } else {
                Notification notification = new Notification("Ошибка! Кредитные предложения должны быть уникальны",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
            }
        } catch (Exception e) {
            new Notification("Ошибка! Проверьте правильность набора данных",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
        CreditView.updateCreditGrid(creditService);
    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
