package org.example.astraqa.pages.planSelection;

import com.microsoft.playwright.Page;
import org.example.astraqa.pages.pageInterfaces.PlanPage;
import org.example.astraqa.pages.cart.CheckoutDomainNamePage;

public class VPSHostingPlanPage extends PlanPage {
    public VPSHostingPlanPage(Page page) {
        super(page);
    }

    @Override
    public void selectTerm(String term) {}

    @Override
    public CheckoutDomainNamePage selectOption(String planType) {return new CheckoutDomainNamePage(page);}
}
