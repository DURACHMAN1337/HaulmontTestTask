package org.dentech.VaadinViews.Bank;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.dentech.Entities.CreditOffer;
import org.dentech.Services.CreditOfferService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "BankPayments")
public class BankPayments extends VerticalLayout implements View {

    @Autowired
    CreditOfferService creditOfferService;

    @PostConstruct
    protected void init() {
        Grid<CreditOffer> grid = new Grid<>(CreditOffer.class);
        grid.setColumns("client", "credit", "creditAmount", "paymentSchedule");
        grid.setItems(creditOfferService.findAllOffersForClient(BankView.bank_id));
        grid.setSizeFull();
        addComponent(grid);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
