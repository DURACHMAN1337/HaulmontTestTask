package org.dentech.VaadinViews.Main;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.VaadinViews.MyUI;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.Serializable;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View, Serializable {

    @PostConstruct
    void init() {

        FileResource bankFileResource = new FileResource(new File("src/main/resources/images/bank-safe.png"));
        FileResource clientsFileResource = new FileResource(new File("src/main/resources/images/business-group.png"));
        FileResource creditFileResource = new FileResource(new File("src/main/resources/images/mastercard-credit-card.png"));
        FileResource creditOfferFileResource = new FileResource(new File("src/main/resources/images/create-order.png"));


        HorizontalLayout headerLayout = new HorizontalLayout();
        HorizontalLayout row1Layout = new HorizontalLayout();
        HorizontalLayout row2Layout = new HorizontalLayout();
        VerticalLayout bankLayout = new VerticalLayout();
        VerticalLayout creditLayout = new VerticalLayout();
        VerticalLayout clientsLayout = new VerticalLayout();
        VerticalLayout creditOfferLayout = new VerticalLayout();

        Image bank = new Image("",bankFileResource);
        Image clients = new Image("",clientsFileResource);
        Image credit = new Image("",creditFileResource);
        Image creditOffer = new Image("",creditOfferFileResource);
        Label headerLabel = new Label("DENI$ BANK");

        bank.setWidth("10%");
        clients.setWidth("10%");
        credit.setWidth("10%");
        creditOffer.setWidth("10%");

        Label bankLabel = new Label("Наш Банк");
        bankLabel.addStyleName(ValoTheme.LABEL_BOLD);
        bankLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        Label creditLabel = new Label("Кредитные Ставки");
        creditLabel.addStyleName(ValoTheme.LABEL_BOLD);
        creditLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        Label clientsLabel = new Label("Наши Клиенты");
        clientsLabel.addStyleName(ValoTheme.LABEL_BOLD);
        clientsLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        Label creditOfferLabel = new Label("Оформление Кредита");
        creditOfferLabel.addStyleName(ValoTheme.LABEL_BOLD);
        creditOfferLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        headerLayout.addComponent(headerLabel);
        headerLayout.setComponentAlignment(headerLabel, Alignment.MIDDLE_CENTER);
        headerLabel.addStyleName(ValoTheme.LABEL_H1);
        headerLabel.addStyleName(ValoTheme.LABEL_SPINNER);
        headerLabel.setWidth("50%");

        bankLayout.addComponents(bank,bankLabel);
        bankLayout.setComponentAlignment(bankLabel, Alignment.TOP_CENTER);
        bankLayout.setComponentAlignment(bank, Alignment.TOP_CENTER);
        bankLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("Bank");

        });
        bankLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        creditLayout.addComponents(credit, creditLabel);
        creditLayout.setComponentAlignment(creditLabel, Alignment.TOP_CENTER);
        creditLayout.setComponentAlignment(credit, Alignment.TOP_CENTER);
        creditLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("Credits");

        });
        creditLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        clientsLayout.addComponents(clients, clientsLabel);
        clientsLayout.setComponentAlignment(clientsLabel, Alignment.TOP_CENTER);
        clientsLayout.setComponentAlignment(clients, Alignment.TOP_CENTER);
        clientsLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("Clients");
            MyUI.buttonsLayout.setVisible(true);
        });
        clientsLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        creditOfferLayout.addComponents(creditOffer, creditOfferLabel);
        creditOfferLayout.setComponentAlignment(creditOfferLabel, Alignment.TOP_CENTER);
        creditOfferLayout.setComponentAlignment(creditOffer, Alignment.TOP_CENTER);
        creditOfferLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("CreditOffer");

        });
        creditOfferLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        row1Layout.setWidth("100%");
        row2Layout.setWidth("100%");

        row1Layout.addComponents(clientsLayout,creditLayout);
        row2Layout.addComponents(bankLayout,creditOfferLayout);
        addComponents(headerLayout,row1Layout,row2Layout);


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
