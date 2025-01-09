package org.example.astraqa.pages.pageInterfaces;

import com.microsoft.playwright.Page;

public abstract class PlanPage {
    protected final Page page;

    public PlanPage(Page page) {
        this.page = page;
    }

    public abstract void selectTerm(String term);

    public abstract Object selectOption(String optionName);
}
