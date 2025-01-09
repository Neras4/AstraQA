package org.example.astraqa.utils.factories;

import com.microsoft.playwright.Page;
import org.example.astraqa.pages.pageInterfaces.PlanPage;
import org.example.astraqa.pages.planSelection.DedicatedHostingServicesPage;
import org.example.astraqa.pages.planSelection.VPSHostingPlanPage;

public class PlanFactory {
    private final Page page;

    public PlanFactory(Page page) {
        this.page = page;
    }

    public PlanPage getPlanPage(String productName) {
        return switch (productName) {
            case "VPS Hosting" -> new VPSHostingPlanPage(page);
            case "Dedicated Hosting" -> new DedicatedHostingServicesPage(page);
            default -> throw new IllegalArgumentException("Invalid product name: " + productName);
        };
    }
}
