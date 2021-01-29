package org.dentech.VaadinViews;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;
@SpringUI
@SpringViewDisplay
@Theme("valo")
public class MyUI  extends UI implements ViewDisplay, Serializable {

    final VerticalLayout root = new VerticalLayout();
    public static HorizontalLayout buttonsLayout;
    private Panel springDisplay;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root.setSizeFull();
        setContent(root);

        buttonsLayout = new HorizontalLayout();

        Button buttonUser = new Button("Users");
        buttonUser.addStyleName(ValoTheme.BUTTON_SMALL);
        buttonUser.addClickListener(event -> getUI().getNavigator().navigateTo("Users"));

        buttonsLayout.addComponent(buttonUser);

        springDisplay = new Panel();
        springDisplay.setSizeFull();

        root.addComponent(springDisplay);
        root.setExpandRatio(springDisplay,1.0f);


    }

    @Override
    public void showView(View view) {
        springDisplay.setContent((Component) view);
    }
}
